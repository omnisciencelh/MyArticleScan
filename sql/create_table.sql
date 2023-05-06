
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
    tags           varchar(1024)   COMMENT '文章标签'
) ENGINE=InnoDB CHARSET=UTF8MB4 COMMENT '存储文章相关信息的表';



-- 下面的表不要管





-- 切换库
use my_article;

-- 问答表
CREATE TABLE query (
  id BIGINT NOT NULL PRIMARY KEY COMMENT '问答的id',
  question BIGINT NOT NULL COMMENT '问题的id',
  answer BIGINT NOT NULL COMMENT '回答的id',
  answered TINYINT DEFAULT 0 NOT NULL COMMENT '是否回答了 0：未删除；1删除',
  like_count INT DEFAULT 0 NOT NULL COMMENT '该话题点赞的人数',
  tags VARCHAR(1024) COMMENT '标签列表',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
);
-- 问题表
CREATE TABLE question (
  id BIGINT NOT NULL PRIMARY KEY COMMENT '问题的id，也就是querytext的id',
  questionee BIGINT NOT NULL COMMENT '提问用户的id',
  text TEXT NOT NULL COMMENT '提问者提问的内容'
) COMMENT='问题表';
-- 回答表
CREATE TABLE answer (
  id BIGINT NOT NULL COMMENT '回答的ID，对应于query表中的ID',
  owner BIGINT NOT NULL COMMENT '回答的用户ID',
  text TEXT NOT NULL COMMENT '回答者回答的内容'
) COMMENT='answer';

-- 文章表
CREATE TABLE text (
  id BIGINT NOT NULL PRIMARY KEY COMMENT '文章ID',
  talk BIGINT NOT NULL COMMENT 'talk类ID',
  likes_count INT DEFAULT 0 COMMENT '点赞数',
  tags VARCHAR(1024) COMMENT '标签列表',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
);
-- 话题表
CREATE TABLE talk (
  id BIGINT NOT NULL COMMENT '话题的ID，对应于text表中的ID',
  text TEXT NOT NULL COMMENT '话题的内容',
  image BIGINT NOT NULL COMMENT '图片的ID'
) COMMENT='存储所有话题的相关信息';

-- 用户表
CREATE TABLE user (
  id BIGINT NOT NULL PRIMARY KEY COMMENT '主键，用户id',
  name VARCHAR(20) NOT NULL COMMENT '用户名',
  avatar_url VARCHAR(1024) NOT NULL COMMENT '用户头像',
  description TEXT COMMENT '用户描述'
) COMMENT='用户表';

-- 图片表
CREATE TABLE thumbnail_image (
  id BIGINT NOT NULL COMMENT '图片的ID，作为主键',
  url VARCHAR(1024) NOT NULL COMMENT '图片的URL',
  type VARCHAR(20) NOT NULL COMMENT '图片的类型',
  width INT NOT NULL COMMENT '图片的宽度',
  height INT NOT NULL COMMENT '图片的高度',
  size INT COMMENT '图片的尺寸'
) COMMENT='thumbnail_image';
CREATE TABLE large_image (
  id BIGINT NOT NULL COMMENT '图片的ID，作为主键',
  url VARCHAR(1024) NOT NULL COMMENT '图片的URL',
  type VARCHAR(20) NOT NULL COMMENT '图片的类型',
  width INT NOT NULL COMMENT '图片的宽度',
  height INT NOT NULL COMMENT '图片的高度',
  size INT COMMENT '图片的尺寸'
) COMMENT='large_image';
CREATE TABLE original_image (
  id BIGINT NOT NULL COMMENT '图片的ID，作为主键',
  url VARCHAR(1024) NOT NULL COMMENT '图片的URL',
  type VARCHAR(20) NOT NULL COMMENT '图片的类型',
  width INT NOT NULL COMMENT '图片的宽度',
  height INT NOT NULL COMMENT '图片的高度',
  size INT COMMENT '图片的尺寸'
) COMMENT='original_image';
#
# ALTER TABLE text
#     ADD COLUMN tags VARCHAR(1024) COMMENT '标签列表';
