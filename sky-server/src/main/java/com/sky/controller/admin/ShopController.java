package com.sky.controller.admin;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController("adminShopController")
@RequestMapping("/admin/shop")
@Slf4j
@Api(tags = "店铺相关接口")
public class ShopController {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @ApiOperation("设置店铺状态")
    @PutMapping("/{status}")
    public Result setStatus(@PathVariable Integer status){
        log.info("将店铺的状态设置为{}",status);
        stringRedisTemplate.opsForValue().set("shop_status",status.toString());
        return Result.success();
    }

    @ApiOperation("查询营业状态")
    @GetMapping("/status")
    public Result<Integer> getStatus(){
        String s = stringRedisTemplate.opsForValue().get("shop_status");
        int i = Integer.parseInt(s);
        log.info("当前店铺的状态为{}",i);
        return Result.success(i);
    }
}
