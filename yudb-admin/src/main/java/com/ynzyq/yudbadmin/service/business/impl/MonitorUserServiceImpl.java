package com.ynzyq.yudbadmin.service.business.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ynzyq.yudbadmin.core.model.*;
import com.ynzyq.yudbadmin.dao.business.dao.MonitorUserMapper;
import com.ynzyq.yudbadmin.dao.business.dto.MonitorUserAddDto;
import com.ynzyq.yudbadmin.dao.business.dto.MonitorUserStatusDto;
import com.ynzyq.yudbadmin.dao.business.model.MonitorUser;
import com.ynzyq.yudbadmin.dao.business.vo.BannerPageVo;
import com.ynzyq.yudbadmin.dao.business.vo.MonitorUserPageVo;
import com.ynzyq.yudbadmin.service.business.MonitorUserService;
import com.ynzyq.yudbadmin.util.PhoneFormatCheckUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Service
public class MonitorUserServiceImpl implements MonitorUserService {
    @Resource
    MonitorUserMapper monitorUserMapper;


    @Override
    public ApiRes<MonitorUserPageVo> findPage(PageWrap pageWrap) {
        PageHelper.startPage(pageWrap.getPage(), pageWrap.getCapacity());
        List<MonitorUserPageVo> list = monitorUserMapper.findPage();
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "查询成功", PageData.from(new PageInfo<>(list)));
    }

    @Override
    public ApiRes add(MonitorUserAddDto monitorUserAddDto, HttpServletRequest httpServletRequest) {
        if (monitorUserAddDto == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        if (!PhoneFormatCheckUtils.isChinaPhoneLegal(monitorUserAddDto.getPhone())) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "请输入正确的手机号码", "");
        }
        if (StringUtils.isEmpty(monitorUserAddDto.getName())) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "请输入姓名", "");
        }
        LoginUserInfo loginUserInfo = (LoginUserInfo) SecurityUtils.getSubject().getPrincipal();
        MonitorUser monitorUser = new MonitorUser();
        monitorUser.setCreateUser(loginUserInfo.getId());
        monitorUser.setCreateTime(new Date());
        monitorUser.setStatus(1);
        monitorUser.setName(monitorUserAddDto.getName());
        monitorUser.setPhone(monitorUserAddDto.getPhone());
        monitorUserMapper.insertSelective(monitorUser);
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "添加成功", "");
    }

    @Override
    public ApiRes updatStatus(MonitorUserStatusDto monitorUserStatusDto) {
        if (monitorUserStatusDto == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        String id = monitorUserStatusDto.getId();
        if (StringUtils.isEmpty(id)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        String status = monitorUserStatusDto.getStatus();
        if (!"1".equals(status) && !"2".equals(status)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "请选择对应的状态", "");
        }
        MonitorUser monitorUser = monitorUserMapper.selectByPrimaryKey(id);
        if (monitorUser == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "监控员不存在", "");
        }
        monitorUser.setStatus(Integer.valueOf(status));
        monitorUserMapper.updateByPrimaryKeySelective(monitorUser);
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "修改成功", "");
    }
}
