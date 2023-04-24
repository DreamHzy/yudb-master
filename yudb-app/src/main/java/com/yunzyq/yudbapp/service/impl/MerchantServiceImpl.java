package com.yunzyq.yudbapp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yunzyq.yudbapp.core.ApiRes;
import com.yunzyq.yudbapp.core.CommonConstant;
import com.yunzyq.yudbapp.dao.*;
import com.yunzyq.yudbapp.dao.dto.YGshangchengDto;
import com.yunzyq.yudbapp.dao.model.Merchant;
import com.yunzyq.yudbapp.dao.vo.IndexNoticeListVo;
import com.yunzyq.yudbapp.dao.vo.IndexOrderVo;
import com.yunzyq.yudbapp.dao.vo.IndexRegionalManagerVo;
import com.yunzyq.yudbapp.redis.RedisUtils;
import com.yunzyq.yudbapp.service.MerchantService;
import com.yunzyq.yudbapp.util.YgScUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

@Service
public class MerchantServiceImpl implements MerchantService {
    @Resource
    BannerMapper bannerMapper;
    @Resource
    NoticeMapper noticeMapper;
    @Resource
    AgencyWorkMapper agencyWorkMapper;
    @Resource
    RedisUtils redisUtils;

    @Resource
    PaymentOrderMasterMapper paymentOrderMasterMapper;

    @Value("${imageUrl}")
    private String imageurl;

    @Resource
    MerchantMapper merchantMapper;


    @Override
    public ApiRes<IndexRegionalManagerVo> index(HttpServletRequest httpServletRequest) {
        //查询正在
        List<String> banners = bannerMapper.qeryOrderSort(imageurl);
        //我的通知
        List<IndexNoticeListVo> noticeListVos = noticeMapper.queryListNoUserType(3);
        //我的代办
        String vlaue = redisUtils.token(httpServletRequest);
        Integer count = paymentOrderMasterMapper.queryWorkCountByMerchantId(vlaue);
        IndexOrderVo indexOrderVo;
        if (count != 0) {
            indexOrderVo = paymentOrderMasterMapper.queryNewestWorkInfoByMerchantId(vlaue);
            if (!Objects.isNull(indexOrderVo)) {
                indexOrderVo.setMsg(indexOrderVo.getName() + "：" + indexOrderVo.getMoney());
            }
        } else {
            indexOrderVo = new IndexOrderVo();
            indexOrderVo.setId("");
            indexOrderVo.setName("");
            indexOrderVo.setMsg("");
            indexOrderVo.setMoney("");
        }
        IndexRegionalManagerVo indexRegionalManagerVo = new IndexRegionalManagerVo();
        indexRegionalManagerVo.setBanners(banners);
        indexRegionalManagerVo.setNoticeListVo(noticeListVos);
        indexRegionalManagerVo.setCount(count + "");
        indexRegionalManagerVo.setIndexOrderVo(indexOrderVo);
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "查询成功", indexRegionalManagerVo);
    }

    @Override
    public ApiRes YGshangcheng(HttpServletRequest httpServletRequest, YGshangchengDto yGshangchengDto) {
        System.out.println("uid=" + yGshangchengDto.getUid());
        if (StringUtils.isBlank(yGshangchengDto.getUid())) {
            return ApiRes.failed("uid不能为空");
        }
        String vlaue = redisUtils.token(httpServletRequest);
        Merchant merchant = merchantMapper.selectByPrimaryKey(vlaue);
        if (merchant == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "加盟商不存在", "");
        }
        String result = YgScUtil.sendSubscriptionMsg(merchant.getName(), merchant.getMobile(), yGshangchengDto.getUid() + "");
        JSONObject jsonObject = JSONObject.parseObject(result);
        String code = jsonObject.getString("code");
        if ("1000".equals(code)) {
            String resultMsg = jsonObject.getString("result");
            JSONObject jsonObject1 = JSONObject.parseObject(resultMsg);
            return ApiRes.response(CommonConstant.SUCCESS_CODE, "登陆成功", jsonObject1.get("redirect_url"));
        }
        return ApiRes.response(CommonConstant.FAIL_CODE, "登陆失败", "");
    }
}
