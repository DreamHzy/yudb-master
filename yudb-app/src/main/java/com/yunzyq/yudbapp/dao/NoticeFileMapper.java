package com.yunzyq.yudbapp.dao;

import com.yunzyq.yudbapp.dao.model.NoticeFile;
import com.yunzyq.yudbapp.util.MyBaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface NoticeFileMapper extends MyBaseMapper<NoticeFile> {

    @Select("SELECT   " +
            "   *    " +
            "FROM   " +
            "   notice_file    " +
            "WHERE   " +
            "   `STATUS` = 1    " +
            "   AND NOTICE_ID=#{id}")
    List<NoticeFile> queryByNoticeId(String id);
}