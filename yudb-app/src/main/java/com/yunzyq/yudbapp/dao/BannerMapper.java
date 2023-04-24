package com.yunzyq.yudbapp.dao;

import com.yunzyq.yudbapp.dao.model.Banner;
import com.yunzyq.yudbapp.util.MyBaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface BannerMapper extends MyBaseMapper<Banner> {


    @Select("SELECT " +
            " CONCAT(#{image},ICON) " +
            "FROM " +
            " banner" +
            " WHERE STATUS = 1 " +
            " ORDER BY SORT")
    List<String> qeryOrderSort(String image);
}