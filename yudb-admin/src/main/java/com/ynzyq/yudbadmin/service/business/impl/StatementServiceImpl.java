package com.ynzyq.yudbadmin.service.business.impl;

import cn.hutool.core.date.DateUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ynzyq.yudbadmin.core.model.ApiRes;
import com.ynzyq.yudbadmin.core.model.PageData;
import com.ynzyq.yudbadmin.core.model.PageWrap;
import com.ynzyq.yudbadmin.dao.business.dao.MerchantStoreStatisticsMapper;
import com.ynzyq.yudbadmin.dao.business.dto.ListOverdueOrderDTO;
import com.ynzyq.yudbadmin.dao.business.dto.ManageStatementDTO;
import com.ynzyq.yudbadmin.dao.business.dto.StatementIdDTO;
import com.ynzyq.yudbadmin.dao.business.vo.*;
import com.ynzyq.yudbadmin.service.business.IStatementService;
import com.ynzyq.yudbadmin.util.ExcelUtil;
import com.ynzyq.yudbadmin.util.ExcelUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Map;

/**
 * @author xinchen
 * @date 2021/12/8 14:40
 * @description:
 */
@Slf4j
@Service
public class StatementServiceImpl implements IStatementService {

    @Resource
    MerchantStoreStatisticsMapper merchantStoreStatisticsMapper;

    @Override
    public ApiRes<ListStatementVO> listStatement(PageWrap pageWrap) {
        PageHelper.startPage(pageWrap.getPage(), pageWrap.getCapacity());
        List<ListStatementVO> listStatementVOS = merchantStoreStatisticsMapper.listStatement();
        return ApiRes.successResponseData(PageData.from(new PageInfo<>(listStatementVOS)));
    }

    @Override
    public ApiRes<StatementDetailVO> statementDetail(StatementIdDTO statementIdDTO) {
        StatementDetailVO statementDetailVO = merchantStoreStatisticsMapper.statementDetail(Integer.parseInt(statementIdDTO.getId()));
        NumberFormat numberInstance = DecimalFormat.getNumberInstance();
        statementDetailVO.setAccountReceivableMoney(numberInstance.format(Double.parseDouble(statementDetailVO.getAccountReceivableMoney())));
        statementDetailVO.setActualReceivableMoney(numberInstance.format(Double.parseDouble(statementDetailVO.getActualReceivableMoney())));
        statementDetailVO.setNotReceivableMoney(numberInstance.format(Double.parseDouble(statementDetailVO.getNotReceivableMoney())));
        statementDetailVO.setSendMoney(numberInstance.format(Double.parseDouble(statementDetailVO.getSendMoney())));
        statementDetailVO.setNotSendMoney(numberInstance.format(Double.parseDouble(statementDetailVO.getNotSendMoney())));
        return ApiRes.successResponseData(statementDetailVO);
    }

    @Override
    public void download(StatementIdDTO statementIdDTO, HttpServletResponse response) {
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        try {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            StatementDetailVO statementDetailVO = merchantStoreStatisticsMapper.statementDetail(Integer.parseInt(statementIdDTO.getId()));
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode(statementDetailVO.getStatisticsDate() + "报表", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=" + fileName + ".xlsx");
            List<StatementDetailVO> list = Lists.newArrayList();
            list.add(statementDetailVO);
            // 这里需要设置不关闭流
            EasyExcel.write(response.getOutputStream(), StatementDetailVO.class).sheet(statementDetailVO.getStatisticsDate()).doWrite(list);
        } catch (Exception e) {
            log.error("下载异常", e);
            // 重置response
            response.reset();
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            Map<String, String> map = Maps.newHashMap();
            map.put("status", "failure");
            map.put("message", "下载文件失败" + e.getMessage());
            try {
                response.getWriter().println(JSON.toJSONString(map));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public ApiRes<ManageStatementVO> listManageStatement(PageWrap<ManageStatementDTO> pageWrap) {
        PageHelper.startPage(pageWrap.getPage(), pageWrap.getCapacity());
        List<ManageStatementVO> manageStatementVOS = merchantStoreStatisticsMapper.listManageStatement(pageWrap.getModel());
        manageStatementVOS.forEach(manageStatementVO -> {
            manageStatementVO.setNotPayCount(String.valueOf(Integer.parseInt(manageStatementVO.getCount()) - Integer.parseInt(manageStatementVO.getPayCount())));
            manageStatementVO.setNotPayAmount(new BigDecimal(manageStatementVO.getAmount()).subtract(new BigDecimal(manageStatementVO.getPayAmount())).toString());
        });
        return ApiRes.successResponseData(PageData.from(new PageInfo<>(manageStatementVOS)));
    }

    @Override
    public void exportManageStatement(ManageStatementDTO manageStatementDTO, HttpServletResponse response) {
        List<ManageStatementVO> manageStatementVOS = merchantStoreStatisticsMapper.listManageStatement(manageStatementDTO);
        manageStatementVOS.forEach(manageStatementVO -> {
            manageStatementVO.setNotPayCount(String.valueOf(Integer.parseInt(manageStatementVO.getCount()) - Integer.parseInt(manageStatementVO.getPayCount())));
            manageStatementVO.setNotPayAmount(new BigDecimal(manageStatementVO.getAmount()).subtract(new BigDecimal(manageStatementVO.getPayAmount())).toString());
        });
//        ExcelUtils.writeExcel(response, manageStatementVOS, ManageStatementVO.class, "管理列表.xlsx");
        EasyExcel.write(ExcelUtil.getOutPutStream("管理列表", response), ManageStatementVO.class).registerWriteHandler(ExcelUtil.getHorizontalCellStyleStrategy()).sheet().doWrite(manageStatementVOS);
    }

    @Override
    public ApiRes<ListOverdueOrderVO> listOverdueOrder(PageWrap<ListOverdueOrderDTO> pageWrap) {
        PageHelper.startPage(pageWrap.getPage(), pageWrap.getCapacity());
        ListOverdueOrderDTO listOverdueOrderDTO = pageWrap.getModel();
        listOverdueOrderDTO.setToday(DateUtil.today());
        List<ListOverdueOrderVO> listOverdueOrderVOS = merchantStoreStatisticsMapper.listOverdueOrder(listOverdueOrderDTO);
        return ApiRes.successResponseData(PageData.from(new PageInfo<>(listOverdueOrderVOS)));
    }

    @Override
    public void exportOverdueOrder(ListOverdueOrderDTO listOverdueOrderDTO, HttpServletResponse response) {
        listOverdueOrderDTO.setToday(DateUtil.today());
        List<ListOverdueOrderVO> listOverdueOrderVOS = merchantStoreStatisticsMapper.listOverdueOrder(listOverdueOrderDTO);
//        ExcelUtils.writeExcel(response, listOverdueOrderVOS, ListOverdueOrderVO.class, "逾期账单.xlsx");
        EasyExcel.write(ExcelUtil.getOutPutStream("逾期账单", response), ListOverdueOrderVO.class).registerWriteHandler(ExcelUtil.getHorizontalCellStyleStrategy()).sheet().doWrite(listOverdueOrderVOS);
    }

}
