package com.webui.framework.service;


import com.webui.framework.facade.Driver;
import com.webui.framework.facade.UiElement;
import com.webui.framework.facade.Wait;
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


public class WebUIElement implements UiElement {


    private static final Logger logger = Logger.getLogger(WebUIElement.class);

    private WebElement element;
    private Driver driver;
    private final Wait<UiElement> uiElementWait;

    private WebUIElement() {
        System.out.println("page_element_init");
        this.uiElementWait = new ConditionWait(this);
    }

    public WebElement getElement() {
        return element;
    }


    public static class Element {

        public static InvocationHandler initUiHandler(Dom dom, int i) {
            return ((proxy, method, args) -> {
                WebUIElement webUIElement = new WebUIElement();
                webUIElement.element = webUIElement.uiElementWait.conditionWait(
                        f -> ((DriverFactory) DriverFactory.getDriverContext()).getWebDriver().findElement(dom.getBy()));
                method.invoke(webUIElement, args);
                return proxy;
            });
        }
    }


    @Override
    public void click() {
        AssertUtils.assertNotNull(element, "element is null");
        AssertUtils.assertTrue(uiElementWait.conditionWait(ExpectedConditions.isDisplayed()), "uiElement is not displayed!");
        AssertUtils.assertTrue(uiElementWait.conditionWait(ExpectedConditions.isEnabled()), "uiElement is not enable!");
        element.click();

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
    public String getText() {
        return element.getText();
    }

    private void findElements(Dom by, int index) {
        String errLog = String.format("NoSearchElement Error: trying to %s find index %d", by, index);
        conditionWait(f -> {
            List<WebElement> elements = ((DriverFactory) driver).getWebDriver().findElements(by.getBy());
            return elements.isEmpty() ? null : (element = elements.get(index));
        }, errLog);
    }


    public UiElement findUiElement(Dom by) {
        return null;
    }

    @Override
    public UiElement findUiElement(Dom dom, int index) {
        return null;
    }


    private <V> V conditionWait(Function<? super Driver, V> isTrue, Object... objects) {
//        Arrays.stream(objects).forEach(this::parseObject);
        return null;
    }


}
