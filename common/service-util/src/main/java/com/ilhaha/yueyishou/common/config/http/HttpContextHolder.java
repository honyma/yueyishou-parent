package com.ilhaha.yueyishou.common.config.http;


import org.apache.hc.client5.http.config.RequestConfig;

/**
 * @author chenwj1
 */
public class HttpContextHolder {

    private static final ThreadLocal<RequestConfig> threadLocal = new ThreadLocal<>();

    public static synchronized void bind(RequestConfig requestConfig) {
        threadLocal.set(requestConfig);
    }

    public static RequestConfig peek() {
        return threadLocal.get();
    }

    public static void unbind() {
        threadLocal.remove();
    }
}