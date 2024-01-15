package com.hive.salesystem.service;

import com.hive.salesystem.entity.Customer;
import com.hive.salesystem.entity.Orders;
import com.hive.salesystem.mapper.OrdersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * @author wyx20
 * @version 1.0
 * @title OrderServiceImpl
 * @description
 * @create 2023/12/21 20:28
 */
@Service
public class OrdersServiceImpl implements OrdersService {
    @Autowired
    OrdersMapper ordersMapper;
    @Override
    public int getTotalOrderCount() {
        return ordersMapper.getTotalOrderCount();
    }

    @Override
    public HashMap<String,Object> getAllOrders(int pageNum, int pageSize, String type, String keyword, String sequence) {
        HashMap<String,Object> result=new HashMap<>();
        result.put("status",0);
        if(type.equals("time")&&keyword!=null) {
            keyword = keyword.replace("T16:00:00.000Z", "");
            keyword = LocalDate.parse(keyword)
                    .plusDays(1)
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
        int start=(pageNum-1)*pageSize;
        List<Orders> ordersList = ordersMapper.getTotal(type,keyword);
        int totalNum=ordersList.size();
        //List<Orders> ordersList = ordersMapper.getAllOrders(start,pageSize,type,keyword);


        // 使用 Comparator 来排序 ordersList
        if(sequence.equals("default"))
            Collections.sort(ordersList, Comparator.nullsLast(Comparator.comparingInt(Orders::getOrderId)));
        else if(sequence.equals("time")) {
            Collections.sort(ordersList, Comparator.nullsLast(Comparator.comparing(Orders::getOrderTime).reversed()));
        }
        else if(sequence.equals("price")) {
            Collections.sort(ordersList, Comparator.nullsLast(Comparator.comparingDouble(Orders::getTotalPrice).reversed()));
        }
        int endIndex = Math.min(start + pageSize, ordersList.size());
        result.put("pageNum",totalNum);
        result.put("data",ordersList.subList(start, endIndex));
        return result;
    }
}
