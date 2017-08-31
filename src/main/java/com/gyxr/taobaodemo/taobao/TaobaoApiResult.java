package com.gyxr.taobaodemo.taobao;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * 淘宝API响应
 */
public class TaobaoApiResult {

    private final String rawString;

    public TaobaoApiResult(String rawString){

        this.rawString = rawString;
    }

    public String getRawString() {

        return rawString;
    }

    public <T> T convert(ContentParser<T> parser){

        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = null;
        try {
            node = mapper.readTree(rawString);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return parser.parse(node);
    }
}
