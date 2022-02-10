package com.hzys.rabbitmq.sendMessage;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.hzys.rabbitmq.entity.po.RabbitMessageConfirmRecord;
import com.hzys.rabbitmq.service.IRabbitMessageConfirmRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.LinkedBlockingQueue;

@Slf4j
@Service
public class SendRabbitMessage {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private IRabbitMessageConfirmRecordService rabbitMessageConfirmRecordService;
    private LinkedBlockingQueue<RabbitMessageConfirmRecord> queue = new LinkedBlockingQueue();

    /**
     * 数据发送到mq的同时把数据持久化
     * @param exchange
     * @param routingKey
     * @param object
     */
    public void convertAndSend(String exchange, String routingKey, final Object object)  {
        convertAndSend(exchange, routingKey, object, true);
    }

    /**
     * 数据发送到mq的同时把数据持久化
     * @param exchange
     * @param routingKey
     * @param object
     * @param durable 是否持久化
     */
    public void convertAndSend(String exchange, String routingKey, final Object object, boolean durable)  {
        RabbitMessageConfirmRecord record = createRabbitMessageConfirmRecord(exchange, routingKey, object);
        rabbitMessageConfirmRecordService.save(record);
        rabbitTemplate.convertAndSend(record.getExchange(), record.getRouteKey(), record.getMessage(), new CorrelationData(record.getMessageId()));
    }

    private RabbitMessageConfirmRecord createRabbitMessageConfirmRecord(String exchange, String routingKey, final Object object){
        String messageJSON = JSONObject.toJSONString(object);
        String messageId = IdWorker.getIdStr();
        RabbitMessageConfirmRecord record = new RabbitMessageConfirmRecord();
        record.setExchange(exchange);
        record.setRouteKey(routingKey);
        record.setMessage(messageJSON);
        record.setMessageId(messageId);
        record.setRetry(0);
        record.setCreatedTime(new Date());
        record.setUpdatedTime(new Date());
        return record;
    }

}
