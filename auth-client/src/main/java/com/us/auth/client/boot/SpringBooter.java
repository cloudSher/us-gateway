package com.us.auth.client.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by Administrator on 2017/1/9.
 */
@EnableWebMvc
@SpringBootApplication
@ComponentScan(basePackages = "com.us.auth.client")
@EnableOAuth2Sso
public class SpringBooter {


    public static void main(String args[]){
        SpringApplication.run(SpringBooter.class, args);
    }


}
