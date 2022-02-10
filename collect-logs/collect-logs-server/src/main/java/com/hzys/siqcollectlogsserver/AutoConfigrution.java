package com.hzys.siqcollectlogsserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan(value = {"com.hzys.siqcollectlogsserver"})
@MapperScan("com.hzys.siqcollectlogsserver.dao")
@Configuration
public class AutoConfigrution {

}
