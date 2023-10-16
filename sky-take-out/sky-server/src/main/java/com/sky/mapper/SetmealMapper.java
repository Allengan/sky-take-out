package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface SetmealMapper {

    /**
     * 根据分类id查询套餐的数量
     * @param id
     * @return
     */
    @Select("select count(id) from setmeal where category_id = #{categoryId}")
    Integer countByCategoryId(Long id);

    /**
     * 套餐新增
     * @param setmeal
     */
    @AutoFill(value = OperationType.INSERT)
    void insertSelective(Setmeal setmeal);

    /**
     * 分页条件查询套餐数据
     *
     * @param setmealPageQueryDTO
     * @return
     */
    Page<SetmealVO> pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    /**
     * 删除套餐
     * @param id
     */
    @Delete("delete from setmeal where id = #{id}")
    void deleteById(Long id);

    /**
     * 套餐查询 数据回显
     * @param id
     * @return
     */
    @Select("select * from setmeal where id = #{id}")
    Setmeal getById(Long id);


    /**
     * 套餐修改
     * @param setmeal
     */
    @AutoFill(value = OperationType.UPDATE)
    void update(Setmeal setmeal);


    /**
     * 套餐状态修改
     * @param status
     * @param id
     */
    @AutoFill(value = OperationType.UPDATE)
    @Update("update setmeal set status = #{status} where id = #{id}")
    void updateStatus(@Param("status") Integer status, @Param("id") Long id);


    /**
     * 根据套餐id查询菜品选项
     * @param setmealId
     * @return
     */
    @Select("select sd.name, sd.copies, d.image, d.description " +
            "from setmeal_dish sd left join dish d on sd.dish_id = d.id " +
            "where sd.setmeal_id = #{setmealId}")
    List<DishItemVO> getDishItemBySetmealId(Long setmealId);

    /**
     * 动态条件查询套餐
     * @param setmeal
     * @return
     */
    List<Setmeal> list(Setmeal setmeal);

    Integer countByMap(Map map);

}
