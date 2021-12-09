package com.webui.util;


import com.webui.exception.ParamsException;
import org.apache.commons.lang.StringUtils;

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

    public static void assertTrue(Boolean boole, String message) {
        if (Boolean.FALSE.equals(boole)) {
            throw new ParamsException(message);
        }
    }

    public static void assertStringNotBlank(String str, String msg){
        if (StringUtils.isNotBlank(str)){
            //
        }
    }

    public static void assertStringIsBlank(String str, String msg) {
        if (StringUtils.isEmpty(str)) {
            throw new ParamsException(msg);
        }
    }

}
