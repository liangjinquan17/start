package com.hzys.siqcollectlogsserver.entity.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户日志
 * </p>
 *
 * @author liangjinquan
 * @since 2022-01-19
 */
@Data
@TableName("user_log")
@ApiModel(value="UserLog对象", description="用户日志")
public class UserLog extends Model<UserLog> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "用户id")
    @TableField("user_id")
    private String userId;

    @ApiModelProperty(value = "用户名")
    @TableField("user_name")
    private String userName;

    @ApiModelProperty(value = "用户IP地址")
    @TableField("ip_address")
    private String ipAddress;

    @ApiModelProperty(value = "服务器地址")
    @TableField("host_address")
    private String hostAddress;

    @ApiModelProperty(value = "url")
    @TableField("url")
    private String url;

    @ApiModelProperty(value = "服务名")
    @TableField("application_name")
    private String applicationName;

    @ApiModelProperty(value = "用户请求页面名称")
    @TableField("request_page")
    private String requestPage;

    @ApiModelProperty(value = "用户操作类型 包括增删改,导入导出,登录登出")
    @TableField("content")
    private String content;

    @ApiModelProperty(value = "用户请求参数")
    @TableField("request_param")
    private String requestParam;

    @ApiModelProperty(value = "用户请求页面返回参数")
    @TableField("response_param")
    private String responseParam;

    @ApiModelProperty(value = "操作时间")
    @TableField("happened_time")
    private Date happenedTime;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private Date createTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
