package com.yupi.springbootinit.esdao;

import java.util.List;
import java.util.stream.Stream;

import com.yupi.springbootinit.model.entity.Article;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Highlight;
import org.springframework.data.elasticsearch.annotations.HighlightField;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * 帖子 ES 操作
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
public interface ArticleEsDao extends ElasticsearchRepository<Article, Long> {


    List<Article> findAllByPath(String path);


    @Highlight(fields = {
            @HighlightField(name = "title"),
            @HighlightField(name = "content")
    })
    List<SearchHit<Article>> findArticlesByContentContainingOrTitleContainingOrTagsContaining(String content, String title,String tags);
}