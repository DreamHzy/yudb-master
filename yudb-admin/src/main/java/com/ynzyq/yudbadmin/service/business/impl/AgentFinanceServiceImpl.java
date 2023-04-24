package com.ynzyq.yudbadmin.service.business.impl;

import com.alibaba.excel.EasyExcel;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ynzyq.yudbadmin.core.model.ApiRes;
import com.ynzyq.yudbadmin.core.model.CommonConstant;
import com.ynzyq.yudbadmin.core.model.PageData;
import com.ynzyq.yudbadmin.core.model.PageWrap;
import com.ynzyq.yudbadmin.dao.business.dao.AgentAreaPaymentOrderMasterMapper;
import com.ynzyq.yudbadmin.dao.business.dao.AgentAreaPaymentTypeMapper;
import com.ynzyq.yudbadmin.dao.business.dto.FinancePageDto;
import com.ynzyq.yudbadmin.dao.business.dto.MoneyDTO;
import com.ynzyq.yudbadmin.dao.business.dto.ReportDTO;
import com.ynzyq.yudbadmin.dao.business.model.AgentAreaPaymentOrderMaster;
import com.ynzyq.yudbadmin.dao.business.model.AgentAreaPaymentType;
import com.ynzyq.yudbadmin.dao.business.vo.*;
import com.ynzyq.yudbadmin.service.business.AgentFinanceService;
import com.ynzyq.yudbadmin.util.ExcelUtil;
import com.ynzyq.yudbadmin.util.ExcelUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;

@Service
public class AgentFinanceServiceImpl implements AgentFinanceService {
    @Resource
    AgentAreaPaymentOrderMasterMapper agentAreaPaymentOrderMasterMapper;
    @Resource
    AgentAreaPaymentTypeMapper agentAreaPaymentTypeMapper;

