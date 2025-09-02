package com.ilhaha.yueyishou.customer.config;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author ilhaha
 * @Create 2024/8/17 09:51
 * @Version 1.0
 */
@Configuration
public class WxConfigOperator {

    @Resource
    private WxConfigProperties wxConfigProperties;

    @Bean
    public WxMaService wxService(){
        WxMaDefaultConfigImpl wxMaConfig = new WxMaDefaultConfigImpl();
        wxMaConfig.setAppid(wxConfigProperties.getAppId());
        wxMaConfig.setSecret(wxConfigProperties.getSecret());

        WxMaServiceImpl wxMaService = new WxMaServiceImpl();
        wxMaService.setWxMaConfig(wxMaConfig);
        return wxMaService;
    }
}
