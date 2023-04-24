package com.ynzyq.yudbadmin.service.business.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ynzyq.yudbadmin.core.model.*;
import com.ynzyq.yudbadmin.dao.business.dao.BannerMapper;
import com.ynzyq.yudbadmin.dao.business.dto.BannerAddDto;
import com.ynzyq.yudbadmin.dao.business.dto.BannerStatus;
import com.ynzyq.yudbadmin.dao.business.model.Banner;
import com.ynzyq.yudbadmin.dao.business.vo.BannerPageVo;
import com.ynzyq.yudbadmin.service.business.BannerService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class BannerServiceImpl implements BannerService {

    @Resource
    BannerMapper bannerMapper;

    @Value("${imageUrl}")
    private String imageurl;

    @Override
    public ApiRes<PageWrap<BannerPageVo>> findPage(PageWrap pageWrap) {
        PageHelper.startPage(pageWrap.getPage(), pageWrap.getCapacity());
        List<BannerPageVo> list = bannerMapper.findPage(imageurl);
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "查询成功", PageData.from(new PageInfo<>(list)));
    }

    @Override
    public ApiRes add(BannerAddDto bannerAddDto) {
        if (bannerAddDto == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        String url = bannerAddDto.getUrl();
        String sort = bannerAddDto.getSort();
        if (StringUtils.isEmpty(url)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "请上传图片", "");
        }
        if (!"1".equals(sort) && !"2".equals(sort) && !"3".equals(sort)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "请选择轮播位", "");
        }
        LoginUserInfo loginUserInfo = (LoginUserInfo) SecurityUtils.getSubject().getPrincipal();
        //先将原来的轮播位下架
        Banner bannerOld = bannerMapper.findBySort(sort);
        if (bannerOld != null) {
            bannerOld.setStatus(2);
            bannerOld.setUpdateTime(new Date());
            bannerOld.setUpdateUser(loginUserInfo.getId());
            bannerMapper.updateByPrimaryKeySelective(bannerOld);
        }
        //新建新的轮播位置
        Banner bannerNew = new Banner();
        bannerNew.setStatus(1);
        bannerNew.setCreateTime(new Date());
        bannerNew.setIcon(url);
        bannerNew.setSort(Integer.valueOf(sort));
        bannerNew.setCreateUser(loginUserInfo.getId());
        bannerMapper.insertSelective(bannerNew);
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "创建成功", "");
    }

    @Override
    public ApiRes updatStatus(BannerStatus bannerStatus) {
        if (bannerStatus == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        String id = bannerStatus.getId();
        if (StringUtils.isEmpty(id)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        String status = bannerStatus.getStatus();
        if (!"1".equals(status) && !"2".equals(status)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "请选择对应的状态", "");
        }
        Banner banner = bannerMapper.selectByPrimaryKey(id);
        if (banner == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "内部错误", "");
        }
        LoginUserInfo loginUserInfo = (LoginUserInfo) SecurityUtils.getSubject().getPrincipal();
        //下架逻辑,下架的话必须得保证有一个是发布状态，不能全部下架
        if ("2".equals(status)) {
            //查询除了要下架的这个banner是否还有发布状态的banner
            int count = bannerMapper.selectCountByStatus(banner);
            if (count == 0) {
                return ApiRes.response(CommonConstant.FAIL_CODE, "不能全部下架，至少保留一个发布状态", "");
            }
            banner.setStatus(2);
            banner.setUpdateUser(loginUserInfo.getId());
            banner.setUpdateTime(new Date());
            bannerMapper.updateByPrimaryKeySelective(banner);
        } else {//上架状态的话需要将相同的轮播位下架
            bannerMapper.updateBySort(banner.getSort() + "");
            banner.setStatus(1);
            banner.setUpdateUser(loginUserInfo.getId());
            banner.setUpdateTime(new Date());
            bannerMapper.updateByPrimaryKeySelective(banner);
        }
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "操作成功", "");
    }
}
