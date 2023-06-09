package com.ynzyq.yudbadmin.biz.impl;

import com.ynzyq.yudbadmin.biz.SystemMenuBiz;
import com.ynzyq.yudbadmin.core.model.CommonConstant;
import com.ynzyq.yudbadmin.dao.system.dto.UpdateSystemMenuSortDTO;
import com.ynzyq.yudbadmin.dao.system.model.SystemMenu;
import com.ynzyq.yudbadmin.dao.system.vo.SystemMenuListVO;
import com.ynzyq.yudbadmin.dao.system.vo.SystemMenuNodeVO;
import com.ynzyq.yudbadmin.service.system.SystemMenuService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SystemMenuBizImpl implements SystemMenuBiz {

    @Autowired
    private SystemMenuService systemMenuService;

    @Override
    public void updateSort(UpdateSystemMenuSortDTO dto) {
        SystemMenu currentMenu = systemMenuService.findById(dto.getId());
        List<SystemMenu> menuPool;
        if (currentMenu.getParentId() == null) {
            menuPool = systemMenuService.findRootList();
        } else {
            SystemMenu queryDto = new SystemMenu();
            queryDto.setParentId(currentMenu.getParentId());
            queryDto.setDeleted(Boolean.FALSE);
            menuPool = systemMenuService.findList(queryDto);
        }
        int currentMenuIndex = menuPool.indexOf(currentMenu);
        // 上移
        if ("top".equals(dto.getDirection())) {
            if (currentMenuIndex - 1 < 0) {
                return;
            }
            SystemMenu preMenu = menuPool.remove(currentMenuIndex - 1);
            menuPool.add(currentMenuIndex, preMenu);
        }
        // 下移
        else {
            if (currentMenuIndex + 1 > menuPool.size() - 1) {
                return;
            }
            SystemMenu nextMenu = menuPool.remove(currentMenuIndex + 1);
            menuPool.add(currentMenuIndex, nextMenu);
        }
        for (int i = 0; i < menuPool.size(); i++) {
            menuPool.get(i).setSort(i);
        }
        // 修改
        systemMenuService.updateByIdInBatch(menuPool);
    }

    @Override
    public List<SystemMenuListVO> findList() {

        List<SystemMenuListVO> menus = systemMenuService.findList();
        List<SystemMenuListVO> rootMenus = new ArrayList<>();
        // 添加根菜单
        for (SystemMenu menu : menus) {
            if (menu.getParentId() == null) {
                SystemMenuListVO rootMenu = new SystemMenuListVO();
                BeanUtils.copyProperties(menu, rootMenu, "children");
                rootMenu.setChildren(new ArrayList<>());
                rootMenus.add(rootMenu);
            }
        }
        menus.removeIf(menu -> menu.getParentId() == null);
        for (SystemMenuListVO child : rootMenus) {
            this.fillChildren(child, menus);
        }
        return rootMenus;
    }

    @Override
    public List<SystemMenuNodeVO> findTree(Integer userId) {
        SystemMenu queryDto = new SystemMenu();
        queryDto.setDeleted(Boolean.FALSE);
        List<SystemMenu> menus = systemMenuService.findByUserId(userId);
        List<SystemMenuNodeVO> rootNodes = new ArrayList<>();
        // 添加根菜单
        for (SystemMenu menu : menus) {
            // 只有这个系统的根菜单才显示
            if (CommonConstant.IS_SYSTEM.equals(menu.getParentId())) {
                SystemMenuNodeVO nodeVO = new SystemMenuNodeVO();
                nodeVO.setId(menu.getId());
                nodeVO.setIndex("menu_" + menu.getId());
                nodeVO.setLabel(menu.getName());
                nodeVO.setUrl(menu.getPath());
                nodeVO.setIcon(menu.getIcon());
                nodeVO.setIsSystem(String.valueOf(menu.getIsSystem()));
                nodeVO.setChildren(new ArrayList<>());
                rootNodes.add(nodeVO);
            }
        }
        menus.removeIf(menu -> menu.getParentId() == null);
        for (SystemMenuNodeVO child : rootNodes) {
            this.fillChildren(child, menus);
        }
        return rootNodes;
    }

    /**
     * 填充子菜单
     *
     * @author Caesar Liu
     * @date 2021-03-29 16:09
     */
    private void fillChildren(SystemMenuListVO parentMenu, List<SystemMenuListVO> menus) {
        if (menus.size() == 0) {
            return;
        }
        List<Integer> handledIds = new ArrayList<>();
        for (SystemMenu menu : menus) {
            if (menu.getParentId().equals(parentMenu.getId())) {
                SystemMenuListVO child = new SystemMenuListVO();
                BeanUtils.copyProperties(menu, child, "children");
                child.setChildren(new ArrayList<>());
                parentMenu.getChildren().add(child);
                handledIds.add(menu.getId());
            }
        }
        menus.removeIf(menu -> handledIds.contains(menu.getId()));
        parentMenu.setHasChildren(Boolean.TRUE);
        if (parentMenu.getChildren().size() > 0) {
            parentMenu.setHasChildren(Boolean.FALSE);
            for (SystemMenuListVO child : parentMenu.getChildren()) {
                this.fillChildren(child, menus);
            }
        }
    }

    /**
     * 填充子菜单
     *
     * @author Caesar Liu
     * @date 2021-03-29 16:09
     */
    private void fillChildren(SystemMenuNodeVO parentNode, List<SystemMenu> menus) {
        if (menus.size() == 0) {
            return;
        }
        List<Integer> handledIds = new ArrayList<>();
        for (SystemMenu menu : menus) {
            if (menu.getParentId().equals(parentNode.getId())) {
                SystemMenuNodeVO child = new SystemMenuNodeVO();
                child.setId(menu.getId());
                child.setLabel(menu.getName());
                child.setUrl(menu.getPath());
                child.setIcon(menu.getIcon());
                child.setIndex("menu_" + menu.getId());
                child.setChildren(new ArrayList<>());
                parentNode.getChildren().add(child);
                handledIds.add(menu.getId());
            }
        }
        menus.removeIf(menu -> handledIds.contains(menu.getId()));
        for (SystemMenuNodeVO child : parentNode.getChildren()) {
            this.fillChildren(child, menus);
        }
    }
}
