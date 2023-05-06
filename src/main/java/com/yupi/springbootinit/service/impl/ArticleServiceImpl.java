package com.yupi.springbootinit.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.springbootinit.mapper.ArticleMapper;
import com.yupi.springbootinit.model.entity.Article;
import com.yupi.springbootinit.service.ArticleService;
import org.springframework.stereotype.Service;

/**
*
*/
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article>
implements ArticleService{

}
