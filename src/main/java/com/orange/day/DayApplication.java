package com.orange.day;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
@MapperScan(value = "com.orange.day.dao")
public class DayApplication {

    public static void main(String[] args) {
        SpringApplication.run(DayApplication.class, args);
    }

}
