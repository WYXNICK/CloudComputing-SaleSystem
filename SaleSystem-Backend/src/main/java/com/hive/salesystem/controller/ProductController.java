package com.hive.salesystem.controller;

import com.hive.salesystem.entity.Product;
import com.hive.salesystem.service.ProductService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * @author wyx20
 * @version 1.0
 * @title ProductController
 * @description
 * @create 2023/12/21 20:35
 */
@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    ProductService productService;

    @PostMapping("/createNewProduct")
    @ApiOperation(notes = "返回的数字为1则成功插入", value = "新建一个商品")
    public int createNewProduct(@RequestBody Product product){
        product.setProductId(productService.getProductsNum()+1);
        return productService.createNewProduct(product);
    }

    @GetMapping("/getAll")
    @ApiOperation(notes = "分为三部分data,pageNum(总条数)和status", value = "分页获取全部商品信息")
    public HashMap<String,Object> getAllProducts(@RequestParam int pageIndex,@RequestParam int pageSize,@RequestParam String type,@RequestParam String keyword,@RequestParam String sequence){
        return productService.getAllProducts(pageIndex,pageSize,type,keyword,sequence);
    }


}
