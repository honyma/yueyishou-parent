package com.ilhaha.yueyishou.common.config.http;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * http config
 *
 * @author mazehong
 * @date 2023/6/2
 */
@Configuration
@ConditionalOnClass(value = {RestTemplate.class, HttpClient.class})
public class RestTemplateConfig {

    @Value("${http.rest.remote.maxTotalConnect:400}")
    private int maxTotalConnect;

    @Value("${http.rest.remote.maxConnectPerRoute:400}")
    private int maxConnectPerRoute;

    @Value("${http.rest.remote.connectTimeout:2000}")
    private int connectTimeout;

    @Value("${http.rest.remote.readTimeout:2000}")
    private int readTimeout;

    @Value("${http.rest.remote.connectionRequestTimeout:2000}")
    private int connectionRequestTimeout;

    /**
     * 创建HTTP客户端工厂
     */
    private ClientHttpRequestFactory createFactory() {
        HttpClient httpClient = HttpClientBuilder.create().build();
        //use custom define factory

        CustomHttpRequestFactory factory = new CustomHttpRequestFactory(httpClient);
        factory.setConnectTimeout(this.connectTimeout);
        factory.setConnectionRequestTimeout(this.connectionRequestTimeout);
        return factory;
    }

    @Bean
    @ConditionalOnMissingBean(RestTemplate.class)
    @Scope("singleton")
    public RestTemplate getRestTemplate() {
        return new RestTemplate(this.createFactory());
    }
}