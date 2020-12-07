package com.antony.springpractice.extention;

import org.springframework.boot.CommandLineRunner;

public class TestCommandLineRunner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        System.out.println("触发时机为整个项目启动完毕后，自动执行");
    }
}