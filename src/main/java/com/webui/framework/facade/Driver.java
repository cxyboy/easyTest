package com.webui.framework.facade;


import com.webui.framework.service.DriverClass;
import org.openqa.selenium.WebElement;

import java.util.Set;

public interface Driver extends SearchContext<WebElement> {

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
