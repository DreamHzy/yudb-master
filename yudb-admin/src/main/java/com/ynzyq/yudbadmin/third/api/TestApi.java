package com.ynzyq.yudbadmin.third.api;

import com.alibaba.fastjson.JSON;
import com.ynzyq.yudbadmin.third.dto.ModifyStoreStatusDTO;
import com.ynzyq.yudbadmin.util.Encrypt;
import com.ynzyq.yudbadmin.util.HttpUtil;
import lombok.extern.slf4j.Slf4j;


/**
 * @author xinchen
 * @date 2021/12/2 18:09
 * @description:
 */
@Slf4j
public class TestApi {

    public static void main(String[] args) {
        TestApi testApi = new TestApi();
        testApi.modifyStoreStatus();
    }

    public void modifyStoreStatus() {
        String url = "http://123.124.145.57:7891/yudb/modifyStoreStatus";
        ModifyStoreStatusDTO modifyStoreStatusDTO = new ModifyStoreStatusDTO();
        String uid = "1112222";
        String timeStamp = System.currentTimeMillis() + "";
        modifyStoreStatusDTO.setUid(uid);
        modifyStoreStatusDTO.setTimeStamp(timeStamp);
        String signStr = uid + "&" + timeStamp;
        String sign = null;
        try {
            sign = Encrypt.AESEncryptByKey(signStr, "uh8yLn1KIq59FvA3");
        } catch (Exception e) {
            e.printStackTrace();
        }
        modifyStoreStatusDTO.setSign(sign);
//        log.info("请求参数：{}", JSON.toJSONString(modifyStoreStatusDTO));
        System.out.println("请求参数：" + JSON.toJSONString(modifyStoreStatusDTO));
        String s = HttpUtil.sendByPostJson(url, JSON.toJSONString(modifyStoreStatusDTO));
//        log.info("响应参数：{}", s);
        System.out.println("响应参数：" + s);
    }
}
