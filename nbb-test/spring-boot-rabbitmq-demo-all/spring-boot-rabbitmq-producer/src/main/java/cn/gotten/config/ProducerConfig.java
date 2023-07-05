package cn.gotten.config;

import cn.gotten.common.core.serializer.jackson.JacksonObjectMapper;
import cn.gotten.config.callback.ProducerCustomCallback;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.HashMap;

@Configuration
public class ProducerConfig {

    // 在配置RabbitAdmin
    // 配置该bean对象的情况下，交换机bean对象、队列bean对象、绑定关系bean对象都会在rabbitMQ服务中自动动创建并绑定(RabbitAutoConfiguration中已配置，这边不配)
    // 如果在mq服务中已存在则不会创建队列、交换机
//    @Bean
//    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
//        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
//        // spring容器启动加载该类
//        rabbitAdmin.setAutoStartup(true);
//        return rabbitAdmin;
//    }

//    /* 声明交换机 */
//    @Bean
//    public DirectExchange directExchange() {
//        HashMap<String, Object> exchangeArguments = new HashMap<>();
//        DirectExchange directExchange = new DirectExchange("exchange.log", true, false, exchangeArguments);
//        return directExchange;
//    }
//
//    /* 声明队列 */
//    @Bean
//    Queue allLogQueue() {
//        // durable: 是否持久化，服务重启队列是否存在，队列中的消息是否存在
//        // autodelete:表示没有程序和队列建立连接 那么就会自动删除队列
//        // exclusive:有且只有一个消费者监听,服务停止的时候删除该队列
//        HashMap<String, Object> queueArguments = new HashMap<>();
//        queueArguments.put("x-max-length", 2);
//        return new Queue("queue.log.all",true, false, false, queueArguments);
//    }
//
//    @Bean
//    Queue errorLogQueue() {
//        // durable: 是否持久化，服务重启队列是否存在，队列中的消息是否存在
//        // autodelete:表示没有程序和队列建立连接 那么就会自动删除队列
//        // exclusive:有且只有一个消费者监听,服务停止的时候删除该队列
//        HashMap<String, Object> queueArguments = new HashMap<>();
//        queueArguments.put("x-max-length", 2);
//        return new Queue("queue.log.all",true, false, false, queueArguments);
//    }
//
//    /* 声明绑定关系 */
//    @Bean
//    Binding binding1() {
//        return BindingBuilder.bind(errorLogQueue()).to(directExchange()).with("error");
//    }
//
//    @Bean
//    Binding binding2() {
//        return BindingBuilder.bind(allLogQueue()).to(directExchange()).with("error");
//    }
//
//    @Bean
//    Binding binding3() {
//        return BindingBuilder.bind(allLogQueue()).to(directExchange()).with("info");
//    }
//
//    @Bean
//    Binding binding4() {
//        return BindingBuilder.bind(allLogQueue()).to(directExchange()).with("debug");
//    }




    @Bean
    public RabbitTemplateCustomizer rabbitTemplateCustomizer() {
        return new RabbitTemplateCustomizer();
    }

    /**
     * 配置json消息转换器，这样发送java Object对象可以直接转换成json字符串，Jackson2JsonMessageConverter内部依赖ObjectMapper，需要导入jackson-annotation包
     * 在{@link RabbitAutoConfiguration.RabbitTemplateConfiguration#rabbitTemplateConfigurer}会传入此消息转换器
     * @return
     */
    @Bean
    Jackson2JsonMessageConverter Jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter(new JacksonObjectMapper());
    }

//    @Bean
//    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
//        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
//        // 配置json消息转换器，这样发送java Object对象可以直接转换成json字符串，Jackson2JsonMessageConverter内部依赖ObjectMapper，需要导入jackson-annotation包
//        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter(new ObjectMapper()));
//        ProducerCustomCallback customCallback = new ProducerCustomCallback();
//        // 消息可达交换机回调对象
//        rabbitTemplate.setConfirmCallback(customCallback);
//        // 开启ReturnCallback的前提
//        rabbitTemplate.setMandatory(true);
//        // 消息可达队列回调对象
//        rabbitTemplate.setReturnCallback(customCallback);
//        rabbitTemplate.setReceiveTimeout(50000);
//        return rabbitTemplate;
//    }
}
