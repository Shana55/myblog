package com.my.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.my.domain.ResponseResult;
import com.my.domain.entity.Comment;


/**
 * 评论表(Comment)表服务接口
 *
 * @author makejava
 * @since 2022-04-13 23:33:16
 */
public interface CommentService extends IService<Comment> {

    ResponseResult commentList(String commentType,Long articleId, Integer pageNum, Integer pageSize);

    ResponseResult addComment(Comment comment);
}
