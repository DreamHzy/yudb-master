package com.ynzyq.yudbadmin.dao.business.dao;

import com.ynzyq.yudbadmin.dao.business.dto.ListApproveProcessDTO;
import com.ynzyq.yudbadmin.dao.business.vo.ApproveProcessStepDTO;
import com.ynzyq.yudbadmin.dao.business.model.ApproveProcess;

import java.util.List;

import com.ynzyq.yudbadmin.dao.business.vo.ExamineTypeVO;
import com.ynzyq.yudbadmin.dao.business.vo.ListApproveProcessVO;
import com.ynzyq.yudbadmin.dao.business.vo.SystemUserVO;
import com.ynzyq.yudbadmin.util.MyBaseMapper;
import org.apache.ibatis.annotations.Select;

public interface ApproveProcessMapper extends MyBaseMapper<ApproveProcess> {
    @Select("<script>" +
            "SELECT " +
            " ID, " +
            " TYPE, " +
            " EXAMINE_TYPE " +
            "FROM " +
            " approve_process  " +
            "WHERE " +
            " `STATUS` = 1 " +
            "<if test='type != null'> AND TYPE = #{type} </if>" +
            "<if test='examineType != null'> AND EXAMINE_TYPE = #{examineType} </if>" +
            "ORDER BY " +
            " ID DESC" +
            "</script>")
    List<ListApproveProcessVO> listApproveProcess(ListApproveProcessDTO listApproveProcessDTO);

    @Select("SELECT " +
            " t1.ID, " +
            " t1.STEP, " +
            " t1.`NAME`, " +
            " t1.TYPE, " +
            " t1.USER_ID,  " +
            " t1.USER_NAME  " +
            "FROM " +
            " approve_process_step t1 " +
            "WHERE " +
            " APPROVE_ID = #{id}  " +
            " AND t1.`STATUS` = 1")
    List<ApproveProcessStepDTO> listApproveProcessStep(Integer id);

    @Select("SELECT ID userId,REALNAME userName FROM system_user WHERE DELETED = 0")
    List<SystemUserVO> listUser();

    //    @Select("SELECT EXAMINE_TYPE FROM payment_order_examine GROUP BY EXAMINE_TYPE ORDER BY EXAMINE_TYPE")
    @Select("SELECT int_val AS EXAMINE_TYPE FROM system_dict WHERE `CODE` = 'examineType' AND `STATUS` = 1")
    List<ExamineTypeVO> listExamineType();

    @Select("SELECT COUNT(*) FROM approve_process t1 INNER JOIN payment_order_examine t2 ON t1.EXAMINE_TYPE = t2.EXAMINE_TYPE WHERE EXAMINE = 1 AND t1.TYPE = 1 AND t1.EXAMINE_TYPE = #{examineType}")
    int getUsedCount(Integer examineType);

    @Select("SELECT COUNT(*) FROM approve_process t1 INNER JOIN agent_area_payment_order_examine t2 ON t1.EXAMINE_TYPE = t2.EXAMINE_TYPE WHERE EXAMINE = 1 AND t1.TYPE = 2 AND t1.EXAMINE_TYPE = #{examineType}")
    int getUsedCount2(Integer examineType);

    @Select("SELECT " +
            " t2.USER_NAME  " +
            "FROM " +
            " approve_process t1 " +
            " INNER JOIN approve_process_step t2 ON t1.ID = t2.APPROVE_ID  " +
            "WHERE " +
            " t1.`STATUS` = 1  " +
            " AND t2.`STATUS` = 1  " +
            " AND t1.TYPE = #{userType}  " +
            " AND t1.EXAMINE_TYPE = #{examineType} " +
            " AND t2.STEP = #{step}  " +
            " AND t2.TYPE = #{approveType} ")
    List<String> getApproveName(Integer userType, Integer examineType, Integer step, Integer approveType);
}