package com.webui.exception;

public class ExecuteTimeoutException extends RuntimeException {


    private static final long serialVersionUID = -1506593494142173088L;


    public ExecuteTimeoutException() {
        super();
    }

    public ExecuteTimeoutException(String msg) {
        super(msg);
    }

    public ExecuteTimeoutException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
}
