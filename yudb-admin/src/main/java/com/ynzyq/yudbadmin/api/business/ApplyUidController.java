package com.ynzyq.yudbadmin.api.business;

import cn.hutool.http.server.HttpServerRequest;
import com.ynzyq.yudbadmin.core.model.ApiRes;
import com.ynzyq.yudbadmin.core.model.PageWrap;
import com.ynzyq.yudbadmin.dao.business.dto.AddApplyUidDTO;
import com.ynzyq.yudbadmin.dao.business.dto.ListApplyRecordDTO;
import com.ynzyq.yudbadmin.dao.business.dto.NextStepDTO;
import com.ynzyq.yudbadmin.dao.business.vo.ListApplyRecordVO;
import com.ynzyq.yudbadmin.dao.business.vo.NextStepVO;
import com.ynzyq.yudbadmin.service.business.IApplyUidService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author xinchen
 * @date 2022/4/6 11:58
 * @description:
 */
@Api(tags = "授权号申请管理")
@Slf4j
@RequestMapping("/applyUid")
@RestController
public class ApplyUidController {

    @Resource
    IApplyUidService iApplyUidService;

    /**
     * 申请列表
     *
     * @param pageWrap
     * @param httpServerRequest
     * @return
     */
    @ApiOperation("申请列表")
    @PostMapping("/listApplyRecord")
    public ApiRes<ListApplyRecordVO> listApplyRecord(@RequestBody PageWrap<ListApplyRecordDTO> pageWrap, HttpServerRequest httpServerRequest) {
        return iApplyUidService.listApplyRecord(pageWrap, httpServerRequest);
    }

    /**
     * 下一步
     *
     * @param nextStepDTO
     * @return
     */
    @ApiOperation("下一步")
    @PostMapping("/nextStep")
    public ApiRes<NextStepVO> nextStep(@RequestBody @Valid NextStepDTO nextStepDTO) {
        return iApplyUidService.nextStep(nextStepDTO);
    }

    /**
     * 确认添加
     *
     * @param addApplyUidDTO
     * @param httpServerRequest
     * @return
     */
    @ApiOperation("确认添加")
    @PostMapping("/addApplyUid")
    public ApiRes addApplyUid(@RequestBody @Valid AddApplyUidDTO addApplyUidDTO, HttpServerRequest httpServerRequest) {
        return iApplyUidService.addApplyUid(addApplyUidDTO, httpServerRequest);
    }
}
