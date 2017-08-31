package com.gyxr.taobaodemo.taobao;

import com.fasterxml.jackson.databind.JsonNode;

public interface ContentParser<T> {

    T parse(JsonNode jsonNode);
}