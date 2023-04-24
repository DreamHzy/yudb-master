package com.yunzyq.yudbapp.dao;

import com.yunzyq.yudbapp.dao.dto.NoticePageDto;
import com.yunzyq.yudbapp.dao.model.Notice;
import com.yunzyq.yudbapp.dao.vo.IndexNoticeListVo;
import com.yunzyq.yudbapp.dao.vo.NoticePageVo;
import com.yunzyq.yudbapp.util.MyBaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface NoticeMapper extends MyBaseMapper<Notice> {


    @Select("SELECT  " +
            "  ID,  " +
            "  TITLE   " +
            "FROM  " +
            "  notice   " +
            "WHERE  " +
            "    STATUS = 1" +
            "  AND USER_TYPE !=#{type}" +
            "  ORDER BY CREATE_TIME DESC " +
            "")
    List<IndexNoticeListVo> queryListNoUserType(int type);


    @Select("<script>" +
            "SELECT  " +
            "  ID,  " +
            "  TITLE ,  " +
            "  CONTENT msg  " +
            "FROM  " +
            "  notice   " +
            "WHERE  " +
            "  USER_TYPE !=#{type}" +
            " AND STATUS = 1" +
            " <if test='noticeType ==2'>" +
            "  AND TYPE = #{noticeType}" +
            "  ORDER BY IS_TOP DESC " +
            " </if>" +
            " <if test='noticeType ==1'>" +
            "  ORDER BY IS_TOP,CREATE_TIME DESC " +
            " </if>" +
            " </script>")
    List<NoticePageVo> findPage(NoticePageDto noticePageDto);
}