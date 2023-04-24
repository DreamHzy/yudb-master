package com.yunzyq.yudbapp.dao;

import com.yunzyq.yudbapp.dao.model.MerchantStoreExamineFile;
import com.yunzyq.yudbapp.util.MyBaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface MerchantStoreExamineFileMapper extends MyBaseMapper<MerchantStoreExamineFile> {
    @Update("UPDATE merchant_store_examine_file  " +
            "SET DELETED = 1  " +
            "WHERE " +
            "  MERCHANT_STORE_EXAMINE_ID =#{id} ")
    void updateDeletedByPaymentOrderExamineId(Integer id);
}