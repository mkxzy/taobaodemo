package com.gyxr.taobaodemo.taobao;


//import com.taobao.api.internal.util.WebUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
