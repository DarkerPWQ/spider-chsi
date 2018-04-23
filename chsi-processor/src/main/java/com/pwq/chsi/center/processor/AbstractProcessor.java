package com.pwq.chsi.center.processor;


import com.alibaba.fastjson.JSONObject;
import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.util.NameValuePair;

import com.pwq.spider.chsi.common.CacheContainer;
import com.pwq.spider.chsi.common.error.SpiderException;
import com.pwq.spider.chsi.common.http.Context;
import com.pwq.spider.chsi.common.http.PageProcessor;
import com.pwq.spider.chsi.common.http.Result;
import com.pwq.spider.chsi.common.utils.*;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import java.util.List;
import java.util.Map;

/**
 * @Author: WenqiangPu
 * @Description:
 * @Date: 10:55 2018/4/23
 */

public class AbstractProcessor implements PageProcessor {

    @Value("${enableProxy}")
    private boolean enableProxy;
    @Value("${spiderManager}")
    private String spiderManager;
    @Value("${spiderRoute}")
    private String spiderRoute;
    @Value("${xdlOrderNo}")
    private String xdlOrderNo;
    @Value("${xdlSecret}")
    private String xdlSecret;
    @Value("${savePageFlag}")
    private boolean savePageFlag;


    @Override
    public Result process(WebClient webClient) {
        //创建WebClient
        webClient = initWebClient(webClient);
        try{
            //开始登录

            //开始爬取解析
            Result result = doCrawler(webClient);
            getLogger().info("爬取解析结果[{}]", result);
        }catch (Exception e){
            throw new SpiderException(e,"");
        }finally {
            close(webClient);
        }
        return new Result();
    }



    /**
     * @desc: 暂停
     * @param: seconds
     * @author: YuYangjun
     * @date: 2018/3/21 下午5:40
     */
    protected void sleep(long seconds){
        try{
            Thread.sleep(seconds * 1000);
        }catch (Exception e){
            getLogger().info(">", e);
        }
    }

    protected void savePages(Context context, CacheContainer cacheContainer) {
        if (savePageFlag) {
            try {
                new CacheContainerUtils().writeToLocal(cacheContainer, context);
                getLogger().info("[{}] 已经保存页面,请在[\\data]目录下查看", context.getTaskId());
            } catch (Exception e) {
                getLogger().info("[{}] 保存页面失败.", context.getTaskId(), e);
            }
        } else {
            getLogger().info("[{}] 保存页面开关为[{}]", context.getTaskId(), savePageFlag);
        }
    }

    /**
     * 创建默认的webclient
     * <b>如果需要特殊处理,需要重写些方法</b>
     * @return
     */
    protected WebClient initWebClientNoProxy(WebClient webClient) {
        if (null == webClient) {
            webClient = new WebClient(BrowserVersion.FIREFOX_45);
            webClient.getCookieManager().clearCookies();
            webClient.getCache().clear();
        }
        webClient.setRefreshHandler(new ImmediateRefreshHandler());
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        webClient.getOptions().setRedirectEnabled(true);
        webClient.getOptions().setUseInsecureSSL(true);
        webClient.getCookieManager().setCookiesEnabled(true);
        webClient.getOptions().setActiveXNative(false);
        webClient.getOptions().setAppletEnabled(false);
        webClient.getOptions().setCssEnabled(false);// 禁用css支持
        webClient.getOptions().setThrowExceptionOnScriptError(false);// js运行错误时，是否抛出异常
        webClient.getOptions().setJavaScriptEnabled(false); //禁用JS
        webClient.getOptions().setTimeout(180000);
        return webClient;
    }

