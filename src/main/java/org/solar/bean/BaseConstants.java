package org.solar.bean;

/**
 * Created by xianchuanwu on 2017/9/24.
 */
public interface BaseConstants {
    /**
     * dr字段 Y=该数据已删除。N=该数据未删除
     */
    String Y="y";
    String N="n";
    /**
     * 字段 0=该数据已删除 1=默认 2-9=其他
     */
    int Num0 = 0;
    int Num1 = 1;
    int Num2 = 2;
    int Num3 = 3;
    int Num4 = 4;
    int Num5 = 5;
    int Num6 = 6;
    int Num7 = 7;
    int Num8 = 8;
    int Num9 = 9;
    /**
     *  CrawlerConfig_type_list 列表抓取方式
     */
    String CrawlerConfig_type_list="list";
    String CrawlerConfig_type_json_list="json-list";
    String CrawlerConfig_type_rss="rss";
    /**
     *  CrawlerConfig_type_list 列表抓取方式
     */
    String sysConfig_code_crawler_enable="crawler_enable";
    String sysConfig_code_participle_index_task="participle_index_task";
    String sysConfig_code_participle_stop_words="participle_stop_words";
    /**
     *  status 状态 len<=10
     */
    String Table_Status_Running="Running";//正在运行
    String Table_Status_Not_Running="NotRunning";//未在运行
    String Table_Status_Sleep="Sleep";//等待唤醒

    String Table_Status_Had_urlId="Had_urlId";//已存在

    String Table_Status_Had_NameId="Had_NameId";//已存在

    String Table_Status_Success="Success";//已存在

    String Table_Status_Exception="Exception";//Exception

    /**
     *  Log level 状态
     */
    String Exception="exception";//Exception
}
