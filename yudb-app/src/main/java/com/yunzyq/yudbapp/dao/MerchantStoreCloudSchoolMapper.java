package com.yunzyq.yudbapp.dao;

import com.yunzyq.yudbapp.dao.model.MerchantStoreCloudSchool;

import com.yunzyq.yudbapp.util.MyBaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface MerchantStoreCloudSchoolMapper extends MyBaseMapper<MerchantStoreCloudSchool> {


    @Select("SELECT*FROM merchant_store_cloud_school WHERE id=#{id}")
    MerchantStoreCloudSchool queryById(Integer id);


    @Update(
            "<script>" +
                    "<foreach collection=\"merchantStoreCloudSchoolList\" item=\"item\" index=\"index\" open=\"\" close=\"\" separator=\";\"> " +
                    "            UPDATE merchant_store_cloud_school " +
                    "            <set> " +
                    "                END_TIME=#{item.endTime}," +
                    "            </set> " +
                    "            <where> " +
                    "                 ID =#{item.id} " +
                    "            </where> " +
                    "        </foreach> " +
                    "</script>")
    void updateList(@Param("merchantStoreCloudSchoolList") List<MerchantStoreCloudSchool> merchantStoreCloudSchoolList);

    @Select("SELECT*FROM merchant_store_cloud_school WHERE MERCHANT_STORE_ID=#{id}")
    List<MerchantStoreCloudSchool> queryByMerChantStoreId(Integer id);
}