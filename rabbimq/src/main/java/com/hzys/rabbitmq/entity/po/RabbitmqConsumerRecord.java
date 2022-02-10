package com.hzys.rabbitmq.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * rabbitmq消费记录表
 * </p>
 *
 * @author liangjinquan
 * @since 2022-02-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("rabbitmq_consumer_record")
@ApiModel(value="RabbitmqConsumerRecord对象", description="rabbitmq消费记录表")
public class RabbitmqConsumerRecord extends Model<RabbitmqConsumerRecord> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "队列名称")
    @TableField("queue_name")
    private String queueName;

    @ApiModelProperty(value = "消息id")
    @TableField("message_id")
    private String messageId;

    @ApiModelProperty(value = "源参数")
    @TableField("origin_param")
    private String originParam;

    @ApiModelProperty(value = "结果")
    @TableField("result")
    private String result;

    @ApiModelProperty(value = "创建时间")
    @TableField("created_time")
    private Date createdTime;

    @ApiModelProperty(value = "修改时间")
    @TableField("updated_time")
    private Date updatedTime;

    @ApiModelProperty(value = "创建人")
    @TableField("created_by")
    private String createdBy;

    @ApiModelProperty(value = "修改人")
    @TableField("updated_by")
    private String updatedBy;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