    /**
     * 创建默认的webclient
     * <b>如果需要特殊处理,需要重写些方法</b>
     * @return
     */
    private WebClient initWebClient(WebClient webClient) {
        if (null == webClient) {
            webClient = new WebClient(BrowserVersion.FIREFOX_45);
            webClient.getCookieManager().clearCookies();
            webClient.getCache().clear();
        }
        webClient.setRefreshHandler(new ImmediateRefreshHandler());
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        webClient.getOptions().setRedirectEnabled(true);
        webClient.getOptions().setUseInsecureSSL(true);
        webClient.getCookieManager().setCookiesEnabled(true);
        webClient.getOptions().setActiveXNative(false);
        webClient.getOptions().setAppletEnabled(false);
        webClient.getOptions().setCssEnabled(false);// 禁用css支持
        webClient.getOptions().setThrowExceptionOnScriptError(false);// js运行错误时，是否抛出异常
        webClient.getOptions().setJavaScriptEnabled(false); //禁用JS
        webClient.getOptions().setTimeout(180000);
        if (enableProxy) {
            ProxyConfig proxyConfig = new ProxyConfig();
            proxyConfig.setProxyHost("forward.xdaili.cn");
            proxyConfig.setProxyPort(80);
            webClient.getOptions().setProxyConfig(proxyConfig);
        }
        return webClient;
    }

    /**
     * @desc:
     * @param webClient  访问客户端
     * @author: YuYangjun
     * @date: 2017/11/30 下午4:50
     */
    private void close(WebClient webClient){
        if (null != webClient) {
            webClient.close();
        }
    }



    /**
     * 获取页面
     * @param webClient  访问客户端
     * @param url        访问地址
     * @return
     * @description
     * @author YuYangjun
     * @create 2016年8月13日 上午9:48:59
     */
    public Page getPage(WebClient webClient, String url) {
        return getPage(webClient, url, HttpMethod.GET);
    }

    /**
     * 获取页面
     * @param webClient  访问客户端
     * @param url        访问地址
     * @param httpMethod 提交方法
     * @return
     * @description
     * @author YuYangjun
     * @create 2016年8月13日 上午9:48:59
     */
    public Page getPage(WebClient webClient, String url, HttpMethod httpMethod) {
        return getPage(webClient, url, httpMethod, StandardCharsets.UTF_8);
    }

    /**
     * 获取页面
     * @param webClient  访问客户端
     * @param url        访问地址
     * @param httpMethod 提交方法
     * @param charset 处理编码
     * @return
     * @description
     * @author YuYangjun
     * @create 2016年8月13日 上午9:48:59
     */
    public Page getPage(WebClient webClient, String url, HttpMethod httpMethod,
                        Charset charset) {
        return getPage(webClient, url, httpMethod, charset, null);
    }

    /**
     * 获取页面
     * @param webClient  访问客户端
     * @param url        访问地址
     * @param httpMethod 提交方法
     * @param charset 处理编码
     * @param params      访问参数
     * @return
     * @description
     * @author YuYangjun
     * @create 2016年8月13日 上午9:48:59
     */
    public Page getPage(WebClient webClient, String url, HttpMethod httpMethod,
                        Charset charset, List<NameValuePair> params) {
        return getPage(webClient, url, httpMethod, charset, params, null);
    }

    /**
     * 获取页面
     * @param webClient  访问客户端
     * @param url        访问地址
     * @param httpMethod 提交方法
     * @param params      访问参数
     * @return
     * @description
     * @author YuYangjun
     * @create 2016年8月13日 上午9:48:59
     */
    public Page getPage(WebClient webClient, String url, HttpMethod httpMethod,
                        List<NameValuePair> params) {
        return getPage(webClient, url, httpMethod, StandardCharsets.UTF_8, params,null);
    }

    /**
     * 获取页面
     * @param webClient  访问客户端
     * @param url        访问地址
     * @param httpMethod 提交方法
     * @param charset 处理编码
     * @param params      访问参数
     * @param header     访问Header
     * @return
     * @description
     * @author YuYangjun
     * @create 2016年8月13日 上午9:48:59
     */
    public Page getPage(WebClient webClient, String url, HttpMethod httpMethod,
                        Charset charset, List<NameValuePair> params,
                        Map<String, String> header) {
        return getPage(webClient, url, httpMethod, charset, params, header, "");
    }

