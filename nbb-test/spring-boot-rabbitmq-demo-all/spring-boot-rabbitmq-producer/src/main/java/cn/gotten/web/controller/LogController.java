package cn.gotten.web.controller;

import cn.gotten.common.core.enums.Enums;
import cn.gotten.common.core.vo.ExecuteResult;
import cn.gotten.common.core.vo.JsonResult;
import cn.gotten.service.service.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
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

    private Map<String, Object> newUserInstance() {
        // 模拟pojo对象
        HashMap<String, Object> map = new HashMap<>();
        map.put("id",UUID.randomUUID().toString());
        map.put("name","hp");
        map.put("age",30);
        return map;
    }

    /**
     * 发送普通消息
     */
    @RequestMapping("/send/message/normal")
    public JsonResult sendMessageNormal() {
        Map<String, Object> user = newUserInstance();
        rabbitTemplate.convertAndSend("queue.ttl", user);
        return new JsonResult();
    }

    /**
     * 发送消息，指定消息最大存货时间
     */
    @RequestMapping("/send/message/ttl")
    public JsonResult sendMessageTtl() {
        MessagePostProcessor messagePostProcessor = message -> {
            message.getMessageProperties().setExpiration("10000"); // 设置消息的超时时长为10秒
            return message;
        };

        Map<String, Object> user = newUserInstance();
        rabbitTemplate.convertAndSend("queue.log.all", user, messagePostProcessor);
        return new JsonResult();
    }

    /**
     * 发送消息，指定消息延时到达队列的时间
     */
    @RequestMapping("/send/message/delay")
    public JsonResult sendMessageDelay() {
        MessagePostProcessor messagePostProcessor = message -> {
            message.getMessageProperties().setHeader("x-delay", 20000); // 消息在交换机中停留20秒之后再发送到对应的队列
            return message;
        };

        Map<String, Object> user = newUserInstance();
            rabbitTemplate.convertAndSend("exchange.delay", "debug", user, messagePostProcessor);
        return new JsonResult();
    }

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
            for(int i=0; i<10; i++) {
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
