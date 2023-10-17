package com.sky.mapper;

import com.sky.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

/**
 * 订单详情
 *
 * @author Aaron.
 * @date 2023/10/17 10:53
 */
@Mapper
public interface OrderDetailMapper {
     void insert(ArrayList<OrderDetail> orderDetialList);
}
