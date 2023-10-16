package com.sky.mapper;

import com.sky.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.HashMap;
import java.util.Map;

/**
 * 小程序端用户登录
 *
 * @author Aaron.
 * @date 2023/10/16 18:01
 */
@Mapper
public interface UserMapper {
    /**
     * 根据openid查询用户
     *
     * @param openid
     * @return
     */
    @Select("select  * from user where openid=#{openid}")
    User selectByOpenId(String openid);

    /**
     * 新增用户
     *
     * @param user
     */
    void insert(User user);

    /**
     * 根据用户id查用户信息
     *
     * @param userId 用户信息
     * @return user
     */
    @Select("select  * from user where id=#{userId}")
    User getById(Long userId);


    /**
     * 统计用户量
     * @param conditions 条件集合
     * @return conditions
     */
    Integer countByUser(HashMap conditions);

    Integer countByMap(Map map);
}
