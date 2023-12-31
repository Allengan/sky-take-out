package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishMapper {

    /**
     * 根据分类id查询菜品数量
     * @param categoryId
     * @return
     */
    @Select("select count(id) from sky_take_out.dish where category_id = #{categoryId}")
    Integer countByCategoryId(Long categoryId);

    /**
     * 添加菜品
     * @param dish
     */
    @AutoFill(value = OperationType.INSERT)
    void insert(Dish dish);
    /**
     * 菜品分页查询
     * @param dishPageQueryDTO
     * @return
     */
    Page<DishVO> pageQuery(DishPageQueryDTO dishPageQueryDTO);

    /**
     * 根据id查询菜品信息
     * @param id
     * @return
     */
    @Select("select * from sky_take_out.dish where id = #{id} ")
    Dish getById(Long id);

    /**
     * 删除菜品
     * @param id
     */
    @Delete("delete from sky_take_out.dish where id = #{id} ")
    void deleteById(Long id);

    /**
     * 根据菜品ids集合批量删除菜品
     * @param ids
     */
    void deleteByIds(List<Long> ids);

    /**
     * 根据id动态修改菜品基本信息
     * @param dish
     */
    @AutoFill(value = OperationType.UPDATE)
    void update(Dish dish);

    /**
     * 菜品的起售状态
     * @param status
     * @param id
     */
    void getStatus(@Param("status") String status,@Param("id") String id);

    /**
     * 根据 setmeal字段中的 dish_id 在 dishMapper 中 查询 菜品的状态
     * @param dishIds
     * @return
     */
    Long selectByIdAndStatus(List<Long> dishIds);

    /**
     * 根据分类id查询所有 在售 菜品信息
     *
     * @param dish
     * @return
     */
    @Select("select * from dish where category_id = #{categoryId} and status = #{status}")
    List<Dish> getByCategoryIdWithStatus(Dish dish);

}
