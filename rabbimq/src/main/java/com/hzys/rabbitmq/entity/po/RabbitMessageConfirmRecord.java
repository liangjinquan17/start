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
 * rabbit发送信息记录表
 * </p>
 *
 * @author liangjinquan
 * @since 2022-01-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("rabbit_message_confirm_record")
@ApiModel(value="RabbitMessageConfirmRecord对象", description="rabbit发送信息记录表")
public class RabbitMessageConfirmRecord extends Model<RabbitMessageConfirmRecord> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "交换机名词")
    @TableField("exchange")
    private String exchange;

    @ApiModelProperty(value = "路由key")
    @TableField("route_key")
    private String routeKey;

    @ApiModelProperty(value = "消息内容")
    @TableField("message")
    private String message;

    @ApiModelProperty(value = "消息唯一id")
    @TableField("message_id")
    private String messageId;

    @ApiModelProperty(value = "重试次数")
    @TableField("retry")
    private Integer retry;

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
