package com.ynzyq.yudbadmin.service.business.impl;

import com.alibaba.excel.EasyExcel;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ynzyq.yudbadmin.api.excel.enums.StatusTwoEnum;
import com.ynzyq.yudbadmin.core.model.*;
import com.ynzyq.yudbadmin.dao.business.dao.*;
import com.ynzyq.yudbadmin.dao.business.dto.PayReviewDetailDto;
import com.ynzyq.yudbadmin.dao.business.dto.PayReviewDto;
import com.ynzyq.yudbadmin.dao.business.dto.PayReviewPageDto;
import com.ynzyq.yudbadmin.dao.business.dto.PopupDto;
import com.ynzyq.yudbadmin.dao.business.model.*;
import com.ynzyq.yudbadmin.dao.business.vo.*;
import com.ynzyq.yudbadmin.service.business.PayReviewService;
import com.ynzyq.yudbadmin.util.DateUtil;
import com.ynzyq.yudbadmin.util.ExcelUtil;
import com.ynzyq.yudbadmin.util.ExcelUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class PayReviewServiceImpl implements PayReviewService {


    @Resource
    PaymentOrderExamineMapper paymentOrderExamineMapper;
    @Resource
    PaymentOrderExamineDeailMapper paymentOrderExamineDeailMapper;
    @Resource
    PaymentOrderMasterMapper paymentOrderMasterMapper;
    @Resource
    PaymentOrderExamineFileMapper paymentOrderExamineFileMapper;
    @Resource
    AgencyWorkMapper agencyWorkMapper;
    @Resource
    MerchantStoreMapper merchantStoreMapper;
    @Resource
    PaymentOrderExamineDeailFileMapper paymentOrderExamineDeailFileMapper;

    @Value("${imageUrl}")
    private String imageurl;

    @Resource
    MerchantStoreCloudSchoolMapper merchantStoreCloudSchoolMapper;

    @Override
    public ApiRes<PageWrap<PayReviewPageVo>> findPage(PageWrap<PayReviewPageDto> pageWrap) {

        PayReviewPageDto payReviewPageDto = pageWrap.getModel();
        if (payReviewPageDto == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        String paymentTypeId = payReviewPageDto.getPaymentTypeId();
        if (StringUtils.isEmpty(paymentTypeId)) {
            payReviewPageDto.setPaymentTypeId(null);
        }

        String condition = payReviewPageDto.getCondition();
        if (StringUtils.isEmpty(condition)) {
            payReviewPageDto.setCondition(null);
        }
        if (StringUtils.isEmpty(payReviewPageDto.getEndTime())) {
            payReviewPageDto.setEndTime(null);
            payReviewPageDto.setStartTime(null);
        } else {
            String startTime = payReviewPageDto.getStartTime();
            String endTime = payReviewPageDto.getEndTime();
            startTime = startTime + " 00:00:00";
            endTime = endTime + " 23:59:59";
            payReviewPageDto.setEndTime(endTime);
            payReviewPageDto.setStartTime(startTime);
        }
        payReviewPageDto.setExamineOneStatus(StringUtils.isBlank(payReviewPageDto.getExamineOneStatus()) ? null : payReviewPageDto.getExamineOneStatus());
        payReviewPageDto.setExamineTwoStatus(StringUtils.isBlank(payReviewPageDto.getExamineTwoStatus()) ? null : payReviewPageDto.getExamineTwoStatus());
        payReviewPageDto.setExamineType(StringUtils.isBlank(payReviewPageDto.getExamineType()) ? null : payReviewPageDto.getExamineType());
        PageHelper.startPage(pageWrap.getPage(), pageWrap.getCapacity());
        List<PayReviewPageVo> payReviewPageVoList = paymentOrderExamineMapper.findPayReviewPageVo(payReviewPageDto);

        payReviewPageVoList.stream().forEach(
                payReviewPageVo -> {
                    switch (payReviewPageVo.getExamineType()) {
                        case "1":
                            payReviewPageVo.setExamineType("金额调整");
                            break;
                        case "2":
                            payReviewPageVo.setExamineType("延期支付");
                            break;
                        case "3":
                            payReviewPageVo.setExamineType("缴费内容审核");
                            break;
                        case "4":
                            payReviewPageVo.setExamineType("取消费用");
                            break;
                        default:
                            payReviewPageVo.setExamineType("线下已收款");
                    }
                    switch (payReviewPageVo.getExamineOneStatus()) {
                        case "1":
                            payReviewPageVo.setExamineOneStatus("待审核");
                            payReviewPageVo.setExamineTwoStatus("");
                            break;
                        case "2":
                            payReviewPageVo.setExamineOneStatus("审核通过");
                            break;
                        case "3":
                            payReviewPageVo.setExamineOneStatus("拒绝");
                            break;
                        default:
                            payReviewPageVo.setExamineOneStatus("");
                    }
                    if (!StringUtils.isEmpty(payReviewPageVo.getExamineTwoStatus())) {
                        switch (payReviewPageVo.getExamineTwoStatus()) {
                            case "1":
                                payReviewPageVo.setExamineTwoStatus("待审核");
                                break;
                            case "2":
                                payReviewPageVo.setExamineTwoStatus("审核通过");
                                break;
                            default:
                                payReviewPageVo.setExamineTwoStatus("拒绝");
                        }
                    } else {
                        payReviewPageVo.setExamineTwoStatus("");

                    }
                    payReviewPageVo.setStatus(StatusTwoEnum.getStatusDesc(Integer.parseInt(payReviewPageVo.getStatus())));
                }
        );
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "查询成功", PageData.from(new PageInfo<>(payReviewPageVoList)));

    }

    @Override
    public ApiRes<PopupVo> popup(PopupDto popupDto) {
        if (popupDto == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        String id = popupDto.getId();
        PaymentOrderExamine paymentOrderExamine = paymentOrderExamineMapper.selectByPrimaryKey(id);
        if (paymentOrderExamine == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "审核订单不存在", "");
        }
        String type = popupDto.getType();
        if (!"1".equals(type) && !"2".equals(type)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        PopupVo popupVo = new PopupVo();
        popupVo.setReviewMsg(paymentOrderExamine.getMsg());
        switch (paymentOrderExamine.getExamineType()) {
            case 1:
                popupVo.setExamineType("金额调整");
                break;
            case 2:
                popupVo.setExamineType("延期支付");
                break;
            case 3:
                popupVo.setExamineType("缴费内容审核");
                break;
            case 4:
                popupVo.setExamineType("取消费用");
                break;
            default:
                popupVo.setExamineType("线下已收款");
        }
        popupVo.setReviewMsg(paymentOrderExamine.getMsg());
        if ("2".equals(type) && paymentOrderExamine.getExamine() == 2) {
            //查询一审内容
            PaymentOrderExamineDeail paymentOrderExamineDeail = paymentOrderExamineDeailMapper.queryByOne(paymentOrderExamine);
            popupVo.setExamineOneMsg(paymentOrderExamineDeail.getRemark());
            popupVo.setExamineOneName(paymentOrderExamineDeail.getCreateName());
            //查询一审的审批照片
            List<String> images = paymentOrderExamineDeailFileMapper.queryUrlByPaymentOrderExamineDeailId(paymentOrderExamineDeail.getId(), imageurl);
            popupVo.setImages(images);
        }
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "查询成功", popupVo);
    }

    @Override
    public ApiRes one(PayReviewDto payReviewDto) {
        if (payReviewDto == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        String id = payReviewDto.getId();
        if (StringUtils.isEmpty(id)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        String status = payReviewDto.getStatus();
        if (!"2".equals(status) && !"3".equals(status)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");

        }
        String msg = payReviewDto.getMsg();
        PaymentOrderExamine paymentOrderExamine = paymentOrderExamineMapper.selectByPrimaryKey(id);
        if (paymentOrderExamine == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "审核订单不存在", "");
        }
        if (paymentOrderExamine.getExamine() != 1) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "流程错误", "");

        }
        //查询一审内容
        PaymentOrderExamineDeail paymentOrderExamineDeail = paymentOrderExamineDeailMapper.queryByOne(paymentOrderExamine);
        if (paymentOrderExamineDeail == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "流程错误", "");
        }
        if (paymentOrderExamineDeail.getStatus() != 1) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "审核状态错误", "");
        }
        LoginUserInfo loginUserInfo = (LoginUserInfo) SecurityUtils.getSubject().getPrincipal();

        PaymentOrderMaster paymentOrderMaster = paymentOrderMasterMapper.selectByPrimaryKey(paymentOrderExamine.getPaymentOrderMasterId());
        if (paymentOrderMaster == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "系统内部错误", "");
        }
        List<PaymentOrderExamineDeailFile> paymentOrderExamineDeailFiles = new ArrayList<>();
        List<String> images = payReviewDto.getImages();
        if (images.size() > 0) {
            images.stream().forEach(
                    s -> {
                        PaymentOrderExamineDeailFile paymentOrderExamineDeailFile = new PaymentOrderExamineDeailFile();
                        paymentOrderExamineDeailFile.setPaymentOrderExamineDeailId(paymentOrderExamineDeail.getId());
                        paymentOrderExamineDeailFile.setType(4);
                        paymentOrderExamineDeailFile.setUrl(s);
                        paymentOrderExamineDeailFile.setStatus(1);
                        paymentOrderExamineDeailFile.setCreateTime(new Date());
                        paymentOrderExamineDeailFile.setDeleted(false);
                        paymentOrderExamineDeailFiles.add(paymentOrderExamineDeailFile);
                    }
            );
        }
        paymentOrderExamineDeail.setStatus(Integer.valueOf(status));
        paymentOrderExamineDeail.setRemark(msg);
        paymentOrderExamineDeail.setExamineTime(new Date());
        paymentOrderExamineDeail.setUpdateTime(new Date());
        paymentOrderExamineDeail.setCreateUser(loginUserInfo.getId());
        paymentOrderExamineDeail.setCreateName(loginUserInfo.getRealname());
        //如果是审核通过的生成二审订单明细
        if ("2".equals(status)) {//生成二审订单

            PaymentOrderExamineDeail paymentOrderExamineDeailTwo = new PaymentOrderExamineDeail();
            paymentOrderExamineDeailTwo.setPaymentOrderExamineId(paymentOrderExamine.getId());
            paymentOrderExamineDeailTwo.setExamine(2);
            paymentOrderExamineDeailTwo.setStatus(1);
            paymentOrderExamineDeailTwo.setCreateTime(new Date());
            paymentOrderExamineDeailMapper.insertSelective(paymentOrderExamineDeailTwo);
            //将主订单修改为二审
            paymentOrderExamine.setExamine(2);


            if (paymentOrderMaster.getPaymentTypeId().equals(1) && paymentOrderMaster.getType().equals(1)
                    && paymentOrderMaster.getAdjustmentCount().equals(0) && paymentOrderExamine.getExamineType().equals(3)) {
                paymentOrderExamineDeailTwo.setStatus(2);
                paymentOrderExamine.setExamine(0);
                paymentOrderExamineDeailMapper.updateByPrimaryKeySelective(paymentOrderExamineDeailTwo);
            }
            if (paymentOrderExamine.getExamineType().equals(5)) {
                paymentOrderExamineDeailTwo.setStatus(2);
                paymentOrderExamine.setExamine(0);
                paymentOrderExamineDeailMapper.updateByPrimaryKeySelective(paymentOrderExamineDeailTwo);
                paymentOrderMaster.setStatus(2);
                paymentOrderMaster.setPayType(2);
                MerchantStore merchantStore = merchantStoreMapper.selectByPrimaryKey(paymentOrderMaster.getMerchantStoreId());
                //如果是管理费要对门店管理字段进行操作
                if (paymentOrderMaster.getPaymentTypeId() == 1 && paymentOrderExamine.getExamineType().equals(5)) {
                    merchantStore.setAlreadyManagementExpense(paymentOrderMaster.getMoney());
                    Date newTime = DateUtil.endTime("1年后", merchantStore.getServiceExpireTime());
                    merchantStore.setServiceExpireTime(newTime);
                    merchantStoreMapper.updateByPrimaryKeySelective(merchantStore);
                    //如果管理费大缴纳标准2w
                    if (merchantStore.getManagementExpense().compareTo(new BigDecimal(20000)) > -1) {//说明用户的管理费金额包含云学堂的
                        //查询这个用户总共有多少个云学堂账户
                        List<MerchantStoreCloudSchool> merchantStoreCloudSchoolList = merchantStoreCloudSchoolMapper.queryByMerChantStoreId(merchantStore.getId());
                        if (merchantStoreCloudSchoolList.size() > 0) {
                            merchantStoreCloudSchoolList.stream().forEach(
                                    merchantStoreCloudSchool -> {
                                        Date newTime1 = DateUtil.endTime("1年后", merchantStoreCloudSchool.getEndTime());
                                        merchantStoreCloudSchool.setEndTime(newTime1);
                                        merchantStoreCloudSchoolMapper.updateByPrimaryKeySelective(merchantStoreCloudSchool);
                                    }
                            );
                        }
                    }
                }
//                if (paymentOrderMaster.getPaymentTypeId().equals(3) && paymentOrderExamine.getExamineType().equals(5)) {//修改云学堂时间
//                    //查询这个用户的云学堂账号信息
//                    Integer otherId = paymentOrderMaster.getOtherId();
//                    MerchantStoreCloudSchool merchantStoreCloudSchool = merchantStoreCloudSchoolMapper.queryById(otherId);
//                    Date newTime = DateUtil.endTime("1年后", merchantStoreCloudSchool.getEndTime());
//                    merchantStoreCloudSchool.setEndTime(newTime);
//                    merchantStoreCloudSchoolMapper.updateByPrimaryKeySelective(merchantStoreCloudSchool);
//                }

                paymentOrderMaster.setPayTime(new Date());
                //对支付订单进行操作
                paymentOrderMaster.setExamine(1);
                paymentOrderMasterMapper.updateByPrimaryKeySelective(paymentOrderMaster);
            }

        } else {
            //修改主订单的状态
            //将主订单修改为审核完成
            paymentOrderExamine.setExamine(0);
            paymentOrderExamine.setRefuse(msg);
            paymentOrderExamine.setStatus(3);
            paymentOrderExamine.setCompleteTime(new Date());
            //将缴费修改为未审核状态
            paymentOrderMaster.setExamine(1);
//            if (paymentOrderMaster.getType() == 2 && paymentOrderExamine.getExamineType() == 3 && paymentOrderMaster.getAdjustmentCount() .equals(0) ) {
//                //如果这笔订单是区域经理自己生成的还需要将这笔订单的订单状态改为审核拒绝
//                paymentOrderMaster.setStatus(4);
//            }


            if (paymentOrderExamine.getExamineType() == 3 && paymentOrderMaster.getAdjustmentCount().equals(0)) {
                //如果这笔订单是区域经理自己生成的还需要将这笔订单的订单状态改为审核拒绝
                paymentOrderMaster.setStatus(4);
            }


            if (paymentOrderMaster.getPaymentTypeId().equals(1) && paymentOrderMaster.getType().equals(1) && paymentOrderMaster.getAdjustmentCount().equals(0)) {
                paymentOrderMaster.setStatus(4);
            }
            if (paymentOrderMaster.getAdjustmentCount() > 0) {
                if (StringUtils.isEmpty(paymentOrderMaster.getAdjustmentMsg())) {
                    paymentOrderMaster.setAdjustmentMsg(paymentOrderExamine.getMsg() + "(拒绝)");
                } else {
                    paymentOrderMaster.setAdjustmentMsg(paymentOrderMaster.getAdjustmentMsg() + ";" + paymentOrderExamine.getMsg() + "(拒绝)");
                }
            }

            paymentOrderMasterMapper.updateByPrimaryKeySelective(paymentOrderMaster);
            paymentOrderExamine.setCompleteTime(new Date());
            //将主订单改为审核完成
            paymentOrderExamine.setExamine(0);
            //将缴费订单修改为未审核状态
            paymentOrderMaster.setExamine(1);
            paymentOrderMasterMapper.updateByPrimaryKeySelective(paymentOrderMaster);
            paymentOrderExamineDeailMapper.updateByPrimaryKeySelective(paymentOrderExamineDeail);
            paymentOrderExamineMapper.updateByPrimaryKeySelective(paymentOrderExamine);
            if (paymentOrderExamineDeailFiles.size() > 0) {
                paymentOrderExamineDeailFileMapper.insertList(paymentOrderExamineDeailFiles);
            }

        }
        paymentOrderExamineDeailMapper.updateByPrimaryKeySelective(paymentOrderExamineDeail);
        paymentOrderExamineMapper.updateByPrimaryKeySelective(paymentOrderExamine);
        if (paymentOrderExamineDeailFiles.size() > 0) {
            paymentOrderExamineDeailFileMapper.insertList(paymentOrderExamineDeailFiles);
        }

        if (paymentOrderMaster.getPaymentTypeId().equals(1)
                && paymentOrderMaster.getType().equals(1) &&
                paymentOrderMaster.getAdjustmentCount().equals(0) && "2".equals(status)) {
            paymentOrderMaster.setExamine(1);
            paymentOrderMasterMapper.updateByPrimaryKeySelective(paymentOrderMaster);
        }

        return ApiRes.response(CommonConstant.SUCCESS_CODE, "审核成功", "");
    }

    @Override
    public ApiRes two(PayReviewDto payReviewDto) {

        if (payReviewDto == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        String id = payReviewDto.getId();
        if (StringUtils.isEmpty(id)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        String status = payReviewDto.getStatus();
        if (!"2".equals(status) && !"3".equals(status)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");

        }
        String msg = payReviewDto.getMsg();
        PaymentOrderExamine paymentOrderExamine = paymentOrderExamineMapper.selectByPrimaryKey(id);
        if (paymentOrderExamine == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "审核订单不存在", "");
        }
        if (paymentOrderExamine.getExamine() != 2) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "流程错误", "");
        }

        PaymentOrderExamineDeail paymentOrderExamineDeail = paymentOrderExamineDeailMapper.queryByTwo(paymentOrderExamine);
        if (paymentOrderExamineDeail == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "流程错误", "");
        }
        if (paymentOrderExamineDeail.getStatus() != 1) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "审核状态错误", "");
        }
        LoginUserInfo loginUserInfo = (LoginUserInfo) SecurityUtils.getSubject().getPrincipal();

        PaymentOrderMaster paymentOrderMaster = paymentOrderMasterMapper.selectByPrimaryKey(paymentOrderExamine.getPaymentOrderMasterId());
        if (paymentOrderMaster == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "系统内部错误", "");
        }

        paymentOrderExamineDeail.setStatus(Integer.valueOf(status));
        paymentOrderExamineDeail.setRemark(msg);
        paymentOrderExamineDeail.setExamineTime(new Date());
        paymentOrderExamineDeail.setUpdateTime(new Date());
        paymentOrderExamineDeail.setCreateUser(loginUserInfo.getId());
        paymentOrderExamineDeail.setCreateName(loginUserInfo.getRealname());


        List<PaymentOrderExamineDeailFile> paymentOrderExamineDeailFiles = new ArrayList<>();

        List<String> images = payReviewDto.getImages();
        if (images.size() > 0) {
            images.stream().forEach(
                    s -> {
                        PaymentOrderExamineDeailFile paymentOrderExamineDeailFile = new PaymentOrderExamineDeailFile();
                        paymentOrderExamineDeailFile.setPaymentOrderExamineDeailId(paymentOrderExamineDeail.getId());
                        paymentOrderExamineDeailFile.setType(4);
                        paymentOrderExamineDeailFile.setUrl(s);
                        paymentOrderExamineDeailFile.setStatus(1);
                        paymentOrderExamineDeailFile.setCreateTime(new Date());
                        paymentOrderExamineDeailFile.setDeleted(false);
                        paymentOrderExamineDeailFiles.add(paymentOrderExamineDeailFile);
                    }
            );
        }
        if ("2".equals(status)) {
            //通过该后要对缴费订单进行操作
            switch (paymentOrderExamine.getExamineType()) {
                case 1:
                    paymentOrderMaster.setMoney(paymentOrderExamine.getNewMoney());
                    break;
                case 2:
                    paymentOrderMaster.setExpireTime(paymentOrderExamine.getNewTime());
                    break;
                case 4:
                    paymentOrderMaster.setStatus(3);
                    break;
                case 5:
                    paymentOrderMaster.setStatus(2);
                    paymentOrderMaster.setPayType(2);
                    break;
                default:
            }

            MerchantStore merchantStore = merchantStoreMapper.selectByPrimaryKey(paymentOrderMaster.getMerchantStoreId());
            //如果是管理费要对门店管理字段进行操作
            if (paymentOrderMaster.getPaymentTypeId() == 1 && paymentOrderExamine.getExamineType().equals(5)) {
                paymentOrderMaster.setPayTime(new Date());
                merchantStore.setAlreadyManagementExpense(paymentOrderMaster.getMoney());
                Date newTime = DateUtil.endTime("1年后", merchantStore.getServiceExpireTime());
                merchantStore.setServiceExpireTime(newTime);
                merchantStoreMapper.updateByPrimaryKeySelective(merchantStore);
                //如果管理费大缴纳标准2w
                if (merchantStore.getManagementExpense().compareTo(new BigDecimal(20000)) > -1) {//说明用户的管理费金额包含云学堂的
                    //查询这个用户总共有多少个云学堂账户
                    List<MerchantStoreCloudSchool> merchantStoreCloudSchoolList = merchantStoreCloudSchoolMapper.queryByMerChantStoreId(merchantStore.getId());
                    if (merchantStoreCloudSchoolList.size() > 0) {
                        merchantStoreCloudSchoolList.stream().forEach(
                                merchantStoreCloudSchool -> {
                                    Date newTime1 = DateUtil.endTime("1年后", merchantStoreCloudSchool.getEndTime());
                                    merchantStoreCloudSchool.setEndTime(newTime1);
                                    merchantStoreCloudSchoolMapper.updateByPrimaryKeySelective(merchantStoreCloudSchool);
                                }
                        );
                    }
                }
            }

            paymentOrderExamine.setStatus(2);
            if (paymentOrderExamine.getExamineType() == 4) {//取消费用
                paymentOrderMaster.setStatus(3);
            }

            if (paymentOrderMaster.getAdjustmentCount() > 0) {
                if (StringUtils.isEmpty(paymentOrderMaster.getAdjustmentMsg())) {
                    paymentOrderMaster.setAdjustmentMsg(paymentOrderExamine.getMsg() + "(通过)");
                } else {
                    paymentOrderMaster.setAdjustmentMsg(paymentOrderMaster.getAdjustmentMsg() + ";" + paymentOrderExamine.getMsg() + "(通过)");
                }
            }
            if (paymentOrderMaster.getPaymentTypeId() == 1 && paymentOrderExamine.getExamineType() == 1) {//如果是管理费，且是金额调整，需要修改门店的管理费需缴纳金额字段
                MerchantStore merchantStore1 = merchantStoreMapper.selectByPrimaryKey(paymentOrderMaster.getMerchantStoreId());
                merchantStore1.setNeedManagementExpense(paymentOrderMaster.getMoney());
                merchantStoreMapper.updateByPrimaryKeySelective(merchantStore1);
            }
        } else {
            paymentOrderExamine.setRefuse(msg);
            paymentOrderExamine.setStatus(3);
            if (paymentOrderMaster.getType() == 2 && paymentOrderExamine.getExamineType() == 3) {
                //如果这笔订单是区域经理自己生成的还需要将这笔订单的订单状态改为审核拒绝
                paymentOrderMaster.setStatus(4);
            }


            if (paymentOrderMaster.getAdjustmentCount() > 0) {
                if (StringUtils.isEmpty(paymentOrderMaster.getAdjustmentMsg())) {
                    paymentOrderMaster.setAdjustmentMsg(paymentOrderExamine.getMsg() + "(拒绝)");
                } else {
                    paymentOrderMaster.setAdjustmentMsg(paymentOrderMaster.getAdjustmentMsg() + ";" + paymentOrderExamine.getMsg() + "(拒绝)");
                }
            }

        }
        paymentOrderExamine.setCompleteTime(new Date());
        //将主订单改为审核完成
        paymentOrderExamine.setExamine(0);
        //将缴费订单修改为未审核状态
        paymentOrderMaster.setExamine(1);
        paymentOrderMasterMapper.updateByPrimaryKeySelective(paymentOrderMaster);
        paymentOrderExamineDeailMapper.updateByPrimaryKeySelective(paymentOrderExamineDeail);
        paymentOrderExamineMapper.updateByPrimaryKeySelective(paymentOrderExamine);
        if (paymentOrderExamineDeailFiles.size() > 0) {
            paymentOrderExamineDeailFileMapper.insertList(paymentOrderExamineDeailFiles);
        }
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "审核成功", "");
    }

    @Override
    public ApiRes<PayReviewDetailVo> detail(PayReviewDetailDto payReviewDetailDto) {
        if (payReviewDetailDto == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        String id = payReviewDetailDto.getId();
        if (StringUtils.isEmpty(id)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        PaymentOrderExamine paymentOrderExamine = paymentOrderExamineMapper.selectByPrimaryKey(id);
        if (paymentOrderExamine == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "订单不存在", "");
        }

        PaymentOrderMaster paymentOrderMaster = paymentOrderMasterMapper.selectByPrimaryKey(paymentOrderExamine.getPaymentOrderMasterId());
        if (paymentOrderMaster == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "订单不存在", "");
        }
        //审核明细
        List<PaymentOrderExamineDeail> paymentOrderExamineDeailList = paymentOrderExamineDeailMapper.queryByPaymentOrderExamineId(paymentOrderExamine.getId());
        //文件明细
        List<PaymentOrderExamineFile> paymentOrderExamineFileList = paymentOrderExamineFileMapper.queryByPaymentOrderExamineId(paymentOrderExamine.getId());

        //数据组装
        PayReviewDetailVo payReviewDetailVo = new PayReviewDetailVo();
        payReviewDetailVo.setExpireTime(paymentOrderMaster.getExpireTime());
        payReviewDetailVo.setUid(paymentOrderExamine.getMerchantStoreUid());
        payReviewDetailVo.setMerchantName(paymentOrderMaster.getMerchantName());
        payReviewDetailVo.setMoney(paymentOrderMaster.getMoney() + "");
        payReviewDetailVo.setApplicant(paymentOrderExamine.getApplyName());
        payReviewDetailVo.setCreateTime(paymentOrderExamine.getCreateTime());
        payReviewDetailVo.setReviewMsg(paymentOrderExamine.getMsg());
        payReviewDetailVo.setRemark(paymentOrderExamine.getRemark());
        payReviewDetailVo.setPayTypeName(paymentOrderExamine.getPaymentTypeName());
        switch (paymentOrderExamine.getExamineType()) {
            case 1:
                payReviewDetailVo.setExamineType("金额调整");
                break;
            case 2:
                payReviewDetailVo.setExamineType("延期支付");
                break;
            case 3:
                payReviewDetailVo.setExamineType("缴费内容审核");
                break;
            case 4:
                payReviewDetailVo.setExamineType("取消费用");
                break;
            default:
                payReviewDetailVo.setExamineType("线下已收款");
        }
        List<ExamineDetailVo> examineDetailVoList = new ArrayList<>();
        paymentOrderExamineDeailList.stream().forEach(
                paymentOrderExamineDeail -> {
                    ExamineDetailVo examineDetailVo = new ExamineDetailVo();
                    if (paymentOrderExamineDeail.getExamine() == 1) {
                        examineDetailVo.setName("一审");
                    } else {
                        examineDetailVo.setName("二审");
                    }

                    switch (paymentOrderExamineDeail.getStatus()) {
                        case 1:
                            examineDetailVo.setExamineStatus("待审核");
                            break;
                        case 2:
                            examineDetailVo.setExamineStatus("审核通过");
                            break;
                        default:
                            examineDetailVo.setExamineStatus("拒绝");
                    }
                    examineDetailVo.setExamineMsg(paymentOrderExamineDeail.getRemark());
                    examineDetailVo.setExamineName(paymentOrderExamineDeail.getCreateName());
                    //查询一审的审批照片
                    List<String> images = paymentOrderExamineDeailFileMapper.queryUrlByPaymentOrderExamineDeailId(paymentOrderExamineDeail.getId(), imageurl);
                    examineDetailVo.setImages(images);
                    examineDetailVo.setUrl(imageurl);
                    examineDetailVoList.add(examineDetailVo);
                }
        );
        payReviewDetailVo.setExamineDetailVoList(examineDetailVoList);


        List<ExamineFileVo> examineFileVoList = new ArrayList<>();

        List<String> video = new ArrayList<>();
        List<String> image = new ArrayList<>();
        paymentOrderExamineFileList.stream().forEach(
                paymentOrderExamineFile -> {
                    if (paymentOrderExamineFile.getType() == 4) {
                        image.add(imageurl + paymentOrderExamineFile.getUrl());
                    }
                    if (paymentOrderExamineFile.getType() == 3) {
                        video.add(imageurl + paymentOrderExamineFile.getUrl());

                    }
                }
        );

        ExamineFileVo examineFileVoImage = new ExamineFileVo();
        examineFileVoImage.setName("图片");
        examineFileVoImage.setFileS(image);
        examineFileVoList.add(examineFileVoImage);

        ExamineFileVo examineFileVoVideo = new ExamineFileVo();
        examineFileVoVideo.setName("视频");
        examineFileVoVideo.setFileS(video);
        examineFileVoList.add(examineFileVoVideo);
        payReviewDetailVo.setExamineFileVoList(examineFileVoList);
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "查询成功", payReviewDetailVo);
    }

    @Override
    public void exportPayReview(PayReviewPageDto payReviewPageDto, HttpServletResponse response) {
        String paymentTypeId = payReviewPageDto.getPaymentTypeId();
        if (StringUtils.isEmpty(paymentTypeId)) {
            payReviewPageDto.setPaymentTypeId(null);
        }

        String condition = payReviewPageDto.getCondition();
        if (StringUtils.isEmpty(condition)) {
            payReviewPageDto.setCondition(null);
        }
        if (StringUtils.isEmpty(payReviewPageDto.getEndTime())) {
            payReviewPageDto.setEndTime(null);
            payReviewPageDto.setStartTime(null);
        } else {
            String startTime = payReviewPageDto.getStartTime();
            String endTime = payReviewPageDto.getEndTime();
            startTime = startTime + " 00:00:00";
            endTime = endTime + " 23:59:59";
            payReviewPageDto.setEndTime(endTime);
            payReviewPageDto.setStartTime(startTime);
        }
        payReviewPageDto.setExamineOneStatus(StringUtils.isBlank(payReviewPageDto.getExamineOneStatus()) ? null : payReviewPageDto.getExamineOneStatus());
        payReviewPageDto.setExamineTwoStatus(StringUtils.isBlank(payReviewPageDto.getExamineTwoStatus()) ? null : payReviewPageDto.getExamineTwoStatus());
        payReviewPageDto.setExamineType(StringUtils.isBlank(payReviewPageDto.getExamineType()) ? null : payReviewPageDto.getExamineType());
        List<PayReviewPageVo> payReviewPageVoList = paymentOrderExamineMapper.findPayReviewPageVo(payReviewPageDto);

        payReviewPageVoList.stream().forEach(
                payReviewPageVo -> {
                    switch (payReviewPageVo.getExamineType()) {
                        case "1":
                            payReviewPageVo.setExamineType("金额调整");
                            break;
                        case "2":
                            payReviewPageVo.setExamineType("延期支付");
                            break;
                        case "3":
                            payReviewPageVo.setExamineType("缴费内容审核");
                            break;
                        case "4":
                            payReviewPageVo.setExamineType("取消费用");
                            break;
                        default:
                            payReviewPageVo.setExamineType("线下已收款");
                    }
                    switch (payReviewPageVo.getExamineOneStatus()) {
                        case "1":
                            payReviewPageVo.setExamineOneStatus("待审核");
                            payReviewPageVo.setExamineTwoStatus("");
                            break;
                        case "2":
                            payReviewPageVo.setExamineOneStatus("审核通过");
                            break;
                        case "3":
                            payReviewPageVo.setExamineOneStatus("拒绝");
                            break;
                        default:
                            payReviewPageVo.setExamineOneStatus("");
                    }
                    if (!StringUtils.isEmpty(payReviewPageVo.getExamineTwoStatus())) {
                        switch (payReviewPageVo.getExamineTwoStatus()) {
                            case "1":
                                payReviewPageVo.setExamineTwoStatus("待审核");
                                break;
                            case "2":
                                payReviewPageVo.setExamineTwoStatus("审核通过");
                                break;
                            default:
                                payReviewPageVo.setExamineTwoStatus("拒绝");
                        }
                    } else {
                        payReviewPageVo.setExamineTwoStatus("");

                    }
                    payReviewPageVo.setStatus(StatusTwoEnum.getStatusDesc(Integer.parseInt(payReviewPageVo.getStatus())));
                }
        );
//        ExcelUtils.writeExcel(response, payReviewPageVoList, PayReviewPageVo.class, "门店缴费审核.xlsx");
        EasyExcel.write(ExcelUtil.getOutPutStream("门店缴费审核", response), PayReviewPageVo.class).registerWriteHandler(ExcelUtil.getHorizontalCellStyleStrategy()).sheet().doWrite(payReviewPageVoList);
    }
}