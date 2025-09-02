package com.ilhaha.yueyishou.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author ilhaha
 * @Create ${DATE} ${TIME}
 * @Version 1.0
 */
@SpringBootApplication(scanBasePackages = "com.ilhaha.yueyishou")
@EnableFeignClients(basePackages = "com.ilhaha.yueyishou")
@EnableDiscoveryClient
@MapperScan("com.ilhaha.yueyishou.*.mapper")
public class ServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }
}