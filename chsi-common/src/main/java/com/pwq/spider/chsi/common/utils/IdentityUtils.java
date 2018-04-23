package com.pwq.spider.chsi.common.utils;

import java.util.Random;
import java.util.UUID;

/**
 * @author Yuyangjun
 * @desc
 * @date 2018/3/21 上午10:54
 */
public class IdentityUtils {

    private static Random random = new Random();

    /**
     * 生成标识ID
     * @return
     */
    public static String getMarkId() {
        return DateUtils.getCurrentTime2() + random.nextInt(100);
    }

    /**
     * 生成uuid
     * @return
     */
    public static String getUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replace("-","");
    }

    /**
     * 生成指定长度的UUID
     * @param uuidLength
     * @return String
     * @since 2016-09-6
     */
    public static final String getUUID(int uuidLength){
        //所有候选组成验证码的字符，可以用中文
        final String[] uuidArrary={"0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
                "a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z",
                "A","B","C","D","E","F","G","H","I","J", "K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"
        };
        String uuid = getUUID();
        if(uuidLength < uuid.length()){
            return uuid.substring(0, uuidLength);
        }else if(uuidLength == uuid.length()){
            return uuid;
        }else{
            Random random = new Random();
            //此处是生成验证码的核心了，利用一定范围内的随机数做为验证码数组的下标，循环组成我们需要长度的验证码，做为页面输入验证、邮件、短信验证码验证都行
            StringBuffer sb = new StringBuffer();
            for(int i=0;i<uuidLength - uuid.length(); i++){
                sb.append(uuidArrary[random.nextInt(uuidArrary.length)]);
            }
            sb.append(uuid);
            return sb.toString();
        }
    }

    /**
     * 生成指定长度的UUID
     * @param uuidLength
     * @return String
     * @since 2016-09-6
     */
    public static final String getUUIDUp(int uuidLength){
        return getUUID(uuidLength).toUpperCase();
    }

    /**
     * @desc: 获取随机double
     * @author: YuYangjun
     * @date: 2017/12/6 下午4:48
     */
    public static Double getRanDouble(){
        return random.nextDouble();
    }
}
