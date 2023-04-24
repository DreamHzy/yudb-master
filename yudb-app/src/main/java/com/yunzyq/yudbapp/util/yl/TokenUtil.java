//package com.yunzyq.yudbapp.util.yl;
//
//import com.alibaba.fastjson.JSONObject;
//import com.yunzyq.yudbapp.dao.model.PayChannel;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.codec.digest.DigestUtils;
//import org.apache.http.HttpEntity;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClientBuilder;
//import org.apache.http.util.EntityUtils;
//
//import java.text.SimpleDateFormat;
//
//@Slf4j
//public class TokenUtil {
//
//    private static String token(PayChannel payChannel) {
//        JSONObject jsonObject = new JSONObject();
//        final String AppID = payChannel.getReservedTwo();
//        String Timestamp = gettime();
//        log.info("Timestamp为---------------{}", Timestamp);
//        String Nonce = getnonce();
//        log.info("Nonce为---------------{}", Nonce);
//        String appkey = payChannel.getReservedOne();
//        String all = AppID + Timestamp + Nonce + appkey;
//        String signature = ccsha1(all);
//        log.info("签名为---------------{}", signature);
//        jsonObject.put("appId", AppID);
//        jsonObject.put("timestamp", Timestamp);
//        jsonObject.put("nonce", Nonce);
//        jsonObject.put("signature", signature);
//        log.info("jsonObject-------------------{}", jsonObject);
//        JSONObject response = doPost(payChannel.getChannelApiUrl(), jsonObject);
//        log.info("response---------------{}", response);
//        String accessToken = response.get("accessToken").toString();
//        log.info("accessToken---------------{}", accessToken);
//
//        return accessToken;
//    }
//
//    /*获取系统当前时间*/
//    public static String gettime() {
//        SimpleDateFormat date = new SimpleDateFormat("yyyyMMddHHmmss");
//        String time = date.format(System.currentTimeMillis());
//        return time;
//    }
//
//    /*获取随机数*/
//    public static String getnonce() {
//        int a = (int) (1 + Math.random() * 10000);
//        String nonce = String.valueOf(a);
//        return nonce;
//    }
//
//    public static String ccsha1(String message) {/*加密算法实现*/
//        String c = DigestUtils.sha1Hex(message);
//        return c;
//    }
//
//    /*发送post请求得到返回的数据*/
//    public static JSONObject doPost(String url, JSONObject json) {
//
//        CloseableHttpClient httpclient = HttpClientBuilder.create().build();
//        HttpPost post = new HttpPost(url + "token/access");
//        JSONObject response = null;
//        try {
//            StringEntity s = new StringEntity(json.toString());
//            s.setContentEncoding("UTF-8");
//            s.setContentType("application/json");//发送json数据需要设置contentType
//            post.setEntity(s);
//            CloseableHttpResponse res = httpclient.execute(post);
//            HttpEntity entity = res.getEntity();
//            String result = EntityUtils.toString(entity);// 返回json格式：
//            response = JSONObject.parseObject(result);
//            res.close();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//        return response;
//    }
//}
