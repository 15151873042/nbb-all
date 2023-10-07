package com.nbb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nbb.domain.entity.SysUser;
import com.nbb.mapper.SysUserMapper;
import com.nbb.service.SysUserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author hupeng
 * @since 2023-08-30
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

}
