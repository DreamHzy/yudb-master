package com.yunzyq.yudbapp.service.pay;


import com.yunzyq.yudbapp.core.ApiRes;
import com.yunzyq.yudbapp.dao.dto.PayDto;
import com.yunzyq.yudbapp.dao.dto.PayServiceDto;
import com.yunzyq.yudbapp.dao.model.PayChannel;
import com.yunzyq.yudbapp.dao.model.PaymentOrderPay;
import com.yunzyq.yudbapp.dao.vo.PayChennleVo;

public interface PayService {
    ApiRes<PayChennleVo> pay(PayDto payDto);

    ApiRes callBack(PaymentOrderPay paymentOrderPay);
}
