package com.ynzyq.yudbadmin.dao.business.dao;

import com.ynzyq.yudbadmin.dao.business.model.ApproveProcessStep;

import com.ynzyq.yudbadmin.util.MyBaseMapper;
import org.apache.ibatis.annotations.Select;

public interface ApproveProcessStepMapper extends MyBaseMapper<ApproveProcessStep> {

    @Select("SELECT " +
            " MAX( t1.STEP )  " +
            "FROM " +
            " approve_process_step t1 " +
            " INNER JOIN approve_process t2 ON t1.APPROVE_ID = t2.ID  " +
            "WHERE " +
            " t2.EXAMINE_TYPE = #{examineType}  " +
            " AND t1.`STATUS` = 1  " +
            " AND t2.`STATUS` = 1  " +
            " AND t2.TYPE = #{type} " +
            "GROUP BY " +
            " t1.STEP  " +
            "ORDER BY " +
            " t1.STEP DESC  " +
            " LIMIT 1")
    Integer getMaxStep(Integer examineType, Integer type);

    //    @Select("SELECT ID FROM approve_process_step WHERE STEP = #{step} AND APPROVE_ID = #{approveId} AND `STATUS` = 1 AND TYPE = 2")
    @Select("SELECT t1.ID FROM approve_process_step t1 INNER JOIN approve_process t2 ON t1.APPROVE_ID = t2.ID WHERE t1.STEP = #{step} AND t1.APPROVE_ID = #{approveId} AND t1.`STATUS` = 1 AND t2.`STATUS` = 1 AND t1.TYPE = 2 AND t2.TYPE = #{type}")
    Integer getUserNextStepId(Integer step, Integer approveId, Integer type);

    @Select("SELECT " +
            " t1.ID  " +
            "FROM " +
            " approve_process_step t1 " +
            " INNER JOIN approve_process t2 ON t1.APPROVE_ID = t2.ID  " +
            "WHERE " +
            " t2.EXAMINE_TYPE = #{examineType}  " +
            " AND t1.`STATUS` = 1  " +
            " AND t2.`STATUS` = 1  " +
            " AND t2.TYPE = #{type}  " +
            " AND t1.STEP = #{step}  " +
            " AND t1.USER_ID = #{userId}")
    Integer getUserStepId(Integer examineType, Integer step, Integer userId, Integer type);

}