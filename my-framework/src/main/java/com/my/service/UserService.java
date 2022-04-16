package com.my.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.my.domain.ResponseResult;
import com.my.domain.entity.User;


/**
 * 用户表(User)表服务接口
 *
 * @author makejava
 * @since 2022-04-14 00:07:30
 */
public interface UserService extends IService<User> {

    ResponseResult userInfo();

    ResponseResult updateUserInfo(User user);

    ResponseResult register(User user);
}
