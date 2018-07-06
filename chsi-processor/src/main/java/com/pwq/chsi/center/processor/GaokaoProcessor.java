package com.pwq.chsi.center.processor;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.util.Cookie;
import com.gargoylesoftware.htmlunit.util.NameValuePair;
import com.pwq.chsi.center.model.Speciality;
import com.pwq.chsi.center.parser.GaokaoParser;
import com.pwq.spider.chsi.common.CacheContainer;
import com.pwq.spider.chsi.common.Constants;
import com.pwq.spider.chsi.common.ProcessorCode;
import com.pwq.spider.chsi.common.http.Result;
import com.pwq.spider.chsi.common.utils.FormatUtils;
import com.pwq.spider.chsi.common.utils.RegexUtils;
import com.pwq.spider.chsi.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author：WenqiangPu
 * @Description 高考网采集类
 * @Date：Created in 16:23 2018/4/13
 * @Modified By：
 */
@Component
@Slf4j
public class GaokaoProcessor extends AbstractProcessor {

    @Autowired
    private GaokaoParser gaokaoParser;

    @Override
    public Result doCrawler(WebClient webClient) {
        Result result = new Result();
        //页面缓存
        CacheContainer cacheContainer = new CacheContainer();
        List<Page> detailList = new ArrayList<>();
        List<String> ccList = new ArrayList<>();
        ccList.add("1050");
        ccList.add("1060");
        for(String cc:ccList){
            for(Map.Entry<String,String> entryMl:getMl (webClient,cc).entrySet()){
                for(Map.Entry<String,String> entryXk:getXk (webClient,entryMl.getKey()).entrySet()){
                    for(Map.Entry<String,String> entrySpeciality:getSpecialityIdByXk (webClient,entryXk.getKey()).entrySet()){
                        Speciality speciality = new Speciality();
                        speciality.setCc(cc);
                        speciality.setMl(entryMl.getKey());
                        speciality.setXk(entryXk.getKey());
                        speciality.setSpecialityId(entrySpeciality.getKey());
                        Page page = specialityDetail(webClient,cacheContainer,speciality);
                        if(null != page){
                            detailList.add(page);
                        }
                    }
                }
            }
        }
        cacheContainer.putPages(ProcessorCode.SPECIALITY_DETAIL.getCode(),detailList);
        return result;
    }





    /**
     * @Author: WenqiangPu
     * @Description:  获取学历层次下 门类类别
     * @param webClient
     * @return:
     * @Date: 11:22 2018/4/23
     */
    private Map<String,String> getMl(WebClient webClient,String xlId){
        Map<String,String> map = new HashMap<>();
        HashMap<String, String> header = new HashMap<>();
        List<NameValuePair> reqParam = new ArrayList<>();
        String url = "http://gaokao.chsi.com.cn/sch/zyk/category/sub.do";
        String logFlag = String.format("请求门类类别链接");
        reqParam.add(new NameValuePair("method","subCategory"));
        reqParam.add(new NameValuePair("sel","ml"));
        reqParam.add(new NameValuePair("id",xlId));
        header.put("Referer","http://gaokao.chsi.com.cn/");
        Page resultPage = getPage(webClient, url, HttpMethod.POST, StandardCharsets.UTF_8,reqParam,header,"", Constants.MAX_RETRY_TIIMES,logFlag);
        if(null!=resultPage){
            List<List<String>> mlList = RegexUtils.matchesMutiValue("<option value=\"(.*?)\" >(.*?)</option>",resultPage.getWebResponse().getContentAsString());
            map = FormatUtils.list2map(mlList);
            log.info("获取 [] 下 门类类别成功",xlId);
        }else{
            log.info("获取 [] 下 门类类别失败",xlId);
        }
        return map;
    }


