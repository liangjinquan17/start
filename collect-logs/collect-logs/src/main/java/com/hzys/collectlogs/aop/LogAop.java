package com.hzys.collectlogs.aop;

import com.alibaba.fastjson.JSONObject;
import com.hzys.collectlogsbean.entity.dto.UserLogDTO;
import com.hzys.collectlogsbean.service.ConsumerLogService;
import com.hzys.collectlogsbean.service.FindUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;

@Component
@Aspect
public class LogAop {

    @Value("${spring.application.name}")
    private String APPLICATION_NAME;

    private FindUserService findUserService;
    private ConsumerLogService consumerLogService;

    public LogAop(FindUserService findUserService, ConsumerLogService consumerLogService) {
        this.findUserService = findUserService;
        this.consumerLogService = consumerLogService;
    }

    @Pointcut("@annotation(io.swagger.annotations.ApiOperation)")
    public void controllerLog() {
    }

    /**
     * Advice(通知/增强):
     * 使用@Around环绕通知
     *
     * @param joinPoint 连接点：代理过程中可以被拦截的方法
     *                  对应Controller中的全部方法
     */
    @Around("controllerLog()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        UserLogDTO userLogParam = new UserLogDTO();
        if(null != findUserService){
            userLogParam.setUserId(findUserService.getUserId(request));
            userLogParam.setUserName(findUserService.getUserName(request));
        }

        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;

        String requestURI = request.getRequestURI();
        //url
        userLogParam.setUrl(requestURI);

        // 获取作用在类上注解 模块名称
        Api api = AnnotationUtils.findAnnotation(joinPoint.getTarget().getClass(), Api.class);
        if (api != null) {
            userLogParam.setRequestPage(api.value());//请求页面名
        }
        // 获取作用在方法上注解  如果方法上没有这个注解或ApiOperation标签的tags没有Log的话就不需要日志操作入库
        ApiOperation apiOperation =  AnnotationUtils.findAnnotation(((MethodSignature) signature).getMethod(), ApiOperation.class);
        if (hasAddLog(apiOperation)) {
            userLogParam.setContent(apiOperation.value());//操作
        }else {
            return joinPoint.proceed(joinPoint.getArgs());
        }

        //处理入参
        Object[] paramValues = joinPoint.getArgs();
        String[] paramNames = methodSignature.getParameterNames();
        StringBuilder requestParam = new StringBuilder();
        int paramLength = null == paramNames ? 0 : paramNames.length;
        if (paramLength == 0) {
            requestParam.append("{} ");
        } else {
            requestParam.append("{");
            for (int i = 0; i < paramLength - 1; i++) {
                //如果参数是http请求或者文件流则改参数不转json保存
                if (paramValues[i] instanceof HttpServletResponse
                        || paramValues[i] instanceof MultipartFile
                        || paramValues[i] instanceof HttpServletRequest) {
                    continue;
                }
                if (paramValues[i] instanceof Collection) {
                    Collection parm= (Collection) paramValues[i];
                    if (null != parm && parm.size() > 0) {
                        Object[] objects = parm.toArray();
                        if (objects[0] instanceof HttpServletResponse
                                || objects[0] instanceof MultipartFile
                                || objects[0] instanceof HttpServletRequest) {
                            continue;
                        }
                    }
                }
                requestParam.append(paramNames[i]).append(":").append(JSONObject.toJSONString(paramValues[i])).append(",");
            }
            requestParam.append(paramNames[paramLength - 1]).append(":").append(JSONObject.toJSONString(paramValues[paramLength - 1])).append("}");
        }

        userLogParam.setIpAddress(this.getIpAddress(request));//客户端请求IP
        userLogParam.setHostAddress(this.getLocalIP());//服务器地址


        userLogParam.setRequestParam(String.valueOf(requestParam));
        //执行方法
        Object proceed = joinPoint.proceed(joinPoint.getArgs());
        //返回值插入
        if (!(proceed instanceof ResponseEntity)) {//如果是minio返回的ResponseEntity流则不转json
            userLogParam.setResponseParam(JSONObject.toJSONString(proceed));
        }

        if(null != consumerLogService)
            userLogParam.setApplicationName(APPLICATION_NAME);
            consumerLogService.accept(userLogParam);
        return proceed;
    }

    private boolean hasAddLog(ApiOperation apiOperation){
        return apiOperation != null && null != apiOperation.tags() && apiOperation.tags().length > 0 && Arrays.stream(apiOperation.tags()).anyMatch(tag -> tag.toLowerCase().equals("addlog"));
    }

    /**
     * 获取用户真实IP地址，不使用request.getRemoteAddr();的原因是有可能用户使用了代理软件方式避免真实IP地址,
     * <p>
     * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值，究竟哪个才是真正的用户端的真实IP呢？
     * 答案是取X-Forwarded-For中第一个非unknown的有效IP字符串。
     * <p>
     * 如：X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130,
     * 192.168.1.100
     * <p>
     * 用户真实IP为： 192.168.1.110
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        String unknown = "unknown";
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if (ip.indexOf(",") != -1) {
                ip = ip.split(",")[0];
            }
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
//        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
//            ip = request.getRemoteAddr();
//        }
        return ip;
    }


    /**
     * 获取主机ip
     *
     * @return
     */
    public static String getLocalIP() {
        String ip = "";
        try {
            Enumeration<?> e1 = (Enumeration<?>) NetworkInterface
                    .getNetworkInterfaces();
            while (e1.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) e1.nextElement();
                if (!ni.getName().equals("eth0")) {
                    continue;
                } else {
                    Enumeration<?> e2 = ni.getInetAddresses();
                    while (e2.hasMoreElements()) {
                        InetAddress ia = (InetAddress) e2.nextElement();
                        if (ia instanceof Inet6Address)
                            continue;
                        ip = ia.getHostAddress();
                    }
                    break;
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return ip;
    }

}
