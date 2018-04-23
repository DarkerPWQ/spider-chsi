package com.pwq.spider.chsi.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yuyangjun
 * @desc 页面工具类
 * @date 2018/3/21 下午2:32
 */
@Slf4j
public class PageUtils {

    /**
     * @desc:
     * @param: page
     * @author: YuYangjun
     * @date: 2017/11/30 下午5:44
     */
    public static String getPageInfo(Page page){
        if(null != page){
            return page.getWebResponse().getContentAsString();
        }else{
            return "";
        }
    }

    /**
     * @desc: 获取页面Json
     * @param: page
     * @author: YuYangjun
     * @date: 2017/12/6 下午3:10
     */
    public static JSONObject getJSONObject(Page page) {
        System.out.println();
        JSONObject jsonObject = null;
        try {
            if (null != page) {
                jsonObject = JSONObject.parseObject(getPageInfo(page));
            }
        } catch (Exception e) {
            return null;
        }
        return jsonObject;
    }

    public static String getValueById(Page page, String id){
        String result="";
        try{
            DomElement domElement=((HtmlPage)page).getElementById(id);
            if(domElement!=null){
                result=domElement.getAttribute("value");
            }
        }catch (Exception e){
            log.info("->[{}]没有解析到值", id);
        }
        return result;
    }

    public static String getValueByName(Page page, String name){
        String result="";
        try{
            DomElement domElement=((HtmlPage)page).getElementByName(name);
            if(domElement!=null){
                result=domElement.getAttribute("value");
            }
        }catch (Exception e){
            log.info("->[{}]没有解析到值", name);
        }
        return result;
    }

    public static String getValueByXpath(Page page, String xpath){
        String result="";
        try{
            HtmlElement htmlElement=((HtmlPage)page).getFirstByXPath(xpath);
            if(htmlElement!=null){
                result=htmlElement.asText();
            }
        }catch (Exception e){
            log.info("->[{}]没有解析到值", xpath);
        }
        return result;
    }

    public static String getValueByXpath(HtmlElement element, String xpath){
        String result="";
        try{
            if(null != element){
                HtmlElement ele = element.getFirstByXPath(xpath);
                result = ele.asText();
            }
        }catch (Exception e){
            log.info("->[{}]没有解析到值", xpath);
        }
        return result;
    }

    public static List<String> getValuesByXpath(HtmlElement ele, String xpath){
        List<String> result = new ArrayList();
        List<HtmlElement> elements = ele.getByXPath(xpath);
        if(CollectionUtils.isNotEmpty(elements)){
            for(HtmlElement element : elements){
                String value = element.asText();
                result.add(value) ;
            }
        }
        return result;
    }

    public static HtmlElement getElementById(Page page, String id) {
        return (HtmlElement)((HtmlPage)page).getElementById(id);
    }

    public static HtmlElement getElementByName(Page page, String name) {
        return (HtmlElement)((HtmlPage)page).getElementByName(name);
    }

    public static HtmlElement getElementByXpath(Page page, String xpath){
        try{
            HtmlElement htmlElement = ((HtmlPage)page).getFirstByXPath(xpath);
            return htmlElement;
        }catch (Exception e){
            log.info("->[{}]没有解析到值", xpath);
        }
        return null;
    }

    public static HtmlElement getElementByXpath(HtmlElement element, String xpath){
        try{
            HtmlElement htmlElement = ((HtmlElement)element).getFirstByXPath(xpath);
            return htmlElement;
        }catch (Exception e){
            log.info("->[{}]没有解析到值", xpath);
        }
        return null;
    }

    public static List<HtmlElement> getElementsByXpath(Page page, String xpath){
        try{
            return ((HtmlPage)page).getByXPath(xpath);
        }catch (Exception e){
            log.info("->[{}]没有解析到值", xpath);
        }
        return null;
    }

    public static List<HtmlElement> getElementsByXpath(HtmlElement element, String xpath){
        try{
            return element.getByXPath(xpath);
        }catch (Exception e){
            log.info("->[{}]没有解析到值", xpath);
        }
        return null;
    }
}