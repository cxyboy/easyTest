package com.webui.framework.service;


import com.webui.exception.ExecuteTimeoutException;
import com.webui.exception.SearchUiElementException;
import com.webui.framework.facade.UiElement;
import com.webui.util.ConditionWait;
import com.webui.util.LogUtils;
import org.openqa.selenium.WebElement;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;


public class PageElement extends ConditionWait implements UiElement {
    private WebElement element;
    private DriverFactory driver;
    private int temp;

    public static class Element extends PageElement {

        private Element(DriverFactory driver, Dom dom, int i) {
            super.driver = driver;
            super.findUiElement(dom, i);
        }


        public static Element build(DriverFactory driver, Dom dom, int... index) {

            return new Element(driver, dom, index.length != 0 && index[0] > 0 ? index[0] : 0);
        }
    }

    private PageElement() {

    }


    @Override
    public void click() {
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
            List<WebElement> elements = driver.getWebDriver().findElements(by.getBy());
            return elements.isEmpty() ? null : (element = elements.get(index));
        }, errLog, timeOut, gap);
    }


    public UiElement findUiElement(Dom by) {
        findElements(by, 0);
        return this;
    }

    @Override
    public UiElement findUiElement(Dom dom, int index) {
        findElements(dom, index);
        return this;
    }


    @Override
    public boolean isDisplayed() {
        return conditionWait(f -> element.isDisplayed());
    }


    private <V> V conditionWait(Function<? super Object, V> isTrue, Object... objects) {
        Arrays.stream(objects).forEach(this::parseObject);
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
