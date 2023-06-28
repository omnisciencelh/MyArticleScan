package com.yupi.springbootinit.job.once;

import org.junit.jupiter.api.Test;

class MyDirectoryTest {

    private MyDirectory myDirectory;

    @Test
    void test(){
        myDirectory=new MyDirectory();
        System.out.println(myDirectory.createTree());
    }

}