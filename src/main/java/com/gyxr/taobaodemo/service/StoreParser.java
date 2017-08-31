package com.gyxr.taobaodemo.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.gyxr.taobaodemo.domain.Store;
import com.gyxr.taobaodemo.taobao.ContentParser;

public class StoreParser implements ContentParser<Store> {

    @Override
    public Store parse(JsonNode jsonNode) {

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
