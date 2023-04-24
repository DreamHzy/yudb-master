package com.yunzyq.yudbapp.util.jiliang;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class UnifiedOrderUtil {

//
//    private static String shopNo = "1632623849372";
//
//    private static String sign = "f0c5dbddf5ce47f395412bcc61fff354";
//
//    private static String apiUrl = "https://jlpays.kakapaypay.com/pay/unifiedOrder";

    public static String payOrder(String shopOrderNo,
                                String money, String orderRemark, String callbackUrl,
                                String ip,String shopNo,String sign ,String apiUrl) {
        JSONObject sendData = new JSONObject();
        sendData.put("shop_no", shopNo);
        sendData.put("shop_order_no", shopOrderNo);
        sendData.put("transaction_amount", money);
        sendData.put("order_remark", orderRemark);
        sendData.put("return_url", "");
        sendData.put("callback_url", callbackUrl);
        sendData.put("timestamp", System.currentTimeMillis());
        sendData.put("order_name", orderRemark);
        sendData.put("sign", sign);
        sendData.put("client_ip", ip);
        return  requestPostData(apiUrl, sendData);
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
