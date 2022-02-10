package com.hzys.rabbitmq.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzys.rabbitmq.dao.RabbitMessageConfirmRecordMapper;
import com.hzys.rabbitmq.entity.po.RabbitMessageConfirmRecord;
import com.hzys.rabbitmq.service.IRabbitMessageConfirmRecordService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * rabbit发送信息记录表 服务实现类
 * </p>
 *
 * @author liangjinquan
 * @since 2022-01-25
 */
@Service
public class RabbitMessageConfirmRecordServiceImpl extends ServiceImpl<RabbitMessageConfirmRecordMapper, RabbitMessageConfirmRecord> implements IRabbitMessageConfirmRecordService {

    @Override
    public boolean incrRetry(String messageId) {
        return baseMapper.incrRetry(messageId);
    }
}
