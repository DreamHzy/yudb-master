package com.ynzyq.yudbadmin.api.excel.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xinchen
 * @date 2021/11/21 11:58
 * @description:
 */
@Data
public class SchoolDTO implements Serializable {
    private String uid;
    private Integer otherId;
}
