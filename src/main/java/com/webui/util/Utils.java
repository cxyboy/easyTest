package com.webui.util;


import org.apache.log4j.Logger;

import java.io.File;

public class Utils {
    private final static Logger logger = Logger.getLogger(Utils.class);

    public static String getCurrentPath(String... path) {
        for (String f : path) {
            LogUtils.info(logger, "Got path:%s", f);
            File file = new File(f);
            if (file.exists()) {
                LogUtils.info(logger, "Exist path:%s", f);
                return f;
            }
        }
        return null;

    }
}
