package com.hive.salesystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wyx20
 * @version 1.0
 * @title Customer
 * @description
 * @create 2023/12/20 22:56
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @TableId
    private Integer customerId;
    private String name;
    private String phone;

    private Integer order;
    private Double price;
}
