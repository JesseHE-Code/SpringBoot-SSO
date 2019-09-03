package com.example.ssoapp2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SsoApp2Application {

    public static void main(String[] args) {
        SpringApplication.run(SsoApp2Application.class, args);
    }

}
