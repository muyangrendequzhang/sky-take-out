package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@Api(tags = "菜品相关接口")
@RequestMapping("/admin/dish")
public class DishController {

    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    private DishService dishService;
    @PostMapping
    @ApiOperation("新增菜品")
    public Result save(@RequestBody DishDTO dishDTO){
        log.info("新增菜品："+dishDTO);
        dishService.saveWithFlavor(dishDTO);
        return Result.success();
    }

    @GetMapping("/page")
    @ApiOperation("分页查询")
    public Result<PageResult> pageQuery(DishPageQueryDTO dishPageQueryDTO){
        log.info("进行分页查询：{}",dishPageQueryDTO);
        PageResult pageResult =  dishService.pageQuery(dishPageQueryDTO);

        return Result.success(pageResult);

    }
    @DeleteMapping
    @ApiOperation("批量或者删除单个菜品")
    public Result deleteDish(@RequestParam List<Long> ids){
        Result result = dishService.deleteDish(ids);
        return result;
    }
    @PostMapping("/status/{status}")
    @ApiOperation("修改售卖状况")
    public Result changeStatus(@PathVariable Integer status, Integer id){
        dishService.changeStatus(status,id);
        return Result.success();
    }

    @GetMapping("/{id}")
    @ApiOperation("根据id查询数据")
    public Result<DishVO> getById(@PathVariable Integer id){
        DishVO dishVO = dishService.getById(id);
        return Result.success(dishVO);
    }
    @PutMapping
    @ApiOperation("修改菜品信息")
    public Result transDish(@RequestBody DishDTO dishDTO){
        dishService.transDish(dishDTO);
        return Result.success();
    }

    @GetMapping("/list")
    @ApiOperation("根据分类的id查询菜品")
    public Result<List<DishVO>> getDishById(String categoryId){
        log.info("进行菜品的查询{}",categoryId);
        List<DishVO> dishVOS = dishService.getDishById(categoryId);
        return Result.success(dishVOS);
    }
}
