package com.hzys.rabbitmq.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hzys.rabbitmq.entity.po.RabbitMessageConfirmRecord;

/**
 * <p>
 * rabbit发送信息记录表 服务类
 * </p>
 *
 * @author liangjinquan
 * @since 2022-01-25
 */
public interface IRabbitMessageConfirmRecordService extends IService<RabbitMessageConfirmRecord> {

    /**
     * 重试次数+1
     * @param messageId
     * @return
     */
    boolean incrRetry(String messageId);
}
