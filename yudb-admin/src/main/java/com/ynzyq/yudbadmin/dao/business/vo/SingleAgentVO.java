package com.ynzyq.yudbadmin.dao.business.vo;

import com.ynzyq.yudbadmin.dao.business.dto.AgentAreaDTO;
import com.ynzyq.yudbadmin.dao.business.model.MerchantAgentArea;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author Sir
 */
@Data
public class SingleAgentVO implements Serializable {

    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("加盟商标识")
    private String merchantId;

    @ApiModelProperty("授权号")
    private String uid;

    @ApiModelProperty("省市区数组")
    private List<AgentAreaDTO> areaList;

    @ApiModelProperty("区域经理标识")
    private String regionalManagerId;

    @ApiModelProperty("区域经理")
    private String regionalManagerName;

    @ApiModelProperty("签约时间")
    private String signTime;

    @ApiModelProperty("合同到期时间")
    private String expireTime;

    @ApiModelProperty("服务到期时间")
    private String serviceExpireTime;

    @ApiModelProperty(value = "代理-管理费缴纳标准")
    private String managementExpense;

    @ApiModelProperty(value = "代理-管理费缴需缴纳")
    private String alreadyManagementExpense;

    @ApiModelProperty("代理费")
    private String agencyFees;

    @ApiModelProperty("履约保证金")
    private String depositFee;

    @ApiModelProperty("是否建仓：1：是，2：否")
    private String isOpenPosition;

    public SingleAgentVO(MerchantAgentArea merchantAgentArea) {
        this.setId(merchantAgentArea.getId() + "");
        this.setMerchantId(merchantAgentArea.getMerchantId() + "");
        this.setUid(merchantAgentArea.getUid());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        this.setSignTime(sdf.format(merchantAgentArea.getSignTime()));
        this.setExpireTime(sdf.format(merchantAgentArea.getExpireTime()));
        this.setServiceExpireTime(sdf.format(merchantAgentArea.getServiceExpireTime()));
        this.setManagementExpense(merchantAgentArea.getManagementExpense() + "");
        this.setAlreadyManagementExpense(merchantAgentArea.getNeedManagementExpense() + "");
        this.setAgencyFees(merchantAgentArea.getAgencyFees() + "");
        this.setDepositFee(merchantAgentArea.getDepositFee() + "");
        this.setIsOpenPosition(merchantAgentArea.getIsOpenPosition() + "");
    }
}
