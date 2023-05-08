package com.yupi.springbootinit.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName(value ="article")

@Document(indexName = "article")
@Data
public class Article implements Serializable {
    private static final String DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    /**
     * 文章的id
     */
    @TableId(value = "id")
    @Id
    private Long id;

    /**
     * 文章标题
     */
    @TableField(value = "title")
    private String title;

    /**
     * 文章描述
     */
    @TableField(value = "description")
    private String description;

    /**
     * 文章内容
     */
    @TableField(value = "content")
    private String content;

    /**
     * 文章类型
     */
    @TableField(value = "category")
    private String category;

    /**
     * 文章封面
     */
    @TableField(value = "cover")
    private String cover;

    /**
     * 文章创建时间，默认为当前时间
     */
    @TableField(value = "createTime")
    @Field(index = false, store = true, type = FieldType.Date, format = {}, pattern = DATE_TIME_PATTERN)
    private Date createtime;

    /**
     * 文章更新时间，默认为修改时间
     */
    @TableField(value = "updateTime")
    @Field(index = false, store = true, type = FieldType.Date, format = {}, pattern = DATE_TIME_PATTERN)
    private Date updatetime;

    /**
     * 文章标签
     */
    @TableField(value = "tags")
    private String tags;

    /**
     * 文章路劲
     */
    @TableField(value = "path")
    private String path;

    /**
     * 高光域
     */
    @TableField(exist = false)
    private Map<String,String> highLight;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}