package org.solar.generator;

public class GenerateDemo {
    public static String jdbcUrl = "jdbc:mysql://127.0.0.1:3306/database?user=root&password=123456";
    public static String pojectPath = "/Users/xianchuanwu/Desktop/sc/code/demo/";
    public static String classPath = pojectPath + "src/main/java/";
    static String packagePrefix = "com.demo.";
    static String vueHtmlRootPath = pojectPath + "src/main/webapp/static/html/template/";

    public static void main(String[] args) throws Exception {
        Generator generator = new Generator(jdbcUrl, classPath, packagePrefix);

        //配置
        //generator.templateRootPath="/templatePath/";//自定义模版
        generator.vueHtmlRootPath = vueHtmlRootPath;
        //生成//根据数据库生成实体文件
        generator.generatePackageAndBaseDaoAndBaseService();
        generator.generateBeanCode();// generator.generateBean("Bean","Resume");
        //生成DaoServiceControllerCode//根据已有的实体文件生成
        generator.generateDaoServiceControllerCode();
        //生成VUE的CRUD界面
        generator.generateVueCode();
        //生成数据库字段与备注的properties
        generator.dataBaseFieldDictPath = pojectPath + "src/main/resources/dataBaseFieldDict.properties";
        generator.generatorDataBaseFieldDict();


    }
}
