package com.webui.framework.service;


import com.webui.exception.ParamsException;
import com.webui.framework.facade.UiElement;
import com.webui.framework.proxy.EasyTestProxy;
import com.webui.util.AssertUtils;
import org.openqa.selenium.By;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class Dom {


    private Dom(String locType, String code, int index) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        this.locType = locType;
        this.value = code;
        this.index = index;
        Method method = By.class.getMethod(locType, String.class);
        by = (By) method.invoke(By.class, code);

    }


    @Override
    public String toString() {
        return locType + "=" + value + "&&" + "index=" + index;
    }


    private final By by;
    private final String locType;
    private final String value;
    private final int index;

    public By getBy() {
        return by;
    }

    public int getIndex() {
        return index;
    }

    public static UiElement id(String using) {
        return id(using, 0);
    }

    public static UiElement id(String using, int index) {
        return (UiElement) EasyTestProxy.getUiElementProxy(WebUIElement.Element.initUiHandler(buildUiDom("id", using, index)));
    }

    public static UiElement xpath(String using) {
        return xpath(using, 0);

    }

    public static UiElement xpath(String using, int index) {
        return (UiElement) EasyTestProxy.getUiElementProxy(WebUIElement.Element.initUiHandler(buildUiDom("xpath", using, index)));
    }

    public static UiElement css(String using) {
        return css(using, 0);
    }

    public static UiElement css(String using, int index) {
        return (UiElement) EasyTestProxy.getUiElementProxy(WebUIElement.Element.initUiHandler(buildUiDom("css", using, index)));

    }

    public static UiElement className(String using) {
        return className(using, 0);
    }

    public static UiElement className(String using, int index) {
        return (UiElement) EasyTestProxy.getUiElementProxy(WebUIElement.Element.initUiHandler(buildUiDom("class", using, index)));

    }


    public static UiElement linkText(String using) {
        return linkText(using, 0);
    }

    public static UiElement linkText(String using, int index) {
        return (UiElement) EasyTestProxy.getUiElementProxy(WebUIElement.Element.initUiHandler(buildUiDom("link", using, index)));
    }

    public static Dom buildUiDom(String locType, String using, int index) {
        AssertUtils.assertStringIsBlank(locType, "定位参数属性是null");
        AssertUtils.assertStringIsBlank(using, "定位参数属性的值是null");
        try {
            return new Dom(locType, using, index);
        } catch (Exception e) {
            String message = String.format("定位方法列表:[id,xpath,css,name,link,class] 不包含 [%s]", locType);
            throw new ParamsException(message, e);
        }

    }

    public static void main(String[] args) {
        buildUiDom("od","3",1);
    }
}
/*


    XPATH("xpath定位", "xpath") {
        public void init(){

        }
    },
    ID("id属性定位", "id") {
        @Override
        public void init() {
            setBy(By.id(super.value));
        }
    },
    CSS("css选择器定位", "css") {
        @Override
        public void init() {

        }
    },
    NAME("name属性定位", "name") {
        @Override
        public void init() {

        }
    },
    CLASS("class属性定位", "className") {
        @Override
        public void init() {

        }
    },
    LINK("a标签定位", "linkText") {
        @Override
        public void init() {
            LINK.by = By.linkText(LINK.value);
        }
    };
 */