package com.yunzyq.yudbapp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yunzyq.yudbapp.core.ApiRes;
import com.yunzyq.yudbapp.core.CommonConstant;
import com.yunzyq.yudbapp.dao.*;
import com.yunzyq.yudbapp.dao.dto.*;
import com.yunzyq.yudbapp.dao.model.*;
import com.yunzyq.yudbapp.dao.vo.*;
import com.yunzyq.yudbapp.enums.IsAgentEnum;
import com.yunzyq.yudbapp.redis.RedisUtils;
import com.yunzyq.yudbapp.service.CommonService;
import com.yunzyq.yudbapp.service.IMerchantOrderV2Service;
import com.yunzyq.yudbapp.service.MerchantOrderService;
import com.yunzyq.yudbapp.util.Encrypt;
import com.yunzyq.yudbapp.util.IpUtil;
import com.yunzyq.yudbapp.util.WxUtil;
import com.yunzyq.yudbapp.util.sms.SmsUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

@Service
@Slf4j
public class CommonServiceImpl implements CommonService {
    @Value("${spring.profiles.active}")
    private String springProfilesActive;

    @Resource
    RedisUtils redisUtils;
    @Resource
    SmsRecordingMapper smsRecordingMapper;
    @Resource
    MerchantMapper merchantMapper;
    @Resource
    RegionalManagerMapper regionalManagerMapper;

    @Resource
    MerchantLoginRecordMapper merchantLoginRecordMapper;

    @Resource
    PaymentOrderMasterMapper paymentOrderMasterMapper;

    @Resource
    IMerchantOrderV2Service iMerchantOrderV2Service;

    @Resource
    MerchantOrderService merchantOrderService;

    @Value("${AppID}")
    private String AppID;
    @Value("${AppSecret}")
    private String AppSecret;

    @Override
    public ApiRes sms(SmsDto dto, HttpServletRequest httpServletRequest) {

        if (dto == null) {
            return new ApiRes(CommonConstant.FAIL_CODE, "参数错误", null);
        }
        String phone = dto.getPhone();
        if (StringUtils.isEmpty(phone)) {
            return new ApiRes(CommonConstant.FAIL_CODE, "请输入手机号", null);
        }
        String type = dto.getType();
        if (!"LOGIN".equals(type) && !"PWD".equals(type)) {
            return new ApiRes(CommonConstant.FAIL_CODE, "验证码类型不对", null);
        }
        //先查询用户是否在数据库里面业务员经理/加盟商

        Merchant merchant = merchantMapper.queryByPhone(phone);

        RegionalManager regionalManager = regionalManagerMapper.queryByPhone(phone);
        if (merchant == null && regionalManager == null) {
            return new ApiRes(CommonConstant.FAIL_CODE, "用户不存在", null);
        }
        int code = (int) ((Math.random() * 9 + 1) * 100000);
        String smsResult = "";
        if ("dev".equals(springProfilesActive)) {
            code = 123456;
            smsResult = "2";
        } else {
            JSONObject jsonObject = SmsUtil.senSms(phone, code);
            smsResult = jsonObject.getString("code");
        }
        redisUtils.set(phone + ":" + type, code + "", 180L);
        String ip = IpUtil.getIpAddr(httpServletRequest);
        SmsRecording smsRecording = new SmsRecording();
        smsRecording.setIp(ip);
        smsRecording.setContent(code + type);
        smsRecording.setUserType(1);
        smsRecording.setUserPhone(phone);
        smsRecording.setCreateTime(new Date());
        smsRecording.setUpdateTime(new Date());
        if ("2".equals(smsResult)) {//说明下发短信成功
            smsRecording.setResult(1);
            redisUtils.set(phone + CommonConstant.LOGIN_CODE, code + "", 180L);
            smsRecordingMapper.insertSelective(smsRecording);
            return new ApiRes(CommonConstant.SUCCESS_CODE, "发送成功", "");
        } else {
            smsRecording.setResult(2);
        }
        return new ApiRes(CommonConstant.FAIL_CODE, "发送失败", "");
    }

