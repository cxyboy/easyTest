package com.webui.exception;

import com.webui.util.LogUtils;
import org.apache.log4j.Logger;


public class ParamsException extends RuntimeException {


    private static final long serialVersionUID = -999315214961054651L;

    private static final Logger logger = Logger.getLogger(ParamsException.class);

    public ParamsException() {
        super();
    }

    public ParamsException(String msg) {
        super(msg);
        LogUtils.error(logger, msg, this);
    }

    public ParamsException(String msg, Throwable throwable) {
        super(msg, throwable);
        LogUtils.error(logger, msg, throwable);
    }

    public ParamsException(Object... objects) {
        //placeholder
    }
}
