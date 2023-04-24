package com.yunzyq.yudbapp.dao;

import com.yunzyq.yudbapp.dao.model.Merchant;
import com.yunzyq.yudbapp.dao.vo.MerchantVO;
import com.yunzyq.yudbapp.dao.vo.StoreListVo;
import com.yunzyq.yudbapp.util.MyBaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MerchantMapper extends MyBaseMapper<Merchant> {


    @Select(" SELECT " +
            " *  " +
            " FROM " +
            " merchant  " +
            " WHERE " +
            " MOBILE = #{phone}" +
            " AND STATUS = 1"
    )
    Merchant queryByPhone(String phone);


    @Select("SELECT " +
            " UID, " +
            " CONCAT(PROVINCE,CITY,AREA) provinceAndCityAndArea, " +
            " ADDRESS, " +
            " MOBILE, " +
            " SIGNATORY signName, " +
            " SIGN_TIME, " +
            " STATUS, " +
            " EXPIRE_TIME " +
            "FROM " +
            "  merchant_store" +
            " WHERE MERCHANT_ID=#{vlaue}" +
            " and STATUS != 9")
    List<StoreListVo> storeList(String vlaue);

    @Select("SELECT " +
            " ( SELECT COUNT( * ) FROM merchant_store ms WHERE ms.MERCHANT_ID = m.ID AND `STATUS` != 9 ) storeCount, " +
            " ( SELECT COUNT( * ) FROM merchant_agent_area ms WHERE ms.MERCHANT_ID = m.ID AND IS_EFFECT != 2 ) agentCount  " +
            "FROM " +
            " merchant m  " +
            "WHERE " +
            " ID = #{id}")
    MerchantVO getMerchantData(Integer id);
}