package com.ynzyq.yudbadmin.api.business;

import com.ynzyq.yudbadmin.core.model.ApiRes;
import com.ynzyq.yudbadmin.core.model.PageWrap;
import com.ynzyq.yudbadmin.dao.business.dto.*;
import com.ynzyq.yudbadmin.dao.business.vo.*;
import com.ynzyq.yudbadmin.service.business.IAgentApproveService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author xinchen
 * @date 2021/12/24 11:47
 * @description:
 */
@Api(tags = "代理审核管理")
@Slf4j
@RestController
public class AgentApproveController {

    @Resource
    IAgentApproveService iAgentApproveService;

    /**
     * 代理审核查看列表
     *
     * @param pageWrap
     * @return
     */
    @ApiOperation("代理审核查看列表")
    @PostMapping("/agentApprove/showStoreExamine")
    public ApiRes<ShowStoreExamineVO> showStoreExamine(@RequestBody PageWrap<ShowStoreExamineDTO> pageWrap) {
        return iAgentApproveService.showStoreExamine(pageWrap);
    }

    /**
     * 代理审核管理列表
     *
     * @param pageWrap
     * @return
     */
    @ApiOperation("代理审核管理列表")
    @PostMapping("/agentApprove/listStoreExamine")
    public ApiRes<ShowStoreExamineVO> listStoreExamine(@RequestBody PageWrap<ShowStoreExamineDTO> pageWrap) {
        return iAgentApproveService.listStoreExamine(pageWrap);
    }

    /**
     * 详情
     *
     * @param storeExamineDetailDTO
     * @return
     */
    @ApiOperation("详情")
    @PostMapping("/agentApprove/storeExamineDetail")
    public ApiRes<StoreExamineDetailVO> storeExamineDetail(@RequestBody StoreExamineDetailDTO storeExamineDetailDTO) {
        return iAgentApproveService.storeExamineDetail(storeExamineDetailDTO);
    }

    /**
     * 拒绝
     *
     * @param examineDTO
     * @return
     */
    @ApiOperation("拒绝")
    @PostMapping("/agentApprove/examineRefuse")
    public ApiRes examineRefuse(@RequestBody ExamineDTO examineDTO) {
        return iAgentApproveService.examineRefuse(examineDTO);
    }

    /**
     * 同意
     *
     * @param examineDTO
     * @return
     */
    @ApiOperation("同意")
    @PostMapping("/agentApprove/examineAgree")
    public ApiRes examineAgree(@RequestBody ExamineDTO examineDTO) {
        return iAgentApproveService.examineAgree(examineDTO);
    }
}
