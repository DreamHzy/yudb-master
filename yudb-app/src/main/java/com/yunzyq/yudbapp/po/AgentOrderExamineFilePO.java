package com.yunzyq.yudbapp.po;

import com.yunzyq.yudbapp.dao.model.AgentAreaPaymentOrderExamineFile;
import com.yunzyq.yudbapp.enums.ExamineFileStatusEnum;
import com.yunzyq.yudbapp.enums.ExamineFileTypeEnum;
import com.yunzyq.yudbapp.enums.IsDeleteEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author WJ
 */
@Data
public class AgentOrderExamineFilePO extends AgentAreaPaymentOrderExamineFile implements Serializable {

    public AgentOrderExamineFilePO(Integer examineId, String url,ExamineFileTypeEnum examineFileTypeEnum) {
        this.setPaymentOrderExamineId(examineId);
        this.setType(examineFileTypeEnum.getType());
        this.setUrl(url);
        this.setDeleted(IsDeleteEnum.NOT_DELETE.getIsDelete());
        this.setStatus(ExamineFileStatusEnum.VALID.getStatus());
        this.setCreateTime(new Date());
    }
}
