package com.webui.util;

import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class LogUtils {

        private static final Logger logger = Logger.getLogger(LogUtils.class);

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
     * but placeholder is '%s'.
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
//        RuntimeException exception =
        return String.format(message, objects);
    }

    public static void main(String[] args) {
        LogUtils.info(logger,"hello",new RuntimeException("错误测试！"));
        LogUtils.info(logger,"哇哇%s","666!",new RuntimeException("这是一个错误洗毛线"));
    }
}
