package com.yunzyq.yudbapp.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class YgScUtil {

//        private static String eid = "210152612";//测试/**/
    private static String eid = "210122611";//正式
    private static String domain = "ynzyq";
    private static String key = "4c970cf42b73b221";

    public static String sendSubscriptionMsg(String name, String phone, String userId) {
        String url = "http://passport.yangguangqicai.com/openInterface/authorizelogin";

        String timestamp = System.currentTimeMillis() + "";
        String signature = SecurityUtil.MD5(userId + "_" + timestamp + "_" + domain + key);

        Map<String, Object> map = new HashMap<>();
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("no", userId);
        jsonObject1.put("name", name);
        jsonObject1.put("phone", phone);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("redirect_url", "https://m.yangguangqicai.com");
        jsonObject.put("response_type", "json");
        jsonObject.put("info", jsonObject1);
        jsonObject.put("apply_id", userId);
        jsonObject.put("timestamp", timestamp);
        jsonObject.put("signature", signature);
        String datas = "";
        log.info("jsonObject.toJSONString()={}", jsonObject.toJSONString());
        try {
            datas = AES.Encrypt(jsonObject.toJSONString(), key);
            map.put("eid", eid);
            map.put("domain", domain);
            map.put("datas", datas);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String param = JSON.toJSONString(map);
        log.info("订阳光商城param={}", param);
        String result = HttpUtil.sendByPostJson(url, param);
        log.info("订阳光商城result={}", result);
        return result;

    }


}
