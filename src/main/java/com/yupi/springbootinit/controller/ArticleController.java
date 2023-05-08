package com.yupi.springbootinit.controller;

import com.yupi.springbootinit.common.BaseResponse;
import com.yupi.springbootinit.common.ErrorCode;
import com.yupi.springbootinit.common.ResultUtils;
import com.yupi.springbootinit.esdao.ArticleEsDao;
import com.yupi.springbootinit.model.dto.ArticleRequest;
import com.yupi.springbootinit.model.entity.Article;
import com.yupi.springbootinit.service.ArticleService;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("article")
public class ArticleController {
    @Resource
    private ArticleService articleService;

    /**
     * 通过目录来查询文章
     * @param articleRequest
     * @return
     */
    @PostMapping("searchByDir")
    public BaseResponse<List<Article>> searchByDir(@RequestBody ArticleRequest articleRequest){
        System.out.println("请求参数："+articleRequest);
        return articleService.searchByDir(articleRequest);
    }


    @PostMapping("searchByAll")
    public BaseResponse<List<Article>> searchByAll(@RequestBody ArticleRequest articleRequest){
        return articleService.searchByTitleOrContent(articleRequest);
    }

}
