package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.vo.DishVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DishService {
    void saveWithFlavor(DishDTO dishDTO);

    PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);

    Result deleteDish(List<Long> ids);

    void changeStatus(Integer status, Integer id);

    DishVO getById(Integer id);

    void transDish(DishDTO dishDTO);

    List<DishVO> getDishById(String id);
}
