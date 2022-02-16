package com.hzys.siqcollectlogsclient.service;

import com.alibaba.fastjson.JSONObject;
import com.hzys.collectlogsbean.service.FindUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;

@Slf4j
@Service
public class FindUserServiceImpl implements FindUserService {

    public static final String X_CLIENT_TOKEN_USER = "x-client-token-user";

    public String getUserId(HttpServletRequest request) {
        JSONObject userDTO = getSiqUserDTO(request);
        if(null != userDTO && null != userDTO.getJSONObject("user") && userDTO.getJSONObject("user").containsKey("id"))
            return userDTO.getJSONObject("user").getString("id");
        return null;
    }

    public String getUserName(HttpServletRequest request) {
        JSONObject userDTO = getSiqUserDTO(request);
        if(null != userDTO && null != userDTO.getJSONObject("user") && userDTO.getJSONObject("user").containsKey("username"))
            return userDTO.getJSONObject("user").getString("username");
        return null;
    }

    private JSONObject getSiqUserDTO(HttpServletRequest request){
        try {
            String userJson = request.getHeader(X_CLIENT_TOKEN_USER);
            userJson = URLDecoder.decode(userJson, "UTF-8");
            return JSONObject.parseObject(userJson);
        } catch (Exception e) {
            log.error("init userInfo error", e);
        }
        return null;
    }
}
