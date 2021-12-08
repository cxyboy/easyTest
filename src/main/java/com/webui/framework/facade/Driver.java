package com.webui.framework.facade;


import com.webui.framework.service.DriverClass;
import org.openqa.selenium.WebDriver;

import java.util.Set;

public interface Driver extends SearchContext {

    void createDriver(DriverClass type);

    void get(String url);

    String getCurrentUrl();

    String getTitle();

    void close();

    void quit();

    Set<String> getWindowHandles();

    String getWindowHandle();

    boolean switchTo();

    Driver navigate();

    Driver manage();


}
