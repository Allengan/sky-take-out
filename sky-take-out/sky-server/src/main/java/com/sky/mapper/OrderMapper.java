package com.sky.mapper;

import com.sky.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * 订单管理
 *
 * @author Aaron.
 * @date 2023/10/17 10:13
 */
@Mapper
public interface OrderMapper {


     void save(Orders orders);

     /**
      * 根据订单号和用户id查询订单
      * @param orderNumber
      * @param userId
      */
     @Select("select * from orders where number = #{orderNumber} and user_id= #{userId}")
     Orders getByNumberAndUserId(String orderNumber, Long userId);

     /**
      * 修改订单信息
      * @param orders
      */
     void update(Orders orders);
}
