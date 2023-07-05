import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author: 胡鹏
 * @date: 2021/3/13 21:22
 * @description:
 */
public class ProducerTest {

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setPort(5672);
        factory.setVirtualHost("/dev");
        factory.setUsername("dev");
        factory.setPassword("dev");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.confirmSelect();
        channel.basicPublish("exchange.log", "debug", null,"1".getBytes());

        boolean b = channel.waitForConfirms();
        System.out.println(b);
        channel.close();
        connection.close();
    }
}
