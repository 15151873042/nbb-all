package com.nbb.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nbb.domain.entity.SysRole;

import java.util.List;

/**
 * <p>
 * 角色信息表 Mapper 接口
 * </p>
 *
 * @author hupeng
 * @since 2023-08-31
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 根据用户ID查询角色
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    List<SysRole> selectRolePermissionByUserId(Long userId);
}
