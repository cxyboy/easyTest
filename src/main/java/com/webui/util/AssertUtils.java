package com.webui.util;


import com.webui.exception.ParamsException;

public class AssertUtils {

    public static void assertNotNull(Object object, String str) {
        if (object == null) {
            throw new ParamsException(str);
        }
    }

    public static void assertEquals(Object object, Object object2, String msg) {
        if (!object.equals(object2)) {
            throw new ParamsException(msg);
        }
    }

}
