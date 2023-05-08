package com.yupi.springbootinit.service.impl;

import cn.hutool.core.io.file.FileReader;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.springbootinit.common.BaseResponse;
import com.yupi.springbootinit.common.ErrorCode;
import com.yupi.springbootinit.common.ResultUtils;
import com.yupi.springbootinit.esdao.ArticleEsDao;
import com.yupi.springbootinit.job.once.MyDirectory;
import com.yupi.springbootinit.mapper.ArticleMapper;
import com.yupi.springbootinit.model.dto.ArticleRequest;
import com.yupi.springbootinit.model.entity.Article;

import com.yupi.springbootinit.service.ArticleService;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Stream;


/**
*
*/
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article>
implements ArticleService {

    @Resource
    private MyDirectory myDirectory;

    @Resource
    private ArticleEsDao articleEsDao;

    @Override
    public void pullingMyArticle(int current,int pageSize){
        FileReader fileReader = new FileReader("D:\\其它语言工程项目\\实习项目\\MyArticleScan\\json\\myData.json");
        String result = fileReader.readString();
        Map map1 = JSONUtil.toBean(result, Map.class);
        Integer code =(Integer) map1.get("code");
        if(!(code==0)){
            log.error("获取参数异常");
            return;
        }
        JSONObject data = (JSONObject)map1.get("data");
        JSONArray arr = (JSONArray)data.get("records");
        for(Object o:arr){
            JSONObject item = (JSONObject)o;
            Long id = item.getLong("id");
            String title = item.getStr("title");
            String description = item.getStr("description");
            String content = item.getStr("content");
            String category = item.getStr("category");
            String cover = item.getStr("cover");
            Date createTime = item.getDate("createTime");
            Date updateTime = item.getDate("updateTime");
            Article article = new Article();
            article.setId(id);article.setTitle(title);article.setDescription(description);
            article.setContent(content);article.setCategory(category);
            article.setCover(cover);
            article.setCreatetime(createTime);article.setUpdatetime(updateTime);
            article = myDirectory.SortedOfLabel(article);
            save(article);
        }
    }



    @Override
    public BaseResponse<List<Article>> searchByDir(ArticleRequest articleRequest) {

        String searchText = articleRequest.getSearchText();
        //目录有效且存在
        if(!(searchText!=null && searchText.length()!=0&&myDirectory.isDir(searchText))){
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        int current =(int) articleRequest.getCurrent();
        int pageSize = (int)articleRequest.getPageSize();
        System.out.println("分页参数Page:"+current+",size:"+pageSize);
        Pageable pageable = PageRequest.of(current, pageSize);
        List<Article> list = articleEsDao.findAllByPath(searchText);
        return ResultUtils.success(list);
    }
    @Override
    public BaseResponse<List<Article>> searchByTitleOrContent(ArticleRequest articleRequest) {
        String searchText = articleRequest.getSearchText();
        int current =(int) articleRequest.getCurrent();
        int pageSize = (int)articleRequest.getPageSize();
        Pageable pageable = PageRequest.of(current, pageSize);
        //目录有效且存在
        if(!(searchText!=null && searchText.length()!=0)){
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        List<SearchHit<Article>> articles = articleEsDao.findArticlesByContentContainingOrTitleContainingOrTagsContaining(searchText, searchText,searchText);
        List<Article> list = new ArrayList<>();
        for (SearchHit<Article> a: articles){
            Article article = a.getContent();
            List<String> contentList = a.getHighlightField("content");
            List<String> titleList = a.getHighlightField("title");
            StringBuffer content = new StringBuffer();StringBuffer title = new StringBuffer();
            if(contentList!=null && !contentList.isEmpty()){
                contentList.stream().forEach(content::append);
            }
            if(titleList!=null && !titleList.isEmpty()){
                contentList.stream().forEach(title::append);
            }
            Map<String,String> map = new HashMap<>();
            map.put("content",content.toString());
            map.put("title",title.toString());
            article.setHighLight(map);
            list.add(article);
        }
        return ResultUtils.success(list);
    }
}
