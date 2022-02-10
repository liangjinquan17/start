package com.hzys.siqcollectlogsserver.service;

import com.alibaba.fastjson.JSONObject;
import com.hzys.siqcollectlogsserver.entity.po.UserLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class ConsumerLogsService {

    private final String QUEUE_NAME = "collect.logs.queue";

    @Autowired
    private UserLogService userLogService;

    @RabbitListener(queues = QUEUE_NAME)
    public void consumerCreateSiqUser(String msg){
        try {
            UserLog userLog = converUserLog(msg);
            userLogService.save(userLog);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private UserLog converUserLog(String msg){
        UserLog userLog = new UserLog();
        JSONObject obj = JSONObject.parseObject(msg);
        userLog.setUserId(obj.getString("userId"));
        userLog.setUserName(obj.getString("userName"));
        userLog.setIpAddress(obj.getString("ipAddress"));
        userLog.setHostAddress(obj.getString("hostAddress"));
        userLog.setUrl(obj.getString("url"));
        userLog.setApplicationName(obj.getString("applicationName"));
        userLog.setRequestPage(obj.getString("requestPage"));
        userLog.setContent(obj.getString("content"));
        userLog.setRequestParam(obj.getString("requestParam"));
        userLog.setResponseParam(obj.getString("responseParam"));
        userLog.setHappenedTime(obj.getDate("happenedTime"));
        userLog.setCreateTime(new Date());
        return userLog;
    }
}
