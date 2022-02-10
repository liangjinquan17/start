package com.hzys.collectlogs;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan(value = {"com.hzys.collectlogs.aop"})
@Configuration
public class AutoConfigrution {

}
