package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetmealService;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;

    @Autowired
    private SetmealDishMapper setmealDishMapper;

    @Autowired
    private DishMapper dishMapper;
    /**
     * 新增套餐关联菜品
     * @param setmealDTO
     */
    @Override
    @Transactional
    public void saveWithDish(SetmealDTO setmealDTO) {
        log.info("新增套餐关联菜品");
        Setmeal setmeal = new Setmeal();
        // 对DTO 拷贝属性
        BeanUtils.copyProperties(setmealDTO,setmeal);

        setmealMapper.insertSelective(setmeal);


        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        // 对套餐菜品进行空值校验
        if (setmealDishes!= null && setmealDishes.size() > 0) {
            setmealDishes.forEach(setmealDish->{
                setmealDish.setSetmealId(setmeal.getId());
            });
            setmealDishMapper.insertBatch(setmealDishes);
        }
    }

    /**
     * 分页条件查询套餐数据
     *
     * @param setmealPageQueryDTO
     * @return
     */
    @Override
    public PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO) {
        //开启分页查询
        PageHelper.startPage(setmealPageQueryDTO.getPage(), setmealPageQueryDTO.getPageSize());
        //执行查询
        Page<SetmealVO> page = setmealMapper.pageQuery(setmealPageQueryDTO);
        //封装结果集对象
        PageResult pageResult = new PageResult(page.getTotal(), page.getResult());
        return pageResult;
    }

    @Override
    @Transactional
    public void deleteByIds(List<Long> ids) {
        //1.售卖中套餐不可删除
        for (Long id : ids) {
            Setmeal setMeal = setmealMapper.getById(id);
            if (setMeal.getStatus() == 1) {
                throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ON_SALE);
            }
        }
        for (Long id : ids) {
            //删除套餐
            setmealMapper.deleteById(id);
            //2.删除套餐后，还需删除套餐菜品关系表中数据
            setmealDishMapper.deleteByIdSetmealId(id);
        }
    }

    /**
     * 做数据回显 套餐详情
     * @param id
     * @return
     */
    @Override
    public SetmealVO getById(Long id) {
        log.info("查询套餐详情");

        Setmeal setmeal = setmealMapper.getById(id);
        SetmealVO setmealVO = new SetmealVO();
        BeanUtils.copyProperties(setmeal,setmealVO);

        List<SetmealDish> setmealDishes = setmealDishMapper.getBySetmealId(id);
        setmealVO.setSetmealDishes(setmealDishes);
        return setmealVO;

    }

    /**
     * 修改套餐
     * @param setmealDTO
     *
     */
    @Override
    public void update(SetmealDTO setmealDTO) {
        log.info("修改套餐");
        Setmeal setmeal = new Setmeal();
        // 对DTO 拷贝属性
        BeanUtils.copyProperties(setmealDTO,setmeal);
        setmealMapper.update(setmeal);
        //2.删除套餐后，还需要再删除套餐中包含的菜品菜品  --> setmealDish 表
        setmealDishMapper.deleteByIdSetmealId(setmealDTO.getId());
        //3.重新插入 菜品数据
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        // 对套餐菜品进行空值校验
        if (setmealDishes!= null && setmealDishes.size() > 0) {
            // 对套餐中的菜品设置套餐id
            setmealDishes.forEach(setmealDish->{
                setmealDish.setSetmealId(setmeal.getId());
            });
            setmealDishMapper.insertBatch(setmealDishes);
        }
    }

    /**
     * 修改套餐状态
     * @param status
     * @param id
     */
    @Override
    public void updateStatus(Integer status, Long id) {
        List<SetmealDish> byId = setmealDishMapper.getById(id);
        for (SetmealDish setmealDish : byId) {
            Dish dish = dishMapper.getById(setmealDish.getDishId());
            if (dish.getStatus() == StatusConstant.DISABLE){
                throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ENABLE_FAILED);
            }else{
                Setmeal setmeal = Setmeal.builder()
                        .status(status)
                        .id(id)
                        .build();
                setmealMapper.updateStatus(setmeal.getStatus(),setmeal.getId());
            }
        }
    }

    /**
     * 根据id查询菜品选项
     * @param id
     * @return
     */
    public List<DishItemVO> getDishItemById(Long id) {
        return setmealMapper.getDishItemBySetmealId(id);
    }

    /**
     * 条件查询
     * @param setmeal
     * @return
     */
    public List<Setmeal> list(Setmeal setmeal) {
        List<Setmeal> list = setmealMapper.list(setmeal);
        return list;
    }
}