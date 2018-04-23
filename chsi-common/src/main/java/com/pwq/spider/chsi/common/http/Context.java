package com.pwq.spider.chsi.common.http;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Yuyangjun
 * @desc
 * @date 2018/3/21 下午2:55
 */
@Getter
@Setter
public class Context {

    /**
     * 映射id
     */
    private String mappingId;
    /**
     * 唯一taskId
     */
    private String taskId;
    //任务状态
    private String taskStatus;
    //任务子状态 用于判断从什么地方开始执行代码,由爬虫传给网关,再回传即可
    private String taskSubStatus;
    //业务类型
    private String taskType;
    //业务子类类型
    private String taskSubType;

    /**
     * 账户类型
     */
    private String accountType;
    
    //用户名
    private String userName;
    //密码
    private String password;
    //身份证号码
    private String idCard;
    //身份证姓名
    private String idName;

    //用户识别ID(用户身份证或其他识别ID)
    private String userId;
    //商户id
    private String merchantId;

    //地区(省份)
    private String areaCode;
    //附加信息
    private String attachment;
    //通知地址
    private String notifyUrl;
    //验证码值
    private String userInput;

    //备用参数1
    private String param1;
    //备用参数2
    private String param2;
    //备用参数3
    private String param3;
    //备用参数4
    private String param4;
    //备用参数5
    private String param5;
    //备用参数6
    private String param6;
    //备用参数7
    private String param7;

    @Override
    public String toString() {
        return "Context{" +
                "mappingId='" + mappingId + '\'' +
                ", taskId='" + taskId + '\'' +
                ", taskStatus='" + taskStatus + '\'' +
                '}';
    }
}
