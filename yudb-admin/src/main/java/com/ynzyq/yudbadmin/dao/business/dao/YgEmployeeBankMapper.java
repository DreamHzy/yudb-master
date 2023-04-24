package com.ynzyq.yudbadmin.dao.business.dao;

import com.ynzyq.yudbadmin.dao.business.model.YgEmployeeBank;
import com.ynzyq.yudbadmin.dao.business.model.YgEmployeeBankExample;
import com.ynzyq.yudbadmin.dao.evaluate.dto.EmployeeDTO;
import com.ynzyq.yudbadmin.dao.evaluate.dto.EmployeeKeysDTO;
import com.ynzyq.yudbadmin.dao.evaluate.vo.EmployeeVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface YgEmployeeBankMapper {
    int countByExample(YgEmployeeBankExample example);

    int deleteByExample(YgEmployeeBankExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(YgEmployeeBank record);

    int insertSelective(YgEmployeeBank record);

    List<YgEmployeeBank> selectByExample(YgEmployeeBankExample example);

    YgEmployeeBank selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") YgEmployeeBank record, @Param("example") YgEmployeeBankExample example);

    int updateByExample(@Param("record") YgEmployeeBank record, @Param("example") YgEmployeeBankExample example);

    int updateByPrimaryKeySelective(YgEmployeeBank record);

    int updateByPrimaryKey(YgEmployeeBank record);

    List<YgEmployeeBank> selectEmployee(EmployeeDTO employeeDTO);

    List<EmployeeVO> selectEmployees(EmployeeKeysDTO employeeDTO);

    YgEmployeeBank selectEmployeeQueryId(String employeeId);
}