    @Override
    public ApiRes<LoginVo> login(LoginDTO dto, HttpServletRequest httpServletRequest) {

        if (dto == null) {
            return new ApiRes(CommonConstant.FAIL_CODE, "参数错误", null);
        }
        String phone = dto.getPhone();
        if (StringUtils.isEmpty(phone)) {
            return new ApiRes(CommonConstant.FAIL_CODE, "请输入手机号", null);
        }
        String pwd = dto.getPassword();
        if (StringUtils.isEmpty(pwd)) {
            return new ApiRes(CommonConstant.FAIL_CODE, "请输入密码", null);
        }


        Merchant merchant = merchantMapper.queryByPhone(phone);
        RegionalManager regionalManager = regionalManagerMapper.queryByPhone(phone);
        if (merchant == null && regionalManager == null) {
            return new ApiRes(CommonConstant.FAIL_CODE, "用户不存在", null);
        }


        LoginVo loginVo = new LoginVo();
        String token = null;
        try {
            String openId = "";
            ApiRes apiRes = WxUtil.getopenId(dto.getJsCode(), AppID, AppSecret);
            if (apiRes.getCode() == 200) {
                Map<String, String> map = (Map<String, String>) apiRes.getData();
                openId = map.get("openid");
            } else {
                return new ApiRes(CommonConstant.FAIL_CODE, "登录失败", loginVo);
            }
            if (merchant != null) {
                if (merchant.getStatus().equals(2)) {
                    return ApiRes.failed("账户已被禁用，无法登录");
                }
//                else {
//                    MerchantVO merchantData = merchantMapper.getMerchantData(merchant.getId());
//                    // 门店数量和代理权数量都是0的客户
//                    if (merchantData.getAgentCount().equals(0) && merchantData.getStoreCount().equals(0)) {
//                        return new ApiRes(CommonConstant.FAIL_CODE, "账户异常，请联系区域经理", null);
//                    }
//                }
                if (!pwd.equals(merchant.getPassword())) {
                    return new ApiRes(CommonConstant.FAIL_CODE, "密码或手机号错误", null);
                }
                token = Encrypt.AESencrypt(merchant.getId() + "");
                loginVo.setUserType("2");
                // 返回参数增加 是否为代理
                loginVo.setIsMerchant(String.valueOf(merchant.getIsAgent()));

                merchant.setOpenId(openId);
                // 设置最新登录时间
                merchant.setLoginTime(new Date());
                merchantMapper.updateByPrimaryKeySelective(merchant);
                // 添加登录记录
                merchantLoginRecordMapper.insertRecord(merchant.getId(), new Date(), new Date());
            }

            if (regionalManager != null) {
                if (!pwd.equals(regionalManager.getPassword())) {
                    return new ApiRes(CommonConstant.FAIL_CODE, "密码或手机号错误", null);
                }
                token = Encrypt.AESencrypt(regionalManager.getId() + "");
                loginVo.setUserType("3");
                // 是否为代理
                loginVo.setIsMerchant(IsAgentEnum.NO.getIsAgent());
                regionalManager.setOpenId(openId);
                regionalManagerMapper.updateByPrimaryKeySelective(regionalManager);
            }

        } catch (Exception e) {
            log.info("加密失败");
            e.printStackTrace();
            return new ApiRes(CommonConstant.FAIL_CODE, "登录失败", null);
        }
        loginVo.setToken(phone + CommonConstant.LOGIN);
        redisUtils.set(phone + CommonConstant.LOGIN, token);


        return new ApiRes(CommonConstant.SUCCESS_CODE, "登录成功", loginVo);
    }


