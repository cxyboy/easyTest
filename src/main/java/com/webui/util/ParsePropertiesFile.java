package com.webui.util;

import com.webui.exception.ConfigException;
import com.webui.exception.ParamsException;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;


public class ParsePropertiesFile {


    private static final Logger logger = Logger.getLogger(ParsePropertiesFile.class);

    private static final String separator = File.separator;
    private static final String fileType = ".properties";
    private static final Properties properties;
    private static final String propertiesFileRootPath = Paths.get(".").normalize().toAbsolutePath().toString();
    private static final String localResource = "src/main/resources";


    static {
        properties = new Properties();
        String os = System.getenv("os").substring(0, 3);
        String app = System.getProperty("appName");
        String currentPath = Utils.getCurrentPath(
                propertiesFileRootPath + separator + localResource + app + fileType,
                propertiesFileRootPath + separator + app + fileType,
                propertiesFileRootPath + separator + localResource + separator + os + fileType,
                propertiesFileRootPath + separator + os + fileType);
        try {
            AssertUtils.assertNotNull(currentPath, "预期路径下找不到配置文件!");
            properties.load(new FileInputStream(currentPath));
        } catch (IOException e) {
            throw new ConfigException("加载配置文件失败！", e);
        } catch (ParamsException e) {
            logger.warn("没有添加配置文件使用默认配置！");
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


}
