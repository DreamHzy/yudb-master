package com.ynzyq.yudbadmin.dao.business.vo;

import com.ynzyq.yudbadmin.dao.system.vo.SystemMenuListVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import java.util.Date;
import java.util.List;

@Data
public class DepartmentPageVo {


    private Integer id;

    private Boolean hasChildren;

    private Integer parentId;

    private List<DepartmentPageVo> children;

    @ApiModelProperty(value = "部门名称")
    private String name;

    @ApiModelProperty(value = "创建者")
    private String createUser;

    @ApiModelProperty(value = "创建时间")
    private String createTime;

}
