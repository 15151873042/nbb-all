import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author: 胡鹏
 * @date: 2021/3/13 13:49
 * @description:
 */
public class ConsumeTest {

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setPort(5672);
        factory.setVirtualHost("/dev");
        factory.setUsername("dev");
        factory.setPassword("dev");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.basicConsume("queue.log.all", false, new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                long deliveryTag = envelope.getDeliveryTag();
                channel.basicAck(deliveryTag, false);
            }
        });

    }
}
