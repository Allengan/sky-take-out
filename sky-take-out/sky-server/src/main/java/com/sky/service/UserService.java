package com.sky.service;

import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;

/**
 * 小程序用户登录
 *
 * @author Aaron.
 * @date 2023/10/16 17:57
 */
public interface UserService {
    /**
     * 用户微信登录
     */
    User wxLogin(UserLoginDTO userLoginDTO);
}
