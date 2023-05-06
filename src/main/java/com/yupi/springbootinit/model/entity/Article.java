package com.yupi.springbootinit.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 存储文章相关信息的表
 * @TableName article
 */
@TableName(value ="article")
@Data
public class Article implements Serializable {
    /**
     * 文章的id
     */
    @TableId(value = "id")
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
    private Date createtime;

    /**
     * 文章更新时间，默认为修改时间
     */
    @TableField(value = "updateTime")
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

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}