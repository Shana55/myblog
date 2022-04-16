package com.my.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author jhp
 * @create 2022-04-12 15:25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDetailVo {
    private Long id;
    //标题
    private String title;
    //文章摘要
    private String summary;
    //所属分类id
    private Long categoryId;
    //所属分类名
    private String categoryName;
    //文章内容
    private String content;
    //缩略图
    private String thumbnail;
    //访问量
    private Long viewCount;

    private Date createTime;

}
