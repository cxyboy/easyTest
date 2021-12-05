package com.webui.exception;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public class ParamsException extends RuntimeException {


    private static final long serialVersionUID = -999315214961054651L;


    public ParamsException() {
        super();
    }

    public ParamsException(String msg) {
        super(msg);
    }

    public ParamsException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

    public ParamsException(Object... objects) {
        //
    }
}
