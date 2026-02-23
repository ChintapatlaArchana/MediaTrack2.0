package com.cts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class UserAuthenticationMain {
    public static void main(String[] args) {
        SpringApplication.run(UserAuthenticationMain.class, args);
    }
}
