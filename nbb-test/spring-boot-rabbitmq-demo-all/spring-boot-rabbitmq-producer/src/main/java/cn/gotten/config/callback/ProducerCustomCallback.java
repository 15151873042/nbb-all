package cn.gotten.config.callback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ReturnCallback;

public class ProducerCustomCallback implements ConfirmCallback, ReturnCallback {

	private static final Logger logger = LoggerFactory.getLogger(ProducerCustomCallback.class);

	@Override
	public void confirm(CorrelationData correlationData, boolean ack, String cause) {
		// 只要消费者发送消息且配置confirm回调，此方法都会被回调
		// ack=true表明消息发送到了交换机，反则则表明消息未抵达交换机
		logger.info("ConfirmCallback:     "+"相关数据："+correlationData);
		logger.info("ConfirmCallback:     "+"确认情况："+ack);
		logger.info("ConfirmCallback:     "+"原因："+cause);
		String id = correlationData.getId();

		if (ack) {
			logger.info("====id为{}的数据发送到mq成功====", id);
			// TODO 更新消息表发送状态为成功
		} else {
			logger.error("====id为{}的数据发送到mq失败====", id);
		}
	}

	@Override
	public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
		// 目前发现消息发送到队列，但是不能路由到队列时才会回调此方法
		logger.info("ReturnCallback:     "+"消息："+message);
		logger.info("ReturnCallback:     "+"回应码："+replyCode);
		logger.info("ReturnCallback:     "+"回应信息："+replyText);
		logger.info("ReturnCallback:     "+"交换机："+exchange);
		logger.info("ReturnCallback:     "+"路由键："+routingKey);
	}


}
