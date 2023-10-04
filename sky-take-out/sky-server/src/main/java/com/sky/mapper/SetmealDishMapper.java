package com.sky.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 套菜管理
 *
 * @author Aaron.
 * @date 2023/10/4 18:12
 */
@Mapper
public interface SetmealDishMapper {
    /**
     *  根据菜品id查询对应套餐id
     * @param dishIds
     * @return
     */
    List<Long> getSetmealIdsByDishIds(List<Long> dishIds);
}
