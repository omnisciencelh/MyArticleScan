package com.yupi.springbootinit.job.once;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.yupi.springbootinit.common.BaseResponse;
import com.yupi.springbootinit.esdao.ArticleEsDao;
import com.yupi.springbootinit.model.dto.article.ArticleEsDTD;
import com.yupi.springbootinit.model.entity.Article;
import com.yupi.springbootinit.model.vo.DirectoryVO;
import com.yupi.springbootinit.model.vo.PathOfFile;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
/**
 * 我的系统的目录结构
 */
@Component
public class MyDirectory implements CommandLineRunner{

    @Resource
    private ArticleEsDao articleEsDao;

     private int curIndex = -1;
    /**
     * 存储标签树所对应的先序遍历
     */
    private String[] dirArr =
            {
                    "后端","Java","SpringBoot","",
                                    "SpringMVC","",
                                    "Spring","","",
                            "C++","",
                            "Linux","",
                            "Go","",
                            "Python","","",
                    "前端","CSS","",
                            "JavaScript","",
                            "Vue","",
                            "React","","",
                    "数据库","",
                    "算法","",
                    "实习","简历","",""
            };
    /**
     * label存储所有的标签
     */
    private List<String> labels = Arrays.stream(dirArr)
            .filter(s -> !s.equals(""))
            .collect(Collectors.toList());
    /**
     * 标签树
     */
    private DirectoryVO directoryVO = new DirectoryVO("我的目录",null);

    public DirectoryVO getDirectoryVO() {
        return directoryVO;
    }

    @Override
    public void run(String... args){
        //构建目录树;这个肯定用到了递归🐢
        directoryVO.setChildren(createTree());
    }



    /**
     * 构建目录树
     * @return
     */
//    private List<DirectoryVO> createTree(){
    public List<DirectoryVO> createTree(){
        curIndex++;
        List<DirectoryVO> list = new ArrayList<>();
        for(;curIndex < dirArr.length;curIndex++){
            if(dirArr[curIndex]==null || "".equals(dirArr[curIndex])){
                //表示这一层没有目录了
                return list;
            }
            DirectoryVO directoryVO = new DirectoryVO(dirArr[curIndex], null);
            directoryVO.setChildren(createTree());
            list.add(directoryVO);
        }
        return list;
    }

    /**
     * 根据标签在文章中出现的次序进行排序
     * @param article
     * @return
     */
    public Article SortedOfLabel(Article article){
        String content = article.getContent();
        Map<String,Integer> map = new HashMap<>();
        for(int i = 0;i < labels.size();i++){
            String label = labels.get(i);
            String regex = "(?i)"+label;
            Pattern compile = Pattern.compile(regex);
            Matcher matcher = compile.matcher(content);
            int count = 0;
            while(matcher.find()){
                count++;
            }
            map.put(label,count);
        }
        ArrayList<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());
        //根据value排序;降序
        List<Map.Entry<String, Integer>> labelSortByCount = list.stream().sorted((entry1, entry2) -> entry2.getValue() - entry1.getValue()).collect(Collectors.toList());
//        List<Map.Entry<String, Integer>> labelSortByCount = list.stream().sorted(Comparator.comparingInt(Map.Entry::getValue).reversed()).collect(Collectors.toList());
        //IDEA好智能,下面是它自己搞的
//        List<Map.Entry<String, Integer>> labelSortByCount = list.stream().sorted(Comparator.comparing(entry::getValue).reversed()).collect(Collectors.toList());
        LinkedHashMap<String,Integer> result = new LinkedHashMap<>();
        for(Map.Entry<String, Integer> entry:labelSortByCount){
            result.put(entry.getKey(),entry.getValue());
        }

