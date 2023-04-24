package com.ynzyq.yudbadmin.dao.business.dao;

import com.ynzyq.yudbadmin.dao.business.dto.AgentAreaDTO;
import com.ynzyq.yudbadmin.dao.business.dto.ListAppMenuDTO;
import com.ynzyq.yudbadmin.dao.business.dto.MerchantListDto;
import com.ynzyq.yudbadmin.dao.business.dto.MerchantPageDto;
import com.ynzyq.yudbadmin.dao.business.model.Merchant;

import java.util.List;

import com.ynzyq.yudbadmin.dao.business.vo.ListAppMenuVO;
import com.ynzyq.yudbadmin.dao.business.vo.MerchantListVo;
import com.ynzyq.yudbadmin.dao.business.vo.MerchantPageVo;
import com.ynzyq.yudbadmin.util.MyBaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface MerchantMapper extends MyBaseMapper<Merchant> {

    @Select(" SELECT " +
            " *  " +
            " FROM " +
            " merchant  " +
            " WHERE " +
            " MOBILE = #{phone}")
    Merchant queryByPhone(String phone);


    @Select(" SELECT " +
            " *  " +
            " FROM " +
            " merchant  " +
            " WHERE " +
            " UID = #{uid}")
    Merchant queryByUid(String uid);


    @Select("" +
            "<script>" +
            "" +
            "SELECT  " +
            "  ID," +
            " `NAME`,  " +
            " MOBILE phone,  " +
            " IS_AGENT,  " +
            " `STATUS`,  " +
            " ( SELECT COUNT( 1 ) FROM merchant_store ms WHERE ms.MERCHANT_ID = m.ID AND `STATUS` != 9 ) storeCount," +
            " ( SELECT COUNT( 1 ) FROM merchant_agent_area ms WHERE ms.MERCHANT_ID = m.ID AND IS_EFFECT != 2) agentCount," +
            " ID_NUMBER "+
            "FROM  " +
            " merchant m " +
            " WHERE 1=1 " +
            " <if test='condition !=null'>" +
            " AND ( " +
            "  `NAME`  like concat('%',#{condition},'%') " +
            "  OR MOBILE like concat('%',#{condition},'%') " +
            " )  " +
            " </if>" +
            " <if test='startTime !=null'>" +
            " AND CREATE_TIME BETWEEN #{startTime} AND #{endTime} " +
            " </if>" +
            "</script>")
    List<MerchantPageVo> findPage(MerchantPageDto merchantPageDto);


    @Select(" SELECT " +
            " ID," +
            " NAME  " +
            " FROM " +
            " merchant  " +
            " WHERE " +
//            " STATUS = 1" +
//            " AND " +
            "`NAME` like concat('%',#{name},'%')")
    List<MerchantListVo> queryByMerchantListVoList(MerchantListDto merchantListDto);

    @Select("SELECT " +
            " t3.PROVINCE, " +
            " t3.CITY, " +
            " t3.AREA  " +
            "FROM " +
            " merchant_agent_area_regional_manager t1 " +
            " INNER JOIN merchant_agent_area t2 ON t1.MERCHANT_AGENT_AREA_ID = t2.ID " +
            " INNER JOIN merchant_agent_area_detail t3 ON t3.AGENT_AREA_ID = t2.id  " +
            "WHERE " +
            " t1.REGIONAL_MANAGER_ID = #{managerId}")
    List<AgentAreaDTO> agentAreaList(Integer managerId);

    /**
     * 更新代理权中的加盟商姓名
     *
     * @param name
     * @param id
     */
    @Update("UPDATE merchant_agent_area SET MERCHANT_NAME = #{name} WHERE MERCHANT_ID = #{id}")
    void updateAgentMerchant(String name, Integer id);

    /**
     * 更新门店中的所属代理姓名
     *
     * @param name
     * @param id
     */
    @Update("UPDATE merchant_store SET AGENT_NAME = #{name} WHERE AGENT_ID = #{id}")
    void updateStoreMerchant(String name, Integer id);

    @Select("<script>" +
            "SELECT " +
            " 0 AS menuId, " +
            " 'APP登录' AS menuName, " +
            " IS_AUTH AS isChoose  " +
            "FROM " +
            " merchant  " +
            "WHERE " +
            " ID = #{merchantId} UNION ALL " +
            "SELECT " +
            " id AS menuId, " +
            " `NAME` AS menuName, " +
            " ( SELECT COUNT(*) FROM yg_merchant_app_menu WHERE MENU_ID = t1.ID AND MERCHANT_ID = #{merchantId}  AND `STATUS` = 1 ) AS isChoose  " +
            "FROM " +
            " yg_app_menu t1  " +
            "WHERE " +
            " t1.`STATUS` = 1 " +
            " <if test='menuId != null'> AND t1.id != #{menuId}</if>" +
            "</script>")
    List<ListAppMenuVO> listAppMenu(ListAppMenuDTO listAppMenuDTO);
}