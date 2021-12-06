package com.webui.util;

import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LogUtils {

    //    private static final Logger logger = Logger.getLogger(LogUtils.class);
    static final String tag = "\\{}";

    /**
     * 运行日志
     * using it like ’String.format()'.
     * but placeholder is '{}'.
     *
     * @param logger  log4j 实例
     * @param message 日志消息
     * @param objects 格式化对象
     */
    public static void info(Logger logger, String message, Object... objects) {
        logger.info(messageFormat(message, objects));
    }

    public static void info(Logger logger, String msg) {
        logger.info(msg);
    }

    public static void info(Logger logger, String msg, Throwable throwable) {
        logger.info(msg, throwable);
    }

    /**
     * 错误日志
     * using it like ’String.format()'.
     * but placeholder is '{}'.
     *
     * @param logger  log4j 实例
     * @param message 日志消息
     * @param objects 格式化对象
     */
    public static void error(Logger logger, String message, Object... objects) {
        logger.error(messageFormat(message, objects));
    }


    public static void error(Logger logger, String message) {
        logger.error(message);
    }

    public static void error(Logger logger, String message, Throwable throwable) {
        logger.error(message, throwable);

    }

    private static String messageFormat(String message, Object... objects) {

        List<String> collect = Arrays.stream(message.split(tag)).collect(Collectors.toList());
        int size = collect.size();
        int len = objects.length;
        String msg = String.format("格式化语句错误!%s != %s", tag, objects.length);
        AssertUtils.assertEquals(size - 1, len, msg);
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < len; i++) {
            buffer.append(collect.get(i)).append(objects[i]);
        }
        buffer.append(collect.get(size - 1));
        return buffer.toString();
    }


}
