package com.pwq.spider.chsi.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gargoylesoftware.htmlunit.HttpMethod;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;


/**
 * @author Yuyangjun
 * @desc
 * @date 2018/3/21 上午10:54
 */
@Slf4j
public class TaskUtils {

    /**
     * @desc: 回调任务
     * @param: url
     * @param: body
     * @author: YuYangjun
     * @date: 2018/3/23 上午10:48
     */
    public static JSONObject callBackTask(final String url, Map<String,Object> body){
        return execute(HttpMethod.POST, url, null, body);
    }

    public static JSONObject getTask(final String url, final String taskType){
        //从爬虫路由获取任务
        Map<String, Object> map = new HashMap();
        map.put("task_type", taskType);
        Map<String, Object> params = new HashMap();
        params.put("data_content", JSON.toJSONString(map));
        return execute(HttpMethod.POST, url, null, params);
    }

    private static HttpPost buildPost(String url, Map<String,String> header, Map<String,Object> body){
        HttpPost httpPost = new HttpPost(url);
        buildHeader(header,httpPost);
        StringEntity entity = buildBody(body);
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        httpPost.setEntity(entity);
        return httpPost;
    }

    private static HttpGet buildGet(String url, Map<String,String> header, Map<String,Object> body){
        HttpGet httpGet = new HttpGet(url);
        buildHeader(header,httpGet);
        return httpGet;
    }

    /**
     * 组装http头信息
     * @param header 头信息参数
     * @param httpRequest http请求（GET or POST）
     */
    private static void buildHeader(Map<String,String> header, HttpRequestBase httpRequest){
        if(header==null||header.isEmpty()){
            return;
        }
        for (String key:header.keySet()){
            httpRequest.setHeader(key,header.get(key));
        }
    }

    private static String buildGetUrl(String url,Map<String,Object> body) {
        if(body==null||body.isEmpty()){
            return url;
        }
        String params = "";
        try {
            for (String key:body.keySet()) {
                if(params.length()>0) {
                    params=params+"&";
                }
                params = key + "=" + URLEncoder.encode((String)body.get(key), "utf-8");
            }
        }catch (UnsupportedEncodingException e){

            log.info("url参数转换失败");

            return url;
        }
        return url+"?"+params;
    }

    /**
     * 获取body体信息串
     * @param body body参数
     * @return
     */
    private static StringEntity buildBody(Map<String,Object> body){
        if(body==null||body.isEmpty()){
            return new StringEntity("","utf-8");
        }
        JSONObject requestJson = new JSONObject();
        for (String key:body.keySet()){
            requestJson.put(key,body.get(key));
        }
        return new StringEntity(requestJson.toString(),"utf-8");
    }

    private static String execute(HttpRequestBase httpRequest){
        CloseableHttpClient client = HttpClientBuilder.create().build();
        try {
            HttpResponse response = client.execute(httpRequest);
            if(HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                return EntityUtils.toString(response.getEntity());
            }else {
                log.error("http响应code：{}",response.getStatusLine().getStatusCode());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 执行http请求
     * @param httpMethod GET OR POST
     * @param url http路径
     * @param header http头参数
     * @param body body or param 参数
     * @return 响应JSON内容
     */
    private static JSONObject execute(HttpMethod httpMethod, String url, Map<String,String> header, Map<String,Object> body){
        if(HttpMethod.POST == httpMethod){
            HttpPost httpPost = buildPost(url, header, body);
            String result = execute(httpPost);
            if(null == result){
                return null;
            }else if(StringUtils.isEmpty(result)){
                return JSON.parseObject("{'taskType':''}");
            }else {
                return JSON.parseObject(result);
            }
        }else {
            HttpGet httpGet = buildGet(url, header,body);
            String result = execute(httpGet);
            if(null == result){
                return null;
            }else if(StringUtils.isEmpty(result)){
                return JSON.parseObject("{'taskType':''}");
            }else {
                return JSON.parseObject(result);
            }
        }
    }

}
