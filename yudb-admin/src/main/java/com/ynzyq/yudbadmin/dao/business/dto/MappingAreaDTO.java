package com.ynzyq.yudbadmin.dao.business.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xinchen
 * @date 2021/12/7 19:02
 * @description:
 */
@Data
public class MappingAreaDTO implements Serializable {
    private String province;

    private String city;

    private String area;

    private String region;

    private String level;
}
