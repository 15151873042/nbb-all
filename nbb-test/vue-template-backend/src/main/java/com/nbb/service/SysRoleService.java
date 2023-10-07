package com.nbb.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.nbb.domain.entity.SysRole;

import java.util.Collection;
import java.util.Set;

/**
 * <p>
 * 角色信息表 服务类
 * </p>
 *
 * @author hupeng
 * @since 2023-08-31
 */
public interface SysRoleService extends IService<SysRole> {

    /**
     * 根据用户ID查询角色权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    public Set<String> selectRolePermissionByUserId(Long userId);
}
