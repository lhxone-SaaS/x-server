package org.sang.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.spi.service.contexts.SecurityContext;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;


@Configuration
@EnableSwagger2
@EnableKnife4j
@Import(BeanValidatorPluginsConfiguration.class)
public class Knife4jConfig {

    @Bean
    public Docket createRestApi(){

        Predicate<RequestHandler> selector1 = RequestHandlerSelectors
                .basePackage("org.sang.controller");

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("VBlog博客平台")
                .apiInfo(apiInfo())
                .select()
                .apis(selector1)
                .paths(PathSelectors.any())
                .build()
                .securityContexts(securityContexts())
                .securitySchemes(securitySchemas());

    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("VBlog博客平台RESTful APIs")
                .description("VBlog博客平台 api接口文档")
                .version("1.0")
                .build();

    }

    private List<SecurityScheme> securitySchemas(){
        List<SecurityScheme> list = new ArrayList();
        list.add(new ApiKey("loginToken", "loginToken", "header"));
        return list;
    }


    private List<SecurityReference> securityReferences(){
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[]
                {new AuthorizationScope("global", "accessEverything")};
        List<SecurityReference> securityReferences = new ArrayList<>();
        securityReferences.add(new SecurityReference("loginToken", authorizationScopes));
        return securityReferences;
    }


    private List<SecurityContext> securityContexts(){

        List<SecurityContext> list = new ArrayList();
        SecurityContext securityContext = SecurityContext.builder()
                .securityReferences(securityReferences())
                .forPaths(PathSelectors.regex("/*"))
                .build();
        list.add(securityContext);
        return list;
    }
}
