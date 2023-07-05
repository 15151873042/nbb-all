package cn.gotten;

import cn.gotten.common.core.vo.JsonResult;
import hp.boot.web.config.annotation.EnableWebMvcCommonConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @author: 胡鹏
 * @date: 2021/3/8 17:04
 * @description:
 */
@SpringBootApplication
@EnableWebMvcCommonConfig
@RestController
public class ConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class);
    }


    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RequestMapping("/send")
    public JsonResult test() {
        return new JsonResult();
    }
}
