package com.my.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.my.constants.SystemConstants;
import com.my.domain.ResponseResult;
import com.my.domain.entity.Article;
import com.my.domain.entity.Category;
import com.my.domain.vo.ArticleDetailVo;
import com.my.domain.vo.ArticleListVo;
import com.my.domain.vo.HotArticleVo;
import com.my.domain.vo.PageVo;
import com.my.mapper.ArticleMapper;
import com.my.service.ArticleService;
import com.my.service.CategoryService;
import com.my.utils.BeanCopyUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author jhp
 * @create 2022-04-10 22:36
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Autowired
    private CategoryService categoryService;
    @Override
    public ResponseResult hotArticleList() {
        //查询热门文章，封装成对象返回
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //必须是正式文章
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        // 按照浏览量进行排序
        queryWrapper.orderByDesc(Article::getViewCount);
        // 最多十条
        Page<Article> articlePage = new Page<>(1,10);
        page(articlePage,queryWrapper);
        List<Article> articleList = articlePage.getRecords();
        //bean拷贝
        //List<HotArticleVo> articleVos = new ArrayList<>();
        //for (Article article : articleList) {
        //    HotArticleVo vo = new HotArticleVo();
        //    BeanUtils.copyProperties(article,vo);
        //    articleVos.add(vo);
        //}
        List<HotArticleVo> articleVos = BeanCopyUtils.copyBeanList(articleList,HotArticleVo.class);
        return ResponseResult.okResult(articleVos);
    }

    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        //查询条件
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //如果categoryId有传，查询时要相同
        queryWrapper.eq(Objects.nonNull(categoryId)&&categoryId>0,Article::getCategoryId,categoryId);
        //状态是正式发布的
        queryWrapper.eq(Article::getStatus,SystemConstants.ARTICLE_STATUS_NORMAL);
        //对isTop进行排序
        queryWrapper.orderByDesc(Article::getIsTop);
        //分页查询
        Page<Article> articlePage = new Page<>(pageNum,pageSize);
        page(articlePage,queryWrapper);
        //查询categoryName
        List<Article> articles = articlePage.getRecords();
        //articles.stream()
        //        .map(new Function<Article, Article>() {
        //            @Override
        //            public Article apply(Article article) {
        //                //获取分类id，查询分类信息，获取分类名称
        //                Category category = categoryService.getById(article.getCategoryId());
        //                String name = category.getName();
        //                //把分类名称设置给article
        //                article.setCategoryName(name);
        //                return article;
        //            }
        //        }).collect(Collectors.toList());
        articles.stream()
                .map(article -> article.setCategoryName(categoryService.getById(article.getCategoryId()).getName()))
                .collect(Collectors.toList());
        //根据articleId查询articleName
        //for (Article article : articles) {
        //    Category category = categoryService.getById(article.getCategoryId());
        //    article.setCategoryName(category.getName());
        //}
        //封装查询结果
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(articles, ArticleListVo.class);
        PageVo pageVo = new PageVo(articleListVos, articlePage.getTotal());
        return ResponseResult.okResult(pageVo);

    }

    @Override
    public ResponseResult getArticleDetail(Long id) {
        //根据id查询文章
        Article article = getById(id);
        //封装成vo
        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);
        //根据分类id查询分类名
        Long categoryId = articleDetailVo.getCategoryId();
        Category category = categoryService.getById(categoryId);
        if(category!=null){
            articleDetailVo.setCategoryName(category.getName());
        }
        //封装响应返回

        return ResponseResult.okResult(articleDetailVo);
    }
}
