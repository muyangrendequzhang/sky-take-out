package com.sky.mapper;

import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SetmealDishMapper {
    void insertBatch(List<SetmealDish> setmealDishes);

    void deleteByIds(List<Long> ids);

    void updateDish(List<SetmealDish> setmealDishes);

    List<SetmealDish> getById(Long id);
}
