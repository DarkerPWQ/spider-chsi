package com.pwq.spider.chsi.common;

/**
 * @Author：WenqiangPu
 * @Description
 * @Date：Created in 14:50 2018/4/13
 * @Modified By：
 */
public class Constants {

    /**
     * 项目平台名称
     */
    public final static String PROJECT_NAME = "支付宝平台";
    /**
     * 爬虫名称
     */
    public final static String SPIDER_NAME = "支付宝爬虫";

    /**
     * 账户类型-用户名
     */
    public final static String ACCOUNT_TYPE_USERNAME = "USERNAME";

    /**
     * 账户类型-身份证
     */
    public final static String ACCOUNT_TYPE_IDCARD = "IDCARD";

    /**
     * 日志跟踪标识
     */
    public final static String SESSION_ID = "sessionId";

    /**
     * 重试次数
     */
    public static final int RETRY_TIMES = 6;

    /**
     * 任务结束标志（无需创建任务）
     */
    public static final String TASK_END_Y = "Y";

    /**
     * 基本信息
     */
    public static final String BASIC_INFO= "BASIC_INFO";
    /**
     * 缴税明细
     */
    public static final String TAX_DETAIL= "TAX_DETAIL";

    /**
     * 彩信缴税明细年数
     */
    public static final int TAX_YEARS= 6;

    /**
     * 请求连接失败重试次数
     */
    public final static int MAX_RETRY_TIIMES = 3;

}
