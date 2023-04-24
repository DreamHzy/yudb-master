package com.ynzyq.yudbadmin.util.jiliang;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UnifiedOrderUtil {
    public static final String BASE_URL = "https://jlpays.kakapaypay.com/";


    public static String payOrder(String apiurl, String shop_no, String sign, String orderNo, Integer money, String ip, String callbackUrl, String orderName) {
        JSONObject sendData = new JSONObject();
        sendData.put("shop_no", shop_no);
        sendData.put("shop_order_no", orderNo);
        sendData.put("transaction_amount", money);
        sendData.put("order_remark", orderName);
        sendData.put("return_url", "");
        sendData.put("callback_url", callbackUrl);
        sendData.put("timestamp", System.currentTimeMillis());
        sendData.put("order_name", orderName);
        sendData.put("sign", sign);
        sendData.put("client_ip", ip);
        return requestPostData(apiurl, sendData);
    }


    public static String requestPostData(String url, JSONObject send) {
        try {
            Map<String, Object> hearder = new HashMap<>();
            String ret = HttpUtil.doPostJson(BASE_URL + url, hearder, send);
            System.out.println("http返回报文：" + ret);
            return ret;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
