package com.ynzyq.yudbadmin.dao.evaluate.mapper;

import com.ynzyq.yudbadmin.dao.evaluate.dto.ListEvaluateDTO;
import com.ynzyq.yudbadmin.dao.evaluate.vo.EvaluationVO;
import com.ynzyq.yudbadmin.dao.evaluate.vo.ListEvaluateItemVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author xinchen
 * @date 2021/12/1 11:53
 * @description:
 */
@Mapper
public interface EvaluateMapper {
    /**
     * 查询评估主题
     *
     * @return
     */
    @Select("SELECT " +
            " ID, " +
            " ITEM, " +
            " IS_REQUIRED, " +
            " `LEVEL` " +
            "FROM " +
            " system_evaluation_items  " +
            "WHERE " +
            " `LEVEL` = 1")
    List<ListEvaluateItemVO> listFirstLevelEvaluateItem();

    /**
     * 查询评估选择项
     *
     * @param pid
     * @return
     */
    @Select("SELECT " +
            " ID, " +
            " ITEM, " +
            " IS_REQUIRED, " +
            " `LEVEL` " +
            "FROM " +
            " system_evaluation_items  " +
            "WHERE " +
            " `LEVEL` = 2 " +
            " AND PID = #{pid}")
    List<ListEvaluateItemVO> listSecondLevelEvaluateItem(Integer pid);

    /**
     * region
     *
     * @param condition
     * @param region
     * @return
     */
    @Select("<script>" +
            "SELECT " +
            " *  " +
            "FROM " +
            " system_user_evaluation  " +
            "WHERE " +
            " REGION = #{region}" +
            "<if test='condition != null'> AND ( APPLICANT LIKE concat('%',#{condition},'%') OR CONTACT_PHONE LIKE concat('%',#{condition},'%') )</if>" +
            "ORDER BY " +
            " ID DESC" +
            "</script>")
    List<EvaluationVO> listEvaluate(String condition, String region);
}
