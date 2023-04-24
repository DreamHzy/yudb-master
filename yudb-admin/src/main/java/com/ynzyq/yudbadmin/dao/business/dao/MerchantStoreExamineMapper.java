package com.ynzyq.yudbadmin.dao.business.dao;

import com.ynzyq.yudbadmin.dao.business.dto.StoreReviewPageDto;
import com.ynzyq.yudbadmin.dao.business.model.MerchantStoreExamine;

import java.util.List;

import com.ynzyq.yudbadmin.dao.business.vo.StoreRenewalPageVo;
import com.ynzyq.yudbadmin.dao.business.vo.StoreRescindPageVo;
import com.ynzyq.yudbadmin.dao.business.vo.StoreReviewPageVo;
import com.ynzyq.yudbadmin.util.MyBaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface MerchantStoreExamineMapper extends MyBaseMapper<MerchantStoreExamine> {

    @Select("SELECT " +
            "  *  " +
            "FROM " +
            "  merchant_store_examine " +
            "  WHERE `STATUS` = 1 " +
            "  AND EXAMINE_TYPE = 1 " +
            "  AND MERCHANT_STORE_ID = #{id}")
    MerchantStoreExamine queryByStoreId(String id);


    @Select("" +
            "<script>" +
            "  SELECT  " +
            "  p.ID ,  " +
            "  p.CREATE_NAME 'applicant',  " +
            "  p.MERCHANT_STORE_UID uid,  " +
            "  p.CREATE_TIME ,  " +
            "  p.OLD_TIME,  " +
            "  p.NEW_TIME,  " +
            "  ( SELECT `STATUS` FROM merchant_store_examine_deail WHERE MERCHANT_STORE_EXAMINE_ID = p.id AND EXAMINE = 1 ) EXAMINE_ONE_STATUS,  " +
            "  ( SELECT `STATUS` FROM merchant_store_examine_deail WHERE MERCHANT_STORE_EXAMINE_ID = p.id AND EXAMINE = 2 ) EXAMINE_TWO_STATUS   " +
            " FROM  " +
            "  merchant_store_examine p " +
            "  WHERE  p.EXAMINE != - 1  " +
            "    AND   p.EXAMINE_TYPE = 1  " +
            "    AND   p.DELETED = 0  " +
            " <if test='condition !=null'>" +
            " AND ( " +
            "  p.APPLY_NAME  like concat('%',#{condition},'%') " +
            "  OR p.MERCHANT_STORE_UID like concat('%',#{condition},'%') " +
            " )  " +
            " </if>" +
            " <if test='startTime !=null'>" +
            " AND p.CREATE_TIME BETWEEN #{startTime} AND #{endTime} " +
            " </if>" +
            "ORDER BY " +
            "  p.CREATE_TIME DESC" +
            "</script>")
    List<StoreReviewPageVo> storeReviewPageVos(StoreReviewPageDto storeReviewPageDto);

    @Select("" +
            "<script>" +
            "  SELECT  " +
            "  p.ID ,  " +
            "  p.REMARK ,  " +
            "  p.CREATE_NAME 'applicant',  " +
            "  p.MERCHANT_STORE_UID uid,  " +
            "  p.CREATE_TIME ,  " +
            "  ( SELECT `STATUS` FROM merchant_store_examine_deail WHERE MERCHANT_STORE_EXAMINE_ID = p.id AND EXAMINE = 1 ) EXAMINE_ONE_STATUS,  " +
            "  ( SELECT `STATUS` FROM merchant_store_examine_deail WHERE MERCHANT_STORE_EXAMINE_ID = p.id AND EXAMINE = 2 ) EXAMINE_TWO_STATUS   " +
            " FROM  " +
            "  merchant_store_examine p " +
            "  WHERE  p.EXAMINE != - 1  " +
            "    AND   p.EXAMINE_TYPE = 3  " +
            "    AND   p.DELETED = 0  " +
            " <if test='condition !=null'>" +
            " AND ( " +
            "  p.APPLY_NAME  like concat('%',#{condition},'%') " +
            "  OR p.MERCHANT_STORE_UID like concat('%',#{condition},'%') " +
            " )  " +
            " </if>" +
            " <if test='startTime !=null'>" +
            " AND p.CREATE_TIME BETWEEN #{startTime} AND #{endTime} " +
            " </if>" +
            "ORDER BY " +
            "  p.CREATE_TIME DESC" +
            "</script>")
    List<StoreRescindPageVo> storeRescindPageVos(StoreReviewPageDto storeReviewPageDto);

    @Select("" +
            "<script>" +
            "  SELECT  " +
            "  p.ID ,  " +
            "  p.CREATE_NAME 'applicant',  " +
            "  p.MERCHANT_STORE_UID uid,  " +
            "  p.CREATE_TIME ,  " +
            "  p.OLD_MONEY,  " +
            "  p.NEW_MONEY,  " +
            "  ( SELECT `STATUS` FROM merchant_store_examine_deail WHERE MERCHANT_STORE_EXAMINE_ID = p.id AND EXAMINE = 1 ) EXAMINE_ONE_STATUS,  " +
            "  ( SELECT `STATUS` FROM merchant_store_examine_deail WHERE MERCHANT_STORE_EXAMINE_ID = p.id AND EXAMINE = 2 ) EXAMINE_TWO_STATUS   " +
            " FROM  " +
            "  merchant_store_examine p " +
            "  WHERE  p.EXAMINE != - 1  " +
            "    AND   p.EXAMINE_TYPE = 2  " +
            "    AND   p.DELETED = 0  " +
            " <if test='condition !=null'>" +
            " AND ( " +
            "  p.APPLY_NAME  like concat('%',#{condition},'%') " +
            "  OR p.MERCHANT_STORE_UID like concat('%',#{condition},'%') " +
            " )  " +
            " </if>" +
            " <if test='startTime !=null'>" +
            " AND p.CREATE_TIME BETWEEN #{startTime} AND #{endTime} " +
            " </if>" +
            "ORDER BY " +
            "  p.CREATE_TIME DESC" +
            "</script>")
    List<StoreRenewalPageVo> storeRenewalPageVos(StoreReviewPageDto storeReviewPageDto);
}