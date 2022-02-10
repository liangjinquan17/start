package com.hzys.rabbitmq.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzys.rabbitmq.dao.RabbitmqConsumerRecordMapper;
import com.hzys.rabbitmq.entity.po.RabbitmqConsumerRecord;
import com.hzys.rabbitmq.service.IRabbitmqConsumerRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;


/**
 * <p>
 * rabbitmq消费记录表 服务实现类
 * </p>
 *
 * @author liangjinquan
 * @since 2022-02-07
 */
@Slf4j
@Service
public class RabbitmqConsumerRecordServiceImpl extends ServiceImpl<RabbitmqConsumerRecordMapper, RabbitmqConsumerRecord> implements IRabbitmqConsumerRecordService {

    @Override
    public boolean addRecord(String queueName, String messageId, String originParam) {
        return addRecord(queueName, messageId, originParam, null);
    }

    @Override
    public boolean addRecord(String queueName, String messageId, String originParam, String result) {
        RabbitmqConsumerRecord rabbitmqConsumerRecord = new RabbitmqConsumerRecord();
        rabbitmqConsumerRecord.setQueueName(queueName);
        rabbitmqConsumerRecord.setMessageId(messageId);
        rabbitmqConsumerRecord.setOriginParam(originParam);
        rabbitmqConsumerRecord.setResult(result);
        rabbitmqConsumerRecord.setCreatedTime(new Date());
        rabbitmqConsumerRecord.setUpdatedTime(new Date());
        boolean saveRecord = this.save(rabbitmqConsumerRecord);
        if (!saveRecord) {
            log.error("记录消费银行状态回调信息失败!");
        }
        return saveRecord;
    }

    @Override
    public boolean updateResult(String messageId, String result) {
        return baseMapper.updateResult(messageId, result);
    }
}
