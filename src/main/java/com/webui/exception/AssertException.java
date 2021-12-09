package com.webui.exception;

import com.webui.util.LogUtils;
import org.apache.log4j.Logger;

public class AssertException extends RuntimeException {

    private static final long serialVersionUID = -2740109809527706479L;

    Logger logger = Logger.getLogger(AssertException.class);

    public AssertException() {
        super();
    }

    public AssertException(String message) {
        super(message);
        LogUtils.error(logger, message);
    }


    public AssertException(String message, Throwable cause) {
        super(message, cause);
        LogUtils.error(logger, message, cause);
    }
}