    @Override
    public ApiRes<LoginVo> smsLogin(SmsLoginDto dto, HttpServletRequest httpServletRequest) {
        if (dto == null) {
            return new ApiRes(CommonConstant.FAIL_CODE, "参数错误", null);
        }
        String phone = dto.getPhone();
        if (StringUtils.isEmpty(phone)) {
            return new ApiRes(CommonConstant.FAIL_CODE, "请输入手机号", null);
        }
        String code = dto.getCode();
        if (StringUtils.isEmpty(code)) {
            return new ApiRes(CommonConstant.FAIL_CODE, "请输入验证码", null);
        }
        String value = (String) redisUtils.get(phone + CommonConstant.LOGIN_CODE);
        if (!code.equals(value)) {
            return new ApiRes(CommonConstant.FAIL_CODE, "验证码错误", null);
        }
        Merchant merchant = merchantMapper.queryByPhone(phone);
        RegionalManager regionalManager = regionalManagerMapper.queryByPhone(phone);
        if (merchant == null && regionalManager == null) {
            return new ApiRes(CommonConstant.FAIL_CODE, "用户不存在", null);
        }

        LoginVo loginVo = new LoginVo();
        String token = null;
        try {
            String openId = "";
            ApiRes apiRes = WxUtil.getopenId(dto.getJsCode(), AppID, AppSecret);
            if (apiRes.getCode() == 200) {
                Map<String, String> map = (Map<String, String>) apiRes.getData();
                openId = map.get("openid");
            } else {
                return new ApiRes(CommonConstant.FAIL_CODE, "登录失败", loginVo);
            }

            if (merchant != null) {
                if (merchant.getStatus().equals(2)) {
                    return ApiRes.failed("账户已被禁用，无法登录");
                }
//                else {
//                    MerchantVO merchantData = merchantMapper.getMerchantData(merchant.getId());
//                    // 门店数量和代理权数量都是0的客户
//                    if (merchantData.getAgentCount().equals(0) && merchantData.getStoreCount().equals(0)) {
//                        return new ApiRes(CommonConstant.FAIL_CODE, "账户异常，请联系区域经理", null);
//                    }
//                }
                token = Encrypt.AESencrypt(merchant.getId() + "");
                loginVo.setUserType("2");
                // 返回参数增加 是否为代理
                loginVo.setIsMerchant(String.valueOf(merchant.getIsAgent()));

                merchant.setOpenId(openId);
                // 设置最新登录时间
                merchant.setLoginTime(new Date());
                merchantMapper.updateByPrimaryKeySelective(merchant);
                // 添加登录记录
                merchantLoginRecordMapper.insertRecord(merchant.getId(), new Date(), new Date());
            }
            if (regionalManager != null) {
                token = Encrypt.AESencrypt(regionalManager.getId() + "");
                loginVo.setUserType("3");
                // 是否为代理
                loginVo.setIsMerchant(IsAgentEnum.NO.getIsAgent());
                regionalManager.setOpenId(openId);
                regionalManagerMapper.updateByPrimaryKeySelective(regionalManager);
            }

        } catch (Exception e) {
            log.info("加密失败");
            e.printStackTrace();
            return new ApiRes(CommonConstant.FAIL_CODE, "登录失败", null);
        }
        loginVo.setToken(phone + CommonConstant.LOGIN);
        redisUtils.set(phone + CommonConstant.LOGIN, token);
        return new ApiRes(CommonConstant.SUCCESS_CODE, "登录成功", loginVo);
    }

    @Override
    public void loginRecord(HttpServletRequest httpServletRequest) {
        try {
            String id = redisUtils.token(httpServletRequest);
            Merchant merchant = merchantMapper.selectByPrimaryKey(id);
            if (merchant == null) {
                return;
            }

            // 添加登录记录
            merchantLoginRecordMapper.insertRecord(merchant.getId(), new Date(), new Date());
        } catch (Exception e) {
            log.error("添加登录记录失败", e);
        }

    }

    @Override
    public ApiRes resetSmsPwd(ResetSmsPwdDto dto, HttpServletRequest httpServletRequest) {
        if (dto == null) {
            return new ApiRes(CommonConstant.FAIL_CODE, "参数错误", null);
        }
        String phone = dto.getPhone();
        if (StringUtils.isEmpty(phone)) {
            return new ApiRes(CommonConstant.FAIL_CODE, "请输入手机号", null);
        }
        String pwd = dto.getPwd();
        if (StringUtils.isEmpty(pwd)) {
            return new ApiRes(CommonConstant.FAIL_CODE, "请输入密码", null);
        }
        String code = dto.getCode();
        if (StringUtils.isEmpty(code)) {
            return new ApiRes(CommonConstant.FAIL_CODE, "请输入验证码", null);
        }
        String key = phone + CommonConstant.PWD_CODE;
        String value = (String) redisUtils.get(key);
        if (!code.equals(value)) {
            return new ApiRes(CommonConstant.FAIL_CODE, "验证码错误", null);
        }
        Merchant merchant = merchantMapper.queryByPhone(phone);
        RegionalManager regionalManager = regionalManagerMapper.queryByPhone(phone);
        if (merchant == null && regionalManager == null) {
            return new ApiRes(CommonConstant.FAIL_CODE, "用户不存在", null);
        }
        if (merchant != null) {
            merchant.setPassword(pwd);
            merchantMapper.updateByPrimaryKeySelective(merchant);
        }
        if (regionalManager != null) {
            regionalManager.setPassword(pwd);
            regionalManagerMapper.updateByPrimaryKeySelective(regionalManager);
        }
        return new ApiRes(CommonConstant.SUCCESS_CODE, "密码重置成功", "");
    }

