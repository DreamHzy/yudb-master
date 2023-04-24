package com.yunzyq.yudbapp.dao.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author WJ
 */
@Data
public class MakeOutBillDTO implements Serializable {

    /**
     * 加盟商代理区域ID
     */
    @ApiModelProperty("加盟商代理区域id")
    @NotNull(message = "id不能为空")
    private Integer id;

    /**
     * 缴费类型
     */
    @ApiModelProperty("缴费类型id")
    @NotBlank(message = "缴费类型id不能为空")
    private String payTypeId;

    /**
     * 缴费金额
     */
    @ApiModelProperty("缴费金额")
    @NotBlank(message = "缴费金额不能为空")
    @DecimalMin(value = "0.1",message = "最小金额为0.1")
    private String money;

    /**
     * 费用说明
     */
    @ApiModelProperty("费用说明")
    private String remark;

    /**
     * 缴费截至时间
     */
    @ApiModelProperty("缴费截至时间")
    @NotBlank(message = "缴费截至时间不能为空")
    private String time;

    /**
     * 照片
     */
    @ApiModelProperty("照片")
    private List<String> photos;

    /**
     * 视频
     */
    @ApiModelProperty("视频")
    private List<String> video;

}
