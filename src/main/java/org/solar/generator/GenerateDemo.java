package org.solar.generator;

import org.solar.coder.AESCoder;

public class GenerateDemo {
    public static  String jdbcUrl = "jdbc:mysql://127.0.0.1:3306/database?user=root&password=123456";
    public  static  String classPath = "/Users/xianchuanwu/Desktop/sc/code/hg/src/main/java/";
    static String packagePrefix = "com.hg.";
    static String vueHtmlRootPath = "/Users/xianchuanwu/Desktop/sc/code/hg/src/main/webapp/static/html/template/";
    public static void main(String[] args) throws Exception {
        Generator generator=new Generator(jdbcUrl,classPath,packagePrefix);
        //配置
        //generator.templateRootPath="/templatePath/";//自定义模版
        generator.vueHtmlRootPath=vueHtmlRootPath;
        //生成
        generator.generatePackageAndBaseDaoAndBaseService();
        generator.generateBeanCode();// generator.generateBean("Bean","Resume");
        generator.generateDaoServiceControllerCode();
        generator.generateMybatisMapperXML();
        generator.generateVueCode();



    }
}
