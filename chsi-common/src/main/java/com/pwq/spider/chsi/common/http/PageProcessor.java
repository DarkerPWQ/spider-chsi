package com.pwq.spider.chsi.common.http;

import com.gargoylesoftware.htmlunit.WebClient;

/**
 * @author Yuyangjun
 * @desc 页面处理器
 * @date 2017/11/30 下午2:12
 */
public interface PageProcessor {

    /**
     * @desc: 页面爬取逻辑
     * @param: context
     * @author: YuYangjun
     * @date: 2017/11/30 下午2:13
     * @return Result
     */
    Result process(WebClient webClient);
}
