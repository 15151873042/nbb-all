package com.nbb.framework.mybatisplus;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.nbb.domain.entity.SysUser;
import com.nbb.util.SecurityUtils;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;
import java.util.Date;

public class MyMetaObjectHandler implements MetaObjectHandler {

    /**
     * 表数据新增时自动填充的字段
     * @param metaObject 元对象
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("createTime", LocalDateTime.now(), metaObject);
        this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);

        if (StpUtil.isLogin()) {
            String loginUserName = SecurityUtils.getLoginUser().getUserName();
            this.setFieldValByName("createBy", loginUserName, metaObject);
            this.setFieldValByName("updateBy", loginUserName, metaObject);
        }
    }

    /**
     * 表数据更新时自动填充的字段
     * @param metaObject 元对象
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);

        if (StpUtil.isLogin()) {
            String loginUserName = SecurityUtils.getLoginUser().getUserName();
            this.setFieldValByName("updateBy", loginUserName, metaObject);
        }
    }
}
