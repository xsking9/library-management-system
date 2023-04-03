package com.southwind;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.southwind.mapper")
public class LibSpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibSpringbootApplication.class, args);
    }

}
