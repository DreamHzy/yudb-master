package com.ynzyq.yudbadmin.dao.evaluate.po;

import com.ynzyq.yudbadmin.dao.business.model.SystemUserEvaluation;
import com.ynzyq.yudbadmin.dao.evaluate.dto.EvaluationDTO;
import com.ynzyq.yudbadmin.dao.evaluate.enums.RegionEnum;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * @author xinchen
 * @date 2021/12/1 15:23
 * @description:
 */
public class SystemUserEvaluationPO extends SystemUserEvaluation implements Serializable {
    public SystemUserEvaluationPO(EvaluationDTO evaluationDTO) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        this.setRegion(RegionEnum.getRegionDesc(evaluationDTO.getRegion()));
        this.setApplicant(evaluationDTO.getApplicant());
        this.setIdCard(evaluationDTO.getIdCard());
        try {
            if (StringUtils.isNotBlank(evaluationDTO.getBirth())) {
                this.setBirth(sdf.parse(evaluationDTO.getBirth()));
            }
            if (StringUtils.isNotBlank(evaluationDTO.getOpenStoreTime())) {
                this.setOpenStoreTime(sdf.parse(evaluationDTO.getOpenStoreTime()));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.setHometown(evaluationDTO.getHometown());
        this.setContactPhone(evaluationDTO.getContactPhone());
        this.setMarriage(evaluationDTO.getMarriage());
        this.setMail(evaluationDTO.getMail());
        this.setEmergencyContact(evaluationDTO.getEmergencyContact());
        this.setRelation(evaluationDTO.getRelation());
        this.setEmergencyContactPhone(evaluationDTO.getEmergencyContactPhone());
        this.setProvince(evaluationDTO.getProvince());
        this.setCity(evaluationDTO.getCity());
        this.setArea(evaluationDTO.getArea());
        this.setAddress(evaluationDTO.getAddress());
        this.setIsExistStore(evaluationDTO.getIsExistStore());
        this.setOpenStoreArea(evaluationDTO.getOpenStoreArea());
        this.setIsEntrepreneurshipExperience(evaluationDTO.getIsEntrepreneurshipExperience());
        this.setInvestmentAmount(evaluationDTO.getInvestmentAmount());
        this.setPurposeOfOpen(evaluationDTO.getPurposeOfOpen());
        this.setSourcesOfFunds(evaluationDTO.getSourcesOfFunds());
        this.setChannel(evaluationDTO.getChannel());
        this.setIsPartner(evaluationDTO.getIsPartner());
        this.setOperationManagement(evaluationDTO.getOperationManagement());
        this.setPlanOpenStoreTime(evaluationDTO.getPlanOpenStoreTime());
        this.setRecognized(evaluationDTO.getRecognized());
        this.setPaybackCycle(evaluationDTO.getPaybackCycle());
        this.setIsConsumption(evaluationDTO.getIsConsumption());
        String difference = evaluationDTO.getDifference().stream().collect(Collectors.joining(","));
        this.setDifference(difference);
        this.setDifferenceRemark(evaluationDTO.getDifferenceRemark());
        String cooperate = evaluationDTO.getCooperate().stream().collect(Collectors.joining(","));
        this.setCooperate(cooperate);
        this.setCooperateRemark(evaluationDTO.getCooperateRemark());
        this.setCreateDate(new Date());
        this.setCreateTime(new Date());
    }
}
