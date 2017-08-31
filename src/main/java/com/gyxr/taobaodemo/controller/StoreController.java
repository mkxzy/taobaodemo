package com.gyxr.taobaodemo.controller;

import com.gyxr.taobaodemo.domain.Store;
import com.gyxr.taobaodemo.repository.StoreRepository;
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

//        TaobaoApiConfig api = new TaobaoApiConfig(appkey, secret);
//        TaobaoMethod request = TaobaoMethod.create("taobao.shop.get")
//                .setParam("fields", ApiParam.fromString("sid,cid,title,nick,desc,bulletin,pic_path,created,modified"))
//                .setParam("nick", ApiParam.fromString(nick));
//        Store store = api.call(request).convert(new StoreParser());
//
//        TaobaoMethod request2 = TaobaoMethod.create("taobao.items.onsale.get")
//                .setParam("fields", ApiParam.fromArray("num_iid", "title", "price"));
//        List<Product> productList = api.call(request2, access_token).convert(new ProductListParser());
//        store.setProducts(productList);
//        storeRepository.save(store);
    }

    @GetMapping
    public List<Store> findAll(){

        return storeRepository.findAll();
    }
}
