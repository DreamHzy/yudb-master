package com.ynzyq.yudbadmin.api.excel.po;

import com.ynzyq.yudbadmin.dao.business.model.RegionalManagerDepartment;
import com.ynzyq.yudbadmin.enums.StatusEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author wj
 * @Date 2021/10/28
 */
@Data
public class RegionalManagerDeptPO extends RegionalManagerDepartment implements Serializable {
    public RegionalManagerDeptPO(Integer managerId) {
        this.setRegionalManagerId(managerId);
        this.setDepartmentId(1);
        this.setStatus(StatusEnum.ENABLE.getStatus());
        this.setCreateUser(1);
        this.setCreateTime(new Date());
    }
}