        List<String> dirPath = getDirPath(result);
        List<String> tags = getTags(result);
        article.setTags(tags.toString());article.setPath(dirPath.toString());
        return article;
    }

    /**
     * 获取存储路径
     * 取出出现最多的一次，然后进行细分
     * @param map
     */
    public List<String> getDirPath(Map<String,Integer> map){
        //获取第一个出现了最多次关键词的目录
        String label =(String) map.keySet().toArray()[0];
        //从目录树中遍历出来该标签位于目录的哪里(深度优先遍历)
//        List<String> list = new ArrayList<>();
//        list.add("我的目录");
        List<String> list = dept(label, directoryVO, map);
        return  list;
    }

    private List<String> dept(String label,DirectoryVO directoryVO,Map<String,Integer> map){
        List<String> list = new ArrayList<>();
        list.add("我的目录");
        DirectoryVO directoryVO1 = dept(label, directoryVO, list);

        //接下来找到最深的路径
        return deepPath(directoryVO1,map,list);
    }

    /**
     * 通过最多关键词，确定最深路径
     * @param directoryVO
     * @param map
     * @param list
     * @return
     */
    public List<String> deepPath(DirectoryVO directoryVO, Map<String, Integer> map,List<String> list){
//        if(directoryVO==null){
//        }
        int max = 0;
        DirectoryVO t = null;
        for(DirectoryVO d : directoryVO.getChildren()){
            if(map.get(d.getDirName()) > max){
                max = map.get(d.getDirName());
                t = d;
            }
        }
        if(t == null){
            return list;
        }
        list.add(t.getDirName());
        return deepPath(t,map,list);
    }
    /**根据子目录找路径
     * @param label
     * @param directoryVO
     * @param path
     * @return
     */
    private DirectoryVO dept(String label,DirectoryVO directoryVO,List<String> path) {
        if(directoryVO == null){
            return null;
        }
        if(label.equals(directoryVO.getDirName())){
            path.add(directoryVO.getDirName());
            return directoryVO;
        }
        for(DirectoryVO child:directoryVO.getChildren()){
            //操作数
//            String dirName = child.getDirName();
//            path.add(dirName);
//            if(label.equals(dirName)){
//                //找到了
//                return  child;
//            }
            //没找到,继续递🐢
            DirectoryVO res = dept(label, child, path);
            if(res!=null){
                //这个路径中有label
                return res;
            }
            //返回现场
//            path.remove(path.size()-1);
        }
        return null;
    }

    /**
     * 按照关键字出现次数排序,选取前6个
     * @param map
     * @return
     */
    public List<String> getTags(Map<String,Integer> map){
        Set<String> set = map.keySet();
        List<String> tags = new ArrayList<>(set);
        List<String> list = new ArrayList<>(tags);
        return list.subList(0, (list.size() >= 6 ? 6 : list.size()));
    }

    /**
     * 验证目录的正确性
     * @param path
     * @return
     */
    public boolean isDir(String path) {
        JSONArray array = JSONUtil.parseArray(path);
        List<String> list = array.toList(String.class);
        //深度搜索
        return isExist(directoryVO,list,1);
    }

    /**
     * 判断路径是否存在
     * @param directoryVO
     * @param list
     * @param index
     * @return
     */
    private boolean isExist(DirectoryVO directoryVO,List<String> list,int index){
        if(index == list.size()){
            return true;
        }
        String s = list.get(index);
        for(DirectoryVO d : directoryVO.getChildren()){
            if(d.getDirName().equals(s)){
                return isExist(d,list,index+1);
            }
        }
        return false;
    }

    /**
     * 根据路径获取其下的所有文件
     * @param path
     * @return
     */
    public PathOfFile searchAllChildByPath(String path) {

        //获取到路径
        JSONArray array = JSONUtil.parseArray(path);
        List<String> list = array.toList(String.class);
        //获取最后一层目录下的DirectoryVO对象
        DirectoryVO d = dept(list.get(list.size() - 1), directoryVO, new ArrayList<>());
        PathOfFile pathOfFile = new PathOfFile();
        pathOfFile.setId(list.size()+"");
        pathOfFile.setName(list.get(list.size()-1));
        pathOfFile.setPath(list.toString());
        //开始找它的孩子啦
        searchMyChild(d,pathOfFile);
        return pathOfFile;
    }

    /**
     * 找me的孩子，所有孩子（目录，文章）
     * @param directoryVO
     * @param me
     */
    public void searchMyChild(DirectoryVO directoryVO,PathOfFile me){
        if(directoryVO==null){
            //空的孩子
            return;
        }
        //找到了所有的文章孩子
//        List<ArticleEsDTD> myArticleChildren = articleEsDao.findAllByPath(me.getPath());
        List<ArticleEsDTD> myArticleChildren = articleEsDao.findByPath(me.getPath());
        me.setArticleEsDTDList(myArticleChildren);
        //找到所有的孩子目录
        JSONArray array = JSONUtil.parseArray(me.getPath());
        List<String> list = array.toList(String.class);
        List<PathOfFile> children = new ArrayList<>();
        if(directoryVO.getChildren()==null || directoryVO.getChildren().isEmpty()){
            //说明没有孩子了
            return;
        }
        for(DirectoryVO d : directoryVO.getChildren()){
            List<String> tmp = new ArrayList<>(list);
            tmp.add(d.getDirName());
            PathOfFile pathOfFile = new PathOfFile();
            pathOfFile.setPath(tmp.toString());
            pathOfFile.setName(d.getDirName());
            pathOfFile.setId((Integer.parseUnsignedInt(me.getId())+1)+"");
            searchMyChild(d,pathOfFile);
            children.add(pathOfFile);
        }
        me.setPathOfFileList(children);
    }
}
