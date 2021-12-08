package com.webui.exception;

import com.webui.util.LogUtils;
import org.apache.log4j.Logger;

public class ConfigException extends RuntimeException {


    private static final long serialVersionUID = -5309998460048110829L;

    private static final Logger logger = Logger.getLogger(ConfigException.class);

    public ConfigException() {
        super();
    }

    public ConfigException(String message) {
        super(message);
        logger.error(message);
    }

    public ConfigException(Throwable throwable) {
        super(throwable);
        logger.error(throwable);
    }

    public ConfigException(String msg, Throwable throwable) {
        super(msg, throwable);
        logger.error(msg, throwable);
    }
}
