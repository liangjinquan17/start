package com.hzys.collectlogsbean.service;

import javax.servlet.http.HttpServletRequest;

public interface FindUserService {

    String getUserId(HttpServletRequest request);

    String getUserName(HttpServletRequest request);
}
