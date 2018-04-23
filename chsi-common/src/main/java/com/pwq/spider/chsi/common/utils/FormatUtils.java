package com.pwq.spider.chsi.common.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Yuyangjun
 * @desc
 * @date 2018/3/26 上午9:52
 */
public class FormatUtils {

    protected static Logger log = LoggerFactory.getLogger(FormatUtils.class);


    /**
     * 将字符串金额转换成BigDecimal
     * @param money
     * @return 无法转换时返回 0
     */
    public static BigDecimal formatBmoneyFen(String money) {
        BigDecimal factor = new BigDecimal(100);
        return factor.multiply(formatBmoney(money));
    }

    /**
     * 将字符串金额转换成BigDecimal
     * @param money
     * @return 无法转换时返回 0
     */
    public static BigDecimal formatBmoney(String money) {
        try {
            if (StringUtils.isBlank(money)) {
                return new BigDecimal(0);
            }
            BigDecimal factor = new BigDecimal(1);
            money = money.replaceAll(" ", "");
            money = money.replaceAll("-", "");
            money = money.replaceAll(",", "");
            money = money.replaceAll("元/月", "");
            money = money.replaceAll("￥", "");
            money = money.replaceAll("元", "");
            //去掉【万】
            if (money.contains("万")) {
                money = money.replaceAll("万", "");
                factor = factor.multiply(new BigDecimal(10000));
            }

            BigDecimal bigDecimal = new BigDecimal(money);
            return bigDecimal.multiply(factor);
        } catch (Exception e) {
            log.info(">金额[{}]转换出错", money, e);
        }
        return new BigDecimal(0);
    }

    public static  Map<String,String> list2map(List<List<String>> listList){
        Map<String,String> map = new HashMap<>();
        for(List<String> list:listList){
            if(list.size()==2){
                map.put(list.get(0),list.get(1));
            }
        }
        return map;

    }

    public static void main(String[] args) {
        System.out.println(formatBmoneyFen("1.63元"));

    }

}
