package com.hzys.rabbitmq;

import com.hzys.rabbitmq.service.IRabbitMessageConfirmRecordService;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan(value = {"com.hzys.rabbitmq"})
@MapperScan("com.hzys.rabbitmq.dao")
@Configuration
@Slf4j
public class AutoConfigrution {

    @Autowired
    private IRabbitMessageConfirmRecordService rabbitMessageConfirmRecordService;

    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());

        rabbitTemplate.setReturnCallback((Message message, int replyCode, String replyText, String exchange, String routingKey) ->
                log.info("消息：{} 发送失败, 应答码：{} 原因：{} 交换机: {}  路由键: {}", message, replyCode, replyText, exchange, routingKey));
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                log.info("confirm:{}", correlationData);
                log.info("消息发送到exchange,id: {} ack:{} cause:{}", correlationData, ack, cause);
                if (ack && null != correlationData) {
                    rabbitMessageConfirmRecordService.incrRetry(correlationData.getId());
                }
            }});


        return rabbitTemplate;
    }
}
