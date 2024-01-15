package com.hive.salesystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hive.salesystem.entity.Customer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * @author wyx20
 * @version 1.0
 * @title CustomerMapper
 * @description
 * @create 2023/12/22 15:52
 */
@Mapper
@Repository
public interface CustomerMapper extends BaseMapper<Customer> {

//    @Select("select customer_id,name,phone from customer " +
//            "WHERE " +
//            "   (#{type}='customerId' AND customer_id LIKE CONCAT('%', #{keyword}, '%')) OR " +
//            "   (#{type}='name' AND name LIKE CONCAT('%', #{keyword}, '%')) OR " +
//            "   (#{type}='phone' AND phone LIKE CONCAT('%', #{keyword}, '%'))OR #{type}='' "+
//            "limit #{start},#{pageSize}")
//    List<Customer> getAllCustomers(int start, int pageSize,String type ,String keyword);

    @Select("SELECT customer_id,name,phone from customer " +
            "WHERE " +
            "   (#{type}='customerId' AND customer_id = #{keyword}) OR " +
            "   (#{type}='name' AND name LIKE CONCAT('%', #{keyword}, '%')) OR " +
            "   (#{type}='phone' AND phone LIKE CONCAT('%', #{keyword}, '%'))OR #{type}='' ")
    List<Customer> getTotal(String type, String keyword);
}
