package com.ynzyq.yudbadmin.util.ylWallet.parm;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

@Data
public class ResultParam {

    private String rspResult;

    private String rspCode;

    private JSONObject msgBody;
}
