package com.ynzyq.yudbadmin.dao.business.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ynzyq.yudbadmin.annotation.ExcelColumn;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xinchen
 * @date 2021/11/18 10:36
 * @description:
 */
@Data
public class StoreMappingVO implements Serializable {

    @ExcelIgnore
    @ApiModelProperty("店铺id")
    private String id;

    @ExcelProperty(value = "区域代码")
    @ApiModelProperty("区域代码")
    private String uid;

    @ExcelProperty(value = "代理权代码")
    @ApiModelProperty("代理权代码")
    private String agentUid;

    @ExcelProperty(value = "区域")
    @ApiModelProperty("区域")
    private String region;

    @ExcelProperty(value = "省")
    @ApiModelProperty("省")
    private String province;

    @ExcelProperty(value = "市")
    @ApiModelProperty("市")
    private String city;

    @ExcelProperty(value = "区")
    @ApiModelProperty("区")
    private String area;

    @ExcelIgnore
    @ApiModelProperty("代理id")
    private String agentId;

    @ExcelProperty(value = "所属代理")
    @ApiModelProperty("所属代理")
    private String agentName;

    @ExcelIgnore
    @ApiModelProperty("所属区域id")
    private String managerId;

    @ExcelProperty(value = "所属区域经理")
    @ApiModelProperty("所属区域经理")
    private String regionalName;

    @ExcelProperty(value = "线级城市")
    @ApiModelProperty("线级城市")
    private String level;

    @ExcelProperty(value = "是否映射")
    @ApiModelProperty("是否映射：1：是，2：否")
    private String isMapping;

//    @ApiModelProperty("代仓id")
//    private String warehouseId;
//
//    @ExcelProperty(value = "代仓名称" )
//    @ApiModelProperty("代仓名称")
//    private String warehouseName;
//
//    @ApiModelProperty("限单配置id")
//    private String limitConfigId;
//
//    @ExcelProperty(value = "限单配置名称" )
//    @ApiModelProperty("限单配置名称")
//    private String limitConfigName;
//
//    @ApiModelProperty("供应商匹配规则id")
//    private String ruleId;
//
//    @ExcelProperty(value = "供应商匹配规则" )
//    @ApiModelProperty("供应商匹配规则")
//    private String ruleName;
}