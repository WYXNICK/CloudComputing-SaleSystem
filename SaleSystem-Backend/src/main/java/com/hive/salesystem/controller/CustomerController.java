package com.hive.salesystem.controller;

import com.hive.salesystem.entity.Customer;
import com.hive.salesystem.entity.Product;
import com.hive.salesystem.service.CustomerService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * @author wyx20
 * @version 1.0
 * @title CustomerController
 * @description
 * @create 2023/12/22 15:45
 */
@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @GetMapping("/getAll")
    @ApiOperation(notes = "分为三部分data,pageNum(总条数)和status", value = "分页获取全部顾客信息")
    public HashMap<String,Object> getAllCustomers(@RequestParam int pageIndex, @RequestParam int pageSize,@RequestParam String type,@RequestParam String keyword,@RequestParam String sequence){
        return customerService.getAllCustomers(pageIndex,pageSize,type,keyword,sequence);
    }
}
