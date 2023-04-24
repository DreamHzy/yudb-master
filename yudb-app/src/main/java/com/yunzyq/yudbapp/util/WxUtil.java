package com.yunzyq.yudbapp.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yunzyq.yudbapp.core.ApiRes;
import com.yunzyq.yudbapp.core.CommonConstant;
import com.yunzyq.yudbapp.dao.vo.WxauthVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.encoders.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigDecimal;
import java.security.Key;
import java.security.Security;
import java.util.HashMap;
import java.util.Map;


@Slf4j
public class WxUtil {



//    private static String appid = "wx0192083639f5f9d6";
//    private static String secret = "ad268dc6ce1b5f104deac0815869e722";
//

    private static String appid = "wx0192083639f5f9d6";
    private static String secret = "8411425b804817003ceeb507cfdb2093";


    /**
     * auth
     *
     * @param
     * @return
     */
    public static String auth(WxauthVo wxauthVo) {
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appid + "&secret=" + secret + "&" +
                "js_code=" + wxauthVo.getJs_code() + "&grant_type=" + wxauthVo.getGrant_type();
        log.info("usr={}", url);
        return HttpUtil.sendByGetUrl(url);
    }


    public static String getAccessToken() {
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=" +
                "client_credential&appid=" + appid + "&secret=" + secret;
        log.info("usr={}", url);
        return HttpUtil.sendByGetUrl(url);
    }

    public static String sendSubscriptionMsg(String token, String money, String msg, String time, String openId) {
        String url = "   https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=" + token;

        Map<String, Object> map1 = new HashMap<>();
        map1.put("touser", openId);
        map1.put("template_id", "tNoM1LEvWmy-NSgDcUjgyorc30Z16454DS7-nUioDa0");
        map1.put("page", "pages/franchisee/platform?outside=true");
        //开发版本需要加
        map1.put("miniprogram_state", "trial");
        Map<String, Object> data = new HashMap<>();

        Map<String, Object> thing1 = new HashMap<>();
        thing1.put("value", msg);
        data.put("thing1", thing1);

        Map<String, Object> amount2 = new HashMap<>();
        amount2.put("value", money);
        data.put("amount2", amount2);

        Map<String, Object> time3 = new HashMap<>();
        time3.put("value", time);
        data.put("time3", time3);

        map1.put("data", data);
        String param = JSON.toJSONString(map1);
        log.info("订阅信息参数param={}", param);
        String result = HttpUtil.sendByPostJson(url, param);
        log.info("订阅信息返回result={}", result);
        return result;

    }


    public static String decryptData(String encryptDataB64, String sessionKeyB64, String ivB64) {
        return new String(
                decryptOfDiyIV(
                        Base64.decode(encryptDataB64),
                        Base64.decode(sessionKeyB64),
                        Base64.decode(ivB64)
                )
        );
    }

    private static final String KEY_ALGORITHM = "AES";
    private static final String ALGORITHM_STR = "AES/CBC/PKCS7Padding";
    private static Key key;
    private static Cipher cipher;

    private static void init(byte[] keyBytes) {
        // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
        int base = 16;
        if (keyBytes.length % base != 0) {
            int groups = keyBytes.length / base + (keyBytes.length % base != 0 ? 1 : 0);
            byte[] temp = new byte[groups * base];
            Arrays.fill(temp, (byte) 0);
            System.arraycopy(keyBytes, 0, temp, 0, keyBytes.length);
            keyBytes = temp;
        }
        // 初始化
        Security.addProvider(new BouncyCastleProvider());
        // 转化成JAVA的密钥格式
        key = new SecretKeySpec(keyBytes, KEY_ALGORITHM);
        try {
            // 初始化cipher
            cipher = Cipher.getInstance(ALGORITHM_STR, "BC");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 解密方法
     *
     * @param encryptedData 要解密的字符串
     * @param keyBytes      解密密钥
     * @param ivs           自定义对称解密算法初始向量 iv
     * @return 解密后的字节数组
     */
    private static byte[] decryptOfDiyIV(byte[] encryptedData, byte[] keyBytes, byte[] ivs) {
        byte[] encryptedText = null;
        init(keyBytes);
        try {
            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(ivs));
            encryptedText = cipher.doFinal(encryptedData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encryptedText;
    }


    public static ApiRes<Map<String, String>> getopenId(String jscode, String AppID, String AppSecret) {
        log.info("微信授权接口 jsCode={}", jscode);
        WxauthVo wxauthReq = new WxauthVo();
        wxauthReq.setAppid(AppID);
        wxauthReq.setSecret(AppSecret);
        wxauthReq.setJs_code(jscode);
        wxauthReq.setGrant_type("authorization_code");
        JSONObject jsonObject = null;
        Map<String, String> map = new HashMap<>();
        try {
            String auth = WxUtil.auth(wxauthReq);
            jsonObject = JSONObject.parseObject(auth);
            log.info("微信授权返回 jsonObject={}", jsonObject);
            String openid = jsonObject.getString("openid");
            String session_key = jsonObject.getString("session_key");
            if (StringUtils.isEmpty(openid)) {
                String errmsg = jsonObject.getString("errmsg");
                return new ApiRes(CommonConstant.FAIL_CODE, errmsg, "");
            }
            map.put("openid", openid);
            map.put("session_key", session_key);
            log.info("加密后数据={}", map);
        } catch (Exception e) {
            log.error("微信请求失败：" + e);
            return new ApiRes(CommonConstant.FAIL_CODE, "请求失败", "");
        }
        return new ApiRes(CommonConstant.SUCCESS_CODE, "请求成功", map);
    }

}
