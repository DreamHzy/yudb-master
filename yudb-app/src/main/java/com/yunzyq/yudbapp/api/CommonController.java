package com.yunzyq.yudbapp.api;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.yunzyq.yudbapp.asny.JlNoticeAsync;
import com.yunzyq.yudbapp.core.ApiRes;
import com.yunzyq.yudbapp.core.CommonConstant;
import com.yunzyq.yudbapp.dao.dto.*;
import com.yunzyq.yudbapp.dao.model.AgentAreaPaymentOrderPay;
import com.yunzyq.yudbapp.dao.model.PaymentOrderPay;
import com.yunzyq.yudbapp.dao.vo.AuthQueryVo;
import com.yunzyq.yudbapp.dao.vo.LoginVo;
import com.yunzyq.yudbapp.dao.vo.MerchantOrderPayVo;
import com.yunzyq.yudbapp.dao.vo.PayShareInfoVO;
import com.yunzyq.yudbapp.service.CommonService;
import com.yunzyq.yudbapp.service.RegionalManagerOrderService;
import com.yunzyq.yudbapp.util.oss.FileUploadOss;
import com.yunzyq.yudbapp.util.sande.CertUtil;
import com.yunzyq.yudbapp.util.sande.CryptoUtil;
import com.yunzyq.yudbapp.util.wx.MyConfig;
import com.yunzyq.yudbapp.util.wx.WXPay;
import com.yunzyq.yudbapp.util.wx.WXPayUtil;
import com.yunzyq.yudbapp.util.yl.NotifyUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "公共接口")
@Slf4j
@RequestMapping("/common")
@RestController
public class CommonController extends BaseController {

    //默认配置的是UTF-8
    public static String encoding = "UTF-8";

    @Resource
    CommonService commonService;
    @Value("${imageUrl}")
    private String imageurl;
    @Resource
    RegionalManagerOrderService regionalManagerOrderService;
    @Resource
    JlNoticeAsync jlNoticeAsync;
    @Value("${certPath}")
    private String certPath;
    /**
     * md5秘钥
     */
    static final String md5key = "DaGJjGfd4QJ3nd5XCheyHFXcPBsNWJMbwttHMAJka43MZ4kT";

    /**
     * 登录短信验证码
     */
    @ApiOperation("登录短信验证码(开发测试默认123456)")
    @PostMapping("/sms")
    public ApiRes sms(@RequestBody SmsDto dto, HttpServletRequest httpServletRequest) {
        return commonService.sms(dto, httpServletRequest);
    }

    @ApiOperation("查询是否需要订阅授权")
    @PostMapping("/authQuery")
    public ApiRes<AuthQueryVo> authQuery(HttpServletRequest httpServletRequest) {
        return commonService.authQuery(httpServletRequest);
    }


    @ApiOperation("订阅通知")
    @PostMapping("/sendAuth")
    public ApiRes sendAuth(HttpServletRequest httpServletRequest) {
        return commonService.sendAuth(httpServletRequest);
    }


