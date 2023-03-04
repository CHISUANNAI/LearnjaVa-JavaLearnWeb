package com.example.last;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.example.last.mapper")
@SpringBootApplication
public class LastApplication {

    public static void main(String[] args) {
        SpringApplication.run(LastApplication.class, args);
    }

}
