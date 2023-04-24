package com.yunzyq.yudbapp.dao;

import com.yunzyq.yudbapp.dao.model.MerchantLoginRecord;

import com.yunzyq.yudbapp.util.MyBaseMapper;
import org.apache.ibatis.annotations.Insert;

import java.util.Date;

public interface MerchantLoginRecordMapper extends MyBaseMapper<MerchantLoginRecord> {
    @Insert("INSERT IGNORE INTO merchant_login_record(MERCHANT_ID,LOGIN_TIME,CREATE_TIME) VALUES(#{merchantId},#{loginTime},#{createTime})")
    void insertRecord(Integer merchantId, Date loginTime, Date createTime);
}