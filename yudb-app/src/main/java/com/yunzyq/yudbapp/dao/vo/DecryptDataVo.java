package com.yunzyq.yudbapp.dao.vo;

import lombok.Data;

@Data
public class DecryptDataVo {

    private String openid;

    private String session_key;

    private String errMsg;

    private String encryptedData;

    private String iv;

    private String cloudID;

    private String avatarUrl;

    private String nickName;

    private String gender;

    private String sharerOpenid;

    private String popularizeUid;
}
