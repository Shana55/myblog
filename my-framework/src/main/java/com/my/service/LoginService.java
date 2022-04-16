package com.my.service;

import com.my.domain.ResponseResult;
import com.my.domain.entity.User;

/**
 * @author jhp
 * @create 2022-04-12 21:14
 */
public interface LoginService {

    ResponseResult login(User user);

    ResponseResult logout();
}
