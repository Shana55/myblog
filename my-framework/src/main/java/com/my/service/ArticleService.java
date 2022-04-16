package com.my.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.my.domain.ResponseResult;
import com.my.domain.entity.Article;

/**
 * @author jhp
 * @create 2022-04-10 22:36
 */
public interface ArticleService extends IService<Article> {
    ResponseResult hotArticleList();

    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

    ResponseResult getArticleDetail(Long id);
}
