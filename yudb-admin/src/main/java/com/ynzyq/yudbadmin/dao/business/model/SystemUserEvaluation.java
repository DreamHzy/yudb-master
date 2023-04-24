package com.ynzyq.yudbadmin.dao.business.model;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import java.util.Date;

@Data
public class SystemUserEvaluation {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;

    private String region;

    private String applicant;

    private String idCard;

    private Date birth;

    private String hometown;

    private String contactPhone;

    private String marriage;

    private String mail;

    private String emergencyContact;

    private String relation;

    private String emergencyContactPhone;

    private String province;

    private String city;

    private String area;

    private String address;

    private String isExistStore;

    private String openStoreArea;

    private Date openStoreTime;

    private String isEntrepreneurshipExperience;

    private String investmentAmount;

    private String purposeOfOpen;

    private String sourcesOfFunds;

    private String channel;

    private String isPartner;

    private String operationManagement;

    private String planOpenStoreTime;

    private String recognized;

    private String paybackCycle;

    private String isConsumption;

    private String difference;

    private String differenceRemark;

    private String cooperate;

    private String cooperateRemark;

    private Date createDate;

    private Date createTime;
}