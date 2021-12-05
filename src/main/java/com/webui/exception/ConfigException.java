package com.webui.exception;

public class ConfigException extends RuntimeException {


    private static final long serialVersionUID = -5309998460048110829L;


    public ConfigException() {
        super();
    }

    public ConfigException(String message) {
        super(message);
    }

    public ConfigException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
}
