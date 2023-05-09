package com.yupi.springbootinit.model.vo;

import com.yupi.springbootinit.model.dto.article.ArticleEsDTD;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 表示某个路径下的所有的文件包括文件夹
 */
@Data
public class PathOfFile implements Serializable {
    /**
     * 表示该目录在目录树的第几层
     */
    private String id ;

    /**
     * 该目录的名字
     */
    private String name;

    /**
     * 该文件夹的路径
     */
    private String path;

    /**
     * 该目录之下的所有的文章（是文章不是目录）
     */
    private List<ArticleEsDTD> articleEsDTDList;

    /**
     * 该目录之下的所有的目录
     */
    private List<PathOfFile> pathOfFileList;


    private static final long serialVersionUID = 1L;
}
