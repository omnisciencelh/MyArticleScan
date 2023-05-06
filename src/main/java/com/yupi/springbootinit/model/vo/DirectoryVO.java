package com.yupi.springbootinit.model.vo;

import lombok.Data;

import java.util.List;

//@Data
public class DirectoryVO {
    /**
     * 目录名称
     */
    private String dirName;
    /**
     * 子目录或者文件
     */
    private List<DirectoryVO> children;

    public DirectoryVO(String name,List<DirectoryVO> obj){
        this.dirName = name;
        this.children = obj;
    }

    public void setChildren(List<DirectoryVO> children) {
        this.children = children;
    }

    public void setDirName(String dirName) {
        this.dirName = dirName;
    }

    public List<DirectoryVO> getChildren() {
        return children;
    }

    public String getDirName() {
        return dirName;
    }

    @Override
    public String toString() {
        return
                "dirName='" + dirName + '\'' +
                ", children=" + (children.isEmpty()?null:children) ;
    }
}
