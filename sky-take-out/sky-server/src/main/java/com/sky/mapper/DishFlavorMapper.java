package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 口味
 *
 * @author Aaron.
 * @date 2023/10/3 19:03
 */
@Mapper
public interface DishFlavorMapper {
    /**
     * 批量插入口味
     * @param flavors
     */
    void insertbatch(List<DishFlavor> flavors);

    /**
     * 根据dishId删除口味
     * @param dishId
     */
    @Delete("delete from sky_take_out.dish_flavor  where dish_id = #{dishId} ")
    void deleteById(Long dishId);

    /**
     * 根据dishId删除口味
     * @param dishIds
     */
    void deleteByIds(List<Long> dishIds);

    /**
     * 根据菜品id查询口味数据
     * @param dishId
     * @return
     */
    @Select("select * from sky_take_out.dish_flavor where dish_id = #{dishId} ")
    List<DishFlavor> getByDishId(Long dishId);
}
