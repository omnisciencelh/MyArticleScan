package com.yupi.springbootinit.model.dto.article;

import com.yupi.springbootinit.common.PageRequest;
import lombok.Data;

import java.io.Serializable;
/**
 * 请求参数
 */
@Data
public class ArticleRequest extends PageRequest implements Serializable {

    /**
     * 通过标题来查询数据
     */
    private String searchText;

    private static final long serialVersionUID = 1L;
}
