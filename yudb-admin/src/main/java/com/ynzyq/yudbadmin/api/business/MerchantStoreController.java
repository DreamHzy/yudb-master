package com.ynzyq.yudbadmin.api.business;

import com.ynzyq.yudbadmin.annotation.Log;
import com.ynzyq.yudbadmin.api.BaseController;
import com.ynzyq.yudbadmin.core.model.ApiRes;
import com.ynzyq.yudbadmin.core.model.CommonConstant;
import com.ynzyq.yudbadmin.core.model.PageWrap;
import com.ynzyq.yudbadmin.dao.business.dto.*;
import com.ynzyq.yudbadmin.dao.business.vo.*;
import com.ynzyq.yudbadmin.service.business.MerchantStoreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@Api(tags = "(新)门店管理")
@Slf4j
@RequestMapping("/store")
@RestController
public class MerchantStoreController extends BaseController {


    @Resource
    MerchantStoreService merchantStoreService;

    /**
     * 列表
     */
    @RequiresPermissions("business:store:query")
    @PostMapping("/page")
    @ApiOperation("列表")
    public ApiRes<PageWrap<StroePageVo>> findPage(@RequestBody PageWrap<StroePageDto> pageWrap) {
        return merchantStoreService.findPage(pageWrap);
    }

    /**
     * 区域经理列表
     */
    @PostMapping("/regionalManageList")
    @ApiOperation("区域经理列表")
    public ApiRes<List<RegionalManageListVo>> regionalManageListVo() {
        return merchantStoreService.regionalManageListVo();
    }

    /**
     * 一级状态下拉框
     */
    @PostMapping("/stroeStatusList")
    @ApiOperation("一级状态下拉框")
    public ApiRes<List<OneLevelStatusVO>> stroeStatusVo() {
        return merchantStoreService.stroeStatusVo();
    }

    /**
     * 二级状态下拉框
     */
    @PostMapping("/storeStatusTwoList")
    @ApiOperation("二级状态下拉框")
    public ApiRes<List<OneLevelStatusVO>> storeStatusTwoVo() {
        return merchantStoreService.storeStatusTwoVo();
    }

    /**
     * 加盟商列表
     */
    @PostMapping("/merchantList")
    @ApiOperation("加盟商列表")
    public ApiRes<List<MerchantListVo>> merchantList(@RequestBody MerchantListDto merchantListDto) {
        return merchantStoreService.merchantList(merchantListDto);
    }

    /**
     * 修改区域经理
     */
    @Log("门店修改区域经理")
    @PostMapping("/updateRegionalManage")
    @RequiresPermissions("business:store:updateRegionalManage")
    @ApiOperation("修改区域经理(business:store:updateRegionalManage)")
    public ApiRes updateRegionalManage(@RequestBody UpdateRegionalManageDto updateRegionalManageDto) {
        return merchantStoreService.updateRegionalManage(updateRegionalManageDto);
    }

    /**
     * 修改门店状态
     */
    @Log("修改门店状态")
    @PostMapping("/updateStoreStatus")
    @RequiresPermissions("business:store:updateStoreStatus")
    @ApiOperation("修改门店状态(business:store:updateStoreStatus)")
    public ApiRes updateStoreStatus(@RequestBody UpdateStoreStatusDto updateStoreStatusDto) {
        return merchantStoreService.updateStoreStatus(updateStoreStatusDto);
    }

    /**
     * 详情
     */
    @PostMapping("/detail")
    @RequiresPermissions("business:store:detail")
    @ApiOperation("详情(business:store:detail)")
    public ApiRes<MerchantStoreDetailVo> detail(@RequestBody DetailDto dto) {
        return merchantStoreService.detail(dto);
    }

    /**
     * 查看云学堂账户
     */
    @PostMapping("/cloudSchoolQuery")
    @RequiresPermissions("business:store:cloudSchoolQuery")
    @ApiOperation("云学堂账户信息(business:store:cloudSchoolQuery)")
    public ApiRes<CloudSchoolQueryVo> cloudSchoolQuery(@RequestBody DetailDto dto) {
        return merchantStoreService.cloudSchoolQuery(dto);
    }

    /**
     * 删除云学堂账户
     */
    @PostMapping("/deleteCloudSchool")
    @RequiresPermissions("business:store:deleteCloudSchool")
    @ApiOperation("删除云学堂账户(business:store:deleteCloudSchool)")
    public ApiRes deleteCloudSchool(@RequestBody DetailDto dto) {
        return merchantStoreService.deleteCloudSchool(dto);
    }

