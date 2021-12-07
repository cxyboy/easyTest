package com.webui.framework.service;


import com.webui.framework.facade.Driver;
import com.webui.framework.facade.UiElement;

import com.webui.framework.proxy.UiEasyTestProxy;
import com.webui.util.AssertUtils;
import com.webui.util.Utils;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;


import java.util.Set;

import static com.webui.util.ParsePropertiesFile.getObject;

public class DriverFactory implements Driver {


    private static final Logger logger = Logger.getLogger(DriverFactory.class);
    private WebDriver webDriver;
    private final String rootPath = getObject("driver_path");

    public static Driver getDriverContext() {
        return driverContext.get();
    }

    private static ThreadLocal<Driver> driverContext;


    public static void initDriverContext() {
        driverContext = new ThreadLocal<Driver>() {
            @Override
            protected Driver initialValue() {
                return new DriverFactory();
            }
        };
    }


    private DriverFactory() {
    }


    @Override
    public void createDriver(DriverClass type) {
        String path = Utils.getCurrentPath(rootPath + type.getCode() + (System.getenv("os").toLowerCase().contains("windows") ? ".exe" : ""));
        AssertUtils.assertNotNull(path,"找不到driver文件");
        System.setProperty(type.getKey(), path);
        switch (type) {
            case CHROME:
                webDriver = new ChromeDriver();
                break;
            case FIREFOX:
                webDriver = new FirefoxDriver();
                break;
        }
    }

    public WebDriver getWebDriver() {
        return webDriver;
    }

    @Override
    public void get(String s) {
        webDriver.get(s);
    }

    @Override
    public String getCurrentUrl() {
        return webDriver.getCurrentUrl();
    }

    @Override
    public String getTitle() {
        return webDriver.getTitle();
    }


    @Override
    public void close() {
        webDriver.close();
    }

    @Override
    public void quit() {
        webDriver.quit();
    }

    @Override
    public Set<String> getWindowHandles() {
        return null;
    }

    @Override
    public String getWindowHandle() {
        return null;
    }

    @Override
    public WebDriver.TargetLocator switchTo() {
        return null;
    }

    @Override
    public WebDriver.Navigation navigate() {
        return null;
    }

    @Override
    public WebDriver.Options manage() {
        return null;
    }

    @Override
    public UiElement findUiElement(Dom dom, int index) {
        AssertUtils.assertNotNull(dom, "定位器Dom是null");
        AssertUtils.assertNotNull(index, "元素索引是null");
        return (UiElement) UiEasyTestProxy.getUiElementProxy(WebUIElement.Element.initUiHandler(dom, index));
    }

    public UiElement findUiElement(Dom dom) {
        AssertUtils.assertNotNull(dom, "定位器Dom是null");
        return (UiElement) UiEasyTestProxy.getUiElementProxy(WebUIElement.Element.initUiHandler(dom, 0));
    }
}
