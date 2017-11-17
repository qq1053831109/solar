package org.solar.bean;

/**
 * Created by xianchuanwu on 2017/9/24.
 */
public class Constants {
    /**
     * dr字段 Y=该数据已删除。N=该数据未删除
     */
    public static final String Y="Y";
    public static final String N="N";
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
    public static final String Log_Level_Excep="Excep";//Exception
}
