package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.ShoppingCart;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.service.ShoppingCartService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 购物车管理
 *
 * @author Aaron.
 * @date 2023/10/16 23:12
 */
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private SetmealMapper setmealMapper;

    /**
     * 添加购物车
     *
     * @param shoppingCartDTO
     */
    @Override
    public void addShoppingCart(ShoppingCartDTO shoppingCartDTO) {
        //查询购物车中是否有数据
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);
        //获取到userId
        Long userId = BaseContext.getCurrentId();
        shoppingCart.setUserId(userId);
        List<ShoppingCart> shoppingCartList = shoppingCartMapper.list(shoppingCart);
        if (shoppingCartList != null && shoppingCartList.size() > 0) {
            //购物车存在数据
            ShoppingCart cart = shoppingCartList.get(0);//取出唯一的一条数据；
            cart.setNumber(cart.getNumber() + 1);
            //数据库进行修改+1
            shoppingCartMapper.setNumber(cart);
        } else {
            //购物车不存在数据，进行添加数据，要么添加菜品，要么添加套餐
            Long dishId = shoppingCartDTO.getDishId();
            if (dishId != null) {
                //添加的是菜品
                Dish dish = dishMapper.getById(dishId);
                shoppingCart.setName(dish.getName());
                shoppingCart.setImage(dish.getImage());
                shoppingCart.setAmount(dish.getPrice());

            } else {
                //添加的是套菜
                Long setmealId = shoppingCartDTO.getSetmealId();
                Setmeal setmeal = setmealMapper.getById(setmealId);
                shoppingCart.setName(setmeal.getName());
                shoppingCart.setImage(setmeal.getImage());
                shoppingCart.setAmount(setmeal.getPrice());

            }
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            //添加到购物车
            shoppingCartMapper.insert(shoppingCart);
        }

    }

    /**
     * 查看购物车
     *
     * @return
     */
    @Override
    public List<ShoppingCart> list() {
        //通过userId进行查询
        Long userId = BaseContext.getCurrentId();
        ShoppingCart cart = new ShoppingCart();
        cart.setUserId(userId);
        List<ShoppingCart> shoppingCart = shoppingCartMapper.list(cart);
        return shoppingCart;
    }

    /**
     * 清空购物车
     */
    @Override
    public void clean() {
        Long userId = BaseContext.getCurrentId();
        shoppingCartMapper.clean(userId);
    }

    /**
     * 购物车的增减
     *
     * @param shoppingCartDTO
     */
    @Override
    public void subShoppingCart(ShoppingCartDTO shoppingCartDTO) {
        ShoppingCart cart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO, cart);
        Long userId = BaseContext.getCurrentId();
        cart.setUserId(userId);
        //获取购物车的数据
        List<ShoppingCart> list = shoppingCartMapper.list(cart);
        //判断购物车的数据是单个菜品还是套餐
        if (cart.getDishId() != null) {
            //若为菜品则数量-1
            if (list != null && list.size() == 1) {
                cart = list.get(0);
                cart.setNumber(cart.getNumber() - 1);
                //跟新数据库菜品数量
                shoppingCartMapper.setNumber(cart);
                //菜品为0 则不显示
                if (cart.getNumber() == 0) {
                    shoppingCartMapper.subShoppingCartDish(cart);
                }
            } else {
                //更新购物车
                shoppingCartMapper.setNumber(cart);
            }
        }

    }
}
