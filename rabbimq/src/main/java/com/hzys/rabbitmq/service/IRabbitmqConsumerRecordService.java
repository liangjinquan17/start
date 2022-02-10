package com.hzys.rabbitmq.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hzys.rabbitmq.entity.po.RabbitmqConsumerRecord;

/**
 * <p>
 * rabbitmq消费记录表 服务类
 * </p>
 *
 * @author liangjinquan
 * @since 2022-02-07
 */
public interface IRabbitmqConsumerRecordService extends IService<RabbitmqConsumerRecord> {

    boolean addRecord(String queueName, String messageId, String originParam);

    boolean addRecord(String queueName, String messageId, String originParam, String result);

    boolean updateResult(String messageId, String result);
}
