-- 创建库
create database if not exists my_article;


-- article表
CREATE TABLE article (
    id             bigint  NOT NULL  PRIMARY KEY COMMENT '文章的id',
    title          text    NOT NULL COMMENT '文章标题',
    description    text            COMMENT '文章描述',
    content        text    NOT NULL COMMENT '文章内容',
    category       varchar(20) NOT NULL DEFAULT '文章' COMMENT '文章类型',
    cover          text            COMMENT '文章封面',
    createTime     datetime        DEFAULT CURRENT_TIMESTAMP COMMENT '文章创建时间，默认为当前时间',
    updateTime     datetime        DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '文章更新时间，默认为修改时间',
    tags           varchar(1024)   COMMENT '文章标签',
    path            varchar(1024)   COMMENT '文章路劲'
) ENGINE=InnoDB CHARSET=UTF8MB4 COMMENT '存储文章相关信息的表';

# ALTER TABLE article ADD path varchar(1024);
