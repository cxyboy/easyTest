package com.webui.util;

import java.io.File;

public class ParsePropertiesFile {


    static String SYSTEM_SEPARATOR = File.separator;


    public static String getObject(String key) {
        return "D:" + SYSTEM_SEPARATOR +
                "BrowserDowload" + SYSTEM_SEPARATOR +
                "chromedriver_win32" + SYSTEM_SEPARATOR;

    }


}
