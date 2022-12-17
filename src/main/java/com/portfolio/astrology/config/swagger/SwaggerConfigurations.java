package com.portfolio.astrology.config.swagger;

import com.portfolio.astrology.model.User;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfigurations {

        public static final String AUTHORIZATION_HEADER = "Authorization";
    @Bean
    public Docket astrologyApi() {


        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.portfolio.astrology"))
                .paths(PathSelectors.ant("/**"))
                        .build()
                        .ignoredParameterTypes(User.class);
    }

}
