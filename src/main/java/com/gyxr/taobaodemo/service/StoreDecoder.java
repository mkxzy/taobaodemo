package com.gyxr.taobaodemo.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gyxr.taobaodemo.domain.Store;
import com.gyxr.taobaodemo.taobao.ContentDecoder;

import java.io.IOException;

public class StoreDecoder implements ContentDecoder<Store> {

    @Override
    public Store decode(String rawString) {

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = mapper.readTree(rawString);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Store store = new Store();
        System.out.println(jsonNode.toString());
        String title = jsonNode.get("shop_get_response")
                .get("shop")
                .get("title")
                .asText();
        store.setTitle(title);
        return store;
    }
}
