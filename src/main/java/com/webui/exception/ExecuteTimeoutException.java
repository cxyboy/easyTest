package com.webui.exception;

import com.webui.util.LogUtils;
import org.apache.log4j.Logger;

public class ExecuteTimeoutException extends RuntimeException {


    private static final long serialVersionUID = -1506593494142173088L;

    private static final Logger logger = Logger.getLogger(ExecuteTimeoutException.class);

    public ExecuteTimeoutException() {
        super();
    }

    public ExecuteTimeoutException(String msg) {
        super(msg);
        LogUtils.error(logger, msg);
    }

    public ExecuteTimeoutException(String msg, Throwable throwable) {
        super(msg, throwable);
        LogUtils.error(logger, msg, throwable);
    }
}
