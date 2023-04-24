package com.ynzyq.yudbadmin.service.business;

import com.ynzyq.yudbadmin.core.model.ApiRes;
import com.ynzyq.yudbadmin.core.model.PageWrap;
import com.ynzyq.yudbadmin.dao.business.dto.*;
import com.ynzyq.yudbadmin.dao.business.vo.ListAppMenuVO;
import com.ynzyq.yudbadmin.dao.business.vo.MerchantPageVo;

import javax.servlet.http.HttpServletResponse;

public interface MerchantService {
    ApiRes<PageWrap<MerchantPageVo>> findPage(PageWrap<MerchantPageDto> pageWrap);

    ApiRes add(MerchantAddDto merchantAddDto);

    ApiRes edit(MerchantEditDto merchantEditDto);

    /**
     * 导出
     *
     * @param merchantPageDto
     * @param response
     */
    void exportMerchant(MerchantPageDto merchantPageDto, HttpServletResponse response);

    /**
     * 启用/禁用
     *
     * @param updateStatusDTO
     * @return
     */
    ApiRes updateStatus(UpdateStatusDTO updateStatusDTO);

    /**
     * app菜单列表
     *
     * @param listAppMenuDTO
     * @return
     */
    ApiRes<ListAppMenuVO> listAppMenu(ListAppMenuDTO listAppMenuDTO);

    /**
     * 选择菜单
     *
     * @param chooseAppMenuDTO
     * @return
     */
    ApiRes chooseAppMenu(ChooseAppMenuDTO chooseAppMenuDTO);
}
