package com.webui.framework.service;


import com.webui.exception.ExecuteTimeoutException;
import com.webui.exception.FrameWorkException;
import com.webui.framework.facade.UiElement;
import com.webui.framework.facade.Wait;
import com.webui.util.AssertUtils;
import com.webui.framework.ExpectedConditions;
import com.webui.util.ConditionWait;
import com.webui.util.ParsePropertiesFile;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Sleeper;

import java.lang.reflect.InvocationHandler;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;

import static org.apache.commons.lang.time.DateUtils.MILLIS_PER_SECOND;


public class WebUIElement implements UiElement {


    private static final Logger logger = Logger.getLogger(WebUIElement.class);

    private WebElement element;


    public WebElement getElement() {
        return element;
    }


    public static class Element {

        public static InvocationHandler initUiHandler(Dom dom) {
            return ((proxy, method, args) -> {
                WebUIElement webUIElement = new WebUIElement();
                webUIElement.element = webUIElement.conditionWait(
                        f -> DriverFactory.getDriverContext().findUiElement(dom)
                        , String.format("trying find element to %s!", dom));
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
        findUiElement(by, by.getIndex());
        return this;
    }

    @Override
    public UiElement findUiElement(Dom dom, int index) {
        element = conditionWait(ExpectedConditions.ElementVisibility(dom, index), String.format("trying find element to %s!", dom));
        return this;
    }

    private <T> T conditionWait(Function<? super UiElement, T> isTrue, String message) {
        final long timeOut = Long.parseLong(ParsePropertiesFile.getProperty("timeout", "60"));
        final long gap = Long.parseLong(ParsePropertiesFile.getProperty("gap", "5"));
        return new ConditionWait(this,timeOut,gap).setMessage(message).conditionWait(isTrue);
/*
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
                        Thread.sleep(Duration.ofSeconds(gap).toMillis());
                    } catch (InterruptedException exception) {
                        Thread.currentThread().interrupt();
                        throw new FrameWorkException("currentThread declared dead!");
                    }
                }
            }
        }.conditionWait(isTrue);
 */

    }


}

