package org.solar.generator;

import org.solar.util.DateUtil;
import org.solar.util.FileUtil;
import org.solar.util.PropertiesUtil;
import org.solar.util.StringUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.*;

public class Generator {
    public String jdbcUrl;
    public String rootpath;
    public String templateRootPath;
    String beanPath;
    String servicePath;
    String daoPath;

    String ServiceImplPath;
    String daoImplPath;
    String ControllerPath;
    String beanPackage;
    public static String packagePrefix;
    String mapperXMLRootPath;
    public String vueHtmlRootPath;
    public String dataBaseFieldDictPath;
    public String apiHtmlDocPath;
    public String apiHtmlDocHost;
    public boolean overWriteFile = false;
    private DataBase dataBase;
    private List<Table> tableList;
    public MapperXMLGenerator mapperXMLGenerator;

    public Generator(String jdbcUrl, String classPath, String packagePrefix) {
        this.jdbcUrl = jdbcUrl;
        this.rootpath = classPath;
        this.packagePrefix = packagePrefix;
        beanPackage = packagePrefix + "entity";
        String packagePath = packagePrefix.replace(".", "/");
        beanPath = classPath + packagePath + "entity/";
        servicePath = classPath + packagePath + "service/";
        daoPath = classPath + packagePath + "dao/";
        ServiceImplPath = classPath + packagePath + "service/impl/";
        daoImplPath = classPath + packagePath + "dao/impl/";
        ControllerPath = classPath + packagePath + "controller/base/";
        mapperXMLRootPath = classPath + packagePath + "mapper/";
        dataBase = new DataBase(jdbcUrl);
        tableList = dataBase.getTables();
        tableList = dataBase.getTables();
        String mybatisMapperTemplate = getTemplate("MybatisMapper.template");
        mapperXMLGenerator = new MapperXMLGenerator(tableList, mybatisMapperTemplate, packagePrefix);
    }

