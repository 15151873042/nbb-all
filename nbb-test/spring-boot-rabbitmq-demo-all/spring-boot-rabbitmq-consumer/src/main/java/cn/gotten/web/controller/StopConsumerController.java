package cn.gotten.web.controller;

import cn.gotten.common.core.vo.JsonResult;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * @author: 胡鹏
 * @date: 2021/3/10 16:44
 * @description:
 */
@RestController
public class StopConsumerController {

    @Autowired
    private RabbitListenerEndpointRegistry registry;

    @RequestMapping("/stop")
    public JsonResult stop() {
        Collection<MessageListenerContainer> listenerContainers = registry.getListenerContainers();
        for (MessageListenerContainer container : listenerContainers) {
            container.stop();
        }
        return new JsonResult();
    }


}
