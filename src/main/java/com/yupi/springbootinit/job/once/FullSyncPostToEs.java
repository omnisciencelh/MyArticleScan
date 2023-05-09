package com.yupi.springbootinit.job.once;

import com.yupi.springbootinit.esdao.ArticleEsDao;
import com.yupi.springbootinit.model.dto.article.ArticleEsDTD;
import com.yupi.springbootinit.model.entity.Article;

import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;

import com.yupi.springbootinit.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.boot.CommandLineRunner;

/**
 * 全量同步帖子到 es
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
// todo 取消注释开启任务
//@Component
@Slf4j
public class FullSyncPostToEs implements CommandLineRunner {
    @Resource
    private ArticleService articleService;

    @Resource
    private ArticleEsDao articleEsDao;

    @Override
    public void run(String... args) {
        List<Article> articleList = articleService.list();
        if (CollectionUtils.isEmpty(articleList)) {
            return;
        }
        final int pageSize = 500;
        int total = articleList.size();
        log.info("FullSyncPostToEs start, total {}", total);
        for (int i = 0; i < total; i += pageSize) {
            int end = Math.min(i + pageSize, total);
            log.info("sync from {} to {}", i, end);
            List<ArticleEsDTD> collect = articleList.stream().map(ArticleEsDTD::ArticleToEsDTO).collect(Collectors.toList());
            articleEsDao.saveAll(collect.subList(i, end));
        }
        log.info("FullSyncPostToEs end, total {}", total);
    }
}
