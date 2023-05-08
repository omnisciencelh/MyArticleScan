package com.yupi.springbootinit.service.impl;

import com.yupi.springbootinit.service.ArticleService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class ArticleServiceImplTest {

    @Resource
    private ArticleService articleService;

  @Test
    public void pullingMyArticle() {
      articleService.pullingMyArticle(1,8);
    }
}