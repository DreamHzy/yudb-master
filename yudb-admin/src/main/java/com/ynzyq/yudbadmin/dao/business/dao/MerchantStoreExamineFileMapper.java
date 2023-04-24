package com.ynzyq.yudbadmin.dao.business.dao;

import com.ynzyq.yudbadmin.dao.business.model.MerchantStoreExamineFile;

import com.ynzyq.yudbadmin.util.MyBaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MerchantStoreExamineFileMapper extends MyBaseMapper<MerchantStoreExamineFile> {
    @Select("SELECT   " +
            "    *    " +
            "FROM   " +
            "    merchant_store_examine_file    " +
            "WHERE   " +
            "    MERCHANT_STORE_EXAMINE_ID =#{id}" +
            "   ")
    List<MerchantStoreExamineFile> queryByExamineId(Integer id);
}