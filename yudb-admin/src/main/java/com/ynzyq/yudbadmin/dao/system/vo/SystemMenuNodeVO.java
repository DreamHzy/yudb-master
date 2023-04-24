package com.ynzyq.yudbadmin.dao.system.vo;

import lombok.Data;

import java.util.List;

/**
 * 菜单节点视图对象
 * @author Caesar Liu
 * @date 2021-03-29 15:54
 */
@Data
public class SystemMenuNodeVO {

    private Integer id;

    private String label;

    private String index;

    private String icon;

    private String url;

    private String isSystem;

    private List<SystemMenuNodeVO> children;
}
