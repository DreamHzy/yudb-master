package com.ynzyq.yudbadmin.dao.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * @author xinchen
 * @date 2022/6/20 11:23
 * @description:
 */
@Data
public class EditSystemFactorDTO  implements Serializable {
    @ApiModelProperty("代理权id数字")
    @NotEmpty(message = "代理权id不能为空")
    private List<String> idList;

    @ApiModelProperty("代理体系id：1：1.0，2：2.0，3：3.0")
    @NotBlank(message = "代理体系id不能为空")
    private String systemFactor;
}
