package com.webui.util;

import org.apache.log4j.Logger;

import java.util.Arrays;


public class LogUtils {

    private static final Logger log = Logger.getLogger(LogUtils.class);

    public static void info(Logger logger, Object... objects) {
        if (logger == null) {
            logger = log;
        }
        logger.info(messageFormat(objects));
    }


    public static void error(Logger logger, Object... objects) {
        Throwable throwable = (Throwable) Arrays.stream(objects).filter(object -> object instanceof Throwable).findFirst().orElse(null);
        if (logger == null) {
            logger = log;
        }
        logger.error(messageFormat(objects), throwable);
    }

    public static void warn(Logger logger, Object... objects) {
        if (logger == null) {
            logger = log;
        }
        logger.warn(messageFormat(objects));
    }


    private static String messageFormat(Object... objects) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("===================");
        for (Object obj : objects) {
            if (obj instanceof Throwable){
                continue;
            }
            stringBuilder.append(obj).append(",");
        }
        return stringBuilder.deleteCharAt(stringBuilder.length() - 1).append("===================").toString();


    }

}
