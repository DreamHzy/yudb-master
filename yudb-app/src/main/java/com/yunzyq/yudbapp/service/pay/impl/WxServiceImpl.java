package com.yunzyq.yudbapp.service.pay.impl;

import com.alibaba.fastjson.JSONObject;
import com.yunzyq.yudbapp.core.ApiRes;
import com.yunzyq.yudbapp.core.CommonConstant;
import com.yunzyq.yudbapp.dao.dto.PayDto;
import com.yunzyq.yudbapp.dao.model.PaymentOrderPay;
import com.yunzyq.yudbapp.dao.vo.PayChennleVo;
import com.yunzyq.yudbapp.service.pay.PayService;
import com.yunzyq.yudbapp.util.wx.MyConfig;
import com.yunzyq.yudbapp.util.wx.WXPay;
import com.yunzyq.yudbapp.util.wx.WXPayConstants;
import com.yunzyq.yudbapp.util.wx.WXPayUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service("wx")
@Slf4j
public class WxServiceImpl implements PayService {
    @Value("${spring.profiles.active}")
    private String springProfilesActive;
    @Value("${certPath}")
    private String certPath;

    @Override
    public ApiRes<PayChennleVo> pay(PayDto payDto) {

        PayChennleVo payChennleVo = new PayChennleVo();
        Map<String, String> data = new HashMap<String, String>();
        data.put("body", payDto.getOrderName());
        data.put("out_trade_no", payDto.getOrderNo());
        data.put("fee_type", "CNY");
        if ("dev".equals(springProfilesActive)) {
            data.put("total_fee", 1 + "");//测试使用
        } else {
            data.put("total_fee", payDto.getMoney() + "");
        }
        data.put("spbill_create_ip", payDto.getIp());
        data.put("notify_url", payDto.getCallbackUrl());
        data.put("trade_type", "JSAPI");  // 此处指定为JSAPI
        data.put("openid", payDto.getOpenId());
        MyConfig config = null;
        WXPay wxpay = null;
        Map<String, String> resp = null;
        String result_code = "";
        try {
            config = new MyConfig(certPath);
            wxpay = new WXPay(config);
            resp = wxpay.unifiedOrder(data);
            result_code = resp.get("result_code");
            log.info("支付订单返回信息={}", resp);
            if ("SUCCESS".equals(result_code)) {//下单成功后给前端输出
                String prepay_id = resp.get("prepay_id");
                JSONObject jsonObject = new JSONObject();
                String timeStamp = System.currentTimeMillis() + "";
                String nonceStr = WXPayUtil.generateNonceStr();
                String str = "prepay_id=" + prepay_id;
                String appId = config.getAppID();
                Map<String, String> data1 = new HashMap<String, String>();
                data1.put("appId", appId);
                data1.put("nonceStr", nonceStr);
                data1.put("package", str);
                data1.put("signType", "HMACSHA256");
                data1.put("timeStamp", timeStamp);
                //返回给前端
                jsonObject.put("appId", appId);
                jsonObject.put("timeStamp", timeStamp);
                jsonObject.put("nonceStr", nonceStr);
                jsonObject.put("package", str);
                jsonObject.put("signType", WXPayConstants.SignType.HMACSHA256);
                jsonObject.put("paySign", WXPayUtil.generateSignature(data1, config.getKey(), WXPayConstants.SignType.HMACSHA256));
                payChennleVo.setStatus(2);
                payChennleVo.setCode("200");
                payChennleVo.setPayInfo(jsonObject.toJSONString());
                payChennleVo.setUrl("");
                return ApiRes.response(CommonConstant.SUCCESS_CODE, "请求成功", payChennleVo);
            } else {
                payChennleVo.setStatus(7);
                payChennleVo.setCode("-200");
            }

        } catch (Exception e) {
            log.error("权益套餐下单请求错误={}", e);
            e.printStackTrace();
        }
        payChennleVo.setMsg(result_code);
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "请求成功", payChennleVo);
    }

    @Override
    public ApiRes callBack(PaymentOrderPay paymentOrderPay) {
        return null;
    }
}
