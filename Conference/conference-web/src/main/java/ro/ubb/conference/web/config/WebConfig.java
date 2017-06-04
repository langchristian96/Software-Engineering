package ro.ubb.conference.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.*;
import ro.ubb.conference.web.security.CorsFilter;

import javax.servlet.Filter;

@Configuration
@EnableWebMvc
@ComponentScan({"ro.ubb.conference.web.controller", "ro.ubb.conference.web.converter"})
public class WebConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer(){
        return new WebMvcConfigurerAdapter(){
            public void addCorsMapping(CorsRegistry registry){
                registry.addMapping("/**").allowedOrigins("*")
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowedHeaders("*")
                        //.allowedHeaders("x-test-header", "Origin", "X-Requested-With", "Content-Type", "Accept")
                        .allowedOrigins("http://localhost:4200", "http://localhost:8080");

            }

        };
    }

    @Bean
    public Filter someFilterRegistration() {

        return new CorsFilter();
    }


}

