package com.pwq.chsi.chsimain;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.pwq.spider.chsi.common.Constants;
import com.pwq.spider.chsi.common.error.SpiderException;
import com.pwq.spider.chsi.common.http.PageProcessor;
import com.pwq.spider.chsi.common.http.Result;
import com.pwq.spider.chsi.common.utils.IdentityUtils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.slf4j.MDC;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;


/**
 * @Author: WenqiangPu
 * @Description:
 * @return:
 * @Date: 10:44 2018/4/23
 */
@Slf4j
@Component
public class TaskScanner implements Runnable, ApplicationContextAware {

    private ScheduledExecutorService executor = new ScheduledThreadPoolExecutor(128,
            new BasicThreadFactory.Builder().namingPattern("isp-pool-%d").daemon(true).build());

    private ApplicationContext applicationContext;


    @Value("${taskType}")
    private String taskType;

    @Autowired
    private Map<String, String> processorMap;

    //运行标志
    protected volatile boolean flag = true;

    @Override
    public void run() {
        log.info("开始接收[{}]任务", Constants.SPIDER_NAME);
        String taskId = "";
        String taskStatus = "";
        WebClient webClient = new WebClient(BrowserVersion.INTERNET_EXPLORER);
        executeTask(webClient);

    }

    /**
     * @desc: 执行任务
     * @param: context
     * @author: YuYangjun
     * @date: 2017/11/30 上午11:20
     */
    private void executeTask(WebClient webClient) {
        executor.submit(() -> {
            MDC.put(Constants.SESSION_ID, IdentityUtils.getUUID());
            try {
                //获取执行processor
                String type = "gaokao";
                PageProcessor processor = (PageProcessor) applicationContext.getBean(processorMap.get(type));
                if (null == processor) {
                    log.error("[{}] 暂无[{}]处理器:",type);
                    return;
                }
                //3.执行爬取任务
                Result result = processor.process(webClient);
                log.info("任务结束[{}]", result);
            } catch (Exception ex) {
                throw new SpiderException(ex, IdentityUtils.getMarkId());
            } finally {
                MDC.remove(Constants.SESSION_ID);
            }
        });
    }

    public void stop() {
        this.flag = false;
        executor.shutdown();
    }
    
    private void sleep(long seconds) {
        try {
            Thread.sleep(seconds);
        } catch (Exception e) {
            throw new SpiderException(e, IdentityUtils.getMarkId());
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}