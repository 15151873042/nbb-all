package cn.gotten.web.controller;

import cn.gotten.common.core.enums.Enums;
import cn.gotten.common.core.vo.ExecuteResult;
import cn.gotten.common.core.vo.JsonResult;
import cn.gotten.service.service.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.UUID;

/**
 * @author: 胡鹏
 * @date: 2021/3/10 11:14
 * @description:
 */
@RestController
public class LogController {

    private static Logger logger = LoggerFactory.getLogger(LogController.class);

    @Autowired
    private LogService logService;

    @Autowired
    private RabbitTemplate rabbitTemplate;



    @RequestMapping("/add/save")
    public JsonResult addSave() {
        // 模拟pojo对象
        String id = UUID.randomUUID().toString().replace("-", "");
        HashMap<String, Object> map = new HashMap<>();
        map.put("id",id);
        map.put("name","hp");
        map.put("age",30);
        
        // 1、数据库业务新增
        ExecuteResult<Void> serviceResult = logService.addSave();
        if (!Enums.SysCode.SUCCESS.equalsCode(serviceResult.getCode())) {
            return new JsonResult(serviceResult.getRtnEnum());
        }

        // 2、发送消息到MQ
        try {
            for(int i=0; i<10000; i++) {
                id = UUID.randomUUID().toString().replace("-", "");
                map.put("id",id);
                // 发送消息的时候带上CorrelationData，confirm回调的时候也会带上CorrelationData
                CorrelationData correlationData = new CorrelationData(id);
                rabbitTemplate.convertAndSend("exchange.log", "debug", map, correlationData);
            }
        } catch (Exception e){
            logger.error("====id为"+ id +"的消息发送到MQ出错====", e);
        } finally {
            return new JsonResult();
        }
    }

    @GetMapping("/test")
    public JsonResult test() {
        CorrelationData correlationData = new CorrelationData("1");
        rabbitTemplate.convertAndSend("exchange.log", "1", "str", correlationData);
        return new JsonResult();
    }
}
