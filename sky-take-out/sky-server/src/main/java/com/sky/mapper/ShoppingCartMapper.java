package com.sky.mapper;


import com.sky.entity.ShoppingCart;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 购物车管理
 *
 * @author Aaron.
 * @date 2023/10/16 23:13
 */
@Mapper
public interface ShoppingCartMapper {
    /**
     * 查询购物车
     * @param shoppingCart
     * @return
     */
    List<ShoppingCart> list( ShoppingCart shoppingCart);

    /**
     * 修改购物车
     * @param shoppingCart
     */
    void setNumber( ShoppingCart shoppingCart);

    /**
     * 添加购物车
     * @param shoppingCart
     */
    void insert(ShoppingCart shoppingCart);

    /**
     * 清空购物车
     * @param userId
     */
    void clean(Long userId);

    /**
     * 增减购物车
     * @param cart
     */
    void subShoppingCartDish(ShoppingCart cart);
}
