package com.hzys.siqcollectlogsserver.service.impl;

import com.hzys.siqcollectlogsserver.entity.po.UserLog;
import com.hzys.siqcollectlogsserver.dao.UserLogMapper;
import com.hzys.siqcollectlogsserver.service.UserLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户日志 服务实现类
 * </p>
 *
 * @author liangjinquan
 * @since 2022-01-19
 */
@Service
public class UserLogServiceImpl extends ServiceImpl<UserLogMapper, UserLog> implements UserLogService {

}
