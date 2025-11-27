package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private DishFlavorMapper dishFlavorMapper;

    @Override
    @Transactional
    public void saveWithFlavor(DishDTO dishDTO) {
        //1.将其储存在菜品表内
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
        dishMapper.insert(dish);
        //2.将其储存在口味表内
        Long id = dish.getId();
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if(flavors!=null && flavors.size()>0){
            flavors.forEach(dishFlavor -> dishFlavor.setDishId(id));
            //进行存储
            dishFlavorMapper.insertBatch(flavors);
        }
    }

    @Override
    public PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(),dishPageQueryDTO.getPageSize());
        //page其实底层就是个List
        Page<DishVO> dishVOS  = dishMapper.pageQuery(dishPageQueryDTO);
        return new PageResult(dishVOS.getTotal(),dishVOS.getResult());

    }

    @Transactional
    @Override
    public Result deleteDish(List<Long> ids) {
        if(ids.size()==0 ||ids==null){
            return Result.error("未传输要删除的数据");
            //这种情况几乎不会发生
        }
        Long count = dishMapper.deleteDish(ids);
        if(count>0){
            dishFlavorMapper.deleteFlavor(ids);
            return Result.success();
        }
        return Result.error("有套餐未删除或者状态非0");
    }

    @Override
    public void changeStatus(Integer status, Integer id) {
        dishMapper.changeStatus(status,id);
    }

    @Override
    @Transactional
    public DishVO getById(Integer id) {
        DishVO dishVO = dishMapper.getById(id);
        List<DishFlavor> dishFlavors = dishFlavorMapper.getByDishId(id);
        dishVO.setFlavors(dishFlavors);
        return dishVO;
    }

    @Override
    public void transDish(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
        dishMapper.update(dish);

        Long id = dish.getId();
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if(flavors!=null && flavors.size()>0){
            flavors.forEach(dishFlavor -> dishFlavor.setDishId(id));
            //进行存储
            dishFlavorMapper.updateBatch(flavors);
        }
    }

    @Override
    public List<DishVO> getDishById(String id) {
        Long l = Long.valueOf(id);
        List<DishVO> dish =  dishMapper.getDishById(l);
        return dish;

    }

}
