package com.webui.framework.service;


import com.webui.exception.ExecuteTimeoutException;
import com.webui.exception.FrameWorkException;
import com.webui.framework.facade.Driver;
import com.webui.framework.facade.UiElement;
import com.webui.framework.facade.Wait;
import com.webui.framework.proxy.UiEasyTestProxy;
import com.webui.util.AssertUtils;
import com.webui.util.ConditionWait;
import com.webui.util.ExpectedConditions;
import com.webui.util.ParsePropertiesFile;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Sleeper;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;


public class WebUIElement implements UiElement {


    private static final Logger logger = Logger.getLogger(WebUIElement.class);

    private WebElement element;

    public Driver getDriver() {
        return driver;
    }

    private final Driver driver;

    private WebUIElement(Driver driver) {
        this.driver = driver;
    }

    public WebElement getElement() {
        return element;
    }


    public static class Element {

        public static InvocationHandler initUiHandler(Dom dom, int i) {
            return ((proxy, method, args) -> {
                WebUIElement webUIElement = new WebUIElement(DriverFactory.getDriverContext());
                webUIElement.element = webUIElement.conditionWait(
                        f -> {
                            List<WebElement> elementList = ((DriverFactory) DriverFactory.getDriverContext()).getWebDriver().findElements(dom.getBy());
                            AssertUtils.assertTrue(elementList.size() > 0, "elementList is empty!");
                            return elementList;
                        }, String.format("trying to %s find index %d", dom, i)).get(0);
                webUIElement.conditionWait(ExpectedConditions.isDisplayed(), "uiElement is not displayed!");
                webUIElement.conditionWait(ExpectedConditions.isEnabled(), "uiElement is not enable!");
                return method.invoke(webUIElement, args);

            });
        }
    }


    @Override
    public void click() {
        AssertUtils.assertNotNull(element, "element is null");
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


    public UiElement findUiElement(Dom by) {
        element = (WebElement) conditionWait(ExpectedConditions.ElementVisibility(by, 0), "find element!");
        return this;
    }

    @Override
    public UiElement findUiElement(Dom dom, int index) {
        element = conditionWait(ExpectedConditions.ElementVisibility(dom, index), "find element!");
        return this;
    }


    private <V> V conditionWait(Function<? super UiElement, V> isTrue, String message) {
        final UiElement u = this;

        return new Wait<UiElement>() {
            final long timeOut = Long.parseLong(ParsePropertiesFile.getProperty("timeout", "60"));
            final long gap = Long.parseLong(ParsePropertiesFile.getProperty("gap", "5"));
            final Clock clock = Clock.systemDefaultZone();
            final Sleeper sleeper = Sleeper.SYSTEM_SLEEPER;

            @Override
            public <R> R conditionWait(Function<? super UiElement, R> function) {

                Instant endTime = clock.instant().plus(Duration.ofSeconds(timeOut));
                while (true) {
                    Throwable thr = null;
                    try {
                        R apply = function.apply(u);
                        if (apply != null && (!apply.getClass().equals(Boolean.class) || apply.equals(Boolean.TRUE))) {
                            return apply;
                        }
                    } catch (Throwable throwable) {
                        thr = throwable;
                    }
                    if (endTime.isBefore(clock.instant())) {
                        String messages = String.format("Expected condition failed: [%s] total run[%s] second with %s second interval", message, timeOut, gap);
                        throw new ExecuteTimeoutException(messages, thr);
                    }
                    try {
                        sleeper.sleep(Duration.ofSeconds(gap));
                    } catch (InterruptedException exception) {
                        Thread.currentThread().interrupt();
                        throw new FrameWorkException("currentThread declared dead!");
                    }
                }
            }
        }.conditionWait(isTrue);


    }

    static int demo(Function<String, Integer> function) {
        return function.apply("2");
    }

    public static void main(String[] args) {
        System.out.println(demo(integer -> 1 + 1));
    }

}//        String errLog = String.format("NoSearchElement Error: trying to %s find index %d", by, index);

