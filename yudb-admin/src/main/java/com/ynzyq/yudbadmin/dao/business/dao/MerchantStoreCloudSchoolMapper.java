package com.ynzyq.yudbadmin.dao.business.dao;

import com.ynzyq.yudbadmin.dao.business.model.MerchantStoreCloudSchool;

import java.util.List;

import com.ynzyq.yudbadmin.dao.business.vo.CloudSchool;
import com.ynzyq.yudbadmin.util.MyBaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface MerchantStoreCloudSchoolMapper extends MyBaseMapper<MerchantStoreCloudSchool> {

    @Select("SELECT ID ,CREATE_DATE 'time', END_TIME 'endTime' , ACCOUNT  FROM merchant_store_cloud_school WHERE MERCHANT_STORE_ID=#{id} and STATUS = 1")
    List<CloudSchool> queryDay(String id);

    @Select("SELECT * FROM merchant_store_cloud_school WHERE MERCHANT_STORE_UID=#{id} and STATUS = 1")
    List<MerchantStoreCloudSchool> queryMerchantStoreId(String uid);

    @Select("SELECT*FROM merchant_store_cloud_school WHERE MERCHANT_STORE_ID=#{id}")
    List<MerchantStoreCloudSchool> queryByMerChantStoreId(Integer id);

    @Select("SELECT*FROM merchant_store_cloud_school WHERE id=#{id}")
    MerchantStoreCloudSchool queryById(Integer id);
}