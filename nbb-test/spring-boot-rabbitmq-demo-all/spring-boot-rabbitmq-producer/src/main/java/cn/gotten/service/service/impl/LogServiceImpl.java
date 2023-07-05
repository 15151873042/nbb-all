package cn.gotten.service.service.impl;

import cn.gotten.common.core.vo.ExecuteResult;
import cn.gotten.service.service.LogService;
import org.springframework.stereotype.Service;

/**
 * @author: 胡鹏
 * @date: 2021/3/9 21:08
 * @description:
 */
@Service
public class LogServiceImpl implements LogService {
    @Override
    public ExecuteResult<Void> addSave() {
        // 1、写数据库业务表
        // 2、写数据库消息记录表
        return new ExecuteResult<>();
    }
}
