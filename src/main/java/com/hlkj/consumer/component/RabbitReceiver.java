package com.hlkj.consumer.component;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.messaging.Message;

@Component
public class RabbitReceiver {

    /**
     * 监听
     * @param message
     * @param channel
     * @throws Exception
     */
    @RabbitListener(bindings = @QueueBinding(
        value = @Queue(value = "queue-1", durable = "true"),
        exchange = @Exchange(name = "exchange-1",
                durable = "true",
                type = "topic",
                ignoreDeclarationExceptions = "true"),
        key = "springboot.*")
    )
    @RabbitHandler
    public void onMessage(Message message, Channel channel) throws Exception {
        System.out.println("-----------------------------");
        System.out.println("消费消息：" + message.getPayload().toString());

        //由于配置文件配置了"spring.rabbitmq.listener.simple.acknowledge-mode=manual"手动签收模式
        Long deliveryTag = (Long) message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
        //消费端确认成功消费消息。否则消息再broker中处于unacked
        channel.basicAck(deliveryTag, false);

        //模拟发生异常触发mq消费重试机制
//        int i = 10/0;
    }

}
