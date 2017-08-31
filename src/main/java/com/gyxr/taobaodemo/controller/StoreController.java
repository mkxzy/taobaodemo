package com.gyxr.taobaodemo.controller;

import com.gyxr.taobaodemo.domain.Product;
import com.gyxr.taobaodemo.domain.Store;
import com.gyxr.taobaodemo.repository.StoreRepository;
import com.gyxr.taobaodemo.service.ProductListDecoder;
import com.gyxr.taobaodemo.service.StoreDecoder;
import com.gyxr.taobaodemo.taobao.ApiParam;
import com.gyxr.taobaodemo.taobao.TaobaoApiConfig;
import com.gyxr.taobaodemo.taobao.TaobaoApiMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/store")
public class StoreController {

    @Autowired
    private StoreRepository storeRepository;

    private Logger logger = LoggerFactory.getLogger(StoreController.class);

    private final String appkey = "1021035674";
    private final String secret = "sandbox68cb8f35e1d06f8154c1551de";
    private final String access_token = "6201102109ZZ62efbd88104be840af68a15872a9d067575182558410";

    @Autowired
    private Environment env;

    @PostMapping
    public void add(String nick) throws IOException {

        TaobaoApiConfig api = new TaobaoApiConfig(appkey, secret);
        TaobaoApiMethod request = TaobaoApiMethod.create("taobao.shop.get", api)
                .setParam("fields", ApiParam.fromString("sid,cid,title,nick,desc,bulletin,pic_path,created,modified"))
                .setParam("nick", ApiParam.fromString(nick));
//                .setDecoder(new StoreDecoder());
        Store store = request.call().getObject(new StoreDecoder());

        TaobaoApiMethod request2 = TaobaoApiMethod.create("taobao.items.onsale.get", api)
                .setAccessToken(access_token)
                .setParam("fields", ApiParam.fromArray("num_iid", "title", "price"));
        List<Product> productList = request2.call().getObject(new ProductListDecoder());
        store.setProducts(productList);
        storeRepository.save(store);
    }

    @GetMapping
    public List<Store> findAll(){

        return storeRepository.findAll();
    }
}
