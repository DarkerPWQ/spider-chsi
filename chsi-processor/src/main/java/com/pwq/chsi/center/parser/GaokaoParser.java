package com.pwq.chsi.center.parser;

import com.gargoylesoftware.htmlunit.Page;
import com.pwq.spider.chsi.common.CacheContainer;
import com.pwq.spider.chsi.common.ProcessorCode;
import com.pwq.spider.chsi.common.http.Result;
import com.pwq.spider.chsi.common.utils.RegexUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author：WenqiangPu
 * @Description
 * @Date：Created in 10:55 2018/4/23
 * @Modified By：
 */

@Component
@Slf4j
public class GaokaoParser {

    private Result parseSpecialityDetail(CacheContainer cacheContainer){
        Result result = new Result();
        List<Page> pages = cacheContainer.getPages(ProcessorCode.SPECIALITY_DETAIL.getCode());
        if(null != pages){
            for(Page page:pages){
                String pageString = page.getWebResponse().getContentAsString().replaceAll("\\s+","");
                String specialtyName = RegexUtils.matchValue("专业名称：</span>(.*?)<",pageString);//专业名称
                String specialtyCode = RegexUtils.matchValue("专业代码：</span>(.*?)<",pageString);//专业代码
                String ml = RegexUtils.matchValue("门类：</span>(.*?)<",pageString);//门类
                String xk = RegexUtils.matchValue("学科：</span>(.*?)<",pageString);//学科
                String educationLevel = RegexUtils.matchValue("学历层次：</span>(.*?)<",pageString);//学历层次

            }
        }




    }
}
