package com.my.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.my.domain.ResponseResult;
import com.my.domain.entity.Link;


/**
 * 友链(Link)表服务接口
 *
 * @author makejava
 * @since 2022-04-12 15:46:02
 */
public interface LinkService extends IService<Link> {

    ResponseResult getAllLink();
}
