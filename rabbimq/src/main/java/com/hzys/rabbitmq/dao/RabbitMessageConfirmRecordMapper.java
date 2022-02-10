package com.hzys.rabbitmq.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzys.rabbitmq.entity.po.RabbitMessageConfirmRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * rabbit发送信息记录表 Mapper 接口
 * </p>
 *
 * @author liangjinquan
 * @since 2022-01-25
 */
@Repository
public interface RabbitMessageConfirmRecordMapper extends BaseMapper<RabbitMessageConfirmRecord> {

    /**
     * 重试次数+1
     * @param messageId
     * @return
     */
    boolean incrRetry(@Param("messageId") String messageId);
}
