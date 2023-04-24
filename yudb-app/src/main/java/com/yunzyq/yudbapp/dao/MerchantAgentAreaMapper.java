package com.yunzyq.yudbapp.dao;

import com.yunzyq.yudbapp.dao.model.MerchantAgentArea;

import com.yunzyq.yudbapp.util.MyBaseMapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author WJ
 */
public interface MerchantAgentAreaMapper  extends MyBaseMapper<MerchantAgentArea> {

    @Select("SELECT*FROM merchant_agent_area WHERE uid  =#{uid} ")
    MerchantAgentArea queryByUid(String uid);
}