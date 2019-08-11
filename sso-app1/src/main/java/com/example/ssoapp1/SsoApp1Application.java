package com.example.ssoapp1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SsoApp1Application {

    public static void main(String[] args) {
        SpringApplication.run(SsoApp1Application.class, args);
    }

}
