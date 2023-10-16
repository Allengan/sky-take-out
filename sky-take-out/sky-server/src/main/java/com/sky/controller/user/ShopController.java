package com.sky.controller.user;

import com.sky.constant.StatusConstant;
import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 店铺状态
 *
 * @author Aaron.
 * @date 2023/10/16 16:39
 */
@RestController("usreShopController")
@Slf4j
@RequestMapping("/user/shop/status")
@Api(tags = "用户端店铺营业状态")
public class ShopController {
    @Autowired
    private RedisTemplate redisTemplate;


    @GetMapping("")
    @ApiOperation("获取用户端店铺状态")
    public Result<Integer> getStatus(){
        //先通过redis获取店铺的查询状态
        Integer status = (Integer) redisTemplate.opsForValue().get(StatusConstant.SHOP_STATUS);
        //健壮性设置
        if(status == null) status = 0;
        log.info("获取用户端店铺营业状态:{}",status == 1 ? "营业中":"休息中");
        return Result.success(status);
    }
}
