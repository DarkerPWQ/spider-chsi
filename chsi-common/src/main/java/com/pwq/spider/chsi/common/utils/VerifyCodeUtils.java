package com.pwq.spider.chsi.common.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Yuyangjun
 * @desc
 * @date 2018/3/26 下午4:47
 */
public class VerifyCodeUtils {
    private static Map<Integer, String> cjyMap = new HashMap();
    private static Map<String, Integer> jjtMap = new HashMap();
    static{
        jjtMap.put("1001", 1001);
        jjtMap.put("1004", 1004);
        jjtMap.put("1005", 1005);
        jjtMap.put("1006", 1006);
        jjtMap.put("1007", 1007);
        jjtMap.put("1008", 1008);
        jjtMap.put("1009", 1009);
        jjtMap.put("1010", 1010);
        jjtMap.put("1020", 1020);
        jjtMap.put("2001", 2001);
        jjtMap.put("2002", 2002);
        jjtMap.put("2003", 2003);
        jjtMap.put("2004", 2004);
        jjtMap.put("2005", 2005);
        jjtMap.put("2007", 2007);
        jjtMap.put("3004", 3004);
        jjtMap.put("3005", 3005);
        jjtMap.put("3006", 3006);
        jjtMap.put("3007", 3007);
        jjtMap.put("3008", 3008);
        jjtMap.put("3012", 3010);
        jjtMap.put("4004", 4004);
        jjtMap.put("4005", 4005);
        jjtMap.put("4006", 4006);
        jjtMap.put("4007", 4007);
        jjtMap.put("4008", 4008);
        jjtMap.put("4111", 4010);
        jjtMap.put("5000" ,8001);
        jjtMap.put("6001",6001);

        //-----------------------
        cjyMap.put(8001 ,"5000");
        cjyMap.put(1001 ,"1001");
        cjyMap.put(1002 ,"1002");
        cjyMap.put(1003 ,"1003");
        cjyMap.put(1004 ,"1004");
        cjyMap.put(1005 ,"1005");
        cjyMap.put(1006 ,"1006");
        cjyMap.put(1007 ,"1007");
        cjyMap.put(1008 ,"1008");
        cjyMap.put(1009 ,"1009");
        cjyMap.put(1010 ,"1010");
        cjyMap.put(1011 ,"1020");
        cjyMap.put(1012 ,"1020");
        cjyMap.put(1013 ,"1020");
        cjyMap.put(1014 ,"1020");
        cjyMap.put(1015 ,"1020");
        cjyMap.put(1016 ,"1020");
        cjyMap.put(1017 ,"1020");
        cjyMap.put(1018 ,"1020");
        cjyMap.put(1019 ,"1020");
        cjyMap.put(1020 ,"1020");
        cjyMap.put(2001 ,"2001");
        cjyMap.put(2002 ,"2002");
        cjyMap.put(2003 ,"2003");
        cjyMap.put(2004 ,"2004");
        cjyMap.put(2005 ,"2005");
        cjyMap.put(2006 ,"2006");
        cjyMap.put(2007 ,"2007");
        cjyMap.put(2008 ,"2007");
        cjyMap.put(2009 ,"2007");
        cjyMap.put(2010 ,"2007");
        cjyMap.put(3001 ,"3004");
        cjyMap.put(3002 ,"3004");
        cjyMap.put(3003 ,"3004");
        cjyMap.put(3004 ,"3004");
        cjyMap.put(3005 ,"3005");
        cjyMap.put(3006 ,"3006");
        cjyMap.put(3007 ,"3007");
        cjyMap.put(3008 ,"3008");
        cjyMap.put(3009 ,"3012");
        cjyMap.put(3010 ,"3012");
        cjyMap.put(4001 ,"4004");
        cjyMap.put(4002 ,"4004");
        cjyMap.put(4003 ,"4004");
        cjyMap.put(4004 ,"4004");
        cjyMap.put(4005 ,"4005");
        cjyMap.put(4006 ,"4006");
        cjyMap.put(4007 ,"4007");
        cjyMap.put(4008 ,"4008");
        cjyMap.put(4009 ,"4011");
        cjyMap.put(4010 ,"4111");
        cjyMap.put(5001 ,"5000");
        cjyMap.put(5002 ,"5000");
        cjyMap.put(5003 ,"5000");
        cjyMap.put(5004 ,"5000");
        cjyMap.put(5005 ,"5000");
        cjyMap.put(5006 ,"5000");
        cjyMap.put(5007 ,"5000");
        cjyMap.put(5008 ,"5000");
        cjyMap.put(5009 ,"5000");
        cjyMap.put(5010 ,"5000");
        cjyMap.put(5011 ,"5000");
        cjyMap.put(5012 ,"5000");
        cjyMap.put(5013 ,"5000");
        cjyMap.put(5014 ,"5000");
        cjyMap.put(5015 ,"5000");
        cjyMap.put(5016 ,"5000");
        cjyMap.put(5017 ,"5000");
        cjyMap.put(5018 ,"5000");
        cjyMap.put(5019 ,"5000");
        cjyMap.put(5020 ,"5000");

        cjyMap.put(6001,"6001");
        cjyMap.put(6002,"6003");
    }

    public static String getChaoJiYingCode(int code){
        String codeType = cjyMap.get(code);
        return StringUtils.isEmpty(codeType) ? "5000" : codeType;
    }
    public static int getJjtCode(String code){
        Integer codeType = jjtMap.get(code);
        return null == codeType ? 8001 : codeType;
    }
}
