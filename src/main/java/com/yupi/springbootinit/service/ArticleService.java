package com.yupi.springbootinit.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.yupi.springbootinit.common.BaseResponse;
import com.yupi.springbootinit.model.dto.ArticleRequest;
import com.yupi.springbootinit.model.entity.Article;

import java.util.List;

/**
*
*/
public interface ArticleService extends IService<Article> {

    void pullingMyArticle(int current,int pageSize);

    BaseResponse<List<Article>> searchByDir(ArticleRequest articleRequest);

    BaseResponse<List<Article>> searchByTitleOrContent(ArticleRequest articleRequest);
}
