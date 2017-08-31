package com.gyxr.taobaodemo.taobao;

/**
 * Created by xiezhiyan on 17-8-31.
 */
public class TaobaoApiResult {

    private String rawString;

    public TaobaoApiResult(String rawString){
        this.rawString = rawString;
    }

    public String getRawString() {
        return rawString;
    }

    public <T> T getObject(ContentDecoder<T> decoder){
        return decoder.decode(this.rawString);
    }
}
