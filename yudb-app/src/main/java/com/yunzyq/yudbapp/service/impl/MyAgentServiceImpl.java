package com.yunzyq.yudbapp.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yunzyq.yudbapp.core.ApiRes;
import com.yunzyq.yudbapp.core.CommonConstant;
import com.yunzyq.yudbapp.core.PageData;
import com.yunzyq.yudbapp.core.PageWrap;
import com.yunzyq.yudbapp.dao.*;
import com.yunzyq.yudbapp.dao.dto.HistoryOrderDto;
import com.yunzyq.yudbapp.dao.dto.ListMyAgentDTO;
import com.yunzyq.yudbapp.dao.dto.MakeOutBillDTO;
import com.yunzyq.yudbapp.dao.model.*;
import com.yunzyq.yudbapp.dao.vo.HistoryOrderVo;
import com.yunzyq.yudbapp.dao.vo.ListAgentPaymentTypeVO;
import com.yunzyq.yudbapp.dao.vo.ListMyAgentVO;
import com.yunzyq.yudbapp.enums.ExamineFileTypeEnum;
import com.yunzyq.yudbapp.exception.BaseException;
import com.yunzyq.yudbapp.po.AgentOrderExamineDetailPO;
import com.yunzyq.yudbapp.po.AgentOrderExamineFilePO;
import com.yunzyq.yudbapp.po.AgentOrderExaminePO;
import com.yunzyq.yudbapp.po.AgentOrderMasterPO;
import com.yunzyq.yudbapp.service.IMyAgentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author wj
 * @Date 2021/10/25
 */
@Service
public class MyAgentServiceImpl implements IMyAgentService {

    @Resource
    MyAgentMapper myAgentMapper;

    @Resource
    MerchantAgentAreaMapper merchantAgentAreaMapper;

    @Resource
    RegionalManagerMapper regionalManagerMapper;

    @Resource
    AgentAreaPaymentTypeMapper agentAreaPaymentTypeMapper;

    @Resource
    AgentAreaPaymentOrderMasterMapper agentAreaPaymentOrderMasterMapper;

    @Resource
    AgentAreaPaymentOrderExamineMapper agentAreaPaymentOrderExamineMapper;

    @Resource
    AgentAreaPaymentOrderExamineDeailMapper agentAreaPaymentOrderExamineDeailMapper;

    @Resource
    AgentAreaPaymentOrderExamineFileMapper agentAreaPaymentOrderExamineFileMapper;

    @Resource
    PaymentOrderExamineDeailMapper paymentOrderExamineDeailMapper;

    @Override
    public ApiRes<ListMyAgentVO> listMyAgent(String managerId, ListMyAgentDTO listMyAgentDTO) {
        PageHelper.startPage(listMyAgentDTO.getPage(), listMyAgentDTO.getCapacity());
        List<ListMyAgentVO> listMyAgentVOS = myAgentMapper.listMyAgent(managerId, listMyAgentDTO.getConditions());
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "查询成功", PageData.from(new PageInfo<>(listMyAgentVOS)));
    }

    @Override
    public ApiRes<ListAgentPaymentTypeVO> listAgentPaymentType() {
        List<ListAgentPaymentTypeVO> listAgentPaymentTypeVOS = myAgentMapper.listAgentPaymentType();
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "查询成功", listAgentPaymentTypeVOS);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ApiRes makeOutBill(String managerId, MakeOutBillDTO makeOutBillDTO) {
        Integer id = makeOutBillDTO.getId();
        MerchantAgentArea merchantAgentArea = merchantAgentAreaMapper.selectByPrimaryKey(id);
        if (Objects.isNull(merchantAgentArea)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "加盟商代理区域不存在", "");
        }
        RegionalManager regionalManager = regionalManagerMapper.selectByPrimaryKey(managerId);
        if (Objects.isNull(regionalManager)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "区域经理信息错误", "");
        }

        AgentAreaPaymentType agentAreaPaymentType = agentAreaPaymentTypeMapper.selectByPrimaryKey(makeOutBillDTO.getPayTypeId());
        if (Objects.isNull(agentAreaPaymentType)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "缴费类型错误", "");
        }

//        int count = myAgentMapper.getPaidOrderCount(merchantAgentArea.getMerchantId());
//        if (count != 0) {
//            return ApiRes.response(CommonConstant.FAIL_CODE, "请先让加盟商缴纳前面相同类型费用在进行缴费申请", "");
//        }
        int ret = 0;
        // 添加缴费主订单
        AgentOrderMasterPO agentOrderMasterPO = new AgentOrderMasterPO(managerId, merchantAgentArea, makeOutBillDTO, regionalManager, agentAreaPaymentType);
        ret = agentAreaPaymentOrderMasterMapper.insertSelective(agentOrderMasterPO);
        if (ret == 0) {
            throw new BaseException(CommonConstant.FAIL_CODE, "添加缴费主订单失败");
        }

        // 缴费审核订单
        AgentOrderExaminePO agentOrderExaminePO = new AgentOrderExaminePO(merchantAgentArea.getUid(), agentAreaPaymentType, agentOrderMasterPO, regionalManager);
        ret = agentAreaPaymentOrderExamineMapper.insertSelective(agentOrderExaminePO);
        if (ret == 0) {
            throw new BaseException(CommonConstant.FAIL_CODE, "添加缴费主订单失败");
        }

        // 缴费审核订单明细
        AgentOrderExamineDetailPO agentOrderExamineDetailPO = new AgentOrderExamineDetailPO(agentOrderExaminePO.getId());
        // 查询该阶段的审核人
        List<String> approveNames = paymentOrderExamineDeailMapper.getApproveName(2, agentOrderExaminePO.getExamineType(), agentOrderExamineDetailPO.getStep(), agentOrderExamineDetailPO.getType());
        String approveName = approveNames.stream().collect(Collectors.joining(","));
        agentOrderExamineDetailPO.setApproveName(approveName);
        ret = agentAreaPaymentOrderExamineDeailMapper.insertSelective(agentOrderExamineDetailPO);
        if (ret == 0) {
            throw new BaseException(CommonConstant.FAIL_CODE, "添加缴费主订单失败");
        }

        // 文件信息存储
        List<AgentOrderExamineFilePO> paymentOrderExamineFileList = new ArrayList<>();
        makeOutBillDTO.getPhotos().forEach(
                url -> {
                    AgentOrderExamineFilePO agentOrderExamineFilePO = new AgentOrderExamineFilePO(agentOrderExaminePO.getId(), url, ExamineFileTypeEnum.PHOTO);
                    paymentOrderExamineFileList.add(agentOrderExamineFilePO);
                }
        );
        makeOutBillDTO.getVideo().forEach(
                url -> {
                    AgentOrderExamineFilePO agentOrderExamineFilePO = new AgentOrderExamineFilePO(agentOrderExaminePO.getId(), url, ExamineFileTypeEnum.VIDEO);
                    paymentOrderExamineFileList.add(agentOrderExamineFilePO);
                }
        );
        if (paymentOrderExamineFileList.size() > 0) {
            agentAreaPaymentOrderExamineFileMapper.insertList(paymentOrderExamineFileList);
        }
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "缴费提交成功", "");

    }

    @Override
    public ApiRes<PageWrap<HistoryOrderVo>> historyBill(String managerId, PageWrap<HistoryOrderDto> pageWrap) {
        PageHelper.startPage(pageWrap.getPage(), pageWrap.getCapacity());
        List<HistoryOrderVo> historyOrderVos = myAgentMapper.historyBill(managerId);
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "查询成功", PageData.from(new PageInfo<>(historyOrderVos)));
    }
}
