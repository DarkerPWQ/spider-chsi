package com.pwq.chsi.chsimain;

import com.xinyan.spider.pay.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * @author Yuyangjun
 * @desc 任务服务
 * @date 2018/3/21 上午10:31
 */

@Slf4j
@Service
public class TaskService {

    private ScheduledExecutorService executor = new ScheduledThreadPoolExecutor(1,
            new BasicThreadFactory.Builder().namingPattern("tax-pool-%d").daemon(true).build());


    @Autowired
    private TaskScanner spilderServer;

    @PostConstruct
    public void start(){
        log.info("开始启动[{}]爬虫任务", Constants.SPIDER_NAME);
        try{
            executor.execute(spilderServer);
        }catch (Throwable ex){
            log.info("启动[{}]爬虫任务失败 case", Constants.SPIDER_NAME, ex);
        }
        log.info("[{}]爬虫任务已启动.", Constants.SPIDER_NAME);
    }

    @PreDestroy
    public void stop(){
        log.info("开始停止[{}]爬虫任务", Constants.SPIDER_NAME);
        try{
            spilderServer.stop();
            executor.shutdown();
        }catch (Throwable ex){
            log.info("停止[{}]爬虫任务失败 case", Constants.SPIDER_NAME, ex);
        }
        log.info("[{}]爬虫任务已停止.", Constants.SPIDER_NAME);
    }
}