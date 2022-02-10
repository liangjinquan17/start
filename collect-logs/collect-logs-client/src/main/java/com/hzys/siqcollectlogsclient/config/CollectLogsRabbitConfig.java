package com.hzys.siqcollectlogsclient.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CollectLogsRabbitConfig {

    @Autowired
    private CollectLogRabbitConfigProperties siqCollectLogRabbitConfigProperties;

    @Bean("collectLogsQueue")
    Queue queue() {
        log.info("queue name:{}", siqCollectLogRabbitConfigProperties.getQueue());
        return new Queue(siqCollectLogRabbitConfigProperties.getQueue(), false);
    }

    @Bean("collectLogsExchange")
    DirectExchange exchange() {
        log.info("exchange:{}", siqCollectLogRabbitConfigProperties.getExchange());
        return new DirectExchange(siqCollectLogRabbitConfigProperties.getExchange());
    }

    @Bean("collectLogsBinding")
    Binding binding(@Qualifier("collectLogsQueue") Queue queue, @Qualifier("collectLogsExchange") DirectExchange exchange) {
        log.info("binding {} to {} with {}", queue, exchange, siqCollectLogRabbitConfigProperties.getRoutekey());
        return BindingBuilder.bind(queue).to(exchange).with(siqCollectLogRabbitConfigProperties.getRoutekey());
    }

}
