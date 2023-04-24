package com.ynzyq.yudbadmin.api.system;

import com.ynzyq.yudbadmin.annotation.Log;
import com.ynzyq.yudbadmin.api.BaseController;
import com.ynzyq.yudbadmin.biz.SystemUserBiz;
import com.ynzyq.yudbadmin.core.model.ApiRes;
import com.ynzyq.yudbadmin.core.model.ApiResponse;
import com.ynzyq.yudbadmin.core.model.CommonConstant;
import com.ynzyq.yudbadmin.core.model.LoginUserInfo;
import com.ynzyq.yudbadmin.core.utils.SecureUtil;
import com.ynzyq.yudbadmin.dao.business.dto.SmsLoginDto;
import com.ynzyq.yudbadmin.dao.system.dto.LoginDTO;
import com.ynzyq.yudbadmin.dao.system.dto.SmsUpdatePwdDto;
import com.ynzyq.yudbadmin.dao.system.dto.UpdatePwdDto;
import com.ynzyq.yudbadmin.dao.system.model.SystemUser;
import com.ynzyq.yudbadmin.redis.RedisUtils;
import com.ynzyq.yudbadmin.service.system.SystemUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;

@Api(tags = "系统接口")
@Slf4j
@RequestMapping("/system")
@RestController
public class SystemController extends BaseController {

    @Resource
    RedisUtils redisUtils;

    @Value("${session.timeout}")
    private Long sessionTimeout;
    @Resource
    SystemUserService systemUserService;

    @Autowired
    private SystemUserBiz systemUserBiz;


    /**
     * @author Caesar Liu
     * @date 2021-03-27 21:36
     */
//    @Log("用户密码登录")
    @ApiOperation("用户密码登录")
    @PostMapping("/login")
    public ApiResponse login(@RequestBody LoginDTO dto) {
        Subject subject = SecurityUtils.getSubject();
        subject.getSession().setTimeout(sessionTimeout);
        UsernamePasswordToken token = new UsernamePasswordToken(dto.getUsername(), dto.getPassword());
        try {
            subject.login(token);
            Serializable tokenId = subject.getSession().getId();
            LoginUserInfo loginUserInfo = this.getLoginUser();
            loginUserInfo.setToken(tokenId + "");
            return ApiResponse.success(this.getLoginUser());
        } catch (AuthenticationException e) {
            log.error("登录失败", e);
            return ApiResponse.failed("账号或密码错误");
        }
    }


    @ApiOperation("验证码登录")
    @PostMapping("/smsLogin")
    public ApiRes smsLogin(@RequestBody SmsLoginDto dto) {
        if (dto == null) {
            return new ApiRes(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        String code = dto.getCode();
        if (StringUtils.isEmpty(code)) {
            return new ApiRes(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        String code1 = (String) redisUtils.get(dto.getPhone() + ":LOGIN");
        if (!code.equals(code1)) {
            return new ApiRes(CommonConstant.FAIL_CODE, "验证码不正确", "");
        }
        Subject subject = SecurityUtils.getSubject();
        subject.getSession().setTimeout(sessionTimeout);
        SystemUser systemUser = new SystemUser();
        systemUser.setUsername(dto.getPhone());
        systemUser = systemUserService.findOne(systemUser);
        if (systemUser == null) {
            return new ApiRes(CommonConstant.FAIL_CODE, "用户不存在", "");
        }
        systemUser.setUsername(dto.getPhone() + ",1");
        UsernamePasswordToken token = new UsernamePasswordToken(systemUser.getUsername(), systemUser.getPassword());
        try {
            subject.login(token);

            Serializable tokenId = subject.getSession().getId();
            LoginUserInfo loginUserInfo = this.getLoginUser();
            loginUserInfo.setToken(tokenId + "");

            return new ApiRes(CommonConstant.SUCCESS_CODE, "登录成功", this.getLoginUser());
        } catch (AuthenticationException e) {
            log.error("登录失败", e);
            return new ApiRes(CommonConstant.FAIL_CODE, "登录失败", "");
        }
    }


    /**
     * @author Caesar Liu
     * @date 2021-03-27 21:36
     */
    @ApiOperation("退出登录")
    @PostMapping("/logout")
    public ApiResponse logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return ApiResponse.success(null);
    }

    /**
     * @author Caesar Liu
     * @date 2021-03-31 14:16
     */
    @Log("修改密码")
    @ApiOperation("修改密码")
    @PostMapping("/updatePwd")
    public ApiResponse updatePwd(@RequestBody UpdatePwdDto dto) {
        dto.setUserId(this.getLoginUser().getId());
        systemUserBiz.updatePwd(dto);
        return ApiResponse.success(null);
    }

    /**
     * @author Caesar Liu
     * @date 2021-03-31 14:16
     */
    @ApiOperation("验证码修改密码")
    @PostMapping("/smsUpdatePwd")
    public ApiRes smsUpdatePwd(@RequestBody SmsUpdatePwdDto smsUpdatePwdDto) {
        String code = smsUpdatePwdDto.getCode();
        if (StringUtils.isEmpty(code)) {
            return new ApiRes(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        String code1 = (String) redisUtils.get(smsUpdatePwdDto.getPhone() + ":PWD");
        if (!code.equals(code1)) {
            return new ApiRes(CommonConstant.FAIL_CODE, "验证码不正确", "");
        }
        SystemUser systemUserold = new SystemUser();
        systemUserold.setMobile(smsUpdatePwdDto.getPhone());
        SystemUser systemUser = systemUserService.findOne(systemUserold);
        if (systemUser == null) {
            return new ApiRes(CommonConstant.FAIL_CODE, "用户不存在", "");
        }
        systemUser.setPassword(SecureUtil.encryptPassword(smsUpdatePwdDto.getNewPwd(), systemUser.getSalt()));
        systemUserService.updateById(systemUser);
        return new ApiRes(CommonConstant.SUCCESS_CODE, "修改成功", "");
    }


    /**
     * @author Caesar Liu
     * @date 2021-03-28 17:04
     */
    @ApiOperation("获取当前登录的用户信息")
    @GetMapping("/getUserInfo")
    public ApiResponse getUserInfo() {
        return ApiResponse.success(this.getLoginUser());
    }
}
