package com.webui.exception;



public class SearchUiElementException extends RuntimeException {


    private static final long serialVersionUID = -6247524625100588710L;

    public SearchUiElementException() {
        super();
    }

    public SearchUiElementException(String msg) {
        super(msg);
    }

    public SearchUiElementException(String msg, Throwable throwable) {
        super(msg, throwable);
    }


}