    /**
     * 获取页面
     * @param webClient  访问客户端
     * @param url        访问地址
     * @param httpMethod 提交方法
     * @param params      访问参数
     * @param header     访问Header
     * @return
     * @description
     * @author YuYangjun
     * @create 2016年8月13日 上午9:48:59
     */
    public Page getPage(WebClient webClient, String url, HttpMethod httpMethod,
                        List<NameValuePair> params,
                        Map<String, String> header) {
        return getPage(webClient, url, httpMethod, StandardCharsets.UTF_8, params, header, "");
    }

    /**
     * 获取页面
     * @param webClient  访问客户端
     * @param url        访问地址
     * @param httpMethod 提交方法
     * @param charset 处理编码
     * @param params      访问参数
     * @param header     访问Header
     * @param bodyStr    Body参数
     * @return
     * @description
     * @author YuYangjun
     * @create 2016年8月13日 上午9:48:59
     */
    public Page getPage(WebClient webClient, String url, HttpMethod httpMethod,
                        Charset charset, List<NameValuePair> params,
                        Map<String, String> header, String bodyStr) {
        return getPage(webClient, url, httpMethod, params, charset, header, bodyStr);
    }

    /**
     * 获取页面
     * @param webClient  访问客户端
     * @param url        访问地址
     * @param httpMethod 提交方法
     * @param params      访问参数
     * @param header     访问Header
     * @param bodyStr    Body参数
     * @return
     * @description
     * @author YuYangjun
     * @create 2016年8月13日 上午9:48:59
     */
    public Page getPage(WebClient webClient, String url, HttpMethod httpMethod,
                        List<NameValuePair> params,
                        Map<String, String> header, String bodyStr) {
        return getPage(webClient, url, httpMethod, params, StandardCharsets.UTF_8, header, bodyStr);
    }

    /**
     * 获取页面
     * <b>有重复尝试机制</b>
     * @param webClient  访问客户端
     * @param url        访问地址
     * @param httpMethod 提交方法
     * @param charset 处理编码
     * @param params      访问参数
     * @param header     访问Header
     * @param retryTimes 重复尝试次数
     * @param logFlag    日志标志
     * @return
     * @description
     * @author YuYangjun
     * @create 2016年8月13日 上午9:48:59
     */
    public Page getPage(WebClient webClient, String url, HttpMethod httpMethod,
                        Charset charset, List<NameValuePair> params,
                        Map<String, String> header, String bodyStr,
                        int retryTimes, String logFlag){
        int rt = 1;
        while (rt <= retryTimes) {
            try {
                getLogger().info(logFlag + ",第[{}]尝试请求...", rt);
                Page page = getPage(webClient, url, httpMethod, params, charset, header, bodyStr);
                if (null != page && StringUtils.isNotEmpty(PageUtils.getPageInfo(page))) {
                    return page;
                } else {
                    getLogger().info(">响应内容为空,正在再次尝试请求");
                    rt++;
                }
            } catch (Exception e) {
                getLogger().info(">获取响应出错了,正在再次尝试请求:", e);
                rt++;
            }
        }
        return null;
    }

    /**
     * 获取页面
     * <b>有重复尝试机制</b>
     * @param webClient  访问客户端
     * @param url        访问地址
     * @param httpMethod 提交方法
     * @param params      访问参数
     * @param header     访问Header
     * @param retryTimes 重复尝试次数
     * @param logFlag    日志标志
     * @return
     * @description
     * @author YuYangjun
     * @create 2016年8月13日 上午9:48:59
     */
    public Page getPage(WebClient webClient, String url, HttpMethod httpMethod,
                        List<NameValuePair> params,
                        Map<String, String> header, String bodyStr,
                        int retryTimes, String logFlag){
        return getPage(webClient, url, httpMethod, StandardCharsets.UTF_8, params,header, bodyStr, retryTimes,logFlag);
    }

