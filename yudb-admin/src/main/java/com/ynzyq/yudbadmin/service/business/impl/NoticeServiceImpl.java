package com.ynzyq.yudbadmin.service.business.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ynzyq.yudbadmin.core.model.*;
import com.ynzyq.yudbadmin.dao.business.dao.NoticeFileMapper;
import com.ynzyq.yudbadmin.dao.business.dao.NoticeMapper;
import com.ynzyq.yudbadmin.dao.business.dto.*;
import com.ynzyq.yudbadmin.dao.business.model.Notice;
import com.ynzyq.yudbadmin.dao.business.model.NoticeFile;
import com.ynzyq.yudbadmin.dao.business.vo.NoticeFileVo;
import com.ynzyq.yudbadmin.dao.business.vo.NoticePageVo;
import com.ynzyq.yudbadmin.dao.business.vo.NoticeSeeVo;
import com.ynzyq.yudbadmin.service.business.NoticeService;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
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
    public ApiRes<PageWrap<NoticePageVo>> findPage(PageWrap<NoticePageDto> pageWrap) {
        NoticePageDto noticePageDto = pageWrap.getModel();
        if (noticePageDto == null) {
            noticePageDto.setTitle(null);
        }
        PageHelper.startPage(pageWrap.getPage(), pageWrap.getCapacity());
        List<NoticePageVo> list = noticeMapper.findPage(noticePageDto);
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "查询成功", PageData.from(new PageInfo<>(list)));
    }

    @Override
    public ApiRes top(NoticeTopDto noticeTopDto) {
        if (noticeTopDto == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        String id = noticeTopDto.getId();
        String isTop = noticeTopDto.getIsTop();
        if (StringUtils.isEmpty(id)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        if (!"1".equals(isTop) && !"2".equals(isTop)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        Notice notice = noticeMapper.selectByPrimaryKey(id);
        if (notice == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        LoginUserInfo loginUserInfo = (LoginUserInfo) SecurityUtils.getSubject().getPrincipal();

        if ("1".equals(isTop)) {//将其他同类型的设置为不置顶
            noticeMapper.updateIsTop(notice);
            notice.setIsTop(1);
        } else {
            notice.setIsTop(2);
        }
        notice.setUpdateUser(loginUserInfo.getId());
        notice.setUpdateTime(new Date());
        noticeMapper.updateByPrimaryKeySelective(notice);
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "请求成功", "");
    }

    @Override
    public ApiRes status(NoticeStatusDto noticeStatusDto) {
        if (noticeStatusDto == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        String id = noticeStatusDto.getId();
        String status = noticeStatusDto.getStatus();
        if (StringUtils.isEmpty(id)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        if (!"1".equals(status) && !"2".equals(status)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }

        Notice notice = noticeMapper.selectByPrimaryKey(id);
        if (notice == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        LoginUserInfo loginUserInfo = (LoginUserInfo) SecurityUtils.getSubject().getPrincipal();
        notice.setStatus(Integer.valueOf(status));
        notice.setUpdateUser(loginUserInfo.getId());
        notice.setUpdateTime(new Date());
        noticeMapper.updateByPrimaryKeySelective(notice);
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "请求成功", "");
    }

    @Override
    public ApiRes noticeType(NoticeTypeDto noticeTypeDto) {
        if (noticeTypeDto == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        String id = noticeTypeDto.getId();
        String type = noticeTypeDto.getType();
        if (StringUtils.isEmpty(type)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        Notice notice = noticeMapper.selectByPrimaryKey(id);
        if (notice == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        LoginUserInfo loginUserInfo = (LoginUserInfo) SecurityUtils.getSubject().getPrincipal();
        notice.setType(Integer.valueOf(type));
        notice.setUpdateUser(loginUserInfo.getId());
        notice.setUpdateTime(new Date());
        noticeMapper.updateByPrimaryKeySelective(notice);
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "请求成功", "");
    }

    @Override
    public ApiRes<NoticeSeeVo> see(NoticeSeeDto noticeSeeDto) {
        if (noticeSeeDto == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        String id = noticeSeeDto.getId();
        Notice notice = noticeMapper.selectByPrimaryKey(id);
        if (notice == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        List<NoticeFile> noticeFile = noticeFileMapper.queryByNoticeId(id);
        NoticeSeeVo noticeSeeVo = new NoticeSeeVo();
        noticeSeeVo.setContent(notice.getContent());
        noticeSeeVo.setId(notice.getId() + "");
        noticeSeeVo.setUserType(notice.getUserType() + "");
        noticeSeeVo.setType(notice.getType() + "");
        noticeSeeVo.setIsTop(notice.getIsTop() + "");
        noticeSeeVo.setTitle(notice.getTitle());
        List<NoticeFileVo> noticeFileVoList = new ArrayList<>();
        noticeFile.stream().forEach(
                file -> {
                    NoticeFileVo noticeFileVo = new NoticeFileVo();
                    noticeFileVo.setName(file.getName());
                    noticeFileVo.setUrl(file.getUrl());
                    noticeFileVo.setImageurl(imageurl);
                    noticeFileVoList.add(noticeFileVo);
                }
        );
        noticeSeeVo.setNoticeFileVoList(noticeFileVoList);
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "查询成功", noticeSeeVo);
    }

    @Override
    public ApiRes add(NoticeAddDto noticeAddDto) {

        if (noticeAddDto == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        String title = noticeAddDto.getTitle();
        if (StringUtils.isEmpty(title)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "请输入标题", "");
        }
        String type = noticeAddDto.getType();
        if (StringUtils.isEmpty(type)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "请选择通知类型", "");
        }
        String userType = noticeAddDto.getUserType();
        if (StringUtils.isEmpty(userType)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "请选择用户群体", "");
        }
        String content = noticeAddDto.getContent();
        if (StringUtils.isEmpty(content)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "请输入消息内容", "");
        }
        String isTop = noticeAddDto.getIsTop();
        if (StringUtils.isEmpty(isTop)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "请选择是否置顶", "");
        }
        LoginUserInfo loginUserInfo = (LoginUserInfo) SecurityUtils.getSubject().getPrincipal();

        Notice notice = new Notice();
        notice.setIsTop(Integer.valueOf(isTop));
        notice.setTitle(title);
        notice.setType(Integer.valueOf(type));
        notice.setUserType(Integer.valueOf(userType));
        notice.setContent(content);
        notice.setStatus(1);
        notice.setCreateUser(loginUserInfo.getId());
        notice.setCreateTime(new Date());
        noticeMapper.insertSelective(notice);
        List<NoticeFileDto> noticeFileDtoList = noticeAddDto.getNoticeFileDtoList();
        List<NoticeFile> noticeFileList = new ArrayList<>();
        if (noticeFileDtoList != null && noticeFileDtoList.size() > 0) {
            noticeFileDtoList.stream().forEach(
                    noticeFileDto -> {
                        NoticeFile noticeFile = new NoticeFile();
                        noticeFileList.add(noticeFile);
                        noticeFile.setStatus(1);
                        noticeFile.setName(noticeFileDto.getName());
                        noticeFile.setNoticeId(notice.getId());
                        noticeFile.setCreateTime(new Date());
                        noticeFile.setUrl(noticeFileDto.getUrl());
                    }
            );
            noticeFileMapper.insertList(noticeFileList);
        }
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "新建成功", "");
    }

    @Override
    public ApiRes edit(NoticeEditDto noticeEditDto) {

        if (noticeEditDto == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        String title = noticeEditDto.getTitle();
        if (StringUtils.isEmpty(title)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "请输入标题", "");
        }
        String type = noticeEditDto.getType();
        if (StringUtils.isEmpty(type)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "请选择通知类型", "");
        }
        String userType = noticeEditDto.getUserType();
        if (StringUtils.isEmpty(userType)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "请选择用户群体", "");
        }
        String content = noticeEditDto.getContent();
        if (StringUtils.isEmpty(content)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "请输入消息内容", "");
        }
        String isTop = noticeEditDto.getIsTop();
        if (StringUtils.isEmpty(isTop)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "请选择是否置顶", "");
        }
        String id = noticeEditDto.getId();
        if (StringUtils.isEmpty(id)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        Notice notice = noticeMapper.selectByPrimaryKey(id);

        notice.setContent(content);
        notice.setType(Integer.valueOf(type));
        notice.setIsTop(Integer.valueOf(isTop));
        notice.setUserType(Integer.valueOf(userType));
        notice.setTitle(title);


        LoginUserInfo loginUserInfo = (LoginUserInfo) SecurityUtils.getSubject().getPrincipal();
        notice.setUpdateTime(new Date());
        notice.setUpdateUser(loginUserInfo.getId());
        noticeMapper.updateByPrimaryKeySelective(notice);
        //将原来的附件修改为无用
        noticeFileMapper.updateStatusByNoticeId(notice.getId());

        List<NoticeFileDto> noticeFileDtoList = noticeEditDto.getNoticeFileDtoList();
        List<NoticeFile> noticeFileList = new ArrayList<>();
        if (noticeFileDtoList != null && noticeFileDtoList.size() > 0) {
            noticeFileDtoList.stream().forEach(
                    noticeFileDto -> {
                        NoticeFile noticeFile = new NoticeFile();
                        noticeFileList.add(noticeFile);
                        noticeFile.setStatus(1);
                        noticeFile.setName(noticeFileDto.getName());
                        noticeFile.setNoticeId(notice.getId());
                        noticeFile.setCreateTime(new Date());
                        noticeFile.setUrl(noticeFileDto.getUrl());
                    }
            );
            noticeFileMapper.insertList(noticeFileList);
        }
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "编辑成功", "");
    }
}
