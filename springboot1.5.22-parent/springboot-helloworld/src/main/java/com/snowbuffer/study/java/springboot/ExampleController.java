package com.snowbuffer.study.java.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class ExampleController {

    @RequestMapping("/")
    public String home() throws InterruptedException {
        System.out.println("========");
//        Thread.sleep(10_000);
        System.out.println("完成========");
        return "Hello World!!!";
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(ExampleController.class, args);
    }
}



