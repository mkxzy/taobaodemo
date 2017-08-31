package com.gyxr.taobaodemo.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.gyxr.taobaodemo.domain.Product;
import com.gyxr.taobaodemo.taobao.ContentParser;

import java.util.ArrayList;
import java.util.List;

public class ProductListParser implements ContentParser<List<Product>> {

    @Override
    public List<Product> parse(JsonNode jsonNode) {

        JsonNode productListNode = jsonNode.get("items_onsale_get_response")
                .get("items")
                .get("item");
        List<Product> list = new ArrayList<>();
        productListNode.forEach(p ->{
            Product product = new Product();
            product.setTitle(p.get("title").asText());
            product.setPrice(p.get("price").asDouble());
            product.setOuterId(p.get("num_iid").asLong());
            list.add(product);
        });
        return list;
    }
}
