package com.hive.salesystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hive.salesystem.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import javax.jdo.annotations.Order;
import java.util.Collection;
import java.util.List;

/**
 * @author wyx20
 * @version 1.0
 * @title OrdersMapper
 * @description
 * @create 2023/12/19 21:51
 */

@Mapper
@Repository
public interface OrdersMapper extends BaseMapper<Orders> {
    @Select("select count(*) from orders")
    public int getTotalOrderCount();

//    @Select("select order_id,customer_id, order_time, total_price, product_id,quantity from orders "+
//            "WHERE " +
//            "   (#{type}='customerId' AND customer_id LIKE CONCAT('%', #{keyword}, '%')) OR " +
//            "   (#{type}='time' AND order_time LIKE CONCAT('%', #{keyword}, '%')) OR " +
//            "   (#{type}='productId' AND product_id LIKE CONCAT('%', #{keyword}, '%')) OR " +
//            "   (#{type}='number' AND quantity LIKE CONCAT('%', #{keyword}, '%')) OR " +
//            "   (#{type}='price' AND total_price LIKE CONCAT('%', #{keyword}, '%'))OR #{keyword}='' "+
//     "limit #{start},#{pageSize}")
//    List<Orders> getAllOrders(int start, int pageSize,String type,String keyword);

    @Select("select order_id,customer_id, order_time, total_price, product_id,quantity from orders where customer_id=#{customerId}")
    List<Orders> getByCustomer(Integer customerId);

    @Select("SELECT order_id,customer_id, order_time, total_price, product_id,quantity from orders " +
            "WHERE " +
            "   (#{type}='customerId' AND customer_id =#{keyword}) OR " +
            "   (#{type}='time' AND order_time =#{keyword}) OR " +
            "   (#{type}='productId' AND product_id LIKE CONCAT('%', #{keyword}, '%')) OR " +
            "   (#{type}='number' AND quantity = #{keyword})OR " +
            "   (#{type}='price' AND total_price LIKE CONCAT('%', #{keyword}, '%'))OR #{keyword}='' ")
    List<Orders> getTotal(String type, String keyword);
}