    //--------------------------------------------------------
    //以下方法无须关注
    //--------------------------------------------------------
    private Page getPage(WebClient webClient, String url, HttpMethod httpMethod, List<NameValuePair> params,
                         Charset charset, Map<String, String> header, String bodyStr) {
        if (httpMethod == HttpMethod.GET) {
            return doGet(webClient, url, charset, params, bodyStr, header);
        } else {
            return doPost(webClient, url, charset, params, bodyStr, header);
        }
    }

    private Page doGet(WebClient webClient, final String url,
                              Charset charset, List<NameValuePair> params,
                              String body, Map<String, String> headers) {
        try {
            URL reqUrl;
            if (CollectionUtils.isEmpty(params)) {
                reqUrl = new URL(url);
            } else {
                reqUrl = new URL(url + "?" + EntityUtils.toString(params));
            }
            WebRequest webRequest = new WebRequest(reqUrl, HttpMethod.GET);
            if (null == charset) {
                charset = StandardCharsets.UTF_8;
            }
            webRequest.setCharset(charset);
            if (null != headers) {
                for (String key : headers.keySet()) {
                    webRequest.setAdditionalHeader(key, headers.get(key));
                }
            }
            //统一添加讯代理认证
            webRequest.setAdditionalHeader("Proxy-Authorization", genProxyAuth());

            if (StringUtils.isNotBlank(body)) {
                webRequest.setRequestBody(body);
            }
            return webClient.getPage(webRequest);
        } catch (Exception ex) {
            getLogger().error(">访问出错", ex);
        }
        return null;
    }

    private Page doPost(WebClient webClient, final String url,
                               Charset charset, List<NameValuePair> params,
                               String body, Map<String, String> headers) {
        try {
            URL reqRul = new URL(url);
            WebRequest webRequest = new WebRequest(reqRul, HttpMethod.POST);
            webRequest.setAdditionalHeader("Accept-Language", "zh-CN");
            if (null == charset) {
                charset = StandardCharsets.UTF_8;
            }
            webRequest.setCharset(charset);
            if (null != params) {
                webRequest.setRequestParameters(params);
            }
            if (null != headers) {
                for (String key : headers.keySet()) {
                    webRequest.setAdditionalHeader(key, headers.get(key));
                }
            }
            //统一添加讯代理认证
            webRequest.setAdditionalHeader("Proxy-Authorization", genProxyAuth());

            if (StringUtils.isNotBlank(body)) {
                webRequest.setRequestBody(body);
            }
            return webClient.getPage(webRequest);
        } catch (Exception ex) {
            getLogger().error(">访问出错", ex);
        }
        return null;
    }

    private String genProxyAuth() {
        final long timestamp = System.currentTimeMillis()/1000;
        String planText = String.format("orderno=%s,secret=%s,timestamp=%d", xdlOrderNo, xdlSecret, timestamp);
        String sign = Md5Utils.sign(planText).toUpperCase();
        return String.format("sign=%s&orderno=%s&timestamp=%d", sign, xdlOrderNo, timestamp);
    }

    //-------------------------------
    //需要具体类实现-------------
    //-------------------------------

    /**
     * 具体的数据爬取逻辑
     * @param webClient
     * @param context
     * @return
     */
    protected Result doCrawler(WebClient webClient){
        throw new RuntimeException("该方法[数据爬取]必须重写");
    }

    /**
     * @desc: 具体的退出逻辑
     * @param webClient
     * @param context
     * @author: YuYangjun
     * @date: 2018/3/21 下午5:27
     */
    protected Result loginout(WebClient webClient){
        throw new RuntimeException("该方法[退出登录]必须重写");
    }
    /**
     * 获取日志
     * @return
     * @description
     * @author YuYangjun
     * @create 2016年8月8日 下午5:35:32
     */
    protected Logger getLogger() {
        throw new RuntimeException("该方法[获取日志]必须重写");
    }


}
