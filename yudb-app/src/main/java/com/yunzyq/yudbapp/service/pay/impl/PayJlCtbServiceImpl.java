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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Service("jlw")
@Slf4j
public class PayJlCtbServiceImpl implements PayService {
    @Value("${spring.profiles.active}")
    private String springProfilesActive;

    @Resource
    PaymentOrderPayMapper paymentOrderPayMapper;

    @Override
    public ApiRes<PayChennleVo> pay(PayDto payDto) {
        PayChennleVo payChennleVo = new PayChennleVo();
        String jlResult = "";
        if ("dev".equals(springProfilesActive)) {
            jlResult = CTB.payOrder(payDto.getUrl(), payDto.getShopNo(), payDto.getOrderNo(),
                    10, payDto.getOpenId(), payDto.getIp(),
                    payDto.getCallbackUrl(), payDto.getOrderName(), payDto.getSign());
        } else {
            jlResult = CTB.payOrder(payDto.getUrl(), payDto.getShopNo(), payDto.getOrderNo(),
                    payDto.getMoney(), payDto.getOpenId(), payDto.getIp(),
                    payDto.getCallbackUrl(), payDto.getOrderName(), payDto.getSign());
        }


        log.info("嘉联下单放回={}", jlResult);
        JSONObject jljson = JSONObject.parseObject(jlResult);
        String jlcode = jljson.getString("code");
        String trade_qrcode = jljson.getString("trade_qrcode");
        String result = jljson.getString("result");
        if ("R0001".equals(jlcode)) {
            String payInfo = jljson.getString("pay_info");
            payChennleVo.setStatus(2);
            payChennleVo.setCode("200");
            payChennleVo.setPayInfo(payInfo);
            payChennleVo.setUrl(trade_qrcode);
        } else {
            payChennleVo.setStatus(7);
            payChennleVo.setCode("-200");
        }
        payChennleVo.setMsg(result);
//        paymentOrderPayMapper.updateByPrimaryKeySelective(paymentOrderPas);
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "请求成功", payChennleVo);
    }

    @Override
    public ApiRes callBack(PaymentOrderPay paymentOrderPay) {
        return null;
    }
}
