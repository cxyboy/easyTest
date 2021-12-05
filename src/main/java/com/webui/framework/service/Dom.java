package com.webui.framework.service;


import com.webui.framework.facade.UiElement;
import com.webui.util.AssertUtils;
import org.openqa.selenium.By;

import com.webui.framework.facade.Driver;

public class Dom {


    private enum Locator {
        XPATH,
        ID,
        CSS,
        NAME,
        LINK;
    }

    private Dom(Locator type, String value) {
        locator = type;
        this.value = value;
        switch (type) {
            case XPATH:
                setBy(By.xpath(value));
                break;
            case ID:
                setBy(By.id(value));
                break;
            case CSS:
                setBy(By.cssSelector(value));
                break;
            case LINK:
                setBy(By.linkText(value));
                break;
            case NAME:
                setBy(By.name(value));
                break;
        }
    }

    private By by;
    private final Locator locator;
    private final String value;
    private static final StringBuffer buffer;

    static {
        buffer = new StringBuffer();
    }

    private void setBy(By bys) {
        this.by = bys;
    }

    public By getBy() {
        return by;
    }

    public static Dom id(String using) {
        return new Dom(Locator.ID, using);
    }

    public static Dom xpath(String using) {
        return new Dom(Locator.XPATH, using);

    }

    public static Dom css(String using) {
        return new Dom(Locator.CSS, using);
    }


    public UiElement findUiElement(Driver driver, int... index) {
        AssertUtils.assertNotNull(driver, "没有浏览器驱动实例");
        return PageElement.Element.build((DriverFactory) driver, this, index);
    }

    public String toString() {
        buffer.setLength(0);
        return buffer.append("Dom.").append(locator).append("=").append(value).toString();
    }

}
