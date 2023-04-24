package com.ynzyq.yudbadmin.service.business.impl;

import cn.hutool.core.date.DateUtil;
import com.alibaba.excel.EasyExcel;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ynzyq.yudbadmin.api.excel.dto.AccountDTO;
import com.ynzyq.yudbadmin.api.excel.dto.OrderDTO;
import com.ynzyq.yudbadmin.api.excel.enums.StatusTwoEnum;
import com.ynzyq.yudbadmin.api.excel.mapper.ExcelMapper;
import com.ynzyq.yudbadmin.core.model.*;
import com.ynzyq.yudbadmin.dao.business.dao.DictMapper;
import com.ynzyq.yudbadmin.dao.business.dao.PaymentOrderMasterMapper;
import com.ynzyq.yudbadmin.dao.business.dto.*;
import com.ynzyq.yudbadmin.dao.business.model.PaymentOrderMaster;
import com.ynzyq.yudbadmin.dao.business.vo.*;
import com.ynzyq.yudbadmin.service.business.FinanceService;
import com.ynzyq.yudbadmin.util.ExcelUtil;
import com.ynzyq.yudbadmin.util.ExcelUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;

@Slf4j
@Service
public class FinanceServiceImpl implements FinanceService {

    @Resource
    PaymentOrderMasterMapper paymentOrderMasterMapper;

    @Resource
    DictMapper dictMapper;

    @Resource
    ExcelMapper excelMapper;

