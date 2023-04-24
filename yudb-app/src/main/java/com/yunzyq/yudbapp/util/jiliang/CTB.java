package com.yunzyq.yudbapp.util.jiliang;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class CTB {

    private static String appid = "wx86531cf5d7365328";

    private static String appSecret = "ad268dc6ce1b5f104deac0815869e722";

    public static String payOrder(String url, String shopNo, String orderNo, Integer money,
                                  String authCode, String ip, String callbackUrl, String orderName, String sign) {
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        JSONObject sendData = new JSONObject();
        sendData.put("shop_no", shopNo);
        sendData.put("shop_order_no", orderNo);
        sendData.put("shop_order_time", ft.format(new Date()));
        sendData.put("pay_resource", "2");
        sendData.put("trans_type_code", "1017");
        sendData.put("transaction_amount", money);
        sendData.put("order_remark", orderName);
        sendData.put("callback_url", callbackUrl);
        sendData.put("timestamp", System.currentTimeMillis());
        sendData.put("orderName", orderName);
        sendData.put("sign", sign);
        sendData.put("client_ip", ip);
        sendData.put("auth_code", authCode);
        sendData.put("app_id", appid);
        return requestPostData(url, sendData);
    }


    public static String requestPostData(String url, JSONObject send) {
        try {
            Map<String, Object> hearder = new HashMap<>();
            log.info("嘉联send={}：" + send);
            String ret = HttpUtil.doPostJson(url, hearder, send);
            log.info("嘉联返回={}：" + ret);
            return ret;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