    /**
     * 文件上传
     */
    @ApiOperation("文件上传")
    @PostMapping("/upload")
    public ApiRes uploadFile(@RequestParam(value = "file", required = false) MultipartFile file, @RequestParam(value = "type", required = false) String type) {
        try {

            if (file.isEmpty()) {
                return new ApiRes(CommonConstant.FAIL_CODE, "上传图片错误", "");
            }
            String path = FileUploadOss.uploadMultipartFile(file);
            if (StringUtils.isBlank(path)) {
                log.info("文件上传到OSS失败");
                return new ApiRes(CommonConstant.FAIL_CODE, "文件上传到OSS失败", null);
            }
            Map<String, Object> map = new HashMap<>();
            map.put("path", path);
            map.put("type", type);
            map.put("imageurl", imageurl);
            map.put("fileName", file.getOriginalFilename());
            return new ApiRes(CommonConstant.SUCCESS_CODE, "上传成功", map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ApiRes(CommonConstant.FAIL_CODE, "请求上传文件异常", null);
    }

    /**
     * 文件批量上传
     *
     * @param files
     * @return
     */
    @ApiOperation("文件批量上传")
    @PostMapping("/uploadFileBatch")
    public ApiRes uploadFileBatch(MultipartFile[] files) {
        if (files.length == 0) {
            return new ApiRes(CommonConstant.FAIL_CODE, "上传图片错误", "");
        }
        List<Map<String, Object>> list = Lists.newArrayList();
        for (MultipartFile file : files) {
            String path = null;
            try {
                path = FileUploadOss.uploadMultipartFile(file);
                if (StringUtils.isBlank(path)) {
                    log.info("文件上传到OSS失败");
                    return new ApiRes(CommonConstant.FAIL_CODE, "文件上传到OSS失败", null);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return new ApiRes(CommonConstant.FAIL_CODE, "请求上传文件异常", null);
            }
            Map<String, Object> map = new HashMap<>();
            map.put("path", path);
//            map.put("type", type);
            map.put("imageurl", imageurl);
            map.put("fileName", file.getOriginalFilename());
            list.add(map);
        }
        return new ApiRes(CommonConstant.SUCCESS_CODE, "上传成功", list);
    }

    /**
     * 密码登录
     */
    @ApiOperation("用户密码登录")
    @PostMapping("/login")
    public ApiRes<LoginVo> login(@RequestBody LoginDTO dto, HttpServletRequest httpServletRequest) {
        return commonService.login(dto, httpServletRequest);
    }

    /**
     * 验证码登录
     */
    @ApiOperation("验证码登录")
    @PostMapping("/smsLogin")
    public ApiRes<LoginVo> smsLogin(@RequestBody SmsLoginDto dto, HttpServletRequest httpServletRequest) {
        return commonService.smsLogin(dto, httpServletRequest);
    }

    /**
     * 登录记录
     */
    @ApiOperation("登录记录")
    @PostMapping("/loginRecord")
    public void loginRecord(HttpServletRequest httpServletRequest) {
        commonService.loginRecord(httpServletRequest);
    }


    /**
     * 重置密码(验证码方式)
     */
    @ApiOperation("重置密码(验证码方式)")
    @PostMapping("/resetSmsPwd")
    public ApiRes resetSmsPwd(@RequestBody ResetSmsPwdDto dto, HttpServletRequest httpServletRequest) {
        return commonService.resetSmsPwd(dto, httpServletRequest);
    }


    /**
     * 银联支付商户回调回调
     *
     * @param request
     * @return
     */
    @PostMapping("/ylCallBack")
    public void ylCallBack(HttpServletRequest request, HttpServletResponse response) {
        /*接收参数*/
        Map<String, String> params = getAllRequestParam(request);
        log.info("银联支付回调参数params-------------={}", JSONObject.toJSON(params));
        String sign = params.get("sign");
        log.info("银联支付回调参数sign-------------={}", sign);
        /*验签*/
        //对通知内容生成sign
        String strSign = NotifyUtil.makeSign(md5key, params);
        log.info("鱼店宝延签sign-------------={}", strSign);
        //判断签名是否相等
        if (sign.equals(strSign)) {
            // 收到通知后记得返回SUCCESS
            log.info("验签通过");
            JSONObject respMap = (JSONObject) JSONObject.toJSON(params);
            String status = respMap.getString("status");
            if ("TRADE_SUCCESS".equals(status)) {
                String targetOrderId = respMap.getString("targetOrderId");
                String seqId = respMap.getString("seqId");
                String merOrderId = respMap.getString("merOrderId");
                PaymentOrderPay paymentOrderPay = new PaymentOrderPay();
                paymentOrderPay.setOrderNo(merOrderId);
                paymentOrderPay.setStatus(3);
                paymentOrderPay.setChannelOrderNo(targetOrderId);
                paymentOrderPay.setTransactionId(seqId);
                jlNoticeAsync.callBack(paymentOrderPay);
                PrintWriter writer = null;
                try {
                    writer = response.getWriter();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                writer.print("SUCCESS");
                writer.flush();
                return;
            } else {
                log.info("银联支付状态回调", request.getParameter("merOrderId"));
            }
        } else {
            log.info("验签未通过");
        }
    }


    /**
     * 银联支付代理回调回调
     *
     * @param request
     * @return
     */
    @PostMapping("/ylAgentCallBack")
    public void ylAgentCallBack(HttpServletRequest request, HttpServletResponse response) {
        /*接收参数*/
        Map<String, String> params = getAllRequestParam(request);
        log.info("银联支付回调参数params-------------={}", params);
        String sign = params.get("sign");
        log.info("银联支付回调参数sign-------------={}", sign);
        /*验签*/
        //对通知内容生成sign
        String strSign = NotifyUtil.makeSign(md5key, params);
        //判断签名是否相等
        if (sign.equals(strSign)) {
            // 收到通知后记得返回SUCCESS
            log.info("验签通过");
            JSONObject data = (JSONObject) JSONObject.toJSON(getAllRequestParam(request));
            String status = data.getString("status");
            if ("TRADE_SUCCESS".equals(status)) {
                String targetOrderId = data.getString("targetOrderId");
                String seqId = data.getString("seqId");
                String merOrderId = data.getString("merOrderId");
                AgentAreaPaymentOrderPay paymentOrderPay = new AgentAreaPaymentOrderPay();
                paymentOrderPay.setOrderNo(merOrderId);
                paymentOrderPay.setStatus(3);
                paymentOrderPay.setChannelOrderNo(targetOrderId);
                paymentOrderPay.setTransactionId(seqId);
                jlNoticeAsync.agentJlCallBack(paymentOrderPay);
                PrintWriter writer = null;
                try {
                    writer = response.getWriter();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                writer.print("SUCCESS");
                writer.flush();
            } else {
                log.info("银联支付状态回调", request.getParameter("merOrderId"));
            }

        } else {
            log.info("验签未通过");
        }
    }


    @PostMapping("/sdCallBack")
    public String sdCallBack(HttpServletRequest request) {
        JSONObject respMap = (JSONObject) JSONObject.toJSON(getAllRequestParam(request));
        log.info("杉德代理相关数据回调respMap：{}", respMap);
        String respData = respMap.getString("data");
        log.info("杉德代理相关数据回调respData：{}", respData);
        String respSign = respMap.getString("sign");
        log.info("杉德代理相关数据回调respSign：{}", respSign);
        try {
            // 验证签名
            boolean valid = CryptoUtil.verifyDigitalSign(respData.getBytes(encoding), Base64.decodeBase64(respSign), CertUtil.getPublicKey(), "SHA1WithRSA");
            if (!valid) {
                log.error("verify sign fail.");
                return null;
            }
            log.info("verify sign success.");
            JSONObject data = JSONObject.parseObject(respData).getJSONObject("body");
            log.info("杉德代理相关数据回调data：{}", data);
            //查询自己本地数据库订单是否已经成功；
            String orderStatus = data.getString("orderStatus");
            if ("1".equals(orderStatus)) {
                String orderNo = data.getString("orderCode");
                String channelNo = data.getString("orderCode");
                String shopOrderNo = data.getString("orderCode");
//                String payMoney = data.getString("payMoney");
                PaymentOrderPay paymentOrderPay = new PaymentOrderPay();
                paymentOrderPay.setOrderNo(shopOrderNo);
                paymentOrderPay.setStatus(3);
                paymentOrderPay.setChannelOrderNo(channelNo);
//                paymentOrderPay.setTotalMoney(new BigDecimal(payMoney));
                paymentOrderPay.setTransactionId(orderNo);
                jlNoticeAsync.callBack(paymentOrderPay);
            } else {
                log.info("嘉联错误状态回调", request.getParameter("shopOrderNo"));
                return "fail";
            }
            //回调未处理结合自身业务处理完相关流程
            //如果业务并发高，建议开启异步回调处理
        } catch (Exception e) {
            e.printStackTrace();
            log.error("异常信息：{}", e.getMessage());
        }
        return "000000";
    }


    @PostMapping("/sdAgentCallBack")
    public String sdAgentCallBack(HttpServletRequest request) {
        JSONObject respMap = (JSONObject) JSONObject.toJSON(getAllRequestParam(request));
        log.info("杉德代理相关数据回调respMap：{}", respMap);
        String respData = respMap.getString("data");
        log.info("杉德代理相关数据回调respData：{}", respData);
        String respSign = respMap.getString("sign");
        log.info("杉德代理相关数据回调respSign：{}", respSign);
        try {
            // 验证签名
            boolean valid = CryptoUtil.verifyDigitalSign(respData.getBytes(encoding), Base64.decodeBase64(respSign), CertUtil.getPublicKey(), "SHA1WithRSA");
            if (!valid) {
                log.error("verify sign fail.");
                return null;
            }
            log.info("verify sign success.");
            JSONObject data = JSONObject.parseObject(respData).getJSONObject("body");
            log.info("杉德代理相关数据回调data：{}", data);
            //查询自己本地数据库订单是否已经成功；
            String orderStatus = data.getString("orderStatus");
            if ("1".equals(orderStatus)) {
                String orderNo = data.getString("orderCode");
                String channelNo = data.getString("orderCode");
                String shopOrderNo = data.getString("orderCode");
//                String payMoney = data.getString("payMoney");
                AgentAreaPaymentOrderPay paymentOrderPay = new AgentAreaPaymentOrderPay();
                paymentOrderPay.setOrderNo(shopOrderNo);
                paymentOrderPay.setStatus(3);
                paymentOrderPay.setChannelOrderNo(channelNo);
//                paymentOrderPay.setTotalMoney(new BigDecimal(payMoney));
                paymentOrderPay.setTransactionId(orderNo);
                jlNoticeAsync.agentJlCallBack(paymentOrderPay);
            } else {
                log.info("杉德错误状态回调", request.getParameter("shopOrderNo"));
                return "fail";
            }
            //回调未处理结合自身业务处理完相关流程
            //如果业务并发高，建议开启异步回调处理
        } catch (Exception e) {
            e.printStackTrace();
            log.error("异常信息：{}", e.getMessage());
        }
        return "000000";
    }


    @PostMapping("/jlCallBack")
    public String callBack(HttpServletRequest request) {
        log.info("嘉联门店订单相关数据回调：{}", JSONObject.toJSON(getAllRequestParam(request)));
        try {
            //查询自己本地数据库订单是否已经成功；
            String orderStatus = request.getParameter("orderStatus");
            if ("1".equals(orderStatus)) {
                String orderNo = request.getParameter("orderNo");
                String channelNo = request.getParameter("channelNo");
                String shopOrderNo = request.getParameter("shopOrderNo");
                String payMoney = request.getParameter("payMoney");
                PaymentOrderPay paymentOrderPay = new PaymentOrderPay();
                paymentOrderPay.setOrderNo(shopOrderNo);
                paymentOrderPay.setStatus(3);
                paymentOrderPay.setChannelOrderNo(channelNo);
                paymentOrderPay.setTotalMoney(new BigDecimal(payMoney));
                paymentOrderPay.setTransactionId(orderNo);
                jlNoticeAsync.callBack(paymentOrderPay);
            } else {
                log.info("嘉联错误状态回调", request.getParameter("shopOrderNo"));
                return "fail";
            }
            //回调未处理结合自身业务处理完相关流程
            //如果业务并发高，建议开启异步回调处理
        } catch (Exception e) {
            e.printStackTrace();
            log.error("异常信息：{}", e.getMessage());
        }
        return "success";
    }

    @PostMapping("/agentJlCallBack")
    public String agentJlCallBack(HttpServletRequest request) {
        log.info("嘉联代理订单相关数据回调：{}", JSONObject.toJSON(getAllRequestParam(request)));
        try {
            //查询自己本地数据库订单是否已经成功；
            String orderStatus = request.getParameter("orderStatus");
            if ("1".equals(orderStatus)) {
                String orderNo = request.getParameter("orderNo");
                String channelNo = request.getParameter("channelNo");
                String shopOrderNo = request.getParameter("shopOrderNo");
                String payMoney = request.getParameter("payMoney");
                AgentAreaPaymentOrderPay paymentOrderPay = new AgentAreaPaymentOrderPay();
                paymentOrderPay.setOrderNo(shopOrderNo);
                paymentOrderPay.setStatus(3);
                paymentOrderPay.setChannelOrderNo(channelNo);
                paymentOrderPay.setTotalMoney(new BigDecimal(payMoney));
                paymentOrderPay.setTransactionId(orderNo);
                jlNoticeAsync.agentJlCallBack(paymentOrderPay);
            } else {
                log.info("嘉联错误状态回调", request.getParameter("shopOrderNo"));
                return "fail";
            }
            //回调未处理结合自身业务处理完相关流程
            //如果业务并发高，建议开启异步回调处理
        } catch (Exception e) {
            e.printStackTrace();
            log.error("异常信息：{}", e.getMessage());
        }
        return "success";
    }


    @PostMapping("/wxjlCallBack")
    public String wxjlCallBack(HttpServletRequest request) {

        log.info("微信支付代理回调--------------------------------------------------------");
        Map<String, String> result = new HashMap<>();
        try {
            String resultxml = resultxml(request);
            log.info("回调订单: {},", resultxml);
            if (StringUtils.isEmpty(resultxml)) {
                result.put("return_code", "FAIL");
                result.put("return_msg", "xml解析错误");
                return WXPayUtil.mapToXml(result);
            }
            Map<String, String> notifyMap = wechatPayBack(resultxml);
            String resultCode = notifyMap.get("resultCode");
            if ("SUCCESS".equals(resultCode)) {//说明支付成功然后进入下一步操作
                //系统支付订单编号
                String outTradeNo = notifyMap.get("outTradeNo");
                //微信支付订单号
                String transactionId = notifyMap.get("transactionId");
                String payMoney = notifyMap.get("cashFee");
                PaymentOrderPay paymentOrderPay = new PaymentOrderPay();
                paymentOrderPay.setOrderNo(outTradeNo);
                paymentOrderPay.setStatus(3);
                paymentOrderPay.setChannelOrderNo(transactionId);
                paymentOrderPay.setTotalMoney(new BigDecimal(payMoney));
                paymentOrderPay.setTransactionId(transactionId);
                jlNoticeAsync.callBack(paymentOrderPay);
                result.put("return_code", "SUCCESS");
                result.put("return_msg", "OK");
                return WXPayUtil.mapToXml(result);
            }
        } catch (Exception e) {
            log.error("微信支付回调通知失败", e);
            e.printStackTrace();
        }
        return "";

    }

    /**
     * 微信支付代理回调
     */
    @RequestMapping("/wxagentJlCallBack")
    public String wxagentJlCallBack(HttpServletRequest request) {
        log.info("微信支付代理回调--------------------------------------------------------");
        Map<String, String> result = new HashMap<>();
        try {
            String resultxml = resultxml(request);
            log.info("回调订单: {},", resultxml);
            if (StringUtils.isEmpty(resultxml)) {
                result.put("return_code", "FAIL");
                result.put("return_msg", "xml解析错误");
                return WXPayUtil.mapToXml(result);
            }
            Map<String, String> notifyMap = wechatPayBack(resultxml);
            String resultCode = notifyMap.get("resultCode");
            if ("SUCCESS".equals(resultCode)) {//说明支付成功然后进入下一步操作
                //系统支付订单编号
                String outTradeNo = notifyMap.get("outTradeNo");
                //微信支付订单号
                String transactionId = notifyMap.get("transactionId");
                String payMoney = notifyMap.get("cashFee");
                AgentAreaPaymentOrderPay paymentOrderPay = new AgentAreaPaymentOrderPay();
                paymentOrderPay.setOrderNo(outTradeNo);
                paymentOrderPay.setStatus(3);
                paymentOrderPay.setChannelOrderNo(transactionId);
                paymentOrderPay.setTotalMoney(new BigDecimal(payMoney));
                paymentOrderPay.setTransactionId(transactionId);
                jlNoticeAsync.agentJlCallBack(paymentOrderPay);
                result.put("return_code", "SUCCESS");
                result.put("return_msg", "OK");
                return WXPayUtil.mapToXml(result);
            }
        } catch (Exception e) {
            log.error("微信支付回调通知失败", e);
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 接受微信回调参数
     *
     * @param request
     * @return
     */
    public String resultxml(HttpServletRequest request) {
        InputStream inStream = null;
        ByteArrayOutputStream outSteam = null;
        try {
            inStream = request.getInputStream();
            outSteam = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = inStream.read(buffer)) != -1) {
                outSteam.write(buffer, 0, len);
            }
            outSteam.close();
            inStream.close();
            return new String(outSteam.toByteArray(), "utf-8");
        } catch (Exception e) {
            log.error("微信支付回调通知失败", e);
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 微信支付回调
     *
     * @param xmlStr
     * @return
     */
    public Map<String, String> wechatPayBack(String xmlStr) {
        String xmlBack = "";
        Map<String, String> notifyMap = null;
        Map<String, String> result = new HashMap<>();
        result.put("tradeType", "");
        result.put("transactionId", "");
        result.put("outTradeNo", "");
        result.put("cashFee", "");
        result.put("totalFee", "");
        result.put("date", "");
        result.put("returnCode", "");
        result.put("resultCode", "");
        result.put("returnMsg", "");
        try {
            MyConfig config = new MyConfig(certPath);
            WXPay wxpay = new WXPay(config);
            notifyMap = WXPayUtil.xmlToMap(xmlStr);         // 转换成map
            if (wxpay.isPayResultNotifySignatureValid(notifyMap)) {
                log.info("签名成功 \r\n" + JSONObject.toJSONString(notifyMap));
                // 签名正确
                // 注意特殊情况：订单已经退款，但收到了支付结果成功的通知，不应把商户侧订单状态从退款改成支付成功
                String return_code = notifyMap.get("return_code").trim();//状态SUCCESS/FAIL
                String result_code = notifyMap.get("result_code");//业务结果SUCCESS/FAIL
                String return_msg = notifyMap.get("return_msg");
                //系统支付订单编号
                String outTradeNo = notifyMap.get("out_trade_no").trim();//订单号
                result.put("returnCode", return_code);
                result.put("resultCode", result_code);
                result.put("returnMsg", return_msg);
                result.put("outTradeNo", outTradeNo);
                if (StringUtils.isEmpty(outTradeNo)) {
                    log.info("微信支付回调失败订单号: {}", notifyMap);
                    return result;
                }
                //交易类型
                String tradeType = notifyMap.get("trade_type").trim();
                //微信支付订单号
                String transactionId = notifyMap.get("transaction_id").trim();
                //订单金额
                String totalFee = notifyMap.get("total_fee").trim();
                //支付金额
                String cashFee = notifyMap.get("cash_fee").trim();
                //支付时间
                String timeEnd = notifyMap.get("time_end").trim();

                //错误返回提示,如果是否失败则会有
                String errCodeDes = notifyMap.get("err_code_des");
                if (StringUtils.isNotEmpty(timeEnd)) {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-HH-dd HH:mm:ss");
                    String date = format.format(new SimpleDateFormat("yyyyHHddHHmmss").parse(timeEnd));
                    result.put("date", date);
                }
                result.put("tradeType", tradeType);
                result.put("transactionId", transactionId);
                result.put("totalFee", totalFee);
                result.put("cashFee", cashFee);
                result.put("errCodeDes", errCodeDes);
                return result;
            } else {
                log.error("微信支付回调通知签名错误\r\n" + JSONObject.toJSONString(notifyMap));
                return result;
            }
        } catch (Exception e) {
            log.error("微信支付回调通知失败", e);
        }
        return result;
    }


    /**
     * 支付分享信息
     *
     * @param payShareInfoDTO
     * @return
     */
    @ApiOperation("支付分享信息")
    @PostMapping("/payShareInfo")
    public ApiRes<PayShareInfoVO> payShareInfo(@RequestBody PayShareInfoDTO payShareInfoDTO) {
        return commonService.payShareInfo(payShareInfoDTO);
    }

    /**
     * 支付
     *
     * @param goPayDTO
     * @param request
     * @return
     */
    @ApiOperation("支付")
    @PostMapping("/goPay")
    public ApiRes<MerchantOrderPayVo> goPay(@RequestBody GoPayDTO goPayDTO, HttpServletRequest request) {
        return commonService.goPay(goPayDTO, request);
    }

    /**
     * 拆单支付
     *
     * @param goPaySeparatelyDTO
     * @param request
     * @return
     */
    @ApiOperation("拆单支付")
    @PostMapping("/goPaySeparately")
    public ApiRes<MerchantOrderPayVo> goPaySeparately(@RequestBody GoPaySeparatelyDTO goPaySeparatelyDTO, HttpServletRequest request) {
        return commonService.goPaySeparately(goPaySeparatelyDTO, request);
    }


    private Map<String, String> getAllRequestParam(final HttpServletRequest request) {
        Map<String, String> res = new HashMap<String, String>();
        Enumeration<?> temp = request.getParameterNames();
        if (null != temp) {
            while (temp.hasMoreElements()) {
                String en = (String) temp.nextElement();
                String value = request.getParameter(en);
                res.put(en, value);
            }
        }
        return res;
    }


}
