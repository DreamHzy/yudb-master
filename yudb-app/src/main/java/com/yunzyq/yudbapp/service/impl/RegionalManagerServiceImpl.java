package com.yunzyq.yudbapp.service.impl;

import com.aliyun.oss.common.utils.DateUtil;
import com.yunzyq.yudbapp.core.ApiRes;
import com.yunzyq.yudbapp.core.CommonConstant;
import com.yunzyq.yudbapp.dao.AgencyWorkMapper;
import com.yunzyq.yudbapp.dao.BannerMapper;
import com.yunzyq.yudbapp.dao.NoticeMapper;
import com.yunzyq.yudbapp.dao.PaymentOrderMasterMapper;
import com.yunzyq.yudbapp.dao.model.PaymentOrderMaster;
import com.yunzyq.yudbapp.dao.vo.IndexNoticeListVo;
import com.yunzyq.yudbapp.dao.vo.IndexOrderVo;
import com.yunzyq.yudbapp.dao.vo.IndexRegionalManagerVo;
import com.yunzyq.yudbapp.redis.RedisUtils;
import com.yunzyq.yudbapp.service.RegionalManagerService;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class RegionalManagerServiceImpl implements RegionalManagerService {
    @Resource
    BannerMapper bannerMapper;
    @Resource
    NoticeMapper noticeMapper;
    @Resource
    AgencyWorkMapper agencyWorkMapper;
    @Resource
    PaymentOrderMasterMapper paymentOrderMasterMapper;

    @Resource
    RedisUtils redisUtils;

    @Value("${imageUrl}")
    private String imageurl;

    @Override
    public ApiRes<IndexRegionalManagerVo> index(HttpServletRequest httpServletRequest) {
        //查询正在
        List<String> banners = bannerMapper.qeryOrderSort(imageurl);
        //我的通知
        List<IndexNoticeListVo> noticeListVos = noticeMapper.queryListNoUserType(2);
        //我的代办
        String vlaue = redisUtils.token(httpServletRequest);
        String month = DateFormatUtils.format(new Date(), "MM");
        Integer count = paymentOrderMasterMapper.queryWorkCountByManagerId(vlaue, Integer.parseInt(month));
        IndexOrderVo indexOrderVo;
        if (count != 0) {
            indexOrderVo = paymentOrderMasterMapper.queryNewestWorkInfoByManagerId(vlaue);
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

}
