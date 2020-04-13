package com.twelvet;

import com.twelvet.system.annotation.EnableTFeignClients;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: 启动程序
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableTFeignClients
@EnableRedisHttpSession
@MapperScan("com.twelvet.**.mapper")
public class TwelveTApplication {
    public static void main(String[] args) {
        SpringApplication.run(TwelveTApplication.class, args);
    }
}
