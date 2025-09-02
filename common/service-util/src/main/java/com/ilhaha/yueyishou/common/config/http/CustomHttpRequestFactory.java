package com.ilhaha.yueyishou.common.config.http;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.protocol.HttpClientContext;
import org.apache.hc.core5.http.protocol.HttpContext;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import java.net.URI;

/**
 * @author: chenwenjing
 * @date: 2024/3/26 16:35
 * @decription:
 **/
public class CustomHttpRequestFactory extends HttpComponentsClientHttpRequestFactory {

    private HttpClient httpClient;

    public CustomHttpRequestFactory(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    protected HttpContext createHttpContext(HttpMethod httpMethod, URI uri) {
        RequestConfig config = HttpContextHolder.peek();
        if (config != null) {
            HttpContext context = HttpClientContext.create();

            context.setAttribute(HttpClientContext.REQUEST_CONFIG, config);
            return context;
        }
        return super.createHttpContext(httpMethod, uri);
    }
}
