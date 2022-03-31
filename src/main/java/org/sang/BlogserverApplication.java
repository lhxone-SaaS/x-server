package org.sang;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Repository;

@SpringBootApplication
@EnableScheduling//开启定时任务支持
@EnableConfigurationProperties
@MapperScan(basePackages={"org.sang.mapper"},annotationClass = Repository.class)
public class BlogserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogserverApplication.class, args);
    }
}
