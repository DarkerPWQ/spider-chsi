package com.pwq.spider.chsi.common;

import lombok.Getter;

/**
 * @Author：WenqiangPu
 * @Description 数据抓取编码
 * @Date：Created in 11:50 2018/4/14
 * @Modified By：
 */

@Getter
public enum ProcessorCode {

    //专业详情specialityDetail
    SPECIALITY_DETAIL("speciality_detail");
    private String code;

    ProcessorCode(String code){
        this.code=code;
    }
}