    public void generatorDataBaseFieldDict() {
        if (dataBaseFieldDictPath == null) {
            dataBaseFieldDictPath = rootpath + "dataBaseFieldDict.properties";
        }
        DataBaseFieldDictGenerator dataBaseFieldDictGenerator = new DataBaseFieldDictGenerator(tableList, dataBaseFieldDictPath);

        try {
            File file=new File(dataBaseFieldDictPath);
            if ( file.exists()&&!overWriteFile){
                return;
            }
            dataBaseFieldDictGenerator.generatorPropertiesFile();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void generateDaoServiceControllerCode() {
        try {
            generateCodeByType("Dao", daoPath);
            generateCodeByType("DaoImpl", daoImplPath);
            generateCodeByType("Service", servicePath);
            generateCodeByType("ServiceImpl", ServiceImplPath);
            generateCodeByType("CrudController", ControllerPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void generateVueCode() {
        try {
            generateVueHtml(vueHtmlRootPath);
            generateVuePageHtml(vueHtmlRootPath);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void generatePackageAndBaseDaoAndBaseService() {
        FileUtil.mkdirs(beanPath);
        FileUtil.mkdirs(daoPath);
        FileUtil.mkdirs(servicePath);
        FileUtil.mkdirs(daoImplPath);
        FileUtil.mkdirs(ServiceImplPath);
        FileUtil.mkdirs(ControllerPath);
        FileUtil.mkdirs(mapperXMLRootPath);
        generateBaseCode("BaseDao", daoPath);
        generateBaseCode("BaseDaoImpl", daoImplPath);
        generateBaseCode("BaseService", servicePath);
        generateBaseCode("BaseServiceImpl", ServiceImplPath);
        generateBaseCode("BaseController", ControllerPath);
    }

    private void generateBaseCode(String type, String gcPath) {
        String daoTemplate = getTemplate(type + ".template");
        File gcfile = new File(gcPath + type + ".java");
        Map map = new LinkedHashMap();
        map.put("packagePrefix", packagePrefix);
        if (!gcfile.exists()||overWriteFile) {
            String fosStr = replaceTemplateEL(daoTemplate, map);
            FileUtil.writeToFile(fosStr.getBytes(), gcfile);
            System.out.println("生成" + type + "成功!");
        }
    }

    public String getTemplate(String templateName) {
        byte[] bytes = null;
        if (templateRootPath == null || "".equals(templateRootPath)) {
            InputStream in = this.getClass().getClassLoader().getResourceAsStream("template/" + templateName);
            bytes = FileUtil.getBytesFromFileIo(in);
            //System.out.println("从ClassPath读取文件!");
        } else {
            bytes = FileUtil.getBytesFromFile(templateRootPath + templateName);
        }

        String template = new String(bytes);
        return template;
    }

    private String replaceTemplateEL(String template, Map<String, String> map) {
        Set<Map.Entry<String, String>> entrySet = map.entrySet();
        for (Map.Entry<String, String> entry : entrySet) {
            template = template.replace("${" + entry.getKey() + "}", entry.getValue());
        }
        return template;
    }

    public void generateCodeByType(String type, String gcPath) throws Exception {
        String daoTemplate = getTemplate(type + ".template");
        String date = DateUtil.format(new Date(), "yyyy年MM月dd日 HH:MM");
        File file = new File(beanPath);
        File[] files = file.listFiles();
        for (File f : files) {
            String beanName = f.getName();
            beanName = beanName.replace(".java", "");
            File gcfile = new File(gcPath + beanName + type + ".java");
            Map map = new LinkedHashMap();
            map.put("BeanName", beanName);
            map.put("beanName", StringUtil.toLowerCaseFirstOne(beanName));
            map.put("Date", date);
            map.put("packagePrefix", packagePrefix);
            if (gcfile.exists() && !overWriteFile) {
                continue;
            } else {
                gcfile.createNewFile();
            }

            FileOutputStream fos = new FileOutputStream(gcfile);
            String fosStr = replaceTemplateEL(daoTemplate, map);
            fos.write(fosStr.getBytes());
            fos.flush();
            fos.close();
            System.out.println("生成" + beanName + type + "成功!");
        }

        System.out.println("----------------------生成"+type+"结束!----------------------");

    }


    public void generateMybatisMapperXML() throws Exception {

        String type = "MybatisMapper";
        String gcPath = mapperXMLRootPath;
        for (Table table : tableList) {
            File gcfile = new File(gcPath + table.getCamelName(false) + "Mapper.xml");
            if (gcfile.exists()) {
                if (!overWriteFile) {
                    continue;
                }
            } else {
                gcfile.createNewFile();
            }
            try {
                FileOutputStream fos = new FileOutputStream(gcfile);
                String mapperXMLContent = mapperXMLGenerator.toString(table);
                fos.write(mapperXMLContent.getBytes());
                fos.flush();
                fos.close();
            } catch (Exception e) {
                gcfile.delete();
                throw e;
            }
            System.out.println("生成" + table.getCamelName(false) + "Mapper.xml 成功!");
        }
        System.out.println("----------------------生成"+type+"结束!----------------------");

    }

    public void generateBean() throws Exception {
        String template = getTemplate("Bean.template");
        BeanGenerator beanCodeGenerate = new BeanGenerator(template, packagePrefix);
        for (Table table : tableList) {
            String BeanName = StringUtil.underline2Camel(table.getName().toLowerCase(), false);
            File beanFile = new File(beanPath + BeanName + ".java");
            if (beanFile.exists()) {
                if (!overWriteFile) {
                    continue;
                }
            } else {
                beanFile.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(beanFile);
            String text = beanCodeGenerate.toString(table);
            fos.write(text.getBytes());
            fos.flush();
            fos.close();
            System.out.println("生成 " + BeanName + " 成功!");

        }
        System.out.println("----------------------生成Entity结束!----------------------");

    }

    public void generateApiHtmlDoc() throws Exception {
        String template = getTemplate("ApiHtmlDoc.template");
        ApiHtmlDoc generate = new ApiHtmlDoc(tableList, template, apiHtmlDocHost);
        File file = new File(apiHtmlDocPath);
        if (file.exists()) {
            if (!overWriteFile) {
                return;
            }
        } else {
            file.createNewFile();
        }
        FileOutputStream fos = new FileOutputStream(file);
        String text = generate.toString();
        fos.write(text.getBytes());
        fos.flush();
        fos.close();
        System.out.println("生成 ApiHtmlDoc 成功!");
    }

    public void generateVueHtml(String gcPath) throws Exception {
        String template = getTemplate("VueComponent.template");
        VueGenerator vueGenerator = new VueGenerator(tableList, template);

        for (Table table : tableList) {
            String beanName = table.getCamelName();
            File fileFolder = new File(gcPath + beanName);
            if (!fileFolder.exists()) {
                fileFolder.mkdir();
            }
            File beanFile = new File(gcPath + beanName + "/" + beanName + ".html");
            if (beanFile.exists() && !overWriteFile) {
                return;
            } else {
                beanFile.createNewFile();
            }

            FileOutputStream fos = new FileOutputStream(beanFile);
            String text = vueGenerator.toString(table);
            fos.write(text.getBytes());
            fos.flush();
            fos.close();
            System.out.println("生成 " + beanName + ".html 成功!");

        }
        System.out.println("----------------------生成Vue.Html结束!----------------------");

    }

    public void generateVuePageHtml(String gcPath) throws Exception {
        String template = getTemplate("VuePageComponent.template");
        VueGenerator vueGenerator = new VueGenerator(tableList, template);

        for (Table table : tableList) {
            String beanName = table.getCamelName();
            File fileFolder = new File(gcPath + beanName);
            if (!fileFolder.exists()) {
                fileFolder.mkdir();
            }
            File beanFile = new File(gcPath + beanName + "/" + beanName + "Page.html");
            if (beanFile.exists() && !overWriteFile) {
                return;
            } else {
                beanFile.createNewFile();
            }

            FileOutputStream fos = new FileOutputStream(beanFile);
            String text = vueGenerator.toString(table);
            fos.write(text.getBytes());
            fos.flush();
            fos.close();
            System.out.println("生成 " + beanName + "Page.html 成功!");

        }
        System.out.println("----------------------生成VuePage.Html结束!----------------------");

    }


}
