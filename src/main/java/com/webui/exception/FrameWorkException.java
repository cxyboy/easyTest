package com.webui.exception;

public class FrameWorkException extends RuntimeException {

    private static final long serialVersionUID = -134941992549974382L;


    public FrameWorkException() {
        super();
    }

    public FrameWorkException(String msg) {
        super(msg);
    }

    public FrameWorkException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
}
