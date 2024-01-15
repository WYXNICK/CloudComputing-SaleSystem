package com.hive.salesystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hive.salesystem.entity.Product;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wyx20
 * @version 1.0
 * @title ProductMappler
 * @description
 * @create 2023/12/22 12:33
 */
@Mapper
@Repository
public interface ProductMapper extends BaseMapper<Product> {

    @Insert("INSERT INTO product (product_id,product_name,price,description) VALUES (#{productId},#{productName},#{price},#{description})")
    int createNewProduct(Product product);

    @Select("SELECT count(*) from product")
    int getProductsNum();

    @Select("SELECT product_id, product_name, price, description from product " +
            "WHERE " +
            "   (#{type}='productId' AND product_id = #{keyword}) OR " +
            "   (#{type}='productName' AND product_name LIKE CONCAT('%', #{keyword}, '%')) OR " +
            "   (#{type}='price' AND price LIKE CONCAT('%', #{keyword}, '%')) OR " +
            "   (#{type}='description' AND description LIKE CONCAT('%', #{keyword}, '%')) OR #{keyword}='' ")
    List<Product> getTotal(String type,String keyword);

//    @Select("SELECT product_id, product_name, price, description " +
//            "FROM product " +
//            "WHERE " +
//            "   (#{type}='productId' AND product_id LIKE CONCAT('%', #{keyword}, '%')) OR " +
//            "   (#{type}='productName' AND product_name LIKE CONCAT('%', #{keyword}, '%')) OR " +
//            "   (#{type}='price' AND price LIKE CONCAT('%', #{keyword}, '%')) OR " +
//            "   (#{type}='description' AND description LIKE CONCAT('%', #{keyword}, '%')) OR #{type}='' " +
//            "LIMIT #{start}, #{pageSize}")
//    List<Product> getAllProducts(int start,int pageSize,String type,String keyword);
}