    /**
     * 修改云学堂
     */
    @PostMapping("/updateCloudSchool")
    @RequiresPermissions("business:store:updateCloudSchool")
    @ApiOperation("修改云学堂账户(business:store:updateCloudSchool)")
    public ApiRes updateCloudSchool(@RequestBody UpdateCloudSchoolDto dto) {
        return merchantStoreService.updateCloudSchool(dto);
    }


    /**
     * 添加云学堂账户
     */
    @PostMapping("/addCloudSchool")
    @RequiresPermissions("business:store:addCloudSchool")
    @ApiOperation("添加云学堂账户(business:store:addCloudSchool)")
    public ApiRes addCloudSchool(@RequestBody AddCloudSchoolDto dto) {
        return merchantStoreService.addCloudSchool(dto);
    }

    /**
     * 添加门店
     */
    @Log("添加门店")
    @PostMapping("/addMerchantStore")
    @RequiresPermissions("business:store:addMerchantStore")
    @ApiOperation("添加门店(business:store:addMerchantStore)")
    public ApiRes addMerchantStore(@RequestBody AddMerchantStoreDto addMerchantStoreDto) {
        return merchantStoreService.addMerchantStore(addMerchantStoreDto);
    }

    /**
     * 门店编辑
     */
    @Log("门店编辑")
    @PostMapping("/editMerchantStore")
    @RequiresPermissions("business:store:editMerchantStore")
    @ApiOperation("门店编辑(business:store:editMerchantStore)")
    public ApiRes editMerchantStore(@RequestBody EditMerchantStoreDto editMerchantStoreDto) {
        return merchantStoreService.editMerchantStore(editMerchantStoreDto);
    }


    /**
     * 编辑查询
     */
    @PostMapping("/editMerchantStoreQuery")
    @ApiOperation("编辑查询(business:store:editMerchantStoreQuery)")
    public ApiRes<EditMerchantStoreQueryVo> editMerchantStoreQuery(@RequestBody DetailDto dto) {
        return merchantStoreService.editMerchantStoreQuery(dto);
    }

    /**
     * 查询商户通状态
     */
    @PostMapping("/queryMerchantLinkInfo")
    @ApiOperation("查询商户通状态")
    public ApiRes<QueryMerchantLinkInfoVo> queryMerchantLinkInfo(@RequestBody DetailDto dto) {
        return merchantStoreService.queryMerchantLinkInfo(dto);
    }

    /**
     * 商户通编辑
     */
    @Log("商户通编辑")
    @PostMapping("/editMerchantLinkInfo")
    @RequiresPermissions("business:store:editMerchantLinkInfo")
    @ApiOperation("商户通编辑(business:store:editMerchantLinkInfo)")
    public ApiRes<QueryMerchantLinkInfoVo> editMerchantLinkInfo(@RequestBody EditMerchantLinkInfoDto editMerchantLinkInfoDto) {
        return merchantStoreService.editMerchantLinkInfo(editMerchantLinkInfoDto);
    }


    /**
     * 缴费类型列表
     */
    @PostMapping("/payTypeList")
    @ApiOperation("缴费类型列表")
    public ApiRes<PayTypeListVo> payTypeList() {
        return merchantStoreService.payTypeList();
    }


    /**
     * 单个缴费提交
     */
    @Log("单个缴费提交")
    @PostMapping("/oneSubmitForReview")
    @RequiresPermissions("business:store:oneSubmitForReview")
    @ApiOperation("单个缴费提交(business:store:oneSubmitForReview)")
    public ApiRes submitForReview(@RequestBody SubmitForReviewDto submitForReviewDto, HttpServletRequest httpServletRequest) {
        return merchantStoreService.submitForReview(submitForReviewDto, httpServletRequest);
    }

    /**
     * excel导入缴费表
     */
    @PostMapping("/excelSubmitForReview")
    @RequiresPermissions("business:store:excelSubmitForReview")
    @ApiOperation("excel导入缴费表")
    public ApiRes excelSubmitForReview(@RequestParam(value = "uploadFile", required = false) MultipartFile file, HttpServletRequest httpServletRequest) {
        return merchantStoreService.excelSubmitForReview(file, httpServletRequest);
    }