    /**
     * @Author: WenqiangPu
     * @Description: 获取门类下 学科类别
     * @param webClient
     * @param mlId
     * @return:
     * @Date: 13:53 2018/4/23
     */
    private Map<String,String> getXk(WebClient webClient,String mlId){
        Map<String,String> map = new HashMap<>();
        HashMap<String, String> header = new HashMap<>();
        List<NameValuePair> reqParam = new ArrayList<>();
        String url = "http://gaokao.chsi.com.cn/sch/zyk/category/sub.do";
        String logFlag = String.format("请求门类类别链接");
        reqParam.add(new NameValuePair("method","subCategory"));
        reqParam.add(new NameValuePair("sel","xk"));
        reqParam.add(new NameValuePair("id",mlId));
        header.put("Referer","http://gaokao.chsi.com.cn/");
        Page resultPage = getPage(webClient, url, HttpMethod.POST, StandardCharsets.UTF_8,reqParam,header,"", Constants.MAX_RETRY_TIIMES,logFlag);
        if(null!=resultPage){
            List<List<String>> mlList = RegexUtils.matchesMutiValue("<option value=\"(.*?)\" >(.*?)</option>",resultPage.getWebResponse().getContentAsString());
            map = FormatUtils.list2map(mlList);
            log.info("获取 [] 下 学科类别成功",mlId);
        }else{
            log.info("获取 [] 下 学科类别失败",mlId);
        }
        return map;
    }



    /**
     * @Author: WenqiangPu
     * @Description: 通过学科id获取所有专业Id
     * @param webClient
     * @param xkId
     * @return:
     * @Date: 13:58 2018/4/23
     */
    private Map<String,String> getSpecialityIdByXk(WebClient webClient,String xkId){
        Map<String,String> map = new HashMap<>();
        HashMap<String, String> header = new HashMap<>();
        List<NameValuePair> reqParam = new ArrayList<>();
        String url = "http://gaokao.chsi.com.cn/zyk/zybk/specialityesByCategoryZybk.action";
        String logFlag = String.format("请求门类类别链接");
        reqParam.add(new NameValuePair("key",xkId));
        header.put("Referer","http://gaokao.chsi.com.cn/");
        Page resultPage = getPage(webClient, url, HttpMethod.POST, StandardCharsets.UTF_8,reqParam,header,"", Constants.MAX_RETRY_TIIMES,logFlag);
        if(null!=resultPage){
            List<List<String>> mlList = RegexUtils.matchesMutiValue("<optionvalue=\"(.*?)\">(.*?)</option>",resultPage.getWebResponse().getContentAsString().replaceAll("\\s+",""));
            map = FormatUtils.list2map(mlList);
            log.info("获取 [] 下 专业类别成功",xkId);
        }else{
            log.info("获取 [] 下 专业类别失败",xkId);
        }
        return map;
    }

    private Page specialityDetail(WebClient webClient,CacheContainer cacheContainer,Speciality speciality){
        HashMap<String, String> header = new HashMap<>();
        List<NameValuePair> reqParam = new ArrayList<>();
        List<Page> pageList = new ArrayList<>();
        String url = "http://gaokao.chsi.com.cn/zyk/zybk/specialityDetail.action";
        String logFlag = String.format("请求专业详细链接");
        reqParam.add(new NameValuePair("isExecuteQuery","true"));
        reqParam.add(new NameValuePair("cc",speciality.getCc()));
        reqParam.add(new NameValuePair("ml",speciality.getMl()));
        reqParam.add(new NameValuePair("xk",speciality.getXk()));
        reqParam.add(new NameValuePair("specialityId",speciality.getSpecialityId()));
        header.put("Referer","http://gaokao.chsi.com.cn/");
        Page resultPage = getPage(webClient, url, HttpMethod.POST, StandardCharsets.UTF_8,reqParam,header,"", Constants.MAX_RETRY_TIIMES,logFlag);
        if(null!=resultPage&& StringUtils.contains(resultPage.getWebResponse().getContentAsString(),"基本信息")){
            log.info("[{}] 层次 [{}] 门类 [{}] 学科 [{}] 专业 详细info成功",speciality.getCc(),speciality.getMl(),speciality.getXk(),speciality.getSpecialityId());
        }else{
            log.info("[{}] 层次 [{}] 门类 [{}] 学科 [{}] 专业 详细info失败",speciality.getCc(),speciality.getMl(),speciality.getXk(),speciality.getSpecialityId());
            resultPage = null;
        }
        return resultPage;

    }


    @Override
    protected Logger getLogger() {
        return log;
    }












}
