package com.pwq.chsi.center.parser;

import com.gargoylesoftware.htmlunit.Page;
import com.pwq.spider.chsi.common.CacheContainer;
import com.pwq.spider.chsi.common.ProcessorCode;
import com.pwq.spider.chsi.common.http.Result;
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
                String pageString = page.getWebResponse().getContentAsString();

            }
        }




    }
}
