package com.ynzyq.yudbadmin.dao.business.dao;

import com.ynzyq.yudbadmin.dao.business.dto.DepartmentPageDto;
import com.ynzyq.yudbadmin.dao.business.model.Department;

import java.util.List;

import com.ynzyq.yudbadmin.dao.business.vo.DepartmentListVo;
import com.ynzyq.yudbadmin.dao.business.vo.DepartmentPageVo;
import com.ynzyq.yudbadmin.util.MyBaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface DepartmentMapper extends MyBaseMapper<Department> {


    @Select(" <script>" +
            " SELECT " +
            "  d.ID, " +
            "  d.`NAME`, " +
            "  d.PARENT_ID, " +
            "  s.REALNAME 'createUser', " +
            "  s.CREATE_TIME  " +
            " FROM " +
            "  department d " +
            "  LEFT JOIN system_user s ON d.CREATE_USER = s.ID  " +
            " WHERE " +
            "  d.`STATUS` = 1  " +
            " </script>")
    List<DepartmentPageVo> selectList();

    @Select("SELECT   " +
            "   *    " +
            "FROM   " +
            "   department   " +
            "   WHERE `NAME` =#{name}    " +
            "   AND `STATUS` = 1")
    Department queryByName(String name);


    @Select("SELECT   " +
            "   *    " +
            "FROM   " +
            "   department   " +
            "   WHERE `NAME` =#{name}    " +
            "   AND `STATUS` = 1 " +
            "   AND ID != #{id} ")
    Department queryByNameAndNotInId(Department department);


    @Select("SELECT   " +
            "   NAME," +
            "    ID    " +
            "FROM   " +
            "   department   " +
            "   WHERE `STATUS` = 1 "
    )
    List<DepartmentListVo> quallDepartmentList();
}