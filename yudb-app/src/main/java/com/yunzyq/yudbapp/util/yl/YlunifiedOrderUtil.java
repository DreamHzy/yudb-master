package com.yunzyq.yudbapp.util.yl;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Slf4j
public class YlunifiedOrderUtil {

    private static String appid = "wx0192083639f5f9d6";


    public static String payOrder(String url, String shopNo, String orderNo, Integer money,
                                  String authCode, String callbackUrl, String orderName, String token) {
        /* post参数,格式:JSON */
        JSONObject json = new JSONObject();
        json.put("msgId", "001");   // 消息Id,原样返回
        json.put("requestTimestamp", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));    // 报文请求时间
        json.put("merOrderId", orderNo); // 商户订单号
        json.put("mid", shopNo); // 商户号
        json.put("tid", "8AZ8VGVL");    // 终端号
        json.put("instMid", "MINIDEFAULT"); // 业务类型
        json.put("subAppId", appid); // 业务类型
        json.put("subOpenId", authCode); // 业务类型
        json.put("expireTime", DateFormatUtils.format(new Date().getTime() + 60 * 60 * 1000, "yyyy-MM-dd HH:mm:ss"));  // 订单过期时间,这里设置为一个小时
        json.put("totalAmount", money);      // 支付总金额
        json.put("notifyUrl", callbackUrl);  // 支付结果通知地址
        json.put("tradeType", "MINI");  // 交易类型
        json.put("orderDesc", orderName);  // 账单描述
        log.info("银联支付入参--------------------------------------={}", json);
        String send = null;
        try {
            send = send(url + "/netpay/wx/unified-order", json.toString(), token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return send;
    }


    /**
     * 发送请求
     *
     * @param url eg:http://58.247.0.18:29015/v1/netpay/trade/create
     * @return
     * @throws Exception
     */
    public static String send(String url, String entity, String token) throws Exception {
        token = "OPEN-ACCESS-TOKEN AccessToken=" + token;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Authorization", token);
        StringEntity se = new StringEntity(entity, "UTF-8");
        se.setContentType("application/json");
        httpPost.setEntity(se);
        CloseableHttpResponse response = httpClient.execute(httpPost);
        HttpEntity entity1 = response.getEntity();
        String resStr = null;
        if (entity1 != null) {
            resStr = EntityUtils.toString(entity1, "UTF-8");
        }
        httpClient.close();
        response.close();
        return resStr;
    }

    /**
     * open-body-sig方式获取到Authorization 的值
     *
     * @param appId  f0ec96ad2c3848b5b810e7aadf369e2f
     * @param appKey 775481e2556e4564985f5439a5e6a277
     * @param body   json字符串 String body = "{\"merchantCode\":\"123456789900081\",\"terminalCode\":\"00810001\",\"merchantOrderId\":\"20123333644493200\",\"transactionAmount\":\"1\",\"merchantRemark\":\"测试\",\"payMode\":\"CODE_SCAN\",\"payCode\":\"285668667587422761\",\"transactionCurrencyCode\":\"156\"}";
     * @return
     * @throws Exception
     */
    public static String getOpenBodySig(String appId, String appKey, String body) throws Exception {
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());   // eg:20190227113148
        String nonce = UUID.randomUUID().toString().replace("-", ""); // eg:be46cd581c9f46ecbd71b9858311ea12
        byte[] data = body.getBytes("UTF-8");
        System.out.println("data:\n" + body);
        InputStream is = new ByteArrayInputStream(data);
        String bodyDigest = testSHA256(is); // eg:d60bc3aedeb853e2a11c0c096baaf19954dd9b752e48dea8e919e5fb29a42a8d
        System.out.println("bodyDigest:\n" + bodyDigest);
        String str1_C = appId + timestamp + nonce + bodyDigest; // eg:f0ec96ad2c3848b5b810e7aadf369e2f + 20190227113148 + be46cd581c9f46ecbd71b9858311ea12 + d60bc3aedeb853e2a11c0c096baaf19954dd9b752e48dea8e919e5fb29a42a8d

        System.out.println("str1_C:" + str1_C);

//        System.out.println("appKey_D:\n" + appKey);

        byte[] localSignature = hmacSHA256(str1_C.getBytes(), appKey.getBytes());

        String localSignatureStr = Base64.encodeBase64String(localSignature);   // Signature
        System.out.println("Authorization:\n" + "OPEN-BODY-SIG AppId=" + "\"" + appId + "\"" + ", Timestamp=" + "\"" + timestamp + "\"" + ", Nonce=" + "\"" + nonce + "\"" + ", Signature=" + "\"" + localSignatureStr + "\"\n");
        return ("OPEN-BODY-SIG AppId=" + "\"" + appId + "\"" + ", Timestamp=" + "\"" + timestamp + "\"" + ", Nonce=" + "\"" + nonce + "\"" + ", Signature=" + "\"" + localSignatureStr + "\"");
    }

    /**
     * 进行加密
     *
     * @param is
     * @return 加密后的结果
     */
    private static String testSHA256(InputStream is) {
        try {
//            System.out.println(is.hashCode());
            return DigestUtils.sha256Hex(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param data
     * @param key
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    public static byte[] hmacSHA256(byte[] data, byte[] key) throws NoSuchAlgorithmException, InvalidKeyException {
        String algorithm = "HmacSHA256";
        Mac mac = Mac.getInstance(algorithm);
        mac.init(new SecretKeySpec(key, algorithm));
        return mac.doFinal(data);
    }
//
//    /**
//     * 获取到订单号
//     *
//     * @return 订单号
//     */
//    public static String getMerchantOrderId() {
//        return DateFormatUtils.format(new Date(), "yyyyMMddHHmmssSSS") + RandomStringUtils.randomNumeric(7);
//    }

}
