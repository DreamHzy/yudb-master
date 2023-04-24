package com.ynzyq.yudbadmin.dao.business.dao;

import com.ynzyq.yudbadmin.dao.business.dto.RegionalManagerPageDto;
import com.ynzyq.yudbadmin.dao.business.model.RegionalManager;

import com.ynzyq.yudbadmin.dao.business.vo.RegionalManageListVo;
import com.ynzyq.yudbadmin.dao.business.vo.RegionalManagerPageVo;
import com.ynzyq.yudbadmin.util.MyBaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RegionalManagerMapper extends MyBaseMapper<RegionalManager> {
    @Select(" <script>" +
            "SELECT   " +
            "   rm.ID, " +
            "   rm.UID,  " +
            "   rm.`NAME`,   " +
            "   rm.MOBILE,   " +
            "   rm.MOBILE AS phone,   " +
            "   rm.STATUS,   " +
            "   d.`NAME` departmentName,   " +
            "   d.ID departmentId , " +
            "   su.REALNAME 'createUser',   " +
            "   rm.CREATE_TIME,   " +
            "   ( SELECT COUNT(*) FROM merchant_agent_area_regional_manager WHERE REGIONAL_MANAGER_ID = rm.ID AND `STATUS` = 1) agentAreaCount,   " +
            "   ( SELECT COUNT( 1 ) FROM merchant_store_regional_manager m WHERE m.REGIONAL_MANAGER_ID = rm.ID AND m.`STATUS` = 1 ) sotreCount    " +
            "FROM   " +
            "   regional_manager rm   " +
            "   LEFT JOIN regional_manager_department rmd ON rm.ID = rmd.REGIONAL_MANAGER_ID   " +
            "   LEFT JOIN system_user su ON su.ID = rm.CREATE_USER   " +
            "   LEFT JOIN department d ON d.ID = rmd.DEPARTMENT_ID    " +
            "WHERE   " +
            "   rmd.`STATUS` = 1" +
            " <if test='condition !=null'>" +
            " AND ( " +
            "  rm.`NAME`  like concat('%',#{condition},'%') " +
            "  OR rm.MOBILE like concat('%',#{condition},'%') " +
            " )  " +
            " </if>" +
            "   ORDER BY RM.CREATE_TIME DESC " +
            " </script>")
    List<RegionalManagerPageVo> findPage(RegionalManagerPageDto regionalManagerPageDto);

    @Select("SELECT COUNT( 1 ) FROM merchant_store_regional_manager m WHERE m.REGIONAL_MANAGER_ID = #{id} AND m.`STATUS` = 1")
    Integer selectStroeCountById(String id);


    @Select(" SELECT " +
            " *  " +
            " FROM " +
            " regional_manager  " +
            " WHERE " +
            " MOBILE = #{phone}")
    RegionalManager queryByPhone(String phone);


    @Select(" SELECT " +
            " ID,   " +
            " `NAME`   " +
            " FROM " +
            " regional_manager " +
            " where STATUS = 1"
    )
    List<RegionalManageListVo> queryName();
}