    @Override
    public ApiRes<FinanceListVo> findPage(PageWrap<FinancePageDto> pageWrap) {
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
        financePageDto.setExamine(StringUtils.isBlank(financePageDto.getExamine()) ? null : financePageDto.getExamine());
        financePageDto.setState(StringUtils.isBlank(financePageDto.getState()) ? null : financePageDto.getState());
        financePageDto.setMonth(StringUtils.isBlank(financePageDto.getMonth()) ? null : financePageDto.getMonth());
        financePageDto.setStartCreateTime(StringUtils.isBlank(financePageDto.getStartCreateTime()) ? null : financePageDto.getStartCreateTime() + " 00:00:00");
        financePageDto.setEndCreateTime(StringUtils.isBlank(financePageDto.getEndCreateTime()) ? null : financePageDto.getEndCreateTime() + " 23:59:59");
        FinanceListVo financeListVo = new FinanceListVo();
        financeListVo.setAlerdMoney(BigDecimal.ZERO);
        financeListVo.setNeedMoney(BigDecimal.ZERO);
        PageHelper.startPage(pageWrap.getPage(), pageWrap.getCapacity());
        List<FinancePageVo> financePageVoList = paymentOrderMasterMapper.financePageVoList(financePageDto);
        financeListVo.setFinancePageVoList(PageData.from(new PageInfo<>(financePageVoList)));
        financePageVoList.forEach(
                financePageVo -> {
                    financePageVo.setPrimaryStatusDesc(StatusTwoEnum.getStatusDesc(Integer.parseInt(financePageVo.getPrimaryStatus())));
                    if (StringUtils.isNotBlank(financePageVo.getOverdueDay()) && Integer.parseInt(financePageVo.getOverdueDay()) < 0) {
                        financePageVo.setOverdueDay("0");
                    }
                }
        );
        // 查询待支付总额
        financePageDto.setStatus(1);
        MoneyDTO moneyDTO = paymentOrderMasterMapper.getMoneySUM(financePageDto);
        financeListVo.setNeedCount(moneyDTO.getCount());
        financeListVo.setNeedMoney(moneyDTO.getMoney());
        // 查询已支付总额
        financePageDto.setStatus(2);
        moneyDTO = paymentOrderMasterMapper.getMoneySUM(financePageDto);
        financeListVo.setAlerdCount(moneyDTO.getCount());
        financeListVo.setAlerdMoney(moneyDTO.getMoney());
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "查询成功", financeListVo);
    }


    @Override
    public ApiRes export(FinancePageDto financePageDto, HttpServletResponse response) {
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
        financePageDto.setExamine(StringUtils.isBlank(financePageDto.getExamine()) ? null : financePageDto.getExamine());
        financePageDto.setState(StringUtils.isBlank(financePageDto.getState()) ? null : financePageDto.getState());
        financePageDto.setMonth(StringUtils.isBlank(financePageDto.getMonth()) ? null : financePageDto.getMonth());
        financePageDto.setStartCreateTime(StringUtils.isBlank(financePageDto.getStartCreateTime()) ? null : financePageDto.getStartCreateTime());
        financePageDto.setEndCreateTime(StringUtils.isBlank(financePageDto.getEndCreateTime()) ? null : financePageDto.getEndCreateTime());
        List<FinancePageVo> financePageVoList = paymentOrderMasterMapper.financePageVoList(financePageDto);
        financePageVoList.forEach(financePageVo -> {
            financePageVo.setSend(StringUtils.equals("1", financePageVo.getSend()) ? "已推送" : "未推送");
            financePageVo.setIsReport(StringUtils.equals("1", financePageVo.getIsReport()) ? "是" : "否");
            financePageVo.setIsSettle(StringUtils.equals("1", financePageVo.getIsSettle()) ? "已清算" : "未清算");
            financePageVo.setExamine(StringUtils.equals("1", financePageVo.getExamine()) ? "已发布" : "未发布");
            financePageVo.setPrimaryStatusDesc(StatusTwoEnum.getStatusDesc(Integer.parseInt(financePageVo.getPrimaryStatus())));
            if (StringUtils.isNotBlank(financePageVo.getOverdueDay()) && Integer.parseInt(financePageVo.getOverdueDay()) < 0) {
                financePageVo.setOverdueDay("0");
            }
        });
//        ExcelUtils.writeExcel(response, financePageVoList, FinancePageVo.class, "财务记录.xlsx");
        EasyExcel.write(ExcelUtil.getOutPutStream("财务记录", response), FinancePageVo.class).registerWriteHandler(ExcelUtil.getHorizontalCellStyleStrategy()).sheet().doWrite(financePageVoList);
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "查询成功", "");
    }

    @Override
    public ApiRes<PayTypeListVo> payTypeList() {
        List<PayTypeListVo> payTypeListVos = paymentOrderMasterMapper.payTypeList();
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "查询成功", payTypeListVos);
    }

    @Override
    public ApiRes<OneLevelStatusVO> payTypeSelectBox() {
        List<OneLevelStatusVO> payTypeList = dictMapper.getPayType();
        if (payTypeList.size() == 0) {
            payTypeList = new ArrayList<>();
        }
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "查询成功", payTypeList);
    }

    @Override
    public ApiRes settle(MultipartFile file) {
        List<AccountDTO> list = ExcelUtils.readExcel2("", AccountDTO.class, file);
        List<OrderDTO> agentOrders = excelMapper.listAgentOrder();
        Set<String> agentOrderSet = new HashSet<>();
        agentOrders.forEach(agentOrder -> {
            agentOrderSet.add(agentOrder.getOrderNo());
        });

        List<OrderDTO> storeOrders = excelMapper.listStoreOrder();
        Set<String> storeOrderSet = new HashSet<>();
        storeOrders.forEach(storeOrder -> {
            storeOrderSet.add(storeOrder.getOrderNo());
        });
        list.forEach(accountDTO -> {
            // 代理订单
            if (agentOrderSet.contains(accountDTO.getOrderNo())) {
                excelMapper.updateAgentOrder(accountDTO.getOrderNo());
            }
            // 门店订单
            if (storeOrderSet.contains(accountDTO.getOrderNo())) {
                excelMapper.updateStoreOrder(accountDTO.getOrderNo());
            }
        });
        return ApiRes.successResponse();
    }

    @Override
    public ApiRes report(ReportDTO reportDTO) {
        if (reportDTO.getIds() == null || reportDTO.getIds().size() == 0) {
            return ApiRes.failResponse("请求参数错误");
        } else if (StringUtils.equals("1", reportDTO.getIsReport()) && StringUtils.equals("2", reportDTO.getIsReport())) {
            return ApiRes.failResponse("请求参数错误");
        }
        reportDTO.getIds().forEach(id -> {
            paymentOrderMasterMapper.updateIsReport(Integer.parseInt(reportDTO.getIsReport()), Integer.parseInt(id));
        });
        return ApiRes.successResponse();
    }

    @Override
    public ApiRes<FinanceListVo> listFinanceOrder(PageWrap<ListFinanceOrderDTO> pageWrap) {
        ListFinanceOrderDTO financeOrderDTO = pageWrap.getModel();
        if (StringUtils.isBlank(financeOrderDTO.getType())) {
            return ApiRes.failResponse("类型不能为空");
        }
        String condition = financeOrderDTO.getCondition();
        if (StringUtils.isEmpty(condition)) {
            financeOrderDTO.setCondition(null);
        }
        String startexpireTime = financeOrderDTO.getStartexpireTime();
        if (StringUtils.isEmpty(startexpireTime)) {
            financeOrderDTO.setEndexpireTime(null);
            financeOrderDTO.setStartexpireTime(null);
        } else {
            financeOrderDTO.setStartexpireTime(financeOrderDTO.getStartexpireTime() + " 00:00:00");
            financeOrderDTO.setEndexpireTime(financeOrderDTO.getEndexpireTime() + " 23:59:59");

        }
        String startPayTime = financeOrderDTO.getStartPayTime();
        if (StringUtils.isEmpty(startPayTime)) {
            financeOrderDTO.setEndPayTime(null);
            financeOrderDTO.setStartPayTime(null);
        } else {
            financeOrderDTO.setStartPayTime(financeOrderDTO.getStartPayTime() + " 00:00:00");
            financeOrderDTO.setEndPayTime(financeOrderDTO.getEndPayTime() + " 23:59:59");
        }
        String payStatus = financeOrderDTO.getPayStatus();
        if (StringUtils.isEmpty(payStatus)) {
            financeOrderDTO.setPayStatus(null);
        }
        String paymentTypeId = financeOrderDTO.getPaymentTypeId();
        if (StringUtils.isEmpty(paymentTypeId)) {
            financeOrderDTO.setPaymentTypeId(null);
        }
        financeOrderDTO.setPayType(StringUtils.isBlank(financeOrderDTO.getPayType()) ? null : financeOrderDTO.getPayType());
        financeOrderDTO.setSend(StringUtils.isBlank(financeOrderDTO.getSend()) ? null : financeOrderDTO.getSend());
        financeOrderDTO.setTypeId(StringUtils.isBlank(financeOrderDTO.getTypeId()) ? null : financeOrderDTO.getTypeId());
        financeOrderDTO.setManagerId(StringUtils.isBlank(financeOrderDTO.getManagerId()) ? null : financeOrderDTO.getManagerId());
        financeOrderDTO.setOrderId(StringUtils.isBlank(financeOrderDTO.getOrderId()) ? null : financeOrderDTO.getOrderId());
        FinanceListVo financeListVo = new FinanceListVo();
        financeListVo.setAlerdMoney(BigDecimal.ZERO);
        financeListVo.setNeedMoney(BigDecimal.ZERO);
        PageHelper.startPage(pageWrap.getPage(), pageWrap.getCapacity());
        List<FinancePageVo> financePageVoList = paymentOrderMasterMapper.listFinanceOrder(financeOrderDTO);
        financeListVo.setFinancePageVoList(PageData.from(new PageInfo<>(financePageVoList)));
        financePageVoList.forEach(
                financePageVo -> {
                    financePageVo.setPrimaryStatusDesc(StatusTwoEnum.getStatusDesc(Integer.parseInt(financePageVo.getPrimaryStatus())));
                }
        );
        return ApiRes.successResponseData(PageData.from(new PageInfo<>(financePageVoList)));
    }

    @Override
    public ApiRes cancelOrder(CancelOrderDTO cancelOrderDTO, HttpServletResponse httpServletResponse) {
        LoginUserInfo loginUserInfo = (LoginUserInfo) SecurityUtils.getSubject().getPrincipal();
        for (String id : cancelOrderDTO.getIds()) {
            PaymentOrderMaster paymentOrderMaster = paymentOrderMasterMapper.selectByPrimaryKey(Integer.parseInt(id));
            if (paymentOrderMaster == null) {
                return ApiRes.failResponse("缴费订单不存在");
            } else if (!paymentOrderMaster.getStatus().equals(1)) {
                return ApiRes.failResponse("只能在待支付状态下取消");
            }
            PaymentOrderMaster updatePaymentOrderMaster = new PaymentOrderMaster();
            updatePaymentOrderMaster.setId(paymentOrderMaster.getId());
            updatePaymentOrderMaster.setStatus(3);
            updatePaymentOrderMaster.setDeleted(true);
            updatePaymentOrderMaster.setCancelUser(loginUserInfo.getId());
            updatePaymentOrderMaster.setCancelTime(new Date());
            if (paymentOrderMasterMapper.updateByPrimaryKeySelective(updatePaymentOrderMaster) == 0) {
                return ApiRes.failResponse("取消失败");
            }
        }
        return ApiRes.successResponse();
    }

    @Override
    public ApiRes updateExpireTime(UpdateExpireTimeDTO updateExpireTimeDTO, HttpServletResponse httpServletResponse) {
        PaymentOrderMaster paymentOrderMaster = paymentOrderMasterMapper.selectByPrimaryKey(Integer.parseInt(updateExpireTimeDTO.getId()));
        if (paymentOrderMaster == null) {
            return ApiRes.failResponse("缴费订单不存在");
        } else if (!paymentOrderMaster.getStatus().equals(1)) {
            return ApiRes.failResponse("只能在待支付状态下修改时间");
        }
        PaymentOrderMaster updatePaymentOrderMaster = new PaymentOrderMaster();
        updatePaymentOrderMaster.setId(paymentOrderMaster.getId());
        updatePaymentOrderMaster.setExpireTime(DateUtil.parse(updateExpireTimeDTO.getExpireTime(), "yyyy-MM-dd"));
        if (paymentOrderMasterMapper.updateByPrimaryKeySelective(updatePaymentOrderMaster) == 0) {
            return ApiRes.failResponse("修改失败");
        }
        LoginUserInfo loginUserInfo = (LoginUserInfo) SecurityUtils.getSubject().getPrincipal();
        log.info("用户【{}】修改订单【{}】原缴费截止时间【{}】为新缴费截止时间【{}】", loginUserInfo.getId() + "_" + loginUserInfo.getRealname(), paymentOrderMaster.getOrderNo(), DateUtil.format(paymentOrderMaster.getExpireTime(), "yyyy-MM-dd"), updateExpireTimeDTO.getExpireTime());
        return ApiRes.successResponse();
    }

    @Override
    public ApiRes updatePublishStatus(UpdatePublishStatusDTO updatePublishStatusDTO) {
        PaymentOrderMaster paymentOrderMaster = paymentOrderMasterMapper.selectByPrimaryKey(Integer.parseInt(updatePublishStatusDTO.getId()));
        if (paymentOrderMaster == null) {
            return ApiRes.failResponse("缴费订单不存在");
        } else if (!paymentOrderMaster.getStatus().equals(1)) {
            return ApiRes.failResponse("只能在待支付状态下修改发布状态");
        }
        PaymentOrderMaster updatePaymentOrderMaster = new PaymentOrderMaster();
        updatePaymentOrderMaster.setId(paymentOrderMaster.getId());
        if (paymentOrderMaster.getIsPublish().equals(1)) {
            updatePaymentOrderMaster.setIsPublish(2);
        } else if (paymentOrderMaster.getIsPublish().equals(2)) {
            updatePaymentOrderMaster.setIsPublish(1);
        } else {
            return ApiRes.failResponse("发布状态不正确");
        }
        updatePaymentOrderMaster.setUpdateTime(new Date());
        if (paymentOrderMasterMapper.updateByPrimaryKeySelective(updatePaymentOrderMaster) == 0) {
            return ApiRes.failResponse("修改失败");
        }
        LoginUserInfo loginUserInfo = (LoginUserInfo) SecurityUtils.getSubject().getPrincipal();
        log.info("用户【{}】修改订单【{}】原发布状态【{}】为新发布状态【{}】", loginUserInfo.getId() + "_" + loginUserInfo.getRealname(), paymentOrderMaster.getOrderNo(), paymentOrderMaster.getIsPublish(), updatePaymentOrderMaster.getIsPublish());
        return ApiRes.successResponse();
    }
}
