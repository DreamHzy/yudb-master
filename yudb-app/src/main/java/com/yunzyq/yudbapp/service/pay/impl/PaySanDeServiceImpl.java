package com.yunzyq.yudbapp.service.pay.impl;

import com.alibaba.fastjson.JSONObject;
import com.yunzyq.yudbapp.core.ApiRes;
import com.yunzyq.yudbapp.core.CommonConstant;
import com.yunzyq.yudbapp.dao.dto.PayDto;
import com.yunzyq.yudbapp.dao.model.PaymentOrderPay;
import com.yunzyq.yudbapp.dao.vo.PayChennleVo;
import com.yunzyq.yudbapp.service.pay.PayService;
import com.yunzyq.yudbapp.util.CodeToBase64;
import com.yunzyq.yudbapp.util.jiliang.UnifiedOrderUtil;
import com.yunzyq.yudbapp.util.sande.CertUtil;
import com.yunzyq.yudbapp.util.sande.pay.SadOrderCreate;
import com.yunzyq.yudbapp.util.sande.pay.SanDeBase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("sd")
@Slf4j
public class PaySanDeServiceImpl implements PayService {
    @Value("${spring.profiles.active}")
    private String springProfilesActive;

    @Value("${sandsdk.mid}")
    private String mid;

    @Value("${sandsdk.signCert.pat}")
    private String pat;
    @Value("${sandsdk.signCert.pwd}")
    private String pwd;
    @Value("${sandsdk.sandCert.path}")
    private String path;


    @Override
    public ApiRes<PayChennleVo> pay(PayDto payDto) {
        PayChennleVo payChennleVo = new PayChennleVo();
        String reqAddr = "/order/pay";

        if ("dev".equals(springProfilesActive)) {
            payDto.setMoney(1);
        }
        try {
            //加载证书
            CertUtil.init(path, pat, pwd);
            //设置报文头
            JSONObject header = SadOrderCreate.setHeader(mid);
            //设置报文体
            JSONObject body = SadOrderCreate.setBody(payDto);
            JSONObject resp = SanDeBase.requestServer(header, body, reqAddr);
            if (resp.getJSONObject("head").getString("respCode").equals("000000")) {
                log.info("下单成功");
                log.info("生成的支付凭证为：" + resp.getJSONObject("body").getString("credential"));
                String payInfo = resp.getJSONObject("body").getJSONObject("credential").getString("params");
                payChennleVo.setStatus(2);
                payChennleVo.setCode("200");
                payChennleVo.setPayInfo(payInfo);
                payChennleVo.setUrl("");
            } else {
                payChennleVo.setStatus(7);
                payChennleVo.setCode("-200");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "请求成功", payChennleVo);
    }


    @Override
    public ApiRes callBack(PaymentOrderPay paymentOrderPay) {

        return null;
    }


}
