package com.yunzyq.yudbapp.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yunzyq.yudbapp.core.ApiRes;
import com.yunzyq.yudbapp.core.CommonConstant;
import com.yunzyq.yudbapp.core.PageData;
import com.yunzyq.yudbapp.core.PageWrap;
import com.yunzyq.yudbapp.dao.PaymentOrderMasterMapper;
import com.yunzyq.yudbapp.dao.RegionalManagerMapper;
import com.yunzyq.yudbapp.dao.dto.RegionalManagerDaibDto;
import com.yunzyq.yudbapp.dao.dto.UpdatePwdDto;
import com.yunzyq.yudbapp.dao.model.Merchant;
import com.yunzyq.yudbapp.dao.model.RegionalManager;
import com.yunzyq.yudbapp.dao.vo.IndexOrderVo;
import com.yunzyq.yudbapp.dao.vo.RegionalManageMyVo;
import com.yunzyq.yudbapp.dao.vo.RegionalManagerDaibVo;
import com.yunzyq.yudbapp.redis.RedisUtils;
import com.yunzyq.yudbapp.service.RegionalManagerMyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class RegionalManagerMyServiceImpl implements RegionalManagerMyService {

    @Resource
    RedisUtils redisUtils;
    @Resource
    PaymentOrderMasterMapper paymentOrderMasterMapper;
    @Resource
    RegionalManagerMapper regionalManagerMapper;

    @Override
    public ApiRes my(HttpServletRequest httpServletRequest) {
        String vlaue = redisUtils.token(httpServletRequest);
        RegionalManager regionalManager = regionalManagerMapper.selectByPrimaryKey(vlaue);
        if (regionalManager == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "区域经理不存在", "");
        }

        Integer count = paymentOrderMasterMapper.queryByRegionalManagerIdWorkCount(vlaue);
        IndexOrderVo indexOrderVo = paymentOrderMasterMapper.queryByRegionalManagerIdInfoWorkInfo(vlaue);
        if (indexOrderVo != null) {
            indexOrderVo.setMsg(indexOrderVo.getName() + "：" + indexOrderVo.getMoney());
        }

        RegionalManageMyVo regionalManageMyVo = new RegionalManageMyVo();
        regionalManageMyVo.setCount(count + "");
        regionalManageMyVo.setIndexOrderVo(indexOrderVo);
        regionalManageMyVo.setName(regionalManager.getName());
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "查询成功", regionalManageMyVo);
    }

    @Override
    public ApiRes<PageWrap<RegionalManagerDaibVo>> daib(HttpServletRequest httpServletRequest, PageWrap<RegionalManagerDaibDto> pageWrap) {
        RegionalManagerDaibDto regionalManagerDaibDto = pageWrap.getModel();
        if (regionalManagerDaibDto == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        String type = regionalManagerDaibDto.getType();
        if (!"1".equals(type) && !"2".equals(type)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        String vlaue = redisUtils.token(httpServletRequest);
        RegionalManager regionalManager = regionalManagerMapper.selectByPrimaryKey(vlaue);
        if (regionalManager == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "区域经理不存在", "");
        }
        regionalManagerDaibDto.setId(regionalManager.getId() + "");
        PageHelper.startPage(pageWrap.getPage(), pageWrap.getCapacity());
        List<RegionalManagerDaibVo> list = paymentOrderMasterMapper.daib(regionalManagerDaibDto);
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "查询成功", PageData.from(new PageInfo<>(list)));
    }

    @Override
    public ApiRes updatePwd(HttpServletRequest httpServletRequest, UpdatePwdDto updatePwdDto) {
        String vlaue = redisUtils.token(httpServletRequest);

        String oldPwd = updatePwdDto.getOldPwd();
        String newOld = updatePwdDto.getNewPwd();

        RegionalManager regionalManager = regionalManagerMapper.selectByPrimaryKey(vlaue);
        if (regionalManager == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "区域经理不存在", "");
        }
        if (!oldPwd.equals(regionalManager.getPassword())) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "旧密码错误", "");
        }
        regionalManager.setPassword(newOld);
        regionalManagerMapper.updateByPrimaryKeySelective(regionalManager);
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "修改成功", "");
    }
}
