package com.yunzyq.yudbapp.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yunzyq.yudbapp.core.ApiRes;
import com.yunzyq.yudbapp.core.CommonConstant;
import com.yunzyq.yudbapp.core.PageData;
import com.yunzyq.yudbapp.core.PageWrap;
import com.yunzyq.yudbapp.dao.NoticeFileMapper;
import com.yunzyq.yudbapp.dao.NoticeMapper;
import com.yunzyq.yudbapp.dao.dto.NoticeDetailDto;
import com.yunzyq.yudbapp.dao.dto.NoticePageDto;
import com.yunzyq.yudbapp.dao.model.Notice;
import com.yunzyq.yudbapp.dao.model.NoticeFile;
import com.yunzyq.yudbapp.dao.vo.NoticeDetailVo;
import com.yunzyq.yudbapp.dao.vo.NoticeFileVo;
import com.yunzyq.yudbapp.dao.vo.NoticePageVo;
import com.yunzyq.yudbapp.service.NoticeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class NoticeServiceImpl implements NoticeService {

    @Resource
    NoticeMapper noticeMapper;
    @Resource
    NoticeFileMapper noticeFileMapper;


    @Value("${imageUrl}")
    private String imageurl;

    @Override
    public ApiRes<PageWrap<NoticePageVo>> findPage(HttpServletRequest httpServletRequest, PageWrap<NoticePageDto> pageWrap) {

        NoticePageDto noticePageDto = pageWrap.getModel();
        if (noticePageDto == null) {
            return ApiRes.response(CommonConstant.SUCCESS_CODE, "参数错误", "");
        }
        String type = noticePageDto.getType();
        if (!"2".equals(type) && !"3".equals(type)) {
            return ApiRes.response(CommonConstant.SUCCESS_CODE, "参数错误", "");
        }
        String noticeType = noticePageDto.getNoticeType();
        if (!"1".equals(noticeType) && !"2".equals(noticeType)) {
            return ApiRes.response(CommonConstant.SUCCESS_CODE, "参数错误", "");
        }
        PageHelper.startPage(pageWrap.getPage(), pageWrap.getCapacity());
        List<NoticePageVo> list = noticeMapper.findPage(noticePageDto);
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "查询成功", PageData.from(new PageInfo<>(list)));
    }

    @Override
    public ApiRes<NoticeDetailVo> detail(NoticeDetailDto noticeDetailDto) {
        if (noticeDetailDto == null) {
            return ApiRes.response(CommonConstant.SUCCESS_CODE, "参数错误", "");
        }
        String id = noticeDetailDto.getId();
        if (StringUtils.isEmpty(id)) {
            return ApiRes.response(CommonConstant.SUCCESS_CODE, "参数错误", "");
        }
        Notice notice = noticeMapper.selectByPrimaryKey(id);
        if (notice == null) {
            return ApiRes.response(CommonConstant.SUCCESS_CODE, "参数错误", "");
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        NoticeDetailVo noticeDetailVo = new NoticeDetailVo();
        noticeDetailVo.setMsg(notice.getContent());
        noticeDetailVo.setTime(sdf.format(notice.getCreateTime()));
        noticeDetailVo.setTitle(notice.getTitle());
        List<NoticeFile> noticeFile = noticeFileMapper.queryByNoticeId(id);
        List<NoticeFileVo> noticeFileVoList = new ArrayList<>();
        noticeFile.stream().forEach(
                file -> {
                    NoticeFileVo noticeFileVo = new NoticeFileVo();
                    noticeFileVo.setName(file.getName());
                    noticeFileVo.setUrl(imageurl + file.getUrl());
                    noticeFileVoList.add(noticeFileVo);
                }
        );
        noticeDetailVo.setList(noticeFileVoList);
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "请求成功", noticeDetailVo);
    }
}
