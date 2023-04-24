package com.ynzyq.yudbadmin.service.business.impl;

import com.alibaba.excel.EasyExcel;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.ynzyq.yudbadmin.core.model.*;
import com.ynzyq.yudbadmin.dao.business.dao.MerchantMapper;
import com.ynzyq.yudbadmin.dao.business.dao.RegionalManagerMapper;
import com.ynzyq.yudbadmin.dao.business.dao.YgAppMenuMapper;
import com.ynzyq.yudbadmin.dao.business.dao.YgMerchantAppMenuMapper;
import com.ynzyq.yudbadmin.dao.business.dto.*;
import com.ynzyq.yudbadmin.dao.business.model.Merchant;
import com.ynzyq.yudbadmin.dao.business.model.RegionalManager;
import com.ynzyq.yudbadmin.dao.business.model.YgAppMenu;
import com.ynzyq.yudbadmin.dao.business.model.YgMerchantAppMenu;
import com.ynzyq.yudbadmin.dao.business.vo.*;
import com.ynzyq.yudbadmin.enums.StatusEnum;
import com.ynzyq.yudbadmin.service.business.MerchantService;
import com.ynzyq.yudbadmin.util.Encrypt;
import com.ynzyq.yudbadmin.util.ExcelUtil;
import com.ynzyq.yudbadmin.util.PhoneFormatCheckUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

@Service
public class MerchantServiceImpl implements MerchantService {

    @Resource
    MerchantMapper merchantMapper;
    @Resource
    RegionalManagerMapper regionalManagerMapper;

    @Resource
    YgMerchantAppMenuMapper ygMerchantAppMenuMapper;

    @Resource
    YgAppMenuMapper ygAppMenuMapper;

