package com.webui.framework.service;


import com.webui.framework.facade.Driver;

import com.webui.util.AssertUtils;
import com.webui.util.Utils;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Set;
import com.webui.framework.service.PageFactory.Dom;

import static com.webui.util.ParsePropertiesFile.getProperty;

public class DriverFactory implements Driver , InvocationHandler {


    private static final Logger logger = Logger.getLogger(DriverFactory.class);
    private WebDriver webDriver;

    public static Driver getDriverContext() {
        return driverContext.get();
    }

    private static ThreadLocal<Driver> driverContext;


    public static void initDriverContext() {
        driverContext = ThreadLocal.withInitial(() -> {
            DriverFactory factory = new DriverFactory();
            factory.createDriver();
            return factory;
        });
    }


    private DriverFactory() {
        logger.error("nrby7hhhhhhhh");
    }


    public void createDriver() {
        DriverClass type = DriverClass.CHROME;
        String property = getProperty("driver_class");
        for (DriverClass driverClass : DriverClass.values()) {
            if (driverClass.getName().equals(property) || driverClass.toString().equals(property)) {
                type = driverClass;
            }
        }

        String path = Utils.getCurrentPath(
                getProperty("driver_path"),
                getProperty("driver_path") + type.getCode() + (System.getenv("os").toLowerCase().contains("windows") ? ".exe" : ""));
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
            WebDriver.TargetLocator targetLocator = webDriver.switchTo();
        } catch (Exception e) {
            logger.error(e);
            return false;
        }
        return true;
    }

    @Override
    public Driver navigate() {
        WebDriver.Navigation navigation = webDriver.navigate();
        return this;
    }

    @Override
    public Driver manage() {
        webDriver.manage();
        return this;
    }

    @Override
    public WebElement findUiElement(Dom dom, int index) {
        AssertUtils.assertNotNull(dom, "定位器Dom是null");
        AssertUtils.assertNotNull(index, "元素索引是null");
        return webDriver.findElements(dom.getBy()).get(index);
    }

    public WebElement findUiElement(PageFactory.Dom dom) {
        return findUiElement(dom, dom.getIndex());
    }

    public static void main(String[] args) {
//        initDriverContext();
//        getDriverContext();
        WebDriver o = (WebDriver) Proxy.newProxyInstance(DriverFactory.class.getClassLoader(), new Class[]{WebDriver.class}, new DriverFactory());
        o.get("http://www.baidu.com");
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("*********************");
        WebDriverManager.chromedriver().setup();
        return method.invoke(new ChromeDriver(),args);
    }
}
