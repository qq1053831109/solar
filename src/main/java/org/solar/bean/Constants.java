package org.solar.bean;

/**
 * Created by xianchuanwu on 2017/9/24.
 */
public class Constants {
    /**
     * dr字段 Y=该数据已删除。N=该数据未删除
     */
    public static final String Y="y";
    public static final String N="n";
    /**
     * 字段 0=该数据已删除 1=默认 2-9=其他
     */
    public static final int Num0 = 0;
    public static final int Num1 = 1;
    public static final int Num2 = 2;
    public static final int Num3 = 3;
    public static final int Num4 = 4;
    public static final int Num5 = 5;
    public static final int Num6 = 6;
    public static final int Num7 = 7;
    public static final int Num8 = 8;
    public static final int Num9 = 9;
    /**
     *  CrawlerConfig_type_list 列表抓取方式
     */
    public static final String CrawlerConfig_type_list="list";
    public static final String CrawlerConfig_type_json_list="json-list";
    public static final String CrawlerConfig_type_rss="rss";
    /**
     *  CrawlerConfig_type_list 列表抓取方式
     */
    public static final String sysConfig_code_crawler_enable="crawler_enable";
    public static final String sysConfig_code_participle_index_task="participle_index_task";
    public static final String sysConfig_code_participle_stop_words="participle_stop_words";
    /**
     *  status 状态 len<=10
     */
    public static final String Table_Status_Running="Running";//正在运行
    public static final String Table_Status_Not_Running="NotRunning";//未在运行
    public static final String Table_Status_Sleep="Sleep";//等待唤醒

    public static final String Table_Status_Had_urlId="Had_urlId";//已存在

    public static final String Table_Status_Had_NameId="Had_NameId";//已存在

    public static final String Table_Status_Success="Success";//已存在

    public static final String Table_Status_Exception="Exception";//Exception

    /**
     *  Log level 状态
     */
    public static final String Exception="exception";//Exception
}
