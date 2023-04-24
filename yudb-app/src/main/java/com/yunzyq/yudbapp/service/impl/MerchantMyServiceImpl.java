package com.yunzyq.yudbapp.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yunzyq.yudbapp.core.ApiRes;
import com.yunzyq.yudbapp.core.CommonConstant;
import com.yunzyq.yudbapp.core.PageData;
import com.yunzyq.yudbapp.core.PageWrap;
import com.yunzyq.yudbapp.dao.*;
import com.yunzyq.yudbapp.dao.dto.MerchantDaibDto;
import com.yunzyq.yudbapp.dao.dto.UpdatePwdDto;
import com.yunzyq.yudbapp.dao.model.Merchant;
import com.yunzyq.yudbapp.dao.model.MerchantAgentArea;
import com.yunzyq.yudbapp.dao.model.MerchantAgentAreaDetail;
import com.yunzyq.yudbapp.dao.vo.*;
import com.yunzyq.yudbapp.enums.StatusTwoEnum;
import com.yunzyq.yudbapp.redis.RedisUtils;
import com.yunzyq.yudbapp.service.MerchantMyService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class MerchantMyServiceImpl implements MerchantMyService {

    @Resource
    RedisUtils redisUtils;
    @Resource
    PaymentOrderMasterMapper paymentOrderMasterMapper;
    @Resource
    MerchantMapper merchantMapper;
    @Resource
    MerchantStoreMapper merchantStoreMapper;

    @Resource
    MerchantAgentAreaMapper merchantAgentAreaMapper;

    @Resource
    MerchantAgentAreaDetailMapper merchantAgentAreaDetailMapper;

    @Override
    public ApiRes my(HttpServletRequest httpServletRequest) {
        String vlaue = redisUtils.token(httpServletRequest);
        Merchant merchant = merchantMapper.selectByPrimaryKey(vlaue);
        if (merchant == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "加盟商不存在", "");
        }

        String time = merchantStoreMapper.queryTime(vlaue);


        Integer count = paymentOrderMasterMapper.queryByMerchantIdWorkCount(vlaue);
        IndexOrderVo indexOrderVo = paymentOrderMasterMapper.queryByMerchantIdWorkInfo(vlaue);
        if (indexOrderVo != null) {
            indexOrderVo.setMsg(indexOrderVo.getName() + "：" + indexOrderVo.getMoney());
        }
        MerchantMyVo regionalManageMyVo = new MerchantMyVo();
        regionalManageMyVo.setCount(count + "");
        regionalManageMyVo.setIndexOrderVo(indexOrderVo);
        regionalManageMyVo.setName(merchant.getName());
        regionalManageMyVo.setTime(time);
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "查询成功", regionalManageMyVo);
    }

    @Override
    public ApiRes<PageWrap<MerchantDaibVo>> daib(HttpServletRequest httpServletRequest, PageWrap<MerchantDaibDto> pageWrap) {
        MerchantDaibDto merchantDaibDto = pageWrap.getModel();
        if (merchantDaibDto == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        String type = merchantDaibDto.getType();
        if (!"1".equals(type) && !"2".equals(type)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        String vlaue = redisUtils.token(httpServletRequest);
        Merchant merchant = merchantMapper.selectByPrimaryKey(vlaue);
        if (merchant == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "区域经理不存在", "");
        }
        merchantDaibDto.setId(merchant.getId() + "");
        PageHelper.startPage(pageWrap.getPage(), pageWrap.getCapacity());
        List<MerchantDaibVo> list = paymentOrderMasterMapper.merchantDaib(merchantDaibDto);
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "查询成功", PageData.from(new PageInfo<>(list)));
    }

    @Override
    public ApiRes<PageWrap<StoreListVo>> storeList(HttpServletRequest httpServletRequest, PageWrap pageWrap) {
        PageHelper.startPage(pageWrap.getPage(), pageWrap.getCapacity());
        String vlaue = redisUtils.token(httpServletRequest);
        List<StoreListVo> list = merchantMapper.storeList(vlaue);
        list.forEach(storeListVo -> {
            storeListVo.setStatus(StatusTwoEnum.getStatusDesc(Integer.parseInt(storeListVo.getStatus())));
        });
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "查询成功", PageData.from(new PageInfo<>(list)));
    }

    @Override
    public ApiRes updatePwd(HttpServletRequest httpServletRequest, UpdatePwdDto updatePwdDto) {
        String vlaue = redisUtils.token(httpServletRequest);

        String oldPwd = updatePwdDto.getOldPwd();
        String newOld = updatePwdDto.getNewPwd();

        Merchant merchant = merchantMapper.selectByPrimaryKey(vlaue);
        if (merchant == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "区域经理不存在", "");
        }
        if (!oldPwd.equals(merchant.getPassword())) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "旧密码错误", "");
        }
        merchant.setPassword(newOld);
        merchantMapper.updateByPrimaryKeySelective(merchant);
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "修改成功", "");
    }

    @Override
    public ApiRes myAgentArea(HttpServletRequest httpServletRequest) {
        String vlaue = redisUtils.token(httpServletRequest);
        Merchant merchant = merchantMapper.selectByPrimaryKey(vlaue);
        if (merchant == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "加盟商不存在", "");
        }
        Example example = new Example(MerchantAgentArea.class);
        example.createCriteria().andEqualTo("merchantId", merchant.getId());
        List<MerchantAgentArea> merchantAgentAreas = merchantAgentAreaMapper.selectByExample(example);
        if (merchantAgentAreas.size() == 0) {
            return ApiRes.response(CommonConstant.SUCCESS_CODE, "成功", merchantAgentAreas);
        }
        List<MyAgentAreaVO> list = new ArrayList<>();
        for (MerchantAgentArea merchantAgentArea : merchantAgentAreas) {
            Example detailExample = new Example(MerchantAgentAreaDetail.class);
            detailExample.createCriteria().andEqualTo("agentAreaId", merchantAgentArea.getId());
            List<MerchantAgentAreaDetail> merchantAgentAreaDetails = merchantAgentAreaDetailMapper.selectByExample(detailExample);
            List<AgencyArea> areaList = new ArrayList<>();
            merchantAgentAreaDetails.forEach(merchantAgentAreaDetail -> {
                AgencyArea agencyArea = new AgencyArea();
                agencyArea.setUid(merchantAgentAreaDetail.getUid());
                agencyArea.setProvince(merchantAgentAreaDetail.getProvince());
                agencyArea.setCity(merchantAgentAreaDetail.getCity());
                agencyArea.setArea(merchantAgentAreaDetail.getArea());
                areaList.add(agencyArea);
            });

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            MyAgentAreaVO myAgentAreaVO = new MyAgentAreaVO();
            myAgentAreaVO.setUid(merchantAgentArea.getUid());
            myAgentAreaVO.setStartTime(sdf.format(merchantAgentArea.getStartTime()));
            myAgentAreaVO.setExpireTime(sdf.format(merchantAgentArea.getExpireTime()));
            myAgentAreaVO.setAreaList(areaList);
            list.add(myAgentAreaVO);
        }
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "成功", list);
    }
}
