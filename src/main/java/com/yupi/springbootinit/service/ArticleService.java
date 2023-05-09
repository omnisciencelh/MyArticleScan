package com.yupi.springbootinit.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.yupi.springbootinit.common.BaseResponse;
import com.yupi.springbootinit.model.dto.article.ArticleRequest;
import com.yupi.springbootinit.model.dto.article.ArticleEsDTD;
import com.yupi.springbootinit.model.entity.Article;
import com.yupi.springbootinit.model.vo.PathOfFile;

import java.util.List;

/**
*
*/
public interface ArticleService extends IService<Article> {

    void pullingMyArticle(int current,int pageSize);

    BaseResponse<List<ArticleEsDTD>> searchByDir(ArticleRequest articleRequest);

    BaseResponse<List<ArticleEsDTD>> searchByTitleOrContent(ArticleRequest articleRequest);

    BaseResponse<PathOfFile> searchAllChildByPath(ArticleRequest articleRequest);
}
