package com.ynzyq.yudbadmin.dao.business.dao;

import com.ynzyq.yudbadmin.api.excel.dto.MerchantMoneyDto;
import com.ynzyq.yudbadmin.dao.business.dto.ManagerDTO;
import com.ynzyq.yudbadmin.dao.business.model.MerchantAgentArea;

import java.util.List;

import com.ynzyq.yudbadmin.dao.business.vo.AgentExcelSubmitForReviewVo;
import com.ynzyq.yudbadmin.dao.business.vo.AgentOrderExpireTimeVo;
import com.ynzyq.yudbadmin.dao.business.vo.ExcelSubmitForReviewVo;
import com.ynzyq.yudbadmin.util.MyBaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface MerchantAgentAreaMapper extends MyBaseMapper<MerchantAgentArea> {

    @Select("SELECT * FROM merchant_agent_area WHERE UID =#{uid} ")
    MerchantAgentArea queryByUid(String uid);

    @Select("SELECT  " +
            "  UID  " +
            "FROM  " +
            "  merchant_agent_area")
    List<String> queryUids();


    @Select("SELECT  " +
            "  ms.UID uid,  " +
            "  m.MOBILE merchantMobile,  " +
            "  m.ID merchantId,  " +
            "  m.`NAME` maechantName,  " +
            "  rm.ID regionalManagerId,  " +
            "  rm.`NAME` regionalManagerName,  " +
            "  rm.MOBILE regionalManagerMobile  " +
            "FROM  " +
            "  merchant_agent_area ms LEFT JOIN merchant m ON ms.MERCHANT_ID = m.ID  " +
            "  LEFT JOIN merchant_agent_area_regional_manager msrm ON msrm.MERCHANT_AGENT_AREA_ID = ms.ID  " +
            "  LEFT JOIN regional_manager rm ON rm.ID = msrm.REGIONAL_MANAGER_ID  " +
            "  WHERE msrm.`STATUS`=1  " +
            "  ")
    List<AgentExcelSubmitForReviewVo> ExcelSubmitForReviewVo();


    @Select("<script>" +
            " SELECT    " +
            "    ms.MERCHANT_ID merchantId,    " +
            "    ms.MERCHANT_NAME merchantName,    " +
            "    ms.UID ,    " +
            "    ms.MOBILE ,    " +
            "    ms.PROVINCE ,    " +
            "    ms.CITY ,    " +
            "    ms.AREA ,    " +
            "    msrm.REGIONAL_MANAGER_ID regionalManagerId,    " +
            "    rm.`NAME` regionalManagerName,    " +
            "    rm.MOBILE regionalManagerMobile , " +
            "    ms.MANAGEMENT_EXPENSE  money," +
            "    ms.EXPIRE_TIME  " +
            " FROM    " +
            "    merchant_agent_area ms    " +
            "    LEFT JOIN merchant m ON ms.MERCHANT_ID = m.ID    " +
            "    LEFT JOIN merchant_agent_area_regional_manager msrm ON ms.ID = msrm.MERCHANT_AGENT_AREA_ID    " +
            "    LEFT JOIN regional_manager rm ON rm.ID = msrm.REGIONAL_MANAGER_ID     " +
            " WHERE    " +
            "    ms.UID in " +
            "   <foreach collection=\"list\" item=\"merchantMoneyDto\" index=\"index\"     " +
            "            open=\"(\" close=\")\" separator=\",\">     " +
            "            #{merchantMoneyDto.code}        " +
            "        </foreach> " +
            " </script>")
    List<AgentOrderExpireTimeVo> queryCodes(List<MerchantMoneyDto> list);

    /**
     * 查询区域经理
     *
     * @param areaId
     * @return
     */
    @Select("SELECT " +
            " t1.ID, " +
            " t1.`NAME`  " +
            "FROM " +
            " regional_manager t1 " +
            " INNER JOIN merchant_agent_area_regional_manager t2 ON t1.ID = t2.REGIONAL_MANAGER_ID  " +
            "WHERE " +
            " t2.`STATUS` = 1  " +
            " AND t1.`STATUS` = 1  " +
            " AND t2.MERCHANT_AGENT_AREA_ID = #{areaId}  " +
            " LIMIT 1")
    ManagerDTO getManagerInfo(Integer areaId);
}