    /**
     * EXCEL缴费表模板
     */
    @PostMapping("/excelSubmitForReviewFile")
    @ApiOperation("EXCEL缴费表模板")
    public ApiRes excelSubmitForReview() {
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "查询成功", "https://yudianbao.ynzyq.cn//%E7%BC%B4%E8%B4%B9%E6%A8%A1%E6%9D%BF.xlsx");
    }

    /**
     * 加盟商下拉框
     *
     * @return
     */
    @PostMapping("/merchantSelectBox")
    @ApiOperation("加盟商下拉框")
    public ApiRes merchantSelectBox() {
        return merchantStoreService.merchantSelectBox();
    }


    /**
     * 修改二级状态
     *
     * @param updateStatusTwoDTO
     * @return
     */
    @Log("修改二级状态")
    @PostMapping("/updateStatusTwo")
    @RequiresPermissions("business:store:updateStatusTwo")
    @ApiOperation("修改二级状态(business:store:updateStatusTwo)")
    public ApiRes updateStatusTwo(@RequestBody UpdateStatusTwoDTO updateStatusTwoDTO) {
        return merchantStoreService.updateStoreStatusTwo(updateStatusTwoDTO);
    }

    /**
     * 修改所属代理
     *
     * @return5
     */
    @Log("修改所属代理")
    @PostMapping("/updateMerchant")
    @RequiresPermissions("business:store:updateMerchant")
    @ApiOperation("修改所属代理(business:store:updateMerchant)")
    public ApiRes updateMerchant(@RequestBody UpdateMerchantDTO updateMerchantDTO) {
        return merchantStoreService.updateMerchantDTO(updateMerchantDTO);
    }

    /**
     * 导出
     *
     * @param stroePageDto
     * @return
     */
    @GetMapping("/exportStore")
    @ApiOperation("导出(business:store:exportStore)")
    @RequiresPermissions("business:store:exportStore")
    public void exportStore(StroePageDto stroePageDto, HttpServletResponse response) {
        merchantStoreService.exportStore(stroePageDto, response);
    }

    /**
     * 修改开业时间/闭店时间/迁址时间/暂停营业时间/服务到期时间/合同到期时间
     *
     * @param updateTimeDTO
     * @return5
     */
    @Log("修改开业时间/闭店时间/迁址时间/暂停营业时间/服务到期时间/合同到期时间")
    @PostMapping("/updateTime")
    @ApiOperation("修改开业时间/闭店时间/迁址时间/暂停营业时间/预计开业时间(business:store:updateTime)")
    @RequiresPermissions("business:store:updateTime")
    public ApiRes updateTime(@RequestBody UpdateTimeDTO updateTimeDTO) {
        return merchantStoreService.updateTime(updateTimeDTO);
    }

    /**
     * 云学堂账号下拉框
     *
     * @param cloudSchoolAccountDTO
     * @return5
     */
    @PostMapping("/cloudSchoolAccount")
    @ApiOperation("云学堂账号下拉框")
    public ApiRes<CloudSchoolAccountVO> cloudSchoolAccount(@RequestBody @Valid CloudSchoolAccountDTO cloudSchoolAccountDTO) {
        return merchantStoreService.cloudSchoolAccount(cloudSchoolAccountDTO);
    }

    /**
     * 修改是否适用代理优惠
     *
     * @param detailDto
     * @return5
     */
    @Log("修改是否适用代理优惠")
    @PostMapping("/updateIsPreferential")
    @ApiOperation("修改是否适用代理优惠(business:store:updateIsPreferential)")
    @RequiresPermissions("business:store:updateIsPreferential")
    public ApiRes updateIsPreferential(@RequestBody @Valid DetailDto detailDto) {
        return merchantStoreService.updateIsPreferential(detailDto);
    }

    /**
     * 修改合同状态
     *
     * @param updateContractStatusDTO
     * @return5
     */
    @Log("修改合同状态")
    @PostMapping("/updateContractStatus")
    @ApiOperation("修改合同状态(business:store:updateContractStatus)")
    @RequiresPermissions("business:store:updateContractStatus")
    public ApiRes updateContractStatus(@RequestBody @Valid UpdateContractStatusDTO updateContractStatusDTO) {
        return merchantStoreService.updateContractStatus(updateContractStatusDTO);
    }

    /**
     * 合同状态下拉框
     */
    @PostMapping("/contractStatusSelectBox")
    @ApiOperation("合同状态下拉框")
    public ApiRes<OneLevelStatusVO> contractStatusSelectBox() {
        return merchantStoreService.contractStatusSelectBox();
    }

    /**
     * 修改加盟费/管理费/履约保证金
     *
     * @param updateMoneyDTO
     */
    @Log("修改加盟费/管理费/履约保证金")
    @PostMapping("/updateMoney")
    @ApiOperation("修改加盟费/管理费/履约保证金(business:store:updateMoney)")
    @RequiresPermissions("business:store:updateMoney")
    public ApiRes updateMoney(@RequestBody @Valid UpdateMoneyDTO updateMoneyDTO) {
        return merchantStoreService.updateMoney(updateMoneyDTO);
    }

    /**
     * 详情导出
     *
     * @param stroePageDto
     * @return
     */
    @GetMapping("/exportStoreDetail")
    @ApiOperation("详情导出(business:store:exportStoreDetail)")
    @RequiresPermissions("business:store:exportStoreDetail")
    public void exportStoreDetail(StroePageDto stroePageDto, HttpServletResponse response) {
        merchantStoreService.exportStoreDetail(stroePageDto, response);
    }

    /**
     * 门店类型下拉框
     */
    @PostMapping("/storeTypeSelectBox")
    @ApiOperation("门店类型下拉框")
    public ApiRes<OneLevelStatusVO> storeTypeSelectBox() {
        return merchantStoreService.storeTypeSelectBox();
    }

    /**
     * 修改餐位数、哗啦啦授权码、美团ID、饿了么ID、大众点评ID、备注
     *
     * @param updateExtraFieldDTO
     * @return5
     */
    @Log("修改餐位数、哗啦啦授权码、美团ID、饿了么ID、大众点评ID、备注")
    @PostMapping("/updateExtraField")
    @ApiOperation("修改餐位数、哗啦啦授权码、美团ID、饿了么ID、大众点评ID、备注(business:store:updateExtraField)")
    @RequiresPermissions("business:store:updateExtraField")
    public ApiRes updateExtraField(@RequestBody UpdateExtraFieldDTO updateExtraFieldDTO) {
        return merchantStoreService.updateExtraField(updateExtraFieldDTO);
    }

    /**
     * 导入更新
     *
     * @param multipartFile
     * @param httpServletRequest
     * @return
     */
    @PostMapping("/inputExcelUpdateExtraField")
    @ApiOperation("导入更新(business:store:inputExcelUpdateExtraField)")
    @RequiresPermissions("business:store:inputExcelUpdateExtraField")
    public ApiRes inputExcelUpdateExtraField(MultipartFile multipartFile, HttpServletRequest httpServletRequest) {
        return merchantStoreService.inputExcelUpdateExtraField(multipartFile, httpServletRequest);
    }

    /**
     * 修改送货地址
     *
     * @param updateDeliveryAddressDTO
     * @return5
     */
    @Log("修改送货地址")
    @PostMapping("/updateDeliveryAddress")
    @ApiOperation("修改送货地址(business:store:updateDeliveryAddress)")
    @RequiresPermissions("business:store:updateDeliveryAddress")
    public ApiRes updateDeliveryAddress(@RequestBody UpdateDeliveryAddressDTO updateDeliveryAddressDTO) {
        return merchantStoreService.updateDeliveryAddress(updateDeliveryAddressDTO);
    }

    /**
     * 返回配送到门店的数据
     */
    @PostMapping("/deliveryToStore")
    @ApiOperation("返回配送到门店的数据(business:store:deliveryToStore)")
    public ApiRes<DeliveryVO> deliveryToStore(@RequestBody ReturnDataDTO returnDataDTO) {
        return merchantStoreService.deliveryToStoreService(returnDataDTO);
    }

    /**
     * 返回配送到代理的数据
     */
    @PostMapping("/returnData")
    @ApiOperation("返回配送到代理的数据(business:store:returnData)")
    public ApiRes<ReturnDataVO> returnData(@RequestBody ReturnDataDTO returnDataaDTO) {
        return merchantStoreService.returnData(returnDataaDTO);
    }

    /**
     * 所属代理权查询
     *
     * @param belongAgencyDTO
     * @return
     */
    @PostMapping("/belongAgency")
    @ApiOperation("所属代理权查询(business:store:belongAgency)")
    public ApiRes<BelongAgencyVO> belongAgency(@RequestBody BelongAgencyDTO belongAgencyDTO) {
        return merchantStoreService.belongAgencyService(belongAgencyDTO);
    }

    /**
     * 所属代理权修改
     */
    @Log("所属代理权修改")
    @PostMapping("/updateBelongAgency")
    @ApiOperation("所属代理权修改(business:store:updateBelongAgency)")
    public ApiRes updateBelongAgency(@RequestBody UpdateBelongAgencyDTO updateBelongAgencyDTO) {
        return merchantStoreService.updateBelongAgencyService(updateBelongAgencyDTO);
    }

    /**
     * 批量修改申请调货
     *
     * @param updateApplyAdjustDTO
     * @return
     */
    @Log("批量修改申请调货")
    @PostMapping("/updateApplyAdjust")
    @ApiOperation("批量修改申请调货(business:store:updateApplyAdjust)")
    @RequiresPermissions("business:store:updateApplyAdjust")
    public ApiRes updateApplyAdjust(@RequestBody @Valid UpdateApplyAdjustDTO updateApplyAdjustDTO) {
        return merchantStoreService.updateApplyAdjust(updateApplyAdjustDTO);
    }

}