    @Override
    public ApiRes authQuery(HttpServletRequest httpServletRequest) {
        log.info("进入查询订阅通知是否需要接口");
        String vlaue = redisUtils.token(httpServletRequest);
        Merchant merchant = merchantMapper.selectByPrimaryKey(vlaue);
        if (merchant == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "加盟商不存在", "");
        }
        String key = (String) redisUtils.get(merchant.getMobile() + CommonConstant.AUTH);

        AuthQueryVo authQueryVo = new AuthQueryVo();
        authQueryVo.setIsSend("2");
        authQueryVo.setId("tNoM1LEvWmy-NSgDcUjgyorc30Z16454DS7-nUioDa0");
        if (StringUtils.isEmpty(key)) {
            authQueryVo.setIsSend("1");
            return ApiRes.response(CommonConstant.SUCCESS_CODE, "查询成功", authQueryVo);
        }
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "查询成功", "");
    }

    @Override
    public ApiRes sendAuth(HttpServletRequest httpServletRequest) {
        log.info("订阅通知权限");
        String vlaue = redisUtils.token(httpServletRequest);
        Merchant merchant = merchantMapper.selectByPrimaryKey(vlaue);
        if (merchant == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "加盟商不存在", "");
        }
        redisUtils.set(merchant.getMobile() + CommonConstant.AUTH, "1");
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "请求成功", "");
    }

    @Override
    public ApiRes<PayShareInfoVO> payShareInfo(PayShareInfoDTO payShareInfoDTO) {
        PayShareInfoVO payShareInfoVO = null;
        if ("1".equals(payShareInfoDTO.getType())) {
            payShareInfoVO = paymentOrderMasterMapper.getMerchantOrderById(Integer.parseInt(payShareInfoDTO.getId()));
        } else if ("2".equals(payShareInfoDTO.getType())) {
            payShareInfoVO = paymentOrderMasterMapper.getAgentOrderById(Integer.parseInt(payShareInfoDTO.getId()));
        } else {
            return ApiRes.failed("类型错误");
        }
        if (payShareInfoVO == null) {
            return ApiRes.failed("订单不存在");
        }
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "成功", payShareInfoVO);
    }

    @Override
    public ApiRes<MerchantOrderPayVo> goPay(GoPayDTO goPayDTO, HttpServletRequest request) {
        if ("1".equals(goPayDTO.getType())) {
            MerchantOrderPayDto merchantOrderPayDto = new MerchantOrderPayDto();
            merchantOrderPayDto.setId(goPayDTO.getId());
            merchantOrderPayDto.setOpenId(goPayDTO.getOpenId());
            return merchantOrderService.pay(merchantOrderPayDto, request);
        } else if ("2".equals(goPayDTO.getType())) {
            AgentOrderPayDTO agentOrderPayDto = new AgentOrderPayDTO();
            agentOrderPayDto.setId(goPayDTO.getId());
            agentOrderPayDto.setOpenId(goPayDTO.getOpenId());
            String ip = IpUtil.getIpAddr(request);
            return iMerchantOrderV2Service.agentPay(agentOrderPayDto, ip);
        } else {
            return ApiRes.failed("类型错误");
        }
    }

    @Override
    public ApiRes<MerchantOrderPayVo> goPaySeparately(GoPaySeparatelyDTO goPaySeparatelyDTO, HttpServletRequest request) {
        if ("1".equals(goPaySeparatelyDTO.getType())) {
            MerchantOrderPaySeparatelyDto merchantOrderPaySeparatelyDto = new MerchantOrderPaySeparatelyDto();
            merchantOrderPaySeparatelyDto.setId(goPaySeparatelyDTO.getId());
            merchantOrderPaySeparatelyDto.setMoney(goPaySeparatelyDTO.getMoney());
            merchantOrderPaySeparatelyDto.setOpenId(goPaySeparatelyDTO.getOpenId());
            return merchantOrderService.paySeparately(merchantOrderPaySeparatelyDto, request);
        } else if ("2".equals(goPaySeparatelyDTO.getType())) {
            AgentOrderPaySeparatelyDTO agentOrderPaySeparatelyDTO = new AgentOrderPaySeparatelyDTO();
            agentOrderPaySeparatelyDTO.setId(goPaySeparatelyDTO.getId());
            agentOrderPaySeparatelyDTO.setMoney(goPaySeparatelyDTO.getMoney());
            agentOrderPaySeparatelyDTO.setOpenId(goPaySeparatelyDTO.getOpenId());
            String ip = IpUtil.getIpAddr(request);
            return iMerchantOrderV2Service.agentPaySeparately(agentOrderPaySeparatelyDTO, ip);
        } else {
            return ApiRes.failed("类型错误");
        }
    }
}
