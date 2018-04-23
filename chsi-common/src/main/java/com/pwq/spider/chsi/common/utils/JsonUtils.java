package com.pwq.spider.chsi.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Yuyangjun
 * @desc
 * @date 2018/3/21 下午4:46
 */
public class JsonUtils {

    /**
     * json转化为bean
     * @param json
     * @param beanClass
     * @param <T>
     * @return
     */
    public static <T> T json2Bean(String json,Class<T> beanClass){
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            return objectMapper.readValue(json,beanClass);
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }

    /**
     * bean转化为json
     * @param bean
     * @return
     * @throws Exception
     */
    public static String beanToJson(Object bean) {
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(bean);
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }


    /**
     * @desc: 获取Json
     * @param: page
     * @author: YuYangjun
     * @date: 2017/12/6 下午3:10
     */
    public static JSONObject getJSONObject(String json) {
        JSONObject jsonObject = null;
        try {
            if (StringUtils.isNotEmpty(json)) {
                jsonObject = JSONObject.parseObject(json);
            }
        } catch (Exception e) {
            return null;
        }
        return jsonObject;
    }

    /**
     * to jsonNode
     * @param json
     * @return
     */
    public static JsonNode toJsonNode(String  json) {
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readValue(json, JsonNode.class);
            return rootNode;
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }
}
