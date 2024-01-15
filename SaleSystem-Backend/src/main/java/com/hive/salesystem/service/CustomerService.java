package com.hive.salesystem.service;

import com.hive.salesystem.entity.Customer;

import java.util.HashMap;
import java.util.List;

/**
 * @author wyx20
 * @version 1.0
 * @title CustomerService
 * @description
 * @create 2023/12/22 15:46
 */
public interface CustomerService {
    HashMap<String,Object> getAllCustomers(int pageNum, int pageSize, String type, String keyword, String sequence);
}
