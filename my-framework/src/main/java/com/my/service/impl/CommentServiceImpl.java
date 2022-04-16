package com.my.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.my.constants.SystemConstants;
import com.my.domain.ResponseResult;
import com.my.domain.entity.Article;
import com.my.domain.entity.Comment;
import com.my.domain.entity.User;
import com.my.domain.vo.CommentVo;
import com.my.domain.vo.PageVo;
import com.my.enums.AppHttpCodeEnum;
import com.my.exception.SystemException;
import com.my.mapper.CommentMapper;
import com.my.service.CommentService;
import com.my.service.UserService;
import com.my.utils.BeanCopyUtils;
import com.my.utils.SecurityUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 评论表(Comment)表服务实现类
 *
 * @author makejava
 * @since 2022-04-13 23:33:17
 */
@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private UserService userService;


    @Override
    public ResponseResult commentList(String commentType,Long articleId, Integer pageNum, Integer pageSize) {
        //查询对应文章的根评论
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        //对articleId进行判断
        queryWrapper.eq(SystemConstants.ARTICLE_COMMENT.equals(commentType),Comment::getArticleId,articleId);
        //根评论，rootId为-1
        queryWrapper.eq(Comment::getRootId, SystemConstants.ROOT_COMMENT);
        //评论类型
        queryWrapper.eq(Comment::getType,commentType);
        //分页查询
        Page<Comment> commentPage = new Page<>(pageNum,pageSize);
        page(commentPage,queryWrapper);


        //List<CommentVo> commentVoList = BeanCopyUtils.copyBeanList(commentPage.getRecords(),CommentVo.class);
        List<CommentVo> commentVoList = toCommentList(commentPage.getRecords());
        //查询所有根评论对应的子评论集合，并且赋值给对应的属性
        for (CommentVo commentVo : commentVoList) {
            //查询对应的子评论
            List<CommentVo> children = getChildren(commentVo.getId());
            //赋值
            commentVo.setChildren(children);
        }

        return ResponseResult.okResult(new PageVo(commentVoList,commentPage.getTotal()));
    }

    @Override
    public ResponseResult addComment(Comment comment) {
        if(!StringUtils.hasText(comment.getContent())){
            throw new SystemException(AppHttpCodeEnum.CONTENT_NOT_NULL);
        }
        save(comment);
        return ResponseResult.okResult();
    }

    /**
     * 根据根评论id 查询子评论集合
     * @param id 根评论id
     * @return
     */
    private List<CommentVo> getChildren(Long id) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getRootId,id);
        queryWrapper.orderByAsc(Comment::getCreateTime);
        List<Comment> comments = list(queryWrapper);
        List<CommentVo> commentVos = toCommentList(comments);
        return commentVos;
    }

    private List<CommentVo> toCommentList(List<Comment> list){
        List<CommentVo> commentVos = BeanCopyUtils.copyBeanList(list, CommentVo.class);
        //遍历vo集合
        for (CommentVo commentVo : commentVos) {
            //通过createBy查询用户昵称并赋值
            String nickName = userService.getById(commentVo.getCreateBy()).getNickName();
            commentVo.setUsername(nickName);
            //通过toCommentUserId查询用户昵称并赋值
            if(commentVo.getToCommentUserId()!= -1){
                String toCommentUserName = userService.getById(commentVo.getToCommentUserId()).getNickName();
                commentVo.setToCommentUserName(toCommentUserName);
            }
        }
        return commentVos;

    }
}
