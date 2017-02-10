package com.us.auth.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by Administrator on 2017/1/9.
 */
@EnableWebMvc
@SpringBootApplication
@ComponentScan(basePackages = "com.us.auth")
@EnableOAuth2Client
@EnableAuthorizationServer
public class SpringBooter {


    public static void main(String args[]){
        SpringApplication.run(SpringBooter.class, args);
    }


}
