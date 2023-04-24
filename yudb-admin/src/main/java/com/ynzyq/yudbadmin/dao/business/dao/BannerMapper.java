package com.ynzyq.yudbadmin.dao.business.dao;

import com.ynzyq.yudbadmin.dao.business.model.Banner;

import com.ynzyq.yudbadmin.dao.business.vo.BannerPageVo;
import com.ynzyq.yudbadmin.util.MyBaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface BannerMapper extends MyBaseMapper<Banner> {

    @Select("SELECT   " +
            "   b.ID,   " +
            "   concat(#{imageurl},b.ICON) ICON," +
            "   b.URL,   " +
            "   b.SORT,   " +
            "   b.STATUS,   " +
            "   s.REALNAME,   " +
            "   b.CREATE_TIME    " +
            "FROM   " +
            "   banner b   " +
            "   LEFT JOIN system_user s ON b.CREATE_USER = s.ID    " +
            "ORDER BY   " +
            "  b.SORT, b.CREATE_TIME DESC ")
    List<BannerPageVo> findPage(String imageurl);

    @Update("UPDATE banner    " +
            "SET `STATUS` = 2    " +
            "WHERE   " +
            "   SORT = #{sort}")
    void updateBySort(String sort);

    @Select("SELECT*FROM banner WHERE SORT =#{sort}  AND `STATUS` = 1")
    Banner findBySort(String sort);


    @Select("SELECT count(*) FROM banner WHERE ID !=#{id}  AND `STATUS` = 1")
    int selectCountByStatus(Banner banner);
}