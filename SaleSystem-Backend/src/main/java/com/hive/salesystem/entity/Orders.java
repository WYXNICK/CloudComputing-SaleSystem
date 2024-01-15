package com.hive.salesystem.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wyx20
 * @version 1.0
 * @title orders
 * @description
 * @create 2023/12/19 21:44
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Orders {
    @TableId
    private Integer orderId;
    private Integer customerId;
    private String orderTime;
    private double totalPrice;
    private Integer productId;
    private Integer quantity;
}
