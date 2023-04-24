package com.ynzyq.yudbadmin.dao.business.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author wj
 * @Date 2021/10/26
 */
@Data
public class SingleAgentDetailVO implements Serializable {
    @ApiModelProperty("基本信息")
    private BaseInfoVO baseInfoVO;

    @ApiModelProperty("代理区域")
    private List<AgencyAreaVO> agencyAreaVO;

    @ApiModelProperty("缴费记录")
    private List<PaymentRecordVO> paymentRecordVOList;

    public SingleAgentDetailVO(BaseInfoVO baseInfoVO, List<AgencyAreaVO>  agencyAreaVO, List<PaymentRecordVO> paymentRecordVOList) {
        this.baseInfoVO = baseInfoVO;
        this.agencyAreaVO = agencyAreaVO;
        this.paymentRecordVOList = paymentRecordVOList;
    }
}
