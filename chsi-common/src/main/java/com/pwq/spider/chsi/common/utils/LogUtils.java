package com.pwq.spider.chsi.common.utils;

import org.slf4j.MDC;

/**
 * @author Yuyangjun
 * @desc 日志工具类
 * @date 2017/11/30 上午10:29
 */
public class LogUtils {

    private static final String LOG_ID = "sessionId";

    /**
     * 更新日志跟踪id
     * @param logID 日志跟踪id(为空时创建新日志跟踪id)
     */
    public static void updateLogId(String logID){
        if(StringUtils.isBlank(logID)){
            MDC.put(LOG_ID,createLogId());
        }else {
            MDC.put(LOG_ID, logID);
        }
    }

    /**
     * 更新日志跟踪id
     */
    public static void updateLogId(){
        MDC.put(LOG_ID,createLogId());
    }

    /**
     * 删除日志跟踪id
     */
    public static void removeLogId(){
        MDC.remove(LOG_ID);
    }

    /**
     * 创建日志跟踪id
     * @return 日志跟踪id
     */
    private static String createLogId(){
        return IdentityUtils.getUUID();
    }

    /**
     * 获取日志跟踪id
     * @return 日志跟踪id
     */
    public static String getLogId(){
        Object object =  MDC.get(LOG_ID);
        if(object==null){
            return "-";
        }else {
            return object.toString();
        }
    }

}
