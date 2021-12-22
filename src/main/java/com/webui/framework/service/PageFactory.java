package com.webui.framework.service;


import com.webui.exception.ParamsException;
import com.webui.framework.facade.Driver;
import com.webui.framework.facade.UiElement;
import com.webui.framework.proxy.EasyTestProxy;
import com.webui.util.AssertUtils;
import org.openqa.selenium.By;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class PageFactory {

    public Driver driver;

    public UiElement id(String x, int v) {
        return Dom.id(x, v, driver);
    }

    public UiElement id(String x) {
       return id(x, 0);
    }

    public static class Dom {


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


        public static UiElement id(String using, int index, Driver driver) {
            return (UiElement) EasyTestProxy.getUiElementProxy(WebUIElement.Element.initUiHandler(driver, buildUiDom("id", using, index)));
        }


        public static UiElement xpath(String using, int index, Driver driver) {
            return (UiElement) EasyTestProxy.getUiElementProxy(WebUIElement.Element.initUiHandler(driver, buildUiDom("xpath", using, index)));
        }


        public static UiElement css(String using, int index, Driver driver) {
            return (UiElement) EasyTestProxy.getUiElementProxy(WebUIElement.Element.initUiHandler(driver, buildUiDom("css", using, index)));

        }


        public static UiElement className(String using, int index, Driver driver) {
            return (UiElement) EasyTestProxy.getUiElementProxy(WebUIElement.Element.initUiHandler(driver, buildUiDom("class", using, index)));

        }


        public static UiElement linkText(String using, int index, Driver driver) {
            return (UiElement) EasyTestProxy.getUiElementProxy(WebUIElement.Element.initUiHandler(driver, buildUiDom("link", using, index)));
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

    }
}
