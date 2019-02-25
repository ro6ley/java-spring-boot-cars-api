package com.example.cars;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.builders.ApiInfoBuilder;

@Configuration
@EnableSwagger2
public class SwaggerConfig {                                    
    @Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)  
          .select()                                  
          .apis(RequestHandlerSelectors.basePackage("com.example.cars"))
          .paths(PathSelectors.any())
          .build()
          .apiInfo(metadata());                                           
    }

    /**
     * Adds metadata to Swagger
     *
     * @return
     */
    private ApiInfo metadata() {
      return new ApiInfoBuilder()
              .title("Cars API")
              .description("An API to store car details built using Spring Boot")
              .build();
  }    
}
