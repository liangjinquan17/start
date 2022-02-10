package com.hzys.siqcollectlogsclient;

import com.hzys.collectlogs.aop.LogAop;
import com.hzys.collectlogsbean.service.ConsumerLogService;
import com.hzys.collectlogsbean.service.FindUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan(value = {"com.hzys.siqcollectlogsclient"})
@Configuration
public class AutoConfigrution {

    @Bean
    LogAop generatorLogAop(FindUserService findUserService, ConsumerLogService consumerLogService){
        return new LogAop(findUserService, consumerLogService);
    }
}
