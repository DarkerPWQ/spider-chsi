package com.pwq.spider.chsi.common.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Yuyangjun
 * @desc
 * @date 2018/3/21 下午3:02
 */
public class DateUtils {

    public static final String PATTERN = "yyyy.MM.dd";
    public static final String PATTERN1 = "yyyy-MM-dd HH:mm:ss";
    public static final String PATTERN2 = "yyyyMMddHHmmss";
    public static final String PATTERN3 = "yyyy-MM-dd";
    public static final String PATTERN4 = "yyyyMMdd";



    /**
     * 获取当前时间
     * @return yyyy.MM.dd
     */
    public static String getCurrentTime() {
        return getStringDate(PATTERN);
    }
    /**
     * 获取去年时间
     * @return yyyy.MM.dd
     */
    public static String getLastYearTime() {
        Calendar dd = Calendar.getInstance();
        dd.setTime(new Date());
        dd.add(Calendar.YEAR, -1);
        System.out.println(dd.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        return format.format(dd.getTime());
    }


    /**
     * 获取当前时间
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String getCurrentTime1() {
        return getStringDate(PATTERN1);
    }

    /**
     * 获取当前时间
     * @return yyyyMMddHHmmss
     */
    public static String getCurrentTime2() {
        return getStringDate(PATTERN2);
    }

    /**
     * 获取当前时间
     * @return yyyy-MM-dd
     */
    public static String getCurrentTime3(){
        return getStringDate(PATTERN3);
    }

    /**
     * @desc: 获取指定格式时间
     * @param: date
     * @author: YuYangjun
     * @date: 2018/3/23 下午3:27
     * @return yyyy-MM-dd
     */
    public static String getDate3(Date date){
        return getStringDate(PATTERN3, date);
    }

    /**
     * 获取当前时间
     * @return
     */
    private static String getStringDate(String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(new Date());
    }

    /**
     * 获取指定格式时间
     * @return
     */
    private static String getStringDate(String pattern, Date date) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    public static void main(String[] args) {
        Calendar dd = Calendar.getInstance();
        dd.setTime(new Date());
        dd.add(Calendar.YEAR, -1);
        System.out.println(dd.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        System.out.println(format.format(dd.getTime()));
    }
}
