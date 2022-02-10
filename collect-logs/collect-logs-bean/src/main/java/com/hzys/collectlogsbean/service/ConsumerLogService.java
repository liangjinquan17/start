package com.hzys.collectlogsbean.service;


import com.hzys.collectlogsbean.entity.dto.UserLogDTO;

import java.util.function.Consumer;

public interface ConsumerLogService extends Consumer<UserLogDTO> {
}
