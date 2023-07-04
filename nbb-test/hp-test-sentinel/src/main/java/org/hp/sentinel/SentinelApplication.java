package org.hp.sentinel;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@RestController
public class SentinelApplication {

    public static void main(String[] args) {
        SpringApplication.run(SentinelApplication.class);
        initFlowRules();
    }


    // 限流测试接口
    /**
     * blockHandler只捕获限流降级的异常，对应异常处理函数，最后一个参数必须添加一个BlockException
     * fallback 捕获所有异常，对应异常处理函数，最后一个参数可以添加一个Throwable
     * blockHandler和fallback都配置时，blockHandler优先级高
     * @link https://sentinelguard.io/zh-cn/docs/annotation-support.html
     */
    @RequestMapping("/")
    @SentinelResource(value="myUrl1", fallback = "allExceptionHandler", blockHandler = "blockExceptionHandler")
    public String test() {
        if (System.currentTimeMillis() % 2 == 0) {
            throw new RuntimeException("aaa");
        }
        return "ok";
    }

    // Fallback 函数，函数签名与原函数一致
    public String allExceptionHandler() {
        return "出错啦";
    }

    // Fallback 函数，函数签名与原函数一致
    public String blockExceptionHandler(BlockException ex) {
        return "您访问过快了";
    }

    // 初始化限流规则
    private static void initFlowRules(){

        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule = new FlowRule();
        rule.setResource("myUrl1");
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        // 设置qps为1
        rule.setCount(1);
        rules.add(rule);
        FlowRuleManager.loadRules(rules);
    }
}
