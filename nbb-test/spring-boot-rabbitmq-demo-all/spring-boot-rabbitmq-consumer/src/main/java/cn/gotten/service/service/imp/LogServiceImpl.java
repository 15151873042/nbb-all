package cn.gotten.service.service.imp;

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
        throw new RuntimeException();
//        return new ExecuteResult<>();
    }
}
