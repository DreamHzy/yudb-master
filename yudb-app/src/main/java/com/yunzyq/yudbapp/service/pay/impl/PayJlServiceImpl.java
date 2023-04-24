package com.yunzyq.yudbapp.service.pay.impl;

import com.alibaba.fastjson.JSONObject;
import com.yunzyq.yudbapp.core.ApiRes;
import com.yunzyq.yudbapp.core.CommonConstant;
import com.yunzyq.yudbapp.dao.PaymentOrderPayMapper;
import com.yunzyq.yudbapp.dao.dto.PayDto;
import com.yunzyq.yudbapp.dao.dto.PayServiceDto;
import com.yunzyq.yudbapp.dao.model.PayChannel;
import com.yunzyq.yudbapp.dao.model.PaymentOrderPay;
import com.yunzyq.yudbapp.dao.vo.PayChennleVo;
import com.yunzyq.yudbapp.service.pay.PayService;
import com.yunzyq.yudbapp.util.CodeToBase64;
import com.yunzyq.yudbapp.util.jiliang.CTB;
import com.yunzyq.yudbapp.util.jiliang.UnifiedOrderUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Service("jl")
@Slf4j
public class PayJlServiceImpl implements PayService {
    @Resource
    PaymentOrderPayMapper paymentOrderPayMapper;
    @Value("${spring.profiles.active}")
    private String springProfilesActive;

    @Override
    public ApiRes<PayChennleVo> pay(PayDto payDto) {
        PayChennleVo payChennleVo = new PayChennleVo();
        String jlResult = "";
        if ("dev".equals(springProfilesActive)) {
            jlResult = UnifiedOrderUtil.payOrder(payDto.getOrderNo(), 10 + "", payDto.getOrderName(),
                    payDto.getCallbackUrl(), payDto.getIp(), payDto.getShopNo(), payDto.getSign(), payDto.getUrl());
        } else {
            jlResult = UnifiedOrderUtil.payOrder(payDto.getOrderNo(), payDto.getMoney() + "", payDto.getOrderName(),
                    payDto.getCallbackUrl(), payDto.getIp(), payDto.getShopNo(), payDto.getSign(), payDto.getUrl());
        }
        log.info("嘉联下单放回={}", jlResult);
        JSONObject jljson = JSONObject.parseObject(jlResult);
        String jlcode = jljson.getString("code");
        String trade_qrcode = jljson.getString("trade_qrcode");
        String result = jljson.getString("result");
        if ("R0001".equals(jlcode)) {
            payChennleVo.setStatus(2);
            payChennleVo.setQrCodeBase64(CodeToBase64.b64QRCode(trade_qrcode));
            payChennleVo.setCode("200");
            payChennleVo.setUrl(trade_qrcode);
        } else {
            payChennleVo.setStatus(7);
            payChennleVo.setCode("-200");
        }
        payChennleVo.setMsg(result);
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "请求成功", payChennleVo);
    }

    @Override
    public ApiRes callBack(PaymentOrderPay paymentOrderPay) {


        return null;
    }
}
