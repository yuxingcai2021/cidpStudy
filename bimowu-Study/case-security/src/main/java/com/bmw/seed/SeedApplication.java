package com.bmw.seed;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@MapperScan(value = "com.bmw.seed.dao")
@ServletComponentScan(basePackages = {"com.bmw.seed.filter"})//这一句完成了配置，Springboot的"懒概念"
public class SeedApplication {
	public static void main(String[] args) {
		SpringApplication.run(SeedApplication.class, args);
	}


}

