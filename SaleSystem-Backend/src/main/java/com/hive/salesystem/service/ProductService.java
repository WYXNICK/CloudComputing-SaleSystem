package com.hive.salesystem.service;

import com.hive.salesystem.entity.Product;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @author wyx20
 * @version 1.0
 * @title ProductService
 * @description
 * @create 2023/12/21 20:33
 */
public interface ProductService {

    int createNewProduct(Product product);
    int getProductsNum();

    HashMap<String,Object> getAllProducts(int pageNum, int pageSize,String type,String keyword,String sequence);



}
