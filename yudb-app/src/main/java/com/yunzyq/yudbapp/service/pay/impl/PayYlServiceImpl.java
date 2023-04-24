package com.yunzyq.yudbapp.service.pay.impl;

import com.alibaba.fastjson.JSONObject;
import com.yunzyq.yudbapp.core.ApiRes;
import com.yunzyq.yudbapp.core.CommonConstant;
import com.yunzyq.yudbapp.dao.dto.PayDto;
import com.yunzyq.yudbapp.dao.model.PaymentOrderPay;
import com.yunzyq.yudbapp.dao.vo.PayChennleVo;
import com.yunzyq.yudbapp.redis.RedisUtils;
import com.yunzyq.yudbapp.service.pay.PayService;
import com.yunzyq.yudbapp.util.CodeToBase64;
import com.yunzyq.yudbapp.util.jiliang.CTB;
import com.yunzyq.yudbapp.util.jiliang.UnifiedOrderUtil;
import com.yunzyq.yudbapp.util.yl.YlunifiedOrderUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("yl")
@Slf4j
public class PayYlServiceImpl implements PayService {
    @Value("${spring.profiles.active}")
    private String springProfilesActive;
    @Resource
    RedisUtils redisUtils;


    @Override
    public ApiRes<PayChennleVo> pay(PayDto payDto) {
        PayChennleVo payChennleVo = new PayChennleVo();
        String jlResult = "";
        String token = (String) redisUtils.get("yl_good_token");

        if ("dev".equals(springProfilesActive)) {
            jlResult = YlunifiedOrderUtil.payOrder(payDto.getUrl(), payDto.getShopNo(), payDto.getOrderNo(),
                    1, payDto.getOpenId(),
                    payDto.getCallbackUrl(), payDto.getOrderName(), token);
        } else {
            jlResult = YlunifiedOrderUtil.payOrder(payDto.getUrl(), payDto.getShopNo(), payDto.getOrderNo(),
                    payDto.getMoney(), payDto.getOpenId(),
                    payDto.getCallbackUrl(), payDto.getOrderName(), token);
        }
        log.info("银联下单返回-----------------={}", jlResult);
        JSONObject jljson = JSONObject.parseObject(jlResult);
        String jlcode = jljson.getString("errCode");
        if ("SUCCESS".equals(jlcode)) {
            String payInfo = jljson.getString("miniPayRequest");
            payChennleVo.setStatus(2);
            payChennleVo.setCode("200");
            payChennleVo.setPayInfo(payInfo);
        } else {
            payChennleVo.setStatus(7);
            payChennleVo.setCode("-200");
        }
        payChennleVo.setMsg(jlcode);
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "请求成功", payChennleVo);
    }

    @Override
    public ApiRes callBack(PaymentOrderPay paymentOrderPay) {
        return null;
    }
}
