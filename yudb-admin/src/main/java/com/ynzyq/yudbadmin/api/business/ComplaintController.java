package com.ynzyq.yudbadmin.api.business;

import com.ynzyq.yudbadmin.core.model.ApiRes;
import com.ynzyq.yudbadmin.core.model.PageWrap;
import com.ynzyq.yudbadmin.dao.business.dto.DealWithDTO;
import com.ynzyq.yudbadmin.dao.business.dto.ListComplaintDTO;
import com.ynzyq.yudbadmin.dao.business.vo.ListComplaintVO;
import com.ynzyq.yudbadmin.service.business.IComplaintService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author xinchen
 * @date 2022/1/25 11:50
 * @description:
 */
@Api(tags = "客户投诉")
@Slf4j
@RestController
public class ComplaintController {

    @Resource
    IComplaintService iComplaintService;

    /**
     * 列表
     *
     * @param pageWrap
     * @return
     */
    @ApiOperation("列表")
    @PostMapping("/complaint/listComplaint")
    public ApiRes<ListComplaintVO> listComplaint(@RequestBody PageWrap<ListComplaintDTO> pageWrap) {
        return iComplaintService.listComplaint(pageWrap);
    }

    /**
     * 处理
     *
     * @param dealWithDTO
     * @return
     */
    @ApiOperation("处理")
    @PostMapping("/complaint/dealWith")
    public ApiRes dealWith(@RequestBody DealWithDTO dealWithDTO) {
        return iComplaintService.dealWith(dealWithDTO);
    }
}
