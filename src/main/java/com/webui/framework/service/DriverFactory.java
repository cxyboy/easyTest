package com.webui.framework.service;


import com.webui.exception.ConfigException;
import com.webui.framework.facade.Driver;
import com.webui.framework.facade.UiElement;

import com.webui.util.AssertUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;


import java.io.File;
import java.util.Set;

import static com.webui.util.ParsePropertiesFile.getObject;

public class DriverFactory implements Driver {

    private WebDriver webDriver;
    private final String rootPath = getObject("driver_path");


    public DriverFactory() {
    }

    private String checkPath(String... path) {
        for (String f : path) {
            System.out.println("Got path:" + f);
            File file = new File(f);
            if (file.exists()) {
                System.out.println("Exist path:" + f);
                return f;
            }
        }
        throw new ConfigException("driver path is notFound!");

    }


    @Override
    public void createDriver(DriverClass type) {
        String path = checkPath(rootPath + type.getCode() + (System.getenv("os").toLowerCase().contains("windows") ? ".exe" : ""));
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
        AssertUtils.assertNotNull(dom, "Dom不能是null");
        AssertUtils.assertNotNull(index, "Dom索引值不可以是null");
        return dom.findUiElement(this, index);
    }

    public UiElement findUiElement(Dom dom) {
        AssertUtils.assertNotNull(dom, "Dom不能是null");
        return dom.findUiElement(this);
    }
}
