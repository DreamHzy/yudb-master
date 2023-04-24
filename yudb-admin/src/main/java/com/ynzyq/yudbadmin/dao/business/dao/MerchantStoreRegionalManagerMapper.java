package com.ynzyq.yudbadmin.dao.business.dao;

import com.ynzyq.yudbadmin.dao.business.model.MerchantStoreRegionalManager;

import com.ynzyq.yudbadmin.util.MyBaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface MerchantStoreRegionalManagerMapper extends MyBaseMapper<MerchantStoreRegionalManager> {


    @Update("UPDATE merchant_store_regional_manager    " +
            "SET `STATUS` = 2    " +
            "WHERE   " +
            "   MERCHANT_STORE_ID = #{storeId}")
    void updateStatusByStore(String storeId);

    @Select("SELECT  " +
            "  *   " +
            "FROM  " +
            "  merchant_store_regional_manager   " +
            "WHERE  " +
            "  MERCHANT_STORE_ID = #{id}  " +
            "  AND `STATUS` = 1")
    MerchantStoreRegionalManager queryByMerchantStoreId(Integer id);
}