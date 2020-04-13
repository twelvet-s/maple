package com.twelvet.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: 启动程序
 */
@SpringBootApplication
@EnableDiscoveryClient
public class TwelveTApplication {
    public static void main(String[] args) {
        SpringApplication.run(TwelveTApplication.class, args);
    }
}