    @Override
    public ApiRes<AgentFinanceListVo> findPage(PageWrap<FinancePageDto> pageWrap) {
        FinancePageDto financePageDto = pageWrap.getModel();
        String condition = financePageDto.getCondition();
        if (StringUtils.isEmpty(condition)) {
            financePageDto.setCondition(null);
        }
        String startexpireTime = financePageDto.getStartexpireTime();
        if (StringUtils.isEmpty(startexpireTime)) {
            financePageDto.setEndexpireTime(null);
            financePageDto.setStartexpireTime(null);
        } else {
            financePageDto.setStartexpireTime(financePageDto.getStartexpireTime() + " 00:00:00");
            financePageDto.setEndexpireTime(financePageDto.getEndexpireTime() + " 23:59:59");

        }
        String startPayTime = financePageDto.getStartPayTime();
        if (StringUtils.isEmpty(startPayTime)) {
            financePageDto.setEndPayTime(null);
            financePageDto.setStartPayTime(null);
        } else {
            financePageDto.setStartPayTime(financePageDto.getStartPayTime() + " 00:00:00");
            financePageDto.setEndPayTime(financePageDto.getEndPayTime() + " 23:59:59");
        }
        String payStatus = financePageDto.getPayStatus();
        if (StringUtils.isEmpty(payStatus)) {
            financePageDto.setPayStatus(null);
        }
        String paymentTypeId = financePageDto.getPaymentTypeId();
        if (StringUtils.isEmpty(paymentTypeId)) {
            financePageDto.setPaymentTypeId(null);
        }
        financePageDto.setPayType(StringUtils.isBlank(financePageDto.getPayType()) ? null : financePageDto.getPayType());
        financePageDto.setSend(StringUtils.isBlank(financePageDto.getSend()) ? null : financePageDto.getSend());
        financePageDto.setTypeId(StringUtils.isBlank(financePageDto.getTypeId()) ? null : financePageDto.getTypeId());
        financePageDto.setManagerId(StringUtils.isBlank(financePageDto.getManagerId()) ? null : financePageDto.getManagerId());
        financePageDto.setOrderId(StringUtils.isBlank(financePageDto.getOrderId()) ? null : financePageDto.getOrderId());
        AgentFinanceListVo financeListVo = new AgentFinanceListVo();
        financeListVo.setAlerdMoney(BigDecimal.ZERO);
        financeListVo.setNeedMoney(BigDecimal.ZERO);
        PageHelper.startPage(pageWrap.getPage(), pageWrap.getCapacity());
        List<AgentFinancePageVo> financePageVoList = agentAreaPaymentOrderMasterMapper.financePageVoList(financePageDto);
        financeListVo.setFinancePageVoList(PageData.from(new PageInfo<>(financePageVoList)));
//        financePageVoList.stream().forEach(
//                financePageVo -> {
//                    if (financePageVo.getStatus().equals("待支付")) {
//                        financeListVo.setNeedMoney(financeListVo.getNeedMoney().add(new BigDecimal(financePageVo.getMoney())));
//                    } else {
//                        financeListVo.setAlerdMoney(financeListVo.getAlerdMoney().add(new BigDecimal(financePageVo.getMoney())));
//                    }
//                }
//        );
        // 查询待支付总额
        financePageDto.setStatus(1);
        MoneyDTO moneyDTO = agentAreaPaymentOrderMasterMapper.getMoneySUM(financePageDto);
        financeListVo.setNeedCount(moneyDTO.getCount());
        financeListVo.setNeedMoney(moneyDTO.getMoney());
        // 查询已支付总额
        financePageDto.setStatus(2);
        moneyDTO = agentAreaPaymentOrderMasterMapper.getMoneySUM(financePageDto);
        financeListVo.setAlerdCount(moneyDTO.getCount());
        financeListVo.setAlerdMoney(moneyDTO.getMoney());
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "查询成功", financeListVo);
    }

    @Override
    public ApiRes<PayTypeListVo> payTypeList() {
        List<PayTypeListVo> payTypeListVos = agentAreaPaymentTypeMapper.payTypeList();
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "查询成功", payTypeListVos);
    }

    @Override
    public ApiRes export(FinancePageDto financePageDto, HttpServletResponse httpServletResponse) {
        String condition = financePageDto.getCondition();
        if (StringUtils.isEmpty(condition)) {
            financePageDto.setCondition(null);
        }
        String startexpireTime = financePageDto.getStartexpireTime();
        if (StringUtils.isEmpty(startexpireTime)) {
            financePageDto.setEndexpireTime(null);
            financePageDto.setStartexpireTime(null);
        }
        String startPayTime = financePageDto.getStartPayTime();
        if (StringUtils.isEmpty(startPayTime)) {
            financePageDto.setEndPayTime(null);
            financePageDto.setStartPayTime(null);
        }
        String paymentTypeId = financePageDto.getPaymentTypeId();
        if (StringUtils.isEmpty(paymentTypeId)) {
            financePageDto.setPaymentTypeId(null);
        }
        String payStatus = financePageDto.getPayStatus();
        if (StringUtils.isEmpty(payStatus)) {
            financePageDto.setPayStatus(null);
        }
        financePageDto.setPayType(StringUtils.isBlank(financePageDto.getPayType()) ? null : financePageDto.getPayType());
        financePageDto.setSend(StringUtils.isBlank(financePageDto.getSend()) ? null : financePageDto.getSend());
        financePageDto.setTypeId(StringUtils.isBlank(financePageDto.getTypeId()) ? null : financePageDto.getTypeId());
        financePageDto.setManagerId(StringUtils.isBlank(financePageDto.getManagerId()) ? null : financePageDto.getManagerId());
        financePageDto.setOrderId(StringUtils.isBlank(financePageDto.getOrderId()) ? null : financePageDto.getOrderId());
        List<AgentFinancePageVo> financePageVoList = agentAreaPaymentOrderMasterMapper.financePageVoList(financePageDto);
        financePageVoList.forEach(agentFinancePageVo -> {
            agentFinancePageVo.setSend(StringUtils.equals("1", agentFinancePageVo.getSend()) ? "已推送" : "未推送");
            agentFinancePageVo.setIsSettle(StringUtils.equals("1", agentFinancePageVo.getIsSettle()) ? "已清算" : "未清算");
            agentFinancePageVo.setIsReport(StringUtils.equals("1", agentFinancePageVo.getIsReport()) ? "是" : "否");
        });
//        ExcelUtils.writeExcel(httpServletResponse, financePageVoList, AgentFinancePageVo.class, "财务记录.xlsx");
        EasyExcel.write(ExcelUtil.getOutPutStream("财务记录", httpServletResponse), AgentFinancePageVo.class).registerWriteHandler(ExcelUtil.getHorizontalCellStyleStrategy()).sheet().doWrite(financePageVoList);
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "查询成功", "");
    }

    @Override
    public ApiRes report(ReportDTO reportDTO) {
        if (reportDTO.getIds() == null || reportDTO.getIds().size() == 0) {
            return ApiRes.failResponse("请求参数错误");
        } else if (StringUtils.equals("1", reportDTO.getIsReport()) && StringUtils.equals("2", reportDTO.getIsReport())) {
            return ApiRes.failResponse("请求参数错误");
        }
        reportDTO.getIds().forEach(id -> {
            agentAreaPaymentOrderMasterMapper.updateIsReport(Integer.parseInt(reportDTO.getIsReport()), Integer.parseInt(id));
        });
        return ApiRes.successResponse();
    }
}
