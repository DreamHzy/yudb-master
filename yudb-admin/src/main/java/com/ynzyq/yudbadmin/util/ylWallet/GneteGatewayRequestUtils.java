package com.ynzyq.yudbadmin.util.ylWallet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;


public class GneteGatewayRequestUtils {

    /**
     * appId (请替换成实际appId)
     */
    private final static String APP_ID = "628dfc4884b3aa83852ce92547873c26";
    /**
     * 商户私钥证书 (请替换成实际文件路径)
     */
    private final static String MCT_PRIVATE_KEY_PATH = "/usr/local/java/reren/beijingyu/jieruzhengshu1.p12";
    /**
     * 私钥证书密码 (请替换成实际密码)
     */
    private final static String MCT_PRIVATE_KEY_PWD = "WLX2009w.";
    private final static PrivateKey MCT_PRIVATE_KEY;
    /**
     * 网关请求地址 （测试环境）
     */
    private final static String GNETE_GATEWAY_URL = "https://api.gnete.com/routejson";
    /**
     * 网关公钥
     */
    private final static String GNETE_GATEWAY_PUBLIC_KEY_PATH = "/usr/local/java/reren/beijingyu/qiyewangugan.cer";
    private final static PublicKey GNETE_GATEWAY_PUBLIC_KEY;
    /**
     * 签名算法
     */
    private final static String SIGN_ALG = "SHA1withRSA";

    static {
        MCT_PRIVATE_KEY = CryptoUtils.initPrivateKeyFromFile(new File(MCT_PRIVATE_KEY_PATH), MCT_PRIVATE_KEY_PWD);
        GNETE_GATEWAY_PUBLIC_KEY = CryptoUtils.initPublicKeyFromFile(new File(GNETE_GATEWAY_PUBLIC_KEY_PATH));
    }

    public static JSONObject callGneteGateway(String method, String bizContent) throws IOException {
        // bizContent需要进行URL编码
        bizContent = URLEncoder.encode(bizContent, "UTF-8");
        String requestBody = buildRequestBody(method, bizContent);
        requestBody = sign(requestBody);
        System.out.println();
        System.out.println("-------------------------Request [" + method + "]-------------------------");
        System.out.println(URLDecoder.decode(requestBody, "UTF-8"));
        System.out.println("-------------------------Request [" + method + "]-------------------------");
        System.out.println();
        String result = HttpUtils.post(GNETE_GATEWAY_URL, requestBody);
        System.out.println("-------------------------Response [" + method + "]-------------------------");
        System.out.println(result);
        System.out.println("-------------------------Response [" + method + "]-------------------------");
        System.out.println();

        // 验签
        verifySign(result);

        return JSON.parseObject(result);
    }

    private static String buildRequestBody(String method, String bizContent) {
        String timestamp = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
        return "app_id=" + APP_ID +
                "&timestamp=" + timestamp +
                "&method=" + method +
                "&v=1.0.1" +
                "&biz_content=" + bizContent +
                "&sign_alg=0";
    }

    private static String sign(String requestBody) {
        String signature = CryptoUtils.sign(SIGN_ALG, requestBody, MCT_PRIVATE_KEY, "UTF-8");
        return requestBody + "&sign=" + signature;
    }

    /**
     * 验证响应报文签名
     * 取出sign后，去掉sign，保持原来的key的顺序进行进行签名
     */
    private static void verifySign(String responseBody) {
        int index = responseBody.lastIndexOf("sign");
        String sign = responseBody.substring(index + 7, responseBody.length() - 2);
        String source = responseBody.substring(0, index - 2) + "}";
        boolean pass = CryptoUtils.verifySign(SIGN_ALG, source, sign, GNETE_GATEWAY_PUBLIC_KEY, "UTF-8");
        if (!pass) {
            throw new RuntimeException("验签不通过");
        }
    }
}