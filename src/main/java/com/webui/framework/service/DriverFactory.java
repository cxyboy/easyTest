package com.webui.framework.service;


import com.webui.framework.facade.Driver;
import com.webui.framework.facade.UiElement;

import com.webui.framework.proxy.UiEasyTestProxy;
import com.webui.util.AssertUtils;
import com.webui.util.Utils;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;


import java.util.Set;

import static com.webui.util.ParsePropertiesFile.getProperty;

public class DriverFactory implements Driver {


    private static final Logger logger = Logger.getLogger(DriverFactory.class);
    private WebDriver webDriver;
    private WebDriver.TargetLocator targetLocator;
    private WebDriver.Navigation navigation;

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
        String path = Utils.getCurrentPath(getProperty("driver_path") + type.getCode() + (System.getenv("os").toLowerCase().contains("windows") ? ".exe" : ""));
        if (path != null) {
            System.setProperty(type.getKey(), path);
        } else if (type.getName().equals("谷歌驱动")) {
            WebDriverManager.chromedriver().setup();
        } else if (type.getName().equals("火狐驱动")) {
            WebDriverManager.firefoxdriver().setup();
        }
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
        return webDriver.getWindowHandle();
    }

    @Override
    public boolean switchTo() {
        try {
            targetLocator = webDriver.switchTo();
        } catch (Exception e) {
            logger.error(e);
            return false;
        }
        return true;
    }

    @Override
    public Driver navigate() {
        navigation = webDriver.navigate();
        return this;
    }

    @Override
    public Driver manage() {
        webDriver.manage();
        return this;
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
