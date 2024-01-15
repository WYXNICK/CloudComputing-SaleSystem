package com.hive.salesystem.service;

import com.hive.salesystem.entity.Customer;
import com.hive.salesystem.entity.Orders;
import com.hive.salesystem.entity.Product;
import com.hive.salesystem.mapper.CustomerMapper;
import com.hive.salesystem.mapper.OrdersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * @author wyx20
 * @version 1.0
 * @title CustomerServiceImpl
 * @description
 * @create 2023/12/22 15:46
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerMapper customerMapper;

    @Autowired
    OrdersMapper ordersMapper;

    @Override
    public HashMap<String,Object> getAllCustomers(int pageNum, int pageSize, String type, String keyword, String sequence) {
        HashMap<String,Object> result=new HashMap<>();
        result.put("status",0);
        int start=(pageNum-1)*pageSize;

        List<Customer> customerList=customerMapper.getTotal(type,keyword);
        int totalNum=customerList.size();
//        List<Orders> ordersList=ordersMapper.getTotal("", "");
//
//        // 使用 Map 进行订单按 customer_id 分组，并计算总订单数
//        HashMap<Integer, Integer> customerTotalOrderNum = new HashMap<>();
//        for (Orders order : ordersList) {
//            // 如果 Map 中已存在该客户，累加订单数；否则，添加新的客户记录
//            customerTotalOrderNum.merge(order.getCustomerId(), 1, Integer::sum);
//        }
//        for(Customer customer : customerList){
//            customer.setOrder(customerTotalOrderNum.getOrDefault(customer.getCustomerId(),0));
//        }
//        List<Orders> ordersList1=ordersMapper.getTotal("", "");
//        // 使用 Map 进行订单按 customer_id 分组，并计算总订单金额
//        HashMap<Integer, Double> customerTotalOrderAmount = new HashMap<>();
//        for (Orders order : ordersList1) {
//            // 如果 Map 中已存在该客户，累加订单金额；否则，添加新的客户记录
//            customerTotalOrderAmount.merge(order.getCustomerId(), order.getTotalPrice(), Double::sum);
//        }
//        for(Customer customer : customerList){
//            customer.setPrice(customerTotalOrderAmount.getOrDefault(customer.getCustomerId(), 0.00));
//        }

        // 使用 Comparator 来排序 productList
        if(sequence.equals("default")) {


            Collections.sort(customerList, Comparator.comparingInt(Customer::getCustomerId));
        }
        else if(sequence.equals("order")) {
            List<Orders> ordersList=ordersMapper.getTotal("", "");

            // 使用 Map 进行订单按 customer_id 分组，并计算总订单数
            HashMap<Integer, Integer> customerTotalOrderNum = new HashMap<>();
            for (Orders order : ordersList) {
                // 如果 Map 中已存在该客户，累加订单数；否则，添加新的客户记录
                customerTotalOrderNum.merge(order.getCustomerId(), 1, Integer::sum);
            }
            for(Customer customer : customerList){
                customer.setOrder(customerTotalOrderNum.getOrDefault(customer.getCustomerId(),0));
            }
            Collections.sort(customerList, Comparator.comparing(Customer::getOrder).reversed());
        }
        else if(sequence.equals("price")) {
            List<Orders> ordersList=ordersMapper.getTotal("", "");
            // 使用 Map 进行订单按 customer_id 分组，并计算总订单金额
            HashMap<Integer, Double> customerTotalOrderAmount = new HashMap<>();
            for (Orders order : ordersList) {
                // 如果 Map 中已存在该客户，累加订单金额；否则，添加新的客户记录
                customerTotalOrderAmount.merge(order.getCustomerId(), order.getTotalPrice(), Double::sum);
            }
            for(Customer customer : customerList){
                customer.setPrice(customerTotalOrderAmount.getOrDefault(customer.getCustomerId(), 0.00));
            }
            Collections.sort(customerList, Comparator.comparingDouble(Customer::getPrice).reversed());
        }

        int endIndex = Math.min(start + pageSize, customerList.size());
        result.put("pageNum",totalNum);
        result.put("data",customerList.subList(start, endIndex));
        return result;
    }
}
