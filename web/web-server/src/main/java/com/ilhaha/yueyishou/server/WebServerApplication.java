package com.ilhaha.yueyishou.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author ilhaha
 * @Create ${DATE} ${TIME}
 * @Version 1.0
 */
@SpringBootApplication(scanBasePackages = "com.ilhaha.yueyishou", exclude = {DataSourceAutoConfiguration.class})
@EnableFeignClients(basePackages = "com.ilhaha.yueyishou")
@EnableDiscoveryClient
public class WebServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebServerApplication.class,args);
    }
}