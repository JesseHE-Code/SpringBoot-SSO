package com.example.ssoservernew;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages={"com.example.ssoservernew", "com.example.ssoservernew.config", "com.example.ssoservernew.controller","com.example.ssoservernew.dao", "com.example.ssoservernew.service"})
public class SsoServerNewApplication {

    public static void main(String[] args) {
        SpringApplication.run(SsoServerNewApplication.class, args);
    }

}
