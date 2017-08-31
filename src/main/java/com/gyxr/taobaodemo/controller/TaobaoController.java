package com.gyxr.taobaodemo.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gyxr.taobaodemo.taobao.ApiParam;
import com.gyxr.taobaodemo.taobao.TaobaoApiConfig;
import com.gyxr.taobaodemo.taobao.TaobaoMethod;
import org.apache.commons.io.IOUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tb")
public class TaobaoController {

    private final String appkey = "1021035674";
    private final String secret = "sandbox68cb8f35e1d06f8154c1551de";
    private final String access_token = "6201102109ZZ62efbd88104be840af68a15872a9d067575182558410";

    @GetMapping("/product")
    public Object getProduct() throws IOException {

        TaobaoApiConfig api = new TaobaoApiConfig(appkey, secret);
        TaobaoMethod request = TaobaoMethod.create("taobao.items.onsale.get", access_token)
                .setParam("fields", ApiParam.fromArray("num_iid", "title", "price"));
        return request.call(api);
//        return api.call(request, access_token).convert(new ProductListParser());
    }

    @GetMapping("/seller")
    public Object getSeller() throws IOException {

        TaobaoApiConfig api = new TaobaoApiConfig(appkey, secret);
        TaobaoMethod request = TaobaoMethod.create("taobao.user.seller.get")
                .setParam("fields", ApiParam.fromString("user_id,nick,sex"));
//        return api.call(request, access_token).getRawString();
        return request.call(api);
    }

    @GetMapping("/shop")
    public Object getShop(String nick) throws IOException {

        TaobaoApiConfig api = new TaobaoApiConfig(appkey, secret);
        TaobaoMethod request = TaobaoMethod.create("taobao.shop.get")
                .setParam("fields", ApiParam.fromString("sid,cid,title,nick,desc,bulletin,pic_path,created,modified"))
                .setParam("nick", ApiParam.fromString(nick));
        return request.call(api);
//        ContentParser<Store> contentParser = new StoreParser();
//        return api.call(request).convert(contentParser);
    }

    @GetMapping("/token")
    public String getAccessToken(String code){
        Map<String, String> params = new HashMap<>();

        params.put("response_type", "code");
        params.put("client_id", "1021035674");
        params.put("redirect_uri", "http://mini.tbsandbox.com");
        params.put("state", "1212");
        params.put("scope", "item");
        params.put("view", "web");
        params.put("code", code);
        params.put("client_secret", "sandbox68cb8f35e1d06f8154c1551de");
        params.put("grant_type", "authorization_code");

        String tokenResult = null;
        try {
//            tokenResult = WebUtils.doPost("https://oauth.tbsandbox.com/token", params, 0, 0);
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpPost post = new HttpPost("https://oauth.tbsandbox.com/token");
            post.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
            List<NameValuePair> pairs = params.entrySet().stream()
                    .map(p -> new BasicNameValuePair(p.getKey(), p.getValue()))
                    .collect(Collectors.toList());
            StringEntity stringEntity = new StringEntity(
                    URLEncodedUtils.format(pairs, StandardCharsets.UTF_8),
                    StandardCharsets.UTF_8);
            post.setEntity(stringEntity);
            CloseableHttpResponse response = httpclient.execute(post);
            tokenResult = IOUtils.toString(response.getEntity().getContent(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(tokenResult);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = null;
        try {
            node = mapper.readTree(tokenResult);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String access_token  = node.get("access_token").asText();
        return access_token;
    }
}
