package com.webui.framework.service;

public enum DriverClass {


    CHROME("谷歌驱动", "chromedriver","webdriver.chrome.driver"),
    FIREFOX("火狐驱动", "geckodriver","webdriver.firefox.driver");

    private final String name;
    private final String code;
    private final String key;

    DriverClass(String name, String code, String key) {
        this.name = name;
        this.code = code;
        this.key = key;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }
}
