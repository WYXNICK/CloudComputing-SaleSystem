package com.hive.salesystem.service;

import com.hive.salesystem.entity.Customer;
import com.hive.salesystem.entity.Orders;
import com.hive.salesystem.entity.Product;
import com.hive.salesystem.mapper.OrdersMapper;
import com.hive.salesystem.mapper.ProductMapper;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author wyx20
 * @version 1.0
 * @title ProductServiceImpl
 * @description
 * @create 2023/12/21 20:34
 */
@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    ProductMapper productMapper;

    @Autowired
    OrdersMapper ordersMapper;
    @Override
    public int createNewProduct(Product product) {
        return productMapper.createNewProduct(product);
    }

    @Override
    public int getProductsNum(){
        return productMapper.getProductsNum();
    }

    @Override
    public HashMap<String,Object> getAllProducts(int pageNum, int pageSize,String type,String keyword,String sequence) {
        HashMap<String,Object> result=new HashMap<>();
        int start=(pageNum-1)*pageSize;
        List<Product> productList=productMapper.getTotal(type,keyword);
        int totalNum=productList.size();
//        List<Product> productList = productMapper.getAllProducts(start,pageSize,type,keyword);

        // 使用 Comparator 来排序 productList
        if(sequence.equals("default"))
            Collections.sort(productList, Comparator.comparingInt(Product::getProductId));
        else if(sequence.equals("price"))
            Collections.sort(productList, Comparator.comparing(Product::getPrice).reversed());
        else if(sequence.equals("number")){
            List<Orders> ordersList=ordersMapper.getTotal("", "");
            // 使用 Map 进行订单按 product_id 分组，并计算总订单金额
            HashMap<Integer, Integer> productTotalOrderNum = new HashMap<>();
            for (Orders order : ordersList) {
                // 如果 Map 中已存在该客户，累加订单金额；否则，添加新的客户记录
                productTotalOrderNum.merge(order.getProductId(), order.getQuantity(), Integer::sum);
            }
            for(Product product : productList){
                product.setNumber(productTotalOrderNum.getOrDefault(product.getProductId(), 0));
            }
            Collections.sort(productList, Comparator.comparingInt(Product::getNumber).reversed());
        }

        int endIndex = Math.min(start + pageSize, productList.size());
        result.put("status",0);
        result.put("data",productList.subList(start, endIndex));
        result.put("pageNum",totalNum);
        return result;
    }



}
