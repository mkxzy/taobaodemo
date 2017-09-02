package com.gyxr.taobaodemo.config;

import com.gyxr.taobaodemo.taobao.TaobaoApiConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by xiezhiyan on 17-8-31.
 */
@Configuration
public class TaobaoConfig {

    private String url = "https://gw.api.tbsandbox.com/router/rest";
    private final String appkey = "1021035674";
    private final String secret = "sandbox68cb8f35e1d06f8154c1551de";

    @Bean
    public TaobaoApiConfig taobaoApiConfig(){

        return new TaobaoApiConfig(url, appkey, secret);
    }
}
