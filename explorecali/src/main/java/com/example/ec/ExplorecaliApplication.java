package com.example.ec;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Class for the Spring Boot Application
 *
 * Created by Mary Ellen Bowman
 */
@SpringBootApplication
//@EnableSwagger2
public class ExplorecaliApplication {
//    @Bean
//    public Docket docket() {
//        return new Docket(DocumentationType.SWAGGER_2).select()
//                .apis(RequestHandlerSelectors.basePackage("com.example.ec")).paths(any()).build()
//                .apiInfo(new ApiInfo("Explore California API's",
//                "API's for the Explore California Travel Service", "2.0", null,
//                new Contact("LinkedIn Learning","https://www.linkedin.com/learning", ""),
//                null, null, new ArrayList()));
//    }

	public static void main(String[] args) {
		SpringApplication.run(ExplorecaliApplication.class, args);
		System.out.println("Hello :] ");
	}

}
