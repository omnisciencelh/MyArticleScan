package com.yupi.springbootinit.model.dto.article;

import cn.hutool.core.bean.BeanUtil;
import com.yupi.springbootinit.model.entity.Article;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;


/**
 * 存储文章相关信息的表
 * @TableName article
 */
@Document(indexName = "article")
@Data
public class ArticleEsDTD implements Serializable {
    private static final String DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    /**
     * 文章的id
     */
    @Id
    private Long id;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 文章描述
     */
    private String description;

    /**
     * 文章内容
     */
    private String content;

    /**
     * 文章类型
     */
    private String category;

    /**
     * 文章封面
     */
    private String cover;

    /**
     * 文章创建时间，默认为当前时间
     */
    @Field(index = false, store = true, type = FieldType.Date, format = {}, pattern = DATE_TIME_PATTERN)
    private Date createtime;

    /**
     * 文章更新时间，默认为修改时间
     */
    @Field(index = false, store = true, type = FieldType.Date, format = {}, pattern = DATE_TIME_PATTERN)
    private Date updatetime;

    /**
     * 文章标签
     */
    private String tags;

    /**
     * 文章路劲
    */
    private String path;

    /**
     * 高光域
     */
    private Map<String,String> highLight;

    private static final long serialVersionUID = 1L;

    /**
     * 对象转包装类
     * @param article
     * @return
     */
    public static ArticleEsDTD ArticleToEsDTO(Article article){
        ArticleEsDTD articleEsDTD = BeanUtil.copyProperties(article, ArticleEsDTD.class);
        return articleEsDTD;
    }

    /**
     * 包装类转对象
     * @param articleEsDTD
     * @return
     */
    public static Article ArticleToEsDTO(ArticleEsDTD articleEsDTD)
    {
        return BeanUtil.copyProperties(articleEsDTD,Article.class);
    }

}