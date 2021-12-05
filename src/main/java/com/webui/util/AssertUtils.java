package com.webui.util;


import com.webui.exception.ParamsException;

public class AssertUtils {

    public static void assertNotNull(Object object, String str) {
        if (object == null) {
            throw new ParamsException(str);
        }
    }

}
