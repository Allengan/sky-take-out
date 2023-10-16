package com.sky.controller.admin;

import com.alibaba.druid.util.StringUtils;
import com.sky.constant.StatusConstant;
import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * 店铺状态
 *
 * @author Aaron.
 * @date 2023/10/16 16:39
 */
@RestController("adminShopController")
@Slf4j
@RequestMapping("/admin/shop")
@Api(tags = "店铺营业状态")
public class ShopController {
    @Autowired
    private RedisTemplate redisTemplate;
    @PutMapping ("/{status}")
    @ApiOperation("设置店铺状态")
    public Result setShopStatus(@PathVariable Integer status){
        log.info("设置店铺营业状态：{}",status == 1 ? "营业中" : "打烊中");
        //设置缓存
        redisTemplate.opsForValue().set(StatusConstant.SHOP_STATUS,status);
        return Result.success();
    }

    @GetMapping("/status")
    @ApiOperation("获取店铺状态")
    public Result<Integer> getStatus(){
        //先通过redis获取店铺的查询状态
        Integer status = (Integer) redisTemplate.opsForValue().get(StatusConstant.SHOP_STATUS);
        //健壮性设置
        if(status == null) status = 0;
        log.info("获取店铺营业状态:{}",status == 1 ? "营业中":"打烊中");
        return Result.success(status);
    }
}
