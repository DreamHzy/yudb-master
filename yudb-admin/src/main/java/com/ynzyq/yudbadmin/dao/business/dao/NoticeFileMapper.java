package com.ynzyq.yudbadmin.dao.business.dao;

import com.ynzyq.yudbadmin.dao.business.model.NoticeFile;

import java.util.List;

import com.ynzyq.yudbadmin.util.MyBaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface NoticeFileMapper extends MyBaseMapper<NoticeFile> {

    @Select("SELECT   " +
            "   *    " +
            "FROM   " +
            "   notice_file    " +
            "WHERE   " +
            "   `STATUS` = 1    " +
            "   AND NOTICE_ID=#{id}")
    List<NoticeFile> queryByNoticeId(String id);

    @Update("UPDATE notice_file    " +
            "SET `STATUS` = 2    " +
            "WHERE   " +
            "   NOTICE_ID = #{id}")
    void updateStatusByNoticeId(Integer id);
}