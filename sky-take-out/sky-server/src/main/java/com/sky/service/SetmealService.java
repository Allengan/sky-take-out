package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;

import java.util.List;

public interface SetmealService {


    /**
     * 套餐新增
     * @param setmealDTO
     */
    void saveWithDish(SetmealDTO setmealDTO);

    /**
     * 套餐分页查询
     * @param setmealPageQueryDTO
     * @return
     */
    PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);


    /**
     * 套餐删除
     * @param ids
     */
    void deleteByIds(List<Long> ids);

    /**
     * 套餐详情  做数据回显
     * @param id
     * @return
     */
    SetmealVO getById(Long id);


    /**
     * 套餐修改
     * @param setmealDTO
     */
    void update(SetmealDTO setmealDTO);

    /**
     * 修改套餐状态
     * @param status
     * @param id
     */
    void updateStatus(Integer status, Long id);


    /**
     * 根据id查询菜品选项
     * @param id
     * @return
     */
    List<DishItemVO> getDishItemById(Long id);

    /**
     * 条件查询
     * @param setmeal
     * @return
     */
    List<Setmeal> list(Setmeal setmeal);
}
