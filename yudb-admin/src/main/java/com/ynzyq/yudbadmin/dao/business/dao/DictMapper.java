package com.ynzyq.yudbadmin.dao.business.dao;

import com.ynzyq.yudbadmin.dao.business.vo.OneLevelStatusVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author wj
 * @Date 2021/11/6
 */
@Mapper
public interface DictMapper {
    /**
     * 查询一级状态
     *
     * @return
     */
    @Select("SELECT int_val AS id,string_val AS name FROM system_dict WHERE `CODE` = 'status' AND `STATUS` = 1")
    List<OneLevelStatusVO> getOneLevelStatus();

    /**
     * 查询二级状态
     *
     * @return
     */
    @Select("SELECT int_val AS id,string_val AS name FROM system_dict WHERE `CODE` = 'statusTwo' AND `STATUS` = 1")
    List<OneLevelStatusVO> getTwoLevelStatus();

    /**
     * 查询一级状态(二级状态)
     *
     * @return
     */
    @Select("SELECT int_val AS id,string_val AS name FROM system_dict WHERE `CODE` = 'payType' AND `STATUS` = 1")
    List<OneLevelStatusVO> getPayType();

    /**
     * 查询合同状态
     *
     * @return
     */
    @Select("SELECT int_val AS id,string_val AS name FROM system_dict WHERE `CODE` = 'contractStatus' AND `STATUS` = 1")
    List<OneLevelStatusVO> getContractStatus();

    /**
     * 查询门店类型
     *
     * @return
     */
    @Select("SELECT int_val AS id,string_val AS name FROM system_dict WHERE `CODE` = 'storeType' AND `STATUS` = 1")
    List<OneLevelStatusVO> getStoreType();

    /**
     * 查询门店类型
     *
     * @return
     */
    @Select("SELECT int_val AS id,string_val AS name FROM system_dict WHERE `CODE` = #{code}  AND `STATUS` = 1")
    List<OneLevelStatusVO> getDict(String code);
}
