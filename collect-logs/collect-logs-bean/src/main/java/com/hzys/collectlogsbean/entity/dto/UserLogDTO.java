package com.hzys.collectlogsbean.entity.dto;

import java.util.Date;

/**
 *@description 用户操作页面请求参数传输类
 */
public class UserLogDTO implements java.io.Serializable{

    /**用户id*/
    private String userId;
    /**用户名*/
    private String userName;
    /**用户IP地址*/
    private String ipAddress;
    /**服务器地址*/
    private String hostAddress;
    /**url*/
    private String url;
    /**用户请求页面名称*/
    private String requestPage;
    /**用户操作类型 包括增删改,导入导出,登录登出*/
    private String content;
    /**用户请求参数*/
    private String requestParam;
    /**用户请求页面返回参数*/
    private String responseParam;
    /**操作时间*/
    private Date happenedTime;
    /** 服务名 **/
    private String applicationName;
    /** 接口数据类型**/
    private String type;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getHostAddress() {
        return hostAddress;
    }

    public void setHostAddress(String hostAddress) {
        this.hostAddress = hostAddress;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRequestPage() {
        return requestPage;
    }

    public void setRequestPage(String requestPage) {
        this.requestPage = requestPage;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRequestParam() {
        return requestParam;
    }

    public void setRequestParam(String requestParam) {
        this.requestParam = requestParam;
    }

    public String getResponseParam() {
        return responseParam;
    }

    public void setResponseParam(String responseParam) {
        this.responseParam = responseParam;
    }

    public Date getHappenedTime() {
        return happenedTime;
    }

    public void setHappenedTime(Date happenedTime) {
        this.happenedTime = happenedTime;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
