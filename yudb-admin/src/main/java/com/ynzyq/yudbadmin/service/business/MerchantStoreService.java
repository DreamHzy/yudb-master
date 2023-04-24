package com.ynzyq.yudbadmin.service.business;

import com.ynzyq.yudbadmin.core.model.ApiRes;
import com.ynzyq.yudbadmin.core.model.PageWrap;
import com.ynzyq.yudbadmin.dao.business.dto.*;
import com.ynzyq.yudbadmin.dao.business.vo.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface MerchantStoreService {

    ApiRes<PageWrap<StroePageVo>> findPage(PageWrap<StroePageDto> pageWrap);

    ApiRes<List<RegionalManageListVo>> regionalManageListVo();

    ApiRes<List<OneLevelStatusVO>> stroeStatusVo();

    ApiRes<List<OneLevelStatusVO>> storeStatusTwoVo();

    ApiRes updateRegionalManage(UpdateRegionalManageDto updateRegionalManageDto);

    ApiRes updateStoreStatus(UpdateStoreStatusDto updateStoreStatusDto);

    ApiRes updateStoreStatusTwo(UpdateStatusTwoDTO updateStatusTwoDTO);

    ApiRes detail(DetailDto dto);

    ApiRes<CloudSchoolQueryVo> cloudSchoolQuery(DetailDto dto);

    ApiRes deleteCloudSchool(DetailDto dto);

    ApiRes addCloudSchool(AddCloudSchoolDto dto);

    ApiRes<List<MerchantListVo>> merchantList(MerchantListDto merchantListDto);

    ApiRes addMerchantStore(AddMerchantStoreDto addMerchantStoreDto);

    ApiRes editMerchantStore(EditMerchantStoreDto editMerchantStoreDto);

    ApiRes<EditMerchantStoreQueryVo> editMerchantStoreQuery(DetailDto dto);

    ApiRes renewal(RenewalDto renewalDto);

    ApiRes updateCloudSchool(UpdateCloudSchoolDto dto);

    ApiRes<QueryMerchantLinkInfoVo> queryMerchantLinkInfo(DetailDto dto);

    ApiRes<QueryMerchantLinkInfoVo> editMerchantLinkInfo(EditMerchantLinkInfoDto editMerchantLinkInfoDto);

    ApiRes submitForReview(SubmitForReviewDto submitForReviewDto, HttpServletRequest httpServletRequest);

    ApiRes<PayTypeListVo> payTypeList();

    ApiRes excelSubmitForReview(MultipartFile file, HttpServletRequest httpServletRequest);

    /**
     * 代理商下拉框
     *
     * @return
     */
    ApiRes merchantSelectBox();

    /**
     * 修改所属代理
     *
     * @return
     */
    ApiRes updateMerchantDTO(UpdateMerchantDTO updateMerchantDTO);

    /**
     * 导出门店信息
     *
     * @param stroePageDto
     * @param response
     */
    void exportStore(StroePageDto stroePageDto, HttpServletResponse response);

    /**
     * 修改时间
     *
     * @param updateTimeDTO
     * @return
     */
    ApiRes updateTime(UpdateTimeDTO updateTimeDTO);

    /**
     * 云学堂账号下拉框
     *
     * @param cloudSchoolAccountDTO
     * @return
     */
    ApiRes<CloudSchoolAccountVO> cloudSchoolAccount(CloudSchoolAccountDTO cloudSchoolAccountDTO);

    /**
     * 修改是否适用代理优惠
     *
     * @param detailDto
     * @return
     */
    ApiRes updateIsPreferential(DetailDto detailDto);

    /**
     * 修改合同状态
     *
     * @param updateContractStatusDTO
     * @return
     */
    ApiRes updateContractStatus(UpdateContractStatusDTO updateContractStatusDTO);

    /**
     * 合同状态下拉框
     *
     * @return
     */
    ApiRes<OneLevelStatusVO> contractStatusSelectBox();

    /**
     * 修改加盟费/管理费/履约保证金
     *
     * @param updateMoneyDTO
     * @return
     */
    ApiRes updateMoney(UpdateMoneyDTO updateMoneyDTO);

    /**
     * 详情导出
     *
     * @param stroePageDto
     * @param response
     */
    void exportStoreDetail(StroePageDto stroePageDto, HttpServletResponse response);

    /**
     * 门店类型下拉框
     *
     * @return
     */
    ApiRes<OneLevelStatusVO> storeTypeSelectBox();

    /**
     * 门店类型
     *
     * @param updateExtraFieldDTO
     * @return
     */
    ApiRes updateExtraField(UpdateExtraFieldDTO updateExtraFieldDTO);

    /**
     * 导入更新
     *
     * @param multipartFile
     * @param httpServletRequest
     * @return
     */
    ApiRes inputExcelUpdateExtraField(MultipartFile multipartFile, HttpServletRequest httpServletRequest);

    /**
     * 修改送货地址
     *
     * @param updateDeliveryAddressDTO
     * @return
     */
    ApiRes updateDeliveryAddress(UpdateDeliveryAddressDTO updateDeliveryAddressDTO);

    /**
     * 返回配送到代理的数据
     */
    ApiRes<ReturnDataVO> returnData(ReturnDataDTO returnDataaDTO);

    ApiRes<BelongAgencyVO> belongAgencyService(BelongAgencyDTO belongAgencyDTO);

    /**
     * 所属代理权修改
     */
    ApiRes updateBelongAgencyService(UpdateBelongAgencyDTO updateBelongAgencyDTO);

    /**
     * 返回配送到门店的数据
     *
     * @param returnDataDTO
     * @return
     */
    ApiRes<DeliveryVO> deliveryToStoreService(ReturnDataDTO returnDataDTO);

    /**
     * 批量修改申请调货
     *
     * @param updateApplyAdjustDTO
     * @return
     */
    ApiRes updateApplyAdjust(UpdateApplyAdjustDTO updateApplyAdjustDTO);

}
