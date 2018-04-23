package com.pwq.spider.chsi.common.error;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Yuyangjun
 * @desc
 * @date 2018/3/21 上午11:12
 */
@Slf4j
public class SpiderException extends RuntimeException{

    public SpiderException(Exception ex, String markId){
        log.error(">[{}]发生错误了", markId, ex);
    }
}
