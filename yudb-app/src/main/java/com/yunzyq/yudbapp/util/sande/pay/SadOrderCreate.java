/**
 * Copyright : http://www.sandpay.com.cn , 2011-2014
 * Project : sandpay-gateway-demo
 * $Id$
 * $Revision$
 * Last Changed by pxl at 2018-4-25 下午8:15:41
 * $URL$
 * <p>
 * Change Log
 * Author      Change Date    Comments
 * -------------------------------------------------------------
 * pxl         2018-4-25        Initailized
 */
package com.yunzyq.yudbapp.util.sande.pay;

import com.yunzyq.yudbapp.dao.dto.PayDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;

/**
 * 产品：杉德线上支付<br>
 * 交易：统一下单接口-微信公众号/微信小程序<br>
 * 日期： 2018-04<br>
 * 版本： 1.0.0
 * 说明：以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己需要，按照技术文档编写。该代码仅供参考。<br>
 */
@Slf4j
public class SadOrderCreate {


    public JSONObject body = new JSONObject();

    public static JSONObject setHeader(String mid) {
        JSONObject header = new JSONObject();
        header.put("version", SanDeBase.version);            //版本号
        header.put("method", SanDeBase.ORDERPAY);            //接口名称:统一下单
        header.put("mid", mid);    //商户号
        String plMid = "";        //平台商户号
        if (plMid != null && StringUtils.isNotEmpty(plMid)) {  //平台商户存在时接入
            header.put("accessType", "2");                    //接入类型设置为平台商户接入
            header.put("plMid", plMid);
        } else {
            header.put("accessType", "1");                    //接入类型设置为平台商户接入												//接入类型设置为普通商户接入
        }
        header.put("channelType", "07");                    //渠道类型：07-互联网   08-移动端
        header.put("reqTime", SanDeBase.getCurrentTime());    //请求时间
        header.put("productId", "00002020");                //产品编码

        return header;

    }


    public static JSONObject setBody(PayDto payDto) {

        String totalAmount = "000000000000";
        String money = payDto.getMoney() + "";
        totalAmount = totalAmount.substring(0, totalAmount.length() - money.length()) + money;
        JSONObject body = new JSONObject();
        body.put("orderCode", payDto.getOrderNo());                           //商户订单号
        body.put("totalAmount", totalAmount);                                  //订单金额
        body.put("subject", payDto.getOrderName());                                             //订单标题
        body.put("body", payDto.getOrderName());                                         //订单描述
        body.put("txnTimeOut", SanDeBase.getNextDayTime());                        //订单超时时间
        body.put("clientIp", payDto.getIp());                                    //客户端IP
        body.put("limitPay", "");                                                 //限定支付方式	送1-限定不能使用贷记卡送	4-限定不能使用花呗	送5-限定不能使用贷记卡+花呗
        body.put("notifyUrl", payDto.getCallbackUrl());                         //异步通知地址
        body.put("frontUrl", "http://127.0.0.1/frontNotify");                     //前台通知地址
        body.put("storeId", "");                                                  //商户门店编号
        body.put("terminalId", "");                                               //商户终端编号
        body.put("operatorId", "");                                               //操作员编号
        body.put("clearCycle", "");                                               //清算模式
        body.put("royaltyInfo", "");                                              //分账信息
        body.put("riskRateInfo", "");                                             //风控信息域
        body.put("bizExtendParams", "");                                          //业务扩展参数
        body.put("merchExtendParams", "");                                        //商户扩展参数
        body.put("extend", "");                                                   //扩展域
        body.put("accsplitInfo", "");                                             //分账域

        JSONObject payExtra = new JSONObject();
        payExtra.put("subAppid", "wx86531cf5d7365328");                              //商户公众号Appid
        payExtra.put("userId", payDto.getOpenId());                      //用户在商户公众号下得唯一标示openid

        body.put("payMode", "sand_wx");                                              //支付模式
        body.put("payExtra", payExtra.toJSONString());                              //支付扩展域
        return body;
    }


//    public static void main(String[] args) throws Exception {
//
//        OrderCreate demo = new OrderCreate();
//        String reqAddr = "/order/pay";   //接口报文规范中获取
//
//        //加载配置文件
//        SDKConfig.getConfig().loadPropertiesFromSrc();
//        //加载证书
//        CertUtil.init(SDKConfig.getConfig().getSandCertPath(), SDKConfig.getConfig().getSignCertPath(), SDKConfig.getConfig().getSignCertPwd());
//        //设置报文头
//        demo.setHeader();
//        //设置报文体
//        demo.setBody();
//
//        JSONObject resp = SanDeBase.requestServer(demo.header, demo.body, reqAddr);
//
//        if (resp.getJSONObject("head").getString("respCode").equals("000000")) {
//            log.info("下单成功");
//            log.info("生成的支付凭证为：" + resp.getJSONObject("body").getString("credential"));
//        }
//
//
//    }
}
