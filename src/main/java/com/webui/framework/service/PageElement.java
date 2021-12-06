package com.webui.framework.service;


import com.webui.framework.facade.Driver;
import com.webui.framework.facade.UiElement;
import com.webui.framework.proxy.UiEasyTestProxy;
import com.webui.util.AssertUtils;
import com.webui.util.ConditionWait;
import com.webui.util.ExpectedConditions;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;


public class PageElement extends ConditionWait implements UiElement, InvocationHandler {


    private static final Logger logger = Logger.getLogger(PageElement.class);

    private WebElement element;
    private Driver driver;
    private int temp;
    private Dom dom;
    private int index;

    private PageElement() {

    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        findElements(dom, index);
        return method.invoke(this, args);
    }

    public static class Element extends PageElement {

        public Element(Driver driver, Dom dom, int i) {
            super.driver = driver;
            super.dom = dom;
            super.index = i;
        }
    }


    @Override
    public void click() {
        AssertUtils.assertNotNull(element, "element is null");
        Object o = conditionWait(ExpectedConditions.isDisplayed(dom), "");
        AssertUtils.assertTrue(true, "");
        if (isDisplayed() && isEnabled()) {
            element.click();
        }
    }

    @Override
    public void submit() {
        //
    }

    @Override
    public void sendKeys(CharSequence... charSequences) {
        element.sendKeys(charSequences);
    }

    @Override
    public void clear() {
        element.clear();
    }

    @Override
    public String getTagName() {
        return element.getTagName();
    }

    @Override
    public String getAttribute(String s) {
        return element.getAttribute(s);
    }

    @Override
    public boolean isSelected() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return conditionWait(f -> element.isEnabled());
    }

    @Override
    public String getText() {
        return element.getText();
    }

    private void findElements(Dom by, int index) {
        String errLog = String.format("NoSearchElement Error: trying to %s find index %d", by, index);
        conditionWait(f -> {
            List<WebElement> elements = ((DriverFactory) driver).getWebDriver().findElements(by.getBy());
            return elements.isEmpty() ? null : (element = elements.get(index));
        }, errLog, timeOut, gap);
    }


    public UiElement findUiElement(Dom by) {
        return (UiElement) UiEasyTestProxy.getUiElementProxy(this);
    }

    @Override
    public UiElement findUiElement(Dom dom, int index) {
        return (UiElement) UiEasyTestProxy.getUiElementProxy(this);
    }


    @Override
    public boolean isDisplayed() {
        return conditionWait(f -> element.isDisplayed());
    }


    private <V> V conditionWait(Function<? super Driver, V> isTrue, Object... objects) {
        Arrays.stream(objects).forEach(this::parseObject);
        super.setDriver(driver);
        return super.conditionWait(isTrue);
    }

    private void parseObject(Object o) {
        long lon = 0L;
        switch (o.getClass().getSimpleName()) {
            case "String":
                errMessage = String.valueOf(o);
                break;
            case "long":
                temp++;
                lon = temp >= 2 ? gap = Long.parseLong(String.valueOf(o)) : (timeOut = Long.parseLong(String.valueOf(o)));
                break;
        }

        if (gap > timeOut) {
            timeOut = gap;
            gap = lon;
        }


    }
}
