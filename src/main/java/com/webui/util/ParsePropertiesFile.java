package com.webui.util;

import com.webui.exception.ConfigException;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;


public class ParsePropertiesFile {

    private static final String separator = File.separator;
    private static final String fileType = ".properties";
    private static final Properties properties;
    private static final String propertiesFileRootPath = Paths.get(".").normalize().toAbsolutePath().toString();
    private static final String localResource = "src/main/resources";


    static {
        properties = new Properties();
        String os = System.getenv("os").substring(0, 3);
        String app = System.getProperty("appName");
        String currentPath = Utils.getCurrentPath(propertiesFileRootPath + separator + localResource + separator + os + "-" + (StringUtils.isNotEmpty(app) ? app : "global") + fileType,
                propertiesFileRootPath + separator + os + "-" + (StringUtils.isNotEmpty(app) ? app : "global") + fileType);
        AssertUtils.assertNotNull(currentPath, "找不到配置文件!");
        try {
            properties.load(new FileInputStream(currentPath));
        } catch (IOException e) {
            throw new ConfigException("加载配置文件失败！", e);
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);

    }

    public static String getProperty(String key, String defaultV) {
        return properties.getProperty(key, defaultV);
    }

    public static void setProperty(String key, String val) {
        properties.setProperty(key, val);
    }

    public static Object getProperty(Object key) {
        return properties.get(key);
    }

    public static Object getOrDefault(Object key, Object defaultV) {
        return properties.getOrDefault(key, defaultV);
    }


    public static void main(String[] args) {
//        System.setProperty("appName", "app");
//        System.out.println(System.getProperty("appName"));
        properties.setProperty("w", "bb");
        System.out.println(getProperty("w"));
        System.out.println(System.getProperty("w"));
    }

}
