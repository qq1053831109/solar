package org.solar.util;

import java.io.*;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public class PropertiesUtil {
    public static Map<String, String> mapCache = new ConcurrentHashMap();
    public static Map<String, Properties> propertiesCache = new ConcurrentHashMap<>();

    public PropertiesUtil() {
    }

    public static String getValueByKey(String key, String propertiesName) {
        String cacheValue = (String) mapCache.get(propertiesName + key);
        if (cacheValue != null) {
            return cacheValue;
        }
        if (propertiesCache.get(propertiesName) != null) {
            return (String) propertiesCache.get(propertiesName).get(key);
        }

        InputStream inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream(propertiesName);
        Properties properties = new Properties();
        InputStreamReader inputStreamReader = null;

        try {
            inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            properties.load(inputStreamReader);
        } catch (IOException var14) {
            var14.printStackTrace();
        } finally {
            try {
                if (null != inputStream) {
                    inputStream.close();
                }

                if (null != inputStreamReader) {
                    inputStreamReader.close();
                }
            } catch (IOException var13) {
                var13.printStackTrace();
            }

        }

        String value = properties.getProperty(key);
        mapCache.put(propertiesName + key, value);
        return value;
    }

    public static Properties getProperty(String propertiesName) {
        InputStream inputStream =null;
        if (propertiesName.startsWith("file:")){
            String filePath=propertiesName.replace("file:","");
            try {
                inputStream=new FileInputStream(filePath);
            } catch (FileNotFoundException e) {
                return new Properties();
            }
        }else {
            inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream(propertiesName);
        }
        Properties properties = new Properties();

        try {
            properties.load(new InputStreamReader(inputStream, "utf-8"));
        } catch (IOException var12) {
            var12.printStackTrace();
        } finally {
            try {
                if (null != inputStream) {
                    inputStream.close();
                }
            } catch (IOException var11) {
                var11.printStackTrace();
            }

        }

        return properties;
    }
}
