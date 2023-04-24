package com.ynzyq.yudbadmin.dao.business.dao;

import com.ynzyq.yudbadmin.dao.business.model.MerchantStoreExamine;
import com.ynzyq.yudbadmin.dao.business.model.MerchantStoreExamineDeail;

import java.util.List;

import com.ynzyq.yudbadmin.util.MyBaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface MerchantStoreExamineDeailMapper extends MyBaseMapper<MerchantStoreExamineDeail> {

    @Select("SELECT   " +
            "    *    " +
            "FROM   " +
            "    merchant_store_examine_deail    " +
            "WHERE   " +
            "    MERCHANT_STORE_EXAMINE_ID =#{id}" +
            "   ")
    List<MerchantStoreExamineDeail> queryByExamineId(Integer id);

    @Select("SELECT   " +
            "    *    " +
            "FROM   " +
            "    merchant_store_examine_deail    " +
            "WHERE   " +
            "    MERCHANT_STORE_EXAMINE_ID =#{id}" +
            "   AND EXAMINE= 1 ")
    MerchantStoreExamineDeail queryByOne(MerchantStoreExamine merchantStoreExamine);

    @Select("SELECT   " +
            "    *    " +
            "FROM   " +
            "    merchant_store_examine_deail    " +
            "WHERE   " +
            "    MERCHANT_STORE_EXAMINE_ID =#{id}" +
            "   AND EXAMINE= 2 ")
    MerchantStoreExamineDeail queryByTwo(MerchantStoreExamine merchantStoreExamine);
}