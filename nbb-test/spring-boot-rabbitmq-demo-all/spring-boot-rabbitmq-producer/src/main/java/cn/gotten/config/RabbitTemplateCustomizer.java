package cn.gotten.config;

import cn.gotten.config.callback.ProducerCustomCallback;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

/**
 * @author: 胡鹏
 * @date: 2021/3/10 10:33
 * @description:
 */
public class RabbitTemplateCustomizer implements InitializingBean {

    @Autowired
    private RabbitTemplate rabbitTemplate;


    /**
     * 配置confirm和return回调
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        ProducerCustomCallback customCallback = new ProducerCustomCallback();
        // confirm回调，一定会回调，回调中告知是否抵达交换机
        rabbitTemplate.setConfirmCallback(customCallback);
        // return回调，如果抵达交换机未能路由到队列才回调，个人感觉不需要return回调
//        rabbitTemplate.setReturnCallback(customCallback);
    }
}
