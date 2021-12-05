package com.webui.exception;

import com.webui.util.LogUtils;
import org.apache.log4j.Logger;

public class FrameWorkException extends RuntimeException {

    private static final long serialVersionUID = -134941992549974382L;

    private static final Logger logger = Logger.getLogger(FrameWorkException.class);

    public FrameWorkException() {
        super();
    }

    public FrameWorkException(String msg) {
        super(msg);
        LogUtils.error(logger, msg);
    }

    public FrameWorkException(String msg, Throwable throwable) {
        super(msg, throwable);
        LogUtils.error(logger, msg, throwable);
    }
}
