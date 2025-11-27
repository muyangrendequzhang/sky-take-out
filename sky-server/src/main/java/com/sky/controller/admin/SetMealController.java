package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetMealService;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/setmeal")
@Slf4j
public class SetMealController {

    @Autowired
    private SetMealService setMealService;
    @PostMapping
    @ApiOperation("添加菜单")
    public Result saveMeal(@RequestBody SetmealDTO setmealDTO){
        setMealService.save(setmealDTO);
        return Result.success();
    }
    @GetMapping("/page")
    @ApiOperation("进行分页查询")
    public Result<PageResult> pageQuery(SetmealPageQueryDTO setmealPageQueryDTO){
        PageResult setmeals = setMealService.pageQuery(setmealPageQueryDTO);
        return Result.success(setmeals);
    }
    @DeleteMapping
    @ApiOperation("获取ids进行删除操作")
    public Result deleteByIds(@RequestParam List<Long> ids){
        log.info("进行删除操作{}",ids);
        //删除套餐仅需动setmeal,setmeal_dish表
        setMealService.deleteByIds(ids);
        return Result.success();
    }

    @PutMapping
    @ApiOperation("修改套餐接口")
    public Result updateSetmeal(@RequestBody SetmealDTO setmealDTO){
        log.info("正在修改套餐{}",setmealDTO);
        setMealService.updateSetmeal(setmealDTO);
        return Result.success();
    }

    @GetMapping("/{id}")
    @ApiOperation("根据id查询套餐信息")
    public Result<SetmealVO> getById(@PathVariable Long id){
        log.info("根据id查询{}",id);
        SetmealVO setmealVO = setMealService.getById(id);
        return Result.success(setmealVO);
    }
    @PostMapping("/status/{status}")
    @ApiOperation("对商品进行停售或者起售")
    public Result startOrStop(@PathVariable Long status,Long id){
        log.info("对{}号进行{}操作",id,status);
        setMealService.startOrStop(id,status);
        return Result.success();
    }
}
