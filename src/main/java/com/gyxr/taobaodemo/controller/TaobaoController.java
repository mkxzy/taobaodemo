package com.gyxr.taobaodemo.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gyxr.taobaodemo.service.ProductListDecoder;
import com.gyxr.taobaodemo.service.StoreDecoder;
import com.gyxr.taobaodemo.taobao.ApiParams;
import com.gyxr.taobaodemo.taobao.TaobaoApiConfig;
import com.gyxr.taobaodemo.taobao.TaobaoApiMethod;
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

    private final String access_token = "6201102109ZZ62efbd88104be840af68a15872a9d067575182558410";

    private TaobaoApiConfig taobaoConfig;

    public TaobaoController(TaobaoApiConfig taobaoConfig){
        this.taobaoConfig = taobaoConfig;
    }

    @GetMapping("/product")
    public Object getProduct() throws IOException {

        TaobaoApiMethod request = TaobaoApiMethod.create("taobao.items.onsale.get", taobaoConfig)
                .setParam("fields", ApiParams.arrayParam("num_iid", "title", "price"))
                .setAccessToken(access_token);
        return request.call().getObject(new ProductListDecoder());
    }

    @GetMapping("/seller")
    public Object getSeller() throws IOException {

        TaobaoApiMethod request = TaobaoApiMethod.create("taobao.user.seller.get", taobaoConfig)
                .setParam("fields", ApiParams.stringParam("user_id,nick,sex"));
        return request.call();
    }

    @GetMapping("/shop")
    public Object getShop(String nick) throws IOException {

        TaobaoApiMethod request = TaobaoApiMethod.create("taobao.shop.get", taobaoConfig)
                .setParam("fields", ApiParams.stringParam("sid,cid,title,nick,desc,bulletin,pic_path,created,modified"))
                .setParam("nick", ApiParams.stringParam(nick));
        return request.call().getObject(new StoreDecoder());
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
