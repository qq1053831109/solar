package org.solar.generator;

public class GenerateDemo {
    public static String jdbcUrl = "jdbc:mysql://127.0.0.1:3306/database?user=root&password=123456";
    public static String pojectPath = "/Users/xianchuanwu/Desktop/sc/code/demo/";
    public static String classPath = pojectPath + "src/main/java/";
    static String packagePrefix = "com.demo.";
    static String vueHtmlRootPath = pojectPath + "src/main/webapp/static/html/template/";
    static String apiHtmlDocPath = pojectPath + "src/main/webapp/static/html/";

    public static void main(String[] args) throws Exception {
        Generator generator = new Generator(jdbcUrl, classPath, packagePrefix);
        generator.overWriteFile=true;
        generator.vueHtmlRootPath = vueHtmlRootPath;
        //生成包和基本类
        generator.generatePackageAndBaseDaoAndBaseService();
        //根据数据库生成实体文件
        generator.generateBean();
        //生成DaoServiceControllerCode
        generator.generateDaoServiceControllerCode();

        generator.mapperXMLGenerator.fullTextSearchContainDate=false;
        generator.generateMybatisMapperXML();
        //生成VUE的CRUD界面
        generator.generateVueCode();


        //生成api 文档
        generator.apiHtmlDocPath = apiHtmlDocPath;
        generator.generateApiHtmlDoc();

        //生成数据库字段与备注的properties
        generator.dataBaseFieldDictPath = pojectPath + "src/main/resources/dataBaseFieldDict.properties";
        generator.generatorDataBaseFieldDict();

    }
}
