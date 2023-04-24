package com.ynzyq.yudbadmin.dao.business.dao;

import com.ynzyq.yudbadmin.dao.business.dto.ListApplyRecordDTO;
import com.ynzyq.yudbadmin.dao.business.model.Merchant;
import com.ynzyq.yudbadmin.dao.business.model.MerchantStoreUidApply;

import com.ynzyq.yudbadmin.dao.business.vo.ListApplyRecordVO;
import com.ynzyq.yudbadmin.dao.business.vo.NextStepVO;
import com.ynzyq.yudbadmin.util.MyBaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MerchantStoreUidApplyMapper extends MyBaseMapper<MerchantStoreUidApply> {

    @Select("<script>" +
            "SELECT " +
            " ID, " +
            " UID, " +
            " SIGNATORY, " +
            " MOBILE, " +
            " ID_CARD, " +
            " SIGN_TIME, " +
            " EXPIRE_TIME, " +
            " MANAGE_MONEY, " +
            " BOND_MONEY, " +
            " FRANCHISE_MONEY, " +
            " IS_AGENT, " +
            " IS_COOPERATE, " +
            " CREATE_TIME " +
            "FROM " +
            " merchant_store_uid_apply " +
            "<where> " +
            " <if test='userId != null'> AND CREATE_USER = #{userId} </if> " +
            " <if test='uid != null'> AND UID = #{uid} </if> " +
            " <if test='mobile != null'> AND MOBILE = #{mobile} </if> " +
            " <if test='signatory != null'> AND SIGNATORY LIKE CONCAT('%',#{signatory},'%') </if> " +
            "</where>" +
            "ORDER BY " +
            " ID " +
            "DESC" +
            "</script>")
    List<ListApplyRecordVO> listApplyRecord(ListApplyRecordDTO listApplyRecordDTO);

    @Select("SELECT  " +
            " ID," +
            " `NAME`,  " +
            " MOBILE phone,  " +
            " IS_AGENT,  " +
            " `STATUS`,  " +
            " ( SELECT COUNT( * ) FROM merchant_store ms WHERE ms.MERCHANT_ID = m.ID AND `STATUS` != 9 ) storeCount," +
            " ( SELECT COUNT( * ) FROM merchant_agent_area ms WHERE ms.MERCHANT_ID = m.ID AND IS_EFFECT != 2) agentCount," +
            " ID_NUMBER " +
            "FROM  " +
            " merchant m " +
            "WHERE " +
            " MOBILE = #{mobile} " +
            "LIMIT 1 ")
    NextStepVO singleMerchant(String mobile);


    @Select("SELECT " +
            " UID  " +
            "FROM " +
            " merchant_store  " +
            "WHERE " +
            " UID >= 3850 " +
            "AND UID < 999998 ORDER BY UID DESC LIMIT 1")
    String getMaxUid();

    @Select("SELECT * FROM merchant WHERE MOBILE = #{mobile} LIMIT 1")
    Merchant getMerchant(String mobile);

    @Select("SELECT DISTINCT ROLE_ID FROM system_user_role WHERE USER_ID = #{userId} AND DELETED = 0")
    List<Integer> listRoleId(Integer userId);
}