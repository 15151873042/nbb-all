package com.nbb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nbb.domain.entity.SysRole;
import com.nbb.mapper.SysRoleMapper;
import com.nbb.service.SysRoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色信息表 服务实现类
 * </p>
 *
 * @author hupeng
 * @since 2023-08-31
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Override
    public Set<String> selectRolePermissionByUserId(Long userId) {

        List<SysRole> roles = baseMapper.selectRolePermissionByUserId(userId);

        Set<String> roleKeys = roles.stream()
                .map(SysRole::getRoleKey)
                .filter(StringUtils::isNotBlank)
                .collect(Collectors.toSet());

        return roleKeys;
    }
}
