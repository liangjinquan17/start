package com.hzys.rabbitmq.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzys.rabbitmq.entity.po.RabbitmqConsumerRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * rabbitmq消费记录表 Mapper 接口
 * </p>
 *
 * @author liangjinquan
 * @since 2022-02-07
 */
@Repository
public interface RabbitmqConsumerRecordMapper extends BaseMapper<RabbitmqConsumerRecord> {

    boolean updateResult(@Param("messageId") String messageId, @Param("result") String result);
}
