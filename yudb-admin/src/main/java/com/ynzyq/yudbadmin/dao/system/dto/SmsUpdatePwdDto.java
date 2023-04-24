package com.ynzyq.yudbadmin.dao.system.dto;

import lombok.Data;

@Data
public class SmsUpdatePwdDto {

    private String newPwd;

    private String code;

    private String phone;
}
