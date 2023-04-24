package com.yunzyq.yudbapp.dao.vo;

import com.yunzyq.yudbapp.dao.dto.ExamineDTO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author xinchen
 * @date 2022/1/18 17:54
 * @description:
 */
@Data
public class AuditRecordVO implements Serializable {
    private List<ExamineDTO> examineList;
}
