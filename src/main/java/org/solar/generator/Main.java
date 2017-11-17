package org.solar.generator;

public class Main {
    public static  String jdbcUrl = "jdbc:mysql://base.heduim.com:3306/hugong?user=root&password=wxc%26ZS1314";
    public  static  String classPath = "/Users/xianchuanwu/Desktop/sc/code/solar/src/main/java/";
    static String packagePrefix = "com.hugong.";
    public  static  String templateRootPath = "/Users/xianchuanwu/Desktop/sc/code/solar/src/main/resources/template/";
    static String vueHtmlRootPath = "/Users/xianchuanwu/Desktop/sc/code/solar/src/main/webapp/static/html/template/";
    public static void main(String[] args) throws Exception {
        Generator generator=new Generator(jdbcUrl,classPath,packagePrefix);
        //配置
        //generator.templateRootPath=templateRootPath;
        generator.vueHtmlRootPath=vueHtmlRootPath;
        //生成
        generator.generatePackageAndBaseDaoAndBaseService();
        generator.generateBeanCode();
        generator.generateDaoServiceControllerCode();
       // generator.generateMybatisMapperXML();
//        generator.generateVueCode();


    }
}
