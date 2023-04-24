package com.ynzyq.yudbadmin.util.ylWallet.parm;

public class GetPlugRandomKeyParam extends BaseParam{

    /** 获取个数 */
    private String applyCount;

    /** 备注说明 */
    private String remark;

    public String getApplyCount() {
        return applyCount;
    }

    public void setApplyCount(String applyCount) {
        this.applyCount = applyCount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
