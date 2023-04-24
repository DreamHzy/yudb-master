package com.ynzyq.yudbadmin.dao.business.dto;

import com.github.pagehelper.PageInfo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author xinchen
 * @date 2022/1/7 12:59
 * @description:
 */
@Data
public class GetObjectPageInfoDTO implements Serializable {
    private List<Object> showStoreExamineVOS;
    private PageInfo<Object> source;
}
