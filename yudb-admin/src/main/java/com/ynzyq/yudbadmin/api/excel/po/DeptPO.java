package com.ynzyq.yudbadmin.api.excel.po;

import com.ynzyq.yudbadmin.dao.business.model.Department;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author wj
 * @Date 2021/10/28
 */
@Data
public class DeptPO extends Department implements Serializable {

    public DeptPO(Integer parentId, String dept) {
        this.setParentId(parentId);
        this.setName(dept);
        this.setStatus(1);
        this.setCreateUser(1);
        this.setCreateTime(new Date());

    }
}
