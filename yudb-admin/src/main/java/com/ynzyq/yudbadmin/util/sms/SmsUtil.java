package com.ynzyq.yudbadmin.util.sms;

//接口类型：互亿无线触发短信接口，支持发送验证码短信、订单通知短信等。
// 账户注册：请通过该地址开通账户http://sms.ihuyi.com/register.html
// 注意事项：
//（1）调试期间，请用默认的模板进行测试，默认模板详见接口文档；
//（2）请使用APIID（查看APIID请登录用户中心->验证码短信->产品总览->APIID）及 APIkey来调用接口；
//（3）该代码仅供接入互亿无线短信接口参考使用，客户可根据实际需要自行编写；

import java.io.IOException;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;


@Slf4j
public class SmsUtil {

    private static String Url = "http://106.ihuyi.cn/webservice/sms.php?method=Submit";

    /**
     * 验证码
     *
     * @param phone
     * @param smsCode
     * @return
     */
    public static JSONObject senSms(String phone, int smsCode) {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", "1");

        HttpClient client = new HttpClient();

        PostMethod method = new PostMethod(Url);

        client.getParams().setContentCharset("UTF-8");

        method.setRequestHeader("ContentType", "application/x-www-form-urlencoded;charset=UTF-8");


        String content = new String("您的验证码是：" + smsCode + "。请不要把验证码泄露给其他人。");

        NameValuePair[] data = {//提交短信
                new NameValuePair("account", "C81543612"), //查看用户名是登录用户中心->验证码短信->产品总览->APIID
                new NameValuePair("password", "6f552fe0fe3b28d10c87d919a8230a65"),  //查看密码请登录用户中心->验证码短信->产品总览->APIKEY
                new NameValuePair("mobile", phone),
                new NameValuePair("content", content),
        };
        method.setRequestBody(data);
        try {
            client.executeMethod(method);
            String SubmitResult = method.getResponseBodyAsString();
            Document doc = DocumentHelper.parseText(SubmitResult);
            Element root = doc.getRootElement();
            String code = root.elementText("code");
            String msg = root.elementText("msg");
            String smsid = root.elementText("smsid");
            jsonObject.put("code", code);
            jsonObject.put("msg", msg);
            jsonObject.put("smsid", smsid);
            return jsonObject;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return jsonObject;
        }
    }


    public static JSONObject notice(String phone, String name, Integer count) {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", "1");

        HttpClient client = new HttpClient();

        PostMethod method = new PostMethod(Url);

        client.getParams().setContentCharset("UTF-8");
        method.setRequestHeader("ContentType", "application/x-www-form-urlencoded;charset=UTF-8");
        String content = new String("定时任务" + name + "执行完毕，生成" + count + "笔订单。");
        NameValuePair[] data = {//提交短信
                new NameValuePair("account", "C81543612"), //查看用户名是登录用户中心->验证码短信->产品总览->APIID
                new NameValuePair("password", "6f552fe0fe3b28d10c87d919a8230a65"),  //查看密码请登录用户中心->验证码短信->产品总览->APIKEY
                new NameValuePair("mobile", phone),
                new NameValuePair("content", content),
        };
        method.setRequestBody(data);
        try {
            client.executeMethod(method);
            String SubmitResult = method.getResponseBodyAsString();
            Document doc = DocumentHelper.parseText(SubmitResult);
            Element root = doc.getRootElement();
            String code = root.elementText("code");
            String msg = root.elementText("msg");
            String smsid = root.elementText("smsid");
            jsonObject.put("code", code);
            jsonObject.put("msg", msg);
            jsonObject.put("smsid", smsid);
            return jsonObject;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return jsonObject;
        }
    }


    public static void main(String[] args) {
//        JSONObject msg = senSms("13093783517");
//        log.info("msg---------------------------------={}", msg);
    }

}