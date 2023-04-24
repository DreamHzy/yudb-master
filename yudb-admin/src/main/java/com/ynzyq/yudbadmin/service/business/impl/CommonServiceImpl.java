package com.ynzyq.yudbadmin.service.business.impl;

import com.alibaba.fastjson.JSONObject;
import com.ynzyq.yudbadmin.core.model.ApiRes;
import com.ynzyq.yudbadmin.core.model.CommonConstant;
import com.ynzyq.yudbadmin.dao.business.dao.SmsRecordingMapper;
import com.ynzyq.yudbadmin.dao.business.dto.SmsDto;
import com.ynzyq.yudbadmin.dao.business.model.SmsRecording;
import com.ynzyq.yudbadmin.dao.system.model.SystemUser;
import com.ynzyq.yudbadmin.redis.RedisUtils;
import com.ynzyq.yudbadmin.service.business.CommonService;
import com.ynzyq.yudbadmin.service.system.SystemUserService;
import com.ynzyq.yudbadmin.util.IpUtil;
import com.ynzyq.yudbadmin.util.sms.SmsUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Service
public class CommonServiceImpl implements CommonService {

    @Resource
    RedisUtils redisUtils;
    @Resource
    SystemUserService systemUserService;
    @Resource
    SmsRecordingMapper smsRecordingMapper;

    @Value("${spring.profiles.active}")
    private String springProfilesActive;

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
        //先查询用户是否在数据库里面
        SystemUser queryUserDto = new SystemUser();
        queryUserDto.setDeleted(Boolean.FALSE);
        queryUserDto.setMobile(phone);
        SystemUser user = systemUserService.findOne(queryUserDto);
        if (user == null) {
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
        smsRecording.setContent(code + ":" + type);
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
}
