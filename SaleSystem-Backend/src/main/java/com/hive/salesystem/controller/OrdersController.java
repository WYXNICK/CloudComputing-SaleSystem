package com.hive.salesystem.controller;

import com.hive.salesystem.entity.Orders;
import com.hive.salesystem.mapper.OrdersMapper;
//import io.swagger.annotations.ApiOperation;
import com.hive.salesystem.service.OrdersService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * @author wyx20
 * @version 1.0
 * @title OrdersController
 * @description
 * @create 2023/12/19 21:53
 */

@RestController
@RequestMapping("/orders")
public class OrdersController {
    @Autowired
    private OrdersService ordersService;

    @GetMapping("/count")
    @ApiOperation(notes = "返回int类型数值，表示订单数", value = "获取销售系统订单总数")
    public int findTotalUserCount(){
        return ordersService.getTotalOrderCount();
    }

    @GetMapping("/getAll")
    @ApiOperation(notes = "返回int类型数值，表示订单数", value = "获取销售系统订单总数")
    public HashMap<String,Object> getAllOrders(int pageIndex,int pageSize,String type,String keyword,String sequence){
        return ordersService.getAllOrders(pageIndex,pageSize,type,keyword,sequence);
    }

//    @PostMapping("/createNewOrder")
//    @ApiOperation(notes = "返回int类型数值，表示订单数", value = "新建订单")
}
