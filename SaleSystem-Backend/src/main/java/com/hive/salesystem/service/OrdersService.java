package com.hive.salesystem.service;

import com.hive.salesystem.entity.Orders;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @author wyx20
 * @version 1.0
 * @title OrderService
 * @description
 * @create 2023/12/21 20:27
 */

public interface OrdersService {
    int getTotalOrderCount();

    HashMap<String,Object> getAllOrders(int pageNum, int pageSize, String type, String keyword, String sequence);
}
