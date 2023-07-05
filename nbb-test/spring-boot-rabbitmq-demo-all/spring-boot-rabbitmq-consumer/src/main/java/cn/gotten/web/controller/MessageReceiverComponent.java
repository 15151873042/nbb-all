package cn.gotten.web.controller;

import cn.gotten.common.core.enums.Enums;
import cn.gotten.common.core.util.JacksonUtil;
import cn.gotten.common.core.vo.ExecuteResult;
import cn.gotten.common.core.vo.JsonResult;
import cn.gotten.redis.service.GottenRedisClient;
import cn.gotten.service.service.LogService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author: 胡鹏
 * @date: 2021/3/9 14:36
 * @description: 消费队列中消息
 */
@Component
public class MessageReceiverComponent {

    private static Logger logger = LoggerFactory.getLogger(MessageReceiverComponent.class);
    @Autowired
    private GottenRedisClient redisClient;

    @Autowired
    private LogService logService;

    // 设置消费的队列名称
    @RabbitListener(queues = {"queue.log.all"})
    public void consumerLogAll(Message message, Channel channel) throws Exception {
        byte[] messageBody = message.getBody();
        Map<String, Object> messageObject = JacksonUtil.toObject(messageBody, new TypeReference<Map<String, Object>>() {
        });
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        Thread.sleep(100);
        // 幂等消费，获取redis锁
        Boolean lockFlag = redisClient.tryLock((String) messageObject.get("id"), "test", 10000L);
        if(false == lockFlag) {
            // 拒签消息
            channel.basicReject(deliveryTag, false);
            return;
        }

        // 用业务层处理业务
        ExecuteResult<Void> serviceResult;
        try {
            serviceResult = logService.addSave();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            // 消费消息出错，删除锁
            redisClient.unLock((String) messageObject.get("id"), "test");
            // 拒签消息
            channel.basicReject(deliveryTag, false);
            return;
        }
        if (!Enums.SysCode.SUCCESS.getCode().equals(serviceResult.getCode())) {
            // 消费消息出错，删除锁
            redisClient.unLock((String) messageObject.get("id"), "test");
            // 拒签消息
            channel.basicReject(deliveryTag, false);
            return;
        }
        // 签收消息
        channel.basicAck(deliveryTag, false);
    }


    // 设置消费的队列名称
    @RabbitListener(queues = {"queue.log.dead"})
    public void consumerLogError(Message message, Channel channel) throws Exception {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();

        logger.info("messageProperties:-------{}", message.toString());
        // 签收消息
        channel.basicAck(deliveryTag, false);
    }

}
