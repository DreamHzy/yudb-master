package com.ynzyq.yudbadmin.util.jiliang;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class CTB {

    public static final String BASE_URL = "https://jlpays.kakapaypay.com/";


    public static String payOrder(String orderNo, Integer money, String authCode, String ip, String callbackUrl, String orderName) {
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String reqUrl = "pay/payMoneyCTB";
        JSONObject sendData = new JSONObject();
        sendData.put("shop_no", "1632396809058");
        sendData.put("shop_order_no", orderNo);
        sendData.put("shop_order_time", ft.format(new Date()));
        sendData.put("pay_resource", "2");
        sendData.put("trans_type_code", "1017");
        sendData.put("transaction_amount", money);
        sendData.put("order_remark", orderName);
        sendData.put("callback_url", callbackUrl);
        sendData.put("timestamp", System.currentTimeMillis());
        sendData.put("orderName", orderName);
        sendData.put("sign", "0cabb02ecc7547eca00ecae1856d0a2e");
        sendData.put("client_ip", ip);
        sendData.put("auth_code", authCode);
        sendData.put("app_id", "wxad6f772214dd71a6");
        return requestPostData(reqUrl, sendData);
    }


    public static String requestPostData(String url, JSONObject send) {
        try {
            Map<String, Object> hearder = new HashMap<>();
            log.info("嘉联send={}：" + send);
            String ret = HttpUtil.doPostJson(BASE_URL + url, hearder, send);
            log.info("嘉联返回={}：" + ret);
            return ret;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }




}