    @Override
    public ApiRes<PageWrap<MerchantPageVo>> findPage(PageWrap<MerchantPageDto> pageWrap) {
        MerchantPageDto merchantPageDto = pageWrap.getModel();
        if (StringUtils.isEmpty(merchantPageDto.getCondition())) {
            merchantPageDto.setCondition(null);
        }
        if (StringUtils.isEmpty(merchantPageDto.getEndTime())) {
            merchantPageDto.setEndTime(null);
            merchantPageDto.setStartTime(null);
        } else {
            String startTime = merchantPageDto.getStartTime();
            String endTime = merchantPageDto.getEndTime();
            startTime = startTime + " 00:00:00";
            endTime = endTime + " 23:59:59";
            merchantPageDto.setEndTime(endTime);
            merchantPageDto.setStartTime(startTime);
        }
        PageHelper.startPage(pageWrap.getPage(), pageWrap.getCapacity());
        List<MerchantPageVo> list = merchantMapper.findPage(merchantPageDto);
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "查询成功", PageData.from(new PageInfo<>(list)));
    }

    @Override
    public ApiRes add(MerchantAddDto merchantAddDto) {
        if (merchantAddDto == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        String name = merchantAddDto.getName();
        if (StringUtils.isEmpty(name)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "请输入加盟商", "");
        }

        String phone = merchantAddDto.getPhone();
        if (!PhoneFormatCheckUtils.isChinaPhoneLegal(phone)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "请输入正确的手机号", "");
        }
        String idNumber = merchantAddDto.getIdNumber();
        if (StringUtils.isEmpty(idNumber)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "请输入正确的身份证号", "");
        }

        //判断手机号是否存在
        Merchant merchant = merchantMapper.queryByPhone(phone);
        if (merchant != null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "该手机号已存在", "");
        }
        //业务经理
        RegionalManager regionalManager = regionalManagerMapper.queryByPhone(phone);
        if (regionalManager != null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "该手机号已存在", "");
        }
        LoginUserInfo loginUserInfo = (LoginUserInfo) SecurityUtils.getSubject().getPrincipal();
        merchant = new Merchant();
        merchant.setName(name);
        merchant.setCreateUser(loginUserInfo.getId());
        merchant.setMobile(phone);
        try {
            merchant.setPassword(Encrypt.AESencrypt("123456"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        merchant.setStatus(1);
        merchant.setIsAgent(Integer.valueOf(merchantAddDto.getIsAgent()));
        merchant.setIdNumber(idNumber);
        merchant.setCreateTime(new Date());
        merchantMapper.insertSelective(merchant);
        // 添加app权限
        addAppMenu(merchant.getId(), merchant.getIsAgent(), loginUserInfo);
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "添加成功", "");
    }

    private void addAppMenu(Integer merchantId, Integer isAgent, LoginUserInfo loginUserInfo) {
        YgAppMenu query = new YgAppMenu();
        query.setStatus(1);
        List<YgAppMenu> appMenuList = ygAppMenuMapper.select(query);
        List<YgMerchantAppMenu> addList = Lists.newArrayList();
        YgMerchantAppMenu ygMerchantAppMenu;
        for (YgAppMenu ygAppMenu : appMenuList) {
            if (isAgent.equals(2) && ygAppMenu.getId().equals(4)) {
                continue;
            }
            ygMerchantAppMenu = new YgMerchantAppMenu();
            ygMerchantAppMenu.setMerchantId(merchantId);
            ygMerchantAppMenu.setMenuId(ygAppMenu.getId());
            ygMerchantAppMenu.setStatus(1);
            ygMerchantAppMenu.setCreateTime(new Date());
            ygMerchantAppMenu.setUpdateUser(loginUserInfo.getId());
            ygMerchantAppMenu.setUserName(loginUserInfo.getRealname());
            addList.add(ygMerchantAppMenu);
        }
        ygMerchantAppMenuMapper.insertList(addList);
        addList.clear();
    }

    @Override
    public ApiRes edit(MerchantEditDto merchantEditDto) {
        if (merchantEditDto == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        String id = merchantEditDto.getId();
        if (StringUtils.isEmpty(id)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数不对", "");
        }

        String name = merchantEditDto.getName();
        if (StringUtils.isEmpty(name)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "请输入加盟商", "");
        }
        if (StringUtils.isBlank(merchantEditDto.getIsAgent())) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "请选择是否为代理", "");
        } else if (StringUtils.isBlank(merchantEditDto.getIdNumber())) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "请输入身份证/统一信用代码", "");
        } else if (StringUtils.isBlank(merchantEditDto.getPhone())) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "手机号不能为空", "");
        }
        Merchant merchant = merchantMapper.selectByPrimaryKey(id);
        if (merchant == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "加盟商不存在", "");
        } else if (!merchant.getId().equals(Integer.parseInt(id)) && StringUtils.equals(merchant.getMobile(), merchantEditDto.getPhone())) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "加盟商手机号已存在，不能重复输入", "");
        }
        LoginUserInfo loginUserInfo = (LoginUserInfo) SecurityUtils.getSubject().getPrincipal();
        merchant.setUpdateUser(loginUserInfo.getId());
        merchant.setUpdateTime(new Date());
        merchant.setName(name);
        merchant.setIsAgent(Integer.parseInt(merchantEditDto.getIsAgent()));
        merchant.setIdNumber(merchantEditDto.getIdNumber());
        merchant.setMobile(merchantEditDto.getPhone());
        merchantMapper.updateByPrimaryKeySelective(merchant);
        // 更新代理权的加盟商名字
        merchantMapper.updateAgentMerchant(name, merchant.getId());
        // 更新门店的所属代理名字
        merchantMapper.updateStoreMerchant(name, merchant.getId());
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "编辑成功", "");

    }

    @Override
    public void exportMerchant(MerchantPageDto merchantPageDto, HttpServletResponse response) {
        if (StringUtils.isEmpty(merchantPageDto.getCondition())) {
            merchantPageDto.setCondition(null);
        }
        if (StringUtils.isEmpty(merchantPageDto.getEndTime())) {
            merchantPageDto.setEndTime(null);
            merchantPageDto.setStartTime(null);
        } else {
            String startTime = merchantPageDto.getStartTime();
            String endTime = merchantPageDto.getEndTime();
            startTime = startTime + " 00:00:00";
            endTime = endTime + " 23:59:59";
            merchantPageDto.setEndTime(endTime);
            merchantPageDto.setStartTime(startTime);
        }
        List<MerchantPageVo> list = merchantMapper.findPage(merchantPageDto);
        list.forEach(merchantPageVo -> {
            merchantPageVo.setIsAgent(StringUtils.equals("1", merchantPageVo.getIsAgent()) ? "是" : "否");
            merchantPageVo.setStatus(StringUtils.equals("1", merchantPageVo.getStatus()) ? "启用" : "禁用");
        });
//        ExcelUtils.writeExcel(response, list, MerchantPageVo.class, "客户.xlsx");
        EasyExcel.write(ExcelUtil.getOutPutStream("客户", response), MerchantPageVo.class).registerWriteHandler(ExcelUtil.getHorizontalCellStyleStrategy()).sheet().doWrite(list);
    }

    @Override
    public ApiRes updateStatus(UpdateStatusDTO updateStatusDTO) {
        Merchant merchant = merchantMapper.selectByPrimaryKey(updateStatusDTO.getId());
        if (merchant == null) {
            return ApiRes.failResponse("客户不存在");
        }
        Merchant updateMerchant = new Merchant();
        updateMerchant.setId(merchant.getId());
        if (StatusEnum.ENABLE.getStatus().equals(merchant.getStatus())) {
            updateMerchant.setStatus(StatusEnum.DISABLE.getStatus());
        } else {
            updateMerchant.setStatus(StatusEnum.ENABLE.getStatus());
        }
        updateMerchant.setUpdateTime(new Date());
        if (merchantMapper.updateByPrimaryKeySelective(updateMerchant) == 0) {
            return ApiRes.failResponse("操作失败");
        }
        return ApiRes.successResponse();
    }

    @Override
    public ApiRes<ListAppMenuVO> listAppMenu(ListAppMenuDTO listAppMenuDTO) {
        Merchant merchant = merchantMapper.selectByPrimaryKey(listAppMenuDTO.getMerchantId());
        if (merchant == null) {
            return ApiRes.failResponse("加盟商不存在");
        }
        List<ListAppMenuVO> listAppMenuVOS;
        if (merchant.getIsAgent().equals(1)) {
            listAppMenuVOS = merchantMapper.listAppMenu(listAppMenuDTO);
        } else {
            listAppMenuDTO.setMenuId(4);
            listAppMenuVOS = merchantMapper.listAppMenu(listAppMenuDTO);
        }
        return ApiRes.successResponseData(listAppMenuVOS);
    }

    @Override
    public ApiRes chooseAppMenu(ChooseAppMenuDTO chooseAppMenuDTO) {
        // 先将原来的菜单改为不可用
        YgMerchantAppMenu deleteYgMerchantAppMenu = new YgMerchantAppMenu();
        deleteYgMerchantAppMenu.setStatus(2);
        deleteYgMerchantAppMenu.setUpdateTime(new Date());
        LoginUserInfo loginUserInfo = (LoginUserInfo) SecurityUtils.getSubject().getPrincipal();
        deleteYgMerchantAppMenu.setUpdateUser(loginUserInfo.getId());
        deleteYgMerchantAppMenu.setUserName(loginUserInfo.getRealname());
        Example example = new Example(YgMerchantAppMenu.class);
        example.createCriteria().andEqualTo("merchantId", chooseAppMenuDTO.getMerchantId());
        ygMerchantAppMenuMapper.updateByExampleSelective(deleteYgMerchantAppMenu, example);

        if (chooseAppMenuDTO.getMenuIds() != null && chooseAppMenuDTO.getMenuIds().size() > 0) {
            List<YgMerchantAppMenu> addList = Lists.newArrayList();
            YgMerchantAppMenu ygMerchantAppMenu;
            for (String menuId : chooseAppMenuDTO.getMenuIds()) {
                ygMerchantAppMenu = new YgMerchantAppMenu();
                ygMerchantAppMenu.setMerchantId(Integer.parseInt(chooseAppMenuDTO.getMerchantId()));
                ygMerchantAppMenu.setMenuId(Integer.parseInt(menuId));
                ygMerchantAppMenu.setStatus(1);
                ygMerchantAppMenu.setCreateTime(new Date());
                ygMerchantAppMenu.setUpdateUser(loginUserInfo.getId());
                ygMerchantAppMenu.setUserName(loginUserInfo.getRealname());
                addList.add(ygMerchantAppMenu);
            }
            ygMerchantAppMenuMapper.insertList(addList);
            addList.clear();
            // 修改app登录权限
            Merchant updateMerchant = new Merchant();
            updateMerchant.setId(Integer.parseInt(chooseAppMenuDTO.getMerchantId()));
            updateMerchant.setUpdateTime(new Date());
            if (chooseAppMenuDTO.getMenuIds().contains("0")) {
                updateMerchant.setIsAuth(1);
            } else {
                updateMerchant.setIsAuth(2);
            }
            merchantMapper.updateByPrimaryKeySelective(updateMerchant);
        }
        return ApiRes.successResponse();
    }
}
