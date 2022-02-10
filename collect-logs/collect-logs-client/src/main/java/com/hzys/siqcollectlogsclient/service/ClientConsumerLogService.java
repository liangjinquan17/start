package com.hzys.siqcollectlogsclient.service;

import com.alibaba.fastjson.JSONObject;
import com.hzys.collectlogsbean.entity.dto.UserLogDTO;
import com.hzys.collectlogsbean.service.ConsumerLogService;
import com.hzys.siqcollectlogsclient.config.CollectLogRabbitConfigProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ClientConsumerLogService implements ConsumerLogService {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private CollectLogRabbitConfigProperties rabbitConfigProperties;

    public ClientConsumerLogService(RabbitTemplate rabbitTemplate, CollectLogRabbitConfigProperties rabbitConfigProperties){
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitConfigProperties = rabbitConfigProperties;
    }

    public void accept(UserLogDTO userLogDTO) {
        try {
            rabbitTemplate.convertAndSend(rabbitConfigProperties.getExchange(), rabbitConfigProperties.getRoutekey(), JSONObject.toJSONString(userLogDTO));
        }catch (Exception e){
            log.debug("发送日志信息失败:{}", e);
        }
    }
}
