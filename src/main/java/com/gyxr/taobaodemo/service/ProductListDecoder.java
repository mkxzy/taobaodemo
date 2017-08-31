package com.gyxr.taobaodemo.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gyxr.taobaodemo.domain.Product;
import com.gyxr.taobaodemo.taobao.ContentDecoder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProductListDecoder implements ContentDecoder<List<Product>> {

    @Override
    public List<Product> decode(String rawString) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = mapper.readTree(rawString);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
