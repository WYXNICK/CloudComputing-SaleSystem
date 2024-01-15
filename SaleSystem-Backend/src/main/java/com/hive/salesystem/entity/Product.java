package com.hive.salesystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wyx20
 * @version 1.0
 * @title Product
 * @description
 * @create 2023/12/20 23:16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @TableId
    private Integer productId;
    private String productName;
    private double price;
    private String description;

    private Integer number;
}
