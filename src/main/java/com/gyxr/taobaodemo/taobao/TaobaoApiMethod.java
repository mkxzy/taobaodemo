package com.gyxr.taobaodemo.taobao;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 淘宝抽象接口
 * 负责处理方法和参数
 */
public abstract class TaobaoApiMethod {

    public abstract TaobaoApiMethod setParam(String name, ApiParam value);

    public abstract TaobaoApiMethod setAccessToken(String accessToken);

    public abstract TaobaoApiResult call() throws IOException;

    public static TaobaoApiMethod create(String methodName, TaobaoApiConfig config){

        return new DynamicMethod(methodName, config);
    }

    private static class DynamicMethod<T> extends TaobaoApiMethod {

        private String version = "2.0";
        private String signMethod = "hmac";
        private String format = "json";
        private String accessToken;
        private String methodName;
        private Map<String, ApiParam> params = new HashMap<>();
        private TaobaoApiConfig config;

        public DynamicMethod(String methodName, TaobaoApiConfig config){

            this.methodName = methodName;
            this.config = config;
//            this.accessToken = accessToken;
        }

//        @Override
        private String getAccessToken() {
            return this.accessToken;
        }

//        @Override
        private String getName() {

            return this.methodName;
        }

        @Override
        public TaobaoApiMethod setParam(String name, ApiParam value) {
            params.put(name, value);
            return this;
        }

        @Override
        public TaobaoApiMethod setAccessToken(String accessToken) {
            this.accessToken = accessToken;
            return this;
        }

        //        @Override
        private Map<String, String> toApiParams() {

            Map<String, String> map = new HashMap<>();
            for(Map.Entry<String, ApiParam> entry: this.params.entrySet()){
                map.put(entry.getKey(), entry.getValue().stringfy());
            }
            return map;
        }

        @Override
        public TaobaoApiResult call() throws IOException {

            String accessToken = this.getAccessToken();
            Map<String, String> paramss = this.toApiParams();
            paramss.put("v", version);
            paramss.put("app_key", config.getAppKey());
            if(accessToken != null && !accessToken.equals("")) {
                paramss.put("session", accessToken);
            }
            paramss.put("sign_method", signMethod);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            paramss.put("timestamp", LocalDateTime.now().format(formatter));
            paramss.put("format", format);
            paramss.put("method", this.getName());
            try {
                paramss.put("sign", SignHelper.signTopRequest(paramss, config.getSecret()));
            } catch (IOException e) {
                e.printStackTrace();
            }

                CloseableHttpClient httpclient = HttpClients.createDefault();
                HttpPost post = new HttpPost(config.getUrl());
                post.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
                List<NameValuePair> pairs = paramss.entrySet().stream()
                        .map(p -> new BasicNameValuePair(p.getKey(), p.getValue()))
                        .collect(Collectors.toList());
                StringEntity stringEntity = new StringEntity(
                        URLEncodedUtils.format(pairs, StandardCharsets.UTF_8),
                        StandardCharsets.UTF_8);
                post.setEntity(stringEntity);
                CloseableHttpResponse response = httpclient.execute(post);
                String result = IOUtils.toString(response.getEntity().getContent(), StandardCharsets.UTF_8);
                System.out.println(result);
                return new TaobaoApiResult(result);
        }
    }
}
