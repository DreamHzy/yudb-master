package com.ynzyq.yudbadmin.api.excel.dto;

import com.ynzyq.yudbadmin.annotation.ExcelColumn;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author wj
 * @Date 2021/10/28
 */
@Data
public class StoreDTO implements Serializable {
    @ExcelColumn(value = "授权号", col = 0)
    private String uid;
    @ExcelColumn(value = "一级状态", col = 0)
    private String status;
    @ExcelColumn(value = "二级状态", col = 0)
    private String statusTwo;
    @ExcelColumn(value = "省", col = 0)
    private String province;
    @ExcelColumn(value = "市", col = 0)
    private String city;
    @ExcelColumn(value = "区", col = 0)
    private String area;
    @ExcelColumn(value = "地址", col = 0)
    private String address;
    @ExcelColumn(value = "加盟费", col = 0)
    private String franchiseFee;
    @ExcelColumn(value = "管理费", col = 0)
    private String managementExpense;
    @ExcelColumn(value = "保证金", col = 0)
    private String bondMoney;
    @ExcelColumn(value = "签约时间", col = 0)
    private String signTime;
    @ExcelColumn(value = "合同到期时间", col = 0)
    private String expireTime;
    @ExcelColumn(value = "开业时间", col = 0)
    private String openTime;
    @ExcelColumn(value = "闭店时间", col = 0)
    private String closeTime;
    @ExcelColumn(value = "迁址时间", col = 0)
    private String relocationTime;
    @ExcelColumn(value = "暂停经营时间", col = 0)
    private String pauseTime;
    @ExcelColumn(value = "签约人姓名", col = 0)
    private String signatory;
    @ExcelColumn(value = "加盟商名称", col = 0)
    private String merchantName;
    @ExcelColumn(value = "加盟商电话", col = 0)
    private String mobile;
    @ExcelColumn(value = "所属代理", col = 0)
    private String agentName;
    @ExcelColumn(value = "是否适用代理优惠", col = 0)
    private String isAgentStore;
    @ExcelColumn(value = "区域经理", col = 0)
    private String regionalManager;
    @ExcelColumn(value = "合同状态", col = 0)
    private String contractStatus;
    @ExcelColumn(value = "服务到期时间", col = 0)
    private String serviceTime;
}
