package com.sky.mapper;

import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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


    void insertBatch(List<SetmealDish> setmealDishes);

    /**
     * 根据套餐id查询关联的菜品信息
     *
     * @param setMealId
     * @return
     */
    @Select("select * from setmeal_dish where setmeal_id  =#{setMealId}")
    List<SetmealDish> getById(Long setMealId);

    /**
     * 根据套餐id删除关联表的数据
     *
     * @param id
     */
    @Delete("delete from setmeal_dish where setmeal_id=#{setMealId}")
    void deleteByIdSetmealId(Long id);

    /**
     * 根据套餐id查询关联的菜品信息
     * @param id
     */

    @Select("select * from setmeal_dish where setmeal_id=#{setMealId}")
    List<SetmealDish> getBySetmealId(Long id);

    /**
     * 根据菜品ID查询其所在的套餐
     * @param id
     * @return
     */
    @Select("select setmeal_id from setmeal_dish where dish_id=#{id}")
    List<Long> getSetmealIdsByid(Long id);
}
