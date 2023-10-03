package com.sky.service;

import com.sky.dto.DishDTO;

/**
 * 菜品管理
 *
 * @author Aaron.
 * @date 2023/10/3 18:40
 */
public interface DishService {
    /**
     * 新增菜品和口味
     * @param dishDTO
     */
    public void saveWithFlavor(DishDTO dishDTO);
}
