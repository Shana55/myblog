package com.my.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.my.domain.ResponseResult;
import com.my.domain.entity.Category;


/**
 * 分类表(Category)表服务接口
 *
 * @author makejava
 * @since 2022-04-11 19:02:09
 */
public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryList();
}
