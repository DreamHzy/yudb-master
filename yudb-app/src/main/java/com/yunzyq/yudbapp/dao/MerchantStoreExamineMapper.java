package com.yunzyq.yudbapp.dao;

import com.yunzyq.yudbapp.dao.model.MerchantStoreExamine;
import com.yunzyq.yudbapp.util.MyBaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MerchantStoreExamineMapper extends MyBaseMapper<MerchantStoreExamine> {


    @Select("SELECT     " +
            "     *      " +
            "FROM     " +
            "     merchant_store_examine      " +
            "WHERE     " +
            "     DELETED = 0      " +
            "     AND EXAMINE_TYPE = #{examineType}      " +
            "     AND MERCHANT_STORE_ID=#{id}")
    MerchantStoreExamine queryByStroeIdAndExamineType(@Param("id") Integer id, @Param("examineType") int examineType);
}