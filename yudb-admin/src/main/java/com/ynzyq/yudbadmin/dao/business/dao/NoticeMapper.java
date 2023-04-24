package com.ynzyq.yudbadmin.dao.business.dao;

import com.ynzyq.yudbadmin.dao.business.dto.NoticePageDto;
import com.ynzyq.yudbadmin.dao.business.model.Notice;

import java.util.List;

import com.ynzyq.yudbadmin.dao.business.model.NoticeFile;
import com.ynzyq.yudbadmin.dao.business.vo.NoticePageVo;
import com.ynzyq.yudbadmin.util.MyBaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface NoticeMapper extends MyBaseMapper<Notice> {


    @Select("<script>" +
            " SELECT   " +
            "   n.ID,   " +
            "   n.TITLE,   " +
            "   su.REALNAME 'createUser',   " +
            "   n.USER_TYPE,   " +
            "   n.CREATE_TIME,   " +
            "   n.IS_TOP,   " +
            "   n.`STATUS`,   " +
            "   n.TYPE   " +
            " FROM   " +
            "   notice n   " +
            "   LEFT JOIN system_user su ON n.CREATE_USER = su.ID   " +
            " WHERE 1=1 " +
            " <if test=' title != null'> AND n.TITLE like concat('%',#{title},'%')  </if>" +
            " ORDER BY IS_TOP,CREATE_TIME DESC" +
            " </script>")
    List<NoticePageVo> findPage(NoticePageDto noticePageDto);

    @Update("UPDATE notice    " +
            "SET IS_TOP = 2    " +
            "WHERE   " +
            "   TYPE = #{type}")
    void updateIsTop(Notice notice);
}