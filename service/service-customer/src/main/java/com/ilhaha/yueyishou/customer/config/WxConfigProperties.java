package com.ilhaha.yueyishou.customer.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author ilhaha
 * @Create 2024/8/17 09:47
 * @Version 1.0
 */
@Component
@ConfigurationProperties("wx.miniapp")
@Data
public class WxConfigProperties {
    private String appId;
    private String secret;
}
