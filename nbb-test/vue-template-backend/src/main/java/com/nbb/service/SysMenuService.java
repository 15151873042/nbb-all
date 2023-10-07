package com.nbb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nbb.domain.entity.SysMenu;
import com.nbb.domain.vo.RouterVO;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 菜单权限表 服务类
 * </p>
 *
 * @author hupeng
 * @since 2023-08-30
 */
public interface SysMenuService extends IService<SysMenu> {

    /**
     * 根据用户ID查询菜单树信息
     *
     * @param userId 用户ID
     * @return 菜单列表
     */
    List<SysMenu> selectMenuTreeByUserId(Long userId);

    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    Set<String> selectMenuPermsByUserId(Long userId);

}
