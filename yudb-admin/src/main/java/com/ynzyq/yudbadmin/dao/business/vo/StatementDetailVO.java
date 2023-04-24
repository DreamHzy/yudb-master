package com.ynzyq.yudbadmin.dao.business.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xinchen
 * @date 2021/12/8 14:49
 * @description:
 */
@Data
public class StatementDetailVO implements Serializable {

    @ExcelIgnore
    @ApiModelProperty("日期")
    private String statisticsDate;

    @ExcelProperty(value = {"概览", "门店数"}, index = 0)
    @ApiModelProperty("门店数")
    private String storeCount;

    @ExcelProperty(value = {"概览", "代理权数"}, index = 1)
    @ApiModelProperty("代理权数")
    private String agentAreaCount;

    @ExcelProperty(value = {"概览", "经营门店数"}, index = 2)
    @ApiModelProperty("经营门店数")
    private String manageCount;

    @ExcelProperty(value = {"门店数据分析", "在营"}, index = 4)
    @ApiModelProperty("在营")
    private String businessCount;

    @ExcelProperty(value = {"门店数据分析", "未开业"}, index = 5)
    @ApiModelProperty("未开业")
    private String notOpenCount;

    @ExcelProperty(value = {"门店数据分析", "迁址"}, index = 6)
    @ApiModelProperty("迁址")
    private String relocationCount;

    @ExcelProperty(value = {"门店数据分析", "闭店解约"}, index = 7)
    @ApiModelProperty("闭店解约")
    private String closeCount;

    @ExcelProperty(value = {"门店数据分析", "暂停营业"}, index = 8)
    @ApiModelProperty("暂停营业")
    private String pauseCount;

    @ExcelProperty(value = {"区域", "华北"}, index = 10)
    @ApiModelProperty("华北")
    private String northChinaCount;

    @ExcelProperty(value = {"区域", "华东"}, index = 11)
    @ApiModelProperty("华东")
    private String eastChinaCount;

    @ExcelProperty(value = {"区域", "华南"}, index = 12)
    @ApiModelProperty("华南")
    private String southChinaCount;

    @ExcelProperty(value = {"区域", "华西"}, index = 13)
    @ApiModelProperty("华西")
    private String westChinaCount;

    @ExcelProperty(value = {"区域", "华中"}, index = 14)
    @ApiModelProperty("华中")
    private String centerChinaCount;

    @ExcelProperty(value = {"线级", "新一线"}, index = 16)
    @ApiModelProperty("新一线")
    private String newFirstTierCount;

    @ExcelProperty(value = {"线级", "一线城市"}, index = 17)
    @ApiModelProperty("一线城市")
    private String firstTierCount;

    @ExcelProperty(value = {"线级", "二线城市"}, index = 18)
    @ApiModelProperty("二线城市")
    private String secondTierCount;

    @ExcelProperty(value = {"线级", "三线城市"}, index = 19)
    @ApiModelProperty("三线城市")
    private String thirdTierCount;

    @ExcelProperty(value = {"线级", "四线城市"}, index = 20)
    @ApiModelProperty("四线城市")
    private String fourTierCount;

    @ExcelProperty(value = {"线级", "五线城市"}, index = 21)
    @ApiModelProperty("五线城市")
    private String fiveTierCount;

    @ExcelProperty(value = {"线级", "县城"}, index = 22)
    @ApiModelProperty("县城")
    private String countyCount;

    @ExcelProperty(value = {"线级", "港澳台地区"}, index = 23)
    @ApiModelProperty("港澳台地区")
    private String hkmtCount;

    @ExcelProperty(value = {"客户情况", "客户数"}, index = 25)
    @ApiModelProperty("客户数")
    private String customerCount;

    @ExcelProperty(value = {"客户情况", "今日新增客户数"}, index = 26)
    @ApiModelProperty("今日新增客户数")
    private String addCustomerCount;

    @ExcelProperty(value = {"客户情况", "今日登录客户数"}, index = 27)
    @ApiModelProperty("今日登录客户数")
    private String loginCustomerCount;

    @ExcelProperty(value = {"代理分析", "代理权有效"}, index = 29)
    @ApiModelProperty("代理权有效")
    private String agentValid;

    @ExcelProperty(value = {"代理分析", "代理权无效"}, index = 30)
    @ApiModelProperty("代理权无效")
    private String agentInvalid;

    @ExcelProperty(value = {"账单数据分析", "应收账单数量"}, index = 32)
    @ApiModelProperty("应收账单数量")
    private String accountReceivableCount;

    @ExcelProperty(value = {"账单数据分析", "应收账单金额"}, index = 33)
    @ApiModelProperty("应收账单金额")
    private String accountReceivableMoney;

    @ExcelProperty(value = {"账单数据分析", "已收账单数量"}, index = 34)
    @ApiModelProperty("已收账单数量")
    private String actualReceivableCount;

    @ExcelProperty(value = {"账单数据分析", "已收账单金额"}, index = 35)
    @ApiModelProperty("已收账单金额")
    private String actualReceivableMoney;

    @ExcelProperty(value = {"账单数据分析", "未收账单数量"}, index = 36)
    @ApiModelProperty("未收账单数量")
    private String notReceivableCount;

    @ExcelProperty(value = {"账单数据分析", "未收账单金额"}, index = 37)
    @ApiModelProperty("未收账单金额")
    private String notReceivableMoney;

    @ExcelProperty(value = {"账单数据分析", "已推送账单数量"}, index = 38)
    @ApiModelProperty("已推送账单数量")
    private String sendCount;

    @ExcelProperty(value = {"账单数据分析", "已推送账单金额"}, index = 39)
    @ApiModelProperty("已推送账单金额")
    private String sendMoney;

    @ExcelProperty(value = {"账单数据分析", "未推送账单数量"}, index = 40)
    @ApiModelProperty("未推送账单数量")
    private String notSendCount;

    @ExcelProperty(value = {"账单数据分析", "未推送账单金额"}, index = 41)
    @ApiModelProperty("未推送账单金额")
    private String notSendMoney;
}
