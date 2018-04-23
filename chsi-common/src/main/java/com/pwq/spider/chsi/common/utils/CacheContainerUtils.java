package com.pwq.spider.chsi.common.utils;

import com.gargoylesoftware.htmlunit.Page;
import com.pwq.spider.chsi.common.CacheContainer;
import com.pwq.spider.chsi.common.http.Context;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.Map;

/**
 * @author Yuyangjun on 2017/11/24 下午5:41
 */
@Slf4j
public class CacheContainerUtils {

    /**
     * 保存采集内容到本地文件
     * @param cc
     * @param context
     * @return
     */
    public void writeToLocal(CacheContainer cc, Context context) {
        try {

            String dir = "\\data\\" + DateUtils.getCurrentTime3().replace("-", "\\") + "\\" + context.getUserName() + "_" + context.getTaskId();
            dir = opeFileSeparatorStr(dir);

            //Map
            Map<String, Page> map = cc.getPageMap();
            for (String key : map.keySet()) {
                WriteHTMLFile(map.get(key).getWebResponse().getContentAsString(), key, dir);
            }

            //ListMap
            Map<String, List<Page>> mapList = cc.getPagesMap();
            for (String key : mapList.keySet()) {
                List<Page> pageList = mapList.get(key);
                int ii = 0;
                for (Page page : pageList) {
                    ii++;
                    WriteHTMLFile(page.getWebResponse().getContentAsString(), key + "." + ii, dir);
                }
            }

            //String
            Map<String, String> mapString = cc.getStringMap();
            for (String key : mapString.keySet()) {
                WriteHTMLFile(mapString.get(key), key, dir);
            }

            //ListString
            Map<String, List<String>> mapStringList = cc.getStringsMap();
            for (String key : mapStringList.keySet()) {
                List<String> stringList = mapStringList.get(key);
                int ii = 0;
                for (String page : stringList) {
                    ii++;
                    WriteHTMLFile(page, key + "." + ii, dir);
                }
            }
        } catch (Exception e) {
            log.info("", e);
        }
    }


    /**
     * 文件写入,文件若存在则覆盖
     * @param html      记录的信息
     * @param filename  文件名
     * @param directory 文件夹路径
     * @return
     */
    private void WriteHTMLFile(String html, String filename, String directory) {
        try {
            String path = directory + "\\" + filename + ".html";
            path = opeFileSeparatorStr(path);

            File file = new File(path);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }

            file.createNewFile();

            // 向文件写入对象写入信息
            FileWriter fileWriter = new FileWriter(file);
            // 写文件
            fileWriter.write(html);
            // 关闭
            fileWriter.close();

        } catch (Exception e) {

        }
    }

    /**
     * 处理由于系统不同导致文件分隔符差异
     * @param patternStr
     * @return
     */
    private static String opeFileSeparatorStr(String patternStr){
        if(StringUtils.isNotBlank(patternStr)){
            String fileSeparator = System.getProperties().getProperty("file.separator");
            String osName = System.getProperties().getProperty("os.name");
            if(!StringUtils.contains(osName, "Window")){
                patternStr = patternStr.replace("\\", fileSeparator);
            }
        }
        return  patternStr;
    }
}
