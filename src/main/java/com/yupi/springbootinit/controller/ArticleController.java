package com.yupi.springbootinit.controller;
import com.yupi.springbootinit.common.BaseResponse;
import com.yupi.springbootinit.model.dto.article.ArticleRequest;
import com.yupi.springbootinit.model.dto.article.ArticleEsDTD;
import com.yupi.springbootinit.model.vo.PathOfFile;
import com.yupi.springbootinit.service.ArticleService;
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
    public BaseResponse<List<ArticleEsDTD>> searchByDir(@RequestBody ArticleRequest articleRequest){
        System.out.println("请求参数："+articleRequest);
        return articleService.searchByDir(articleRequest);
    }


    @PostMapping("searchByAll")
    public BaseResponse<List<ArticleEsDTD>> searchByAll(@RequestBody ArticleRequest articleRequest){
        return articleService.searchByTitleOrContent(articleRequest);
    }

    @PostMapping("searchAll")
    public BaseResponse<PathOfFile> searchAllChildByPath(@RequestBody ArticleRequest articleRequest){
        return articleService.searchAllChildByPath(articleRequest);
    }

}
