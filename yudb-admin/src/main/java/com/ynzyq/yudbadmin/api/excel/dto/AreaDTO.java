package com.ynzyq.yudbadmin.api.excel.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author xinchen
 * @date 2021/11/19 19:17
 * @description:
 */
@Data
public class AreaDTO implements Serializable {
    private String value;
    private String label;
}
