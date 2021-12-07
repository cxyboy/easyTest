package com.webui.framework.service;


import com.webui.exception.ParamsException;
import com.webui.framework.facade.UiElement;
import com.webui.framework.proxy.UiEasyTestProxy;
import com.webui.util.AssertUtils;
import org.openqa.selenium.By;

import java.util.Arrays;

public class Dom {


    private enum Locator {
        XPATH("xpath定位"),
        ID("id属性定位"),
        CSS("css选择器定位"),
        NAME("name属性定位"),
        CLASS("class属性定位"),
        LINK("a标签定位");
        private final String desc;

        Locator(String desc) {
            this.desc = desc;
        }

        @Override
        public String toString() {
            return desc;
        }
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

    public static UiElement id(String using) {
        return (UiElement) UiEasyTestProxy.getUiElementProxy(WebUIElement.Element.initUiHandler(buildUiDom("id", using), 0));
    }

    public static UiElement xpath(String using) {
        return (UiElement) UiEasyTestProxy.getUiElementProxy(WebUIElement.Element.initUiHandler(buildUiDom("xpath", using), 0));

    }

    public static UiElement css(String using) {
        return (UiElement) UiEasyTestProxy.getUiElementProxy(WebUIElement.Element.initUiHandler(buildUiDom("css", using), 0));

    }

    public static UiElement className(String using) {
        return (UiElement) UiEasyTestProxy.getUiElementProxy(WebUIElement.Element.initUiHandler(buildUiDom("class", using), 0));

    }


    public String toString() {
        buffer.setLength(0);
        return buffer.append("Dom.").append(locator).append("=").append(value).toString();
    }

    public static Dom buildUiDom(String locType, String using) {
        AssertUtils.assertStringIsBlank(locType, "定位参数属性是null");
        AssertUtils.assertStringIsBlank(using, "定位参数属性的值是null");
        try {
            return new Dom(Locator.valueOf(locType.toUpperCase()), using);
        } catch (RuntimeException e) {
            String message = String.format("params:[%s] is not in %s", locType, Arrays.toString(Locator.values()));
            throw new ParamsException(message, e);
        }

    }

}
