package com.pwq.chsi.center.parser;

import com.gargoylesoftware.htmlunit.Page;
import com.pwq.chsi.center.model.GaokaoInfo;
import com.pwq.spider.chsi.common.CacheContainer;
import com.pwq.spider.chsi.common.ProcessorCode;
import com.pwq.spider.chsi.common.http.Result;
import com.pwq.spider.chsi.common.utils.PageUtils;
import com.pwq.spider.chsi.common.utils.RegexUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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
        List<GaokaoInfo> detailList = new ArrayList<>();
        List<Page> pages = cacheContainer.getPages(ProcessorCode.SPECIALITY_DETAIL.getCode());
        if(null != pages){
            for(Page page:pages){
                String pageString = page.getWebResponse().getContentAsString().replaceAll("\\s+","");
                GaokaoInfo gaokaoInfo = new GaokaoInfo();
                String specialtyName = RegexUtils.matchValue("专业名称：</span>(.*?)<",pageString);//专业名称
                String specialtyCode = RegexUtils.matchValue("专业代码：</span>(.*?)<",pageString);//专业代码
                String ml = RegexUtils.matchValue("门类：</span>(.*?)<",pageString);//门类
                String xk = RegexUtils.matchValue("学科：</span>(.*?)<",pageString);//学科
                String educationLevel = RegexUtils.matchValue("学历层次：</span>(.*?)<",pageString);//学历层次
                String conErate = RegexUtils.matchValue("2015\\((.*?)\\)",pageString);//今年就业
                String lastErate = RegexUtils.matchValue("2016\\((.*?)\\)",pageString);//去年就业
                String blastErate = RegexUtils.matchValue("2017\\((.*?)\\)",pageString);//去年就业
                String graduateScale = RegexUtils.matchValue("data-id=\"(.*?)\"",pageString);;//毕业生规模
                String wkRate = PageUtils.getValueById(page,"hid_wk");
                String lkRate = PageUtils.getValueById(page,"hid_lk");;//理科比例
                String mRate = PageUtils.getValueById(page,"hid_boy");//男比例
                String wRate = PageUtils.getValueById(page,"hid_girl");//女比例
                detailList.add(gaokaoInfo);


            }
        }
        result.setSuccess();
        return result;
    }
}
