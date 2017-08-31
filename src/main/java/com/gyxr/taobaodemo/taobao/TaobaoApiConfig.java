package com.gyxr.taobaodemo.taobao;


//import com.taobao.api.internal.util.WebUtils;

/**
 * 淘宝接口代理类
 */
public class TaobaoApiConfig {

    private String url = "https://gw.api.tbsandbox.com/router/rest";

    public String getAppKey() {
        return appKey;
    }

    private String appKey;

    public String getSecret() {
        return secret;
    }

    private String secret;

    public TaobaoApiConfig(String appKey, String secret){

        this.appKey = appKey;
        this.secret = secret;
    }

    public String getUrl() {
        return url;
    }
}
