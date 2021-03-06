package com.webui.framework;

import com.webui.framework.facade.Driver;
import com.webui.framework.facade.UiElement;
import com.webui.framework.service.PageFactory.Dom;
import com.webui.framework.service.DriverFactory;
import com.webui.framework.service.WebUIElement;
import com.webui.util.AssertUtils;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.function.Function;

public class ExpectedConditions {

    public static Function<UiElement, Boolean> isDisplayed() {
        return element -> ((WebUIElement) element).getElement().isDisplayed();
    }

    public static Function<UiElement, Boolean> isEnabled() {
        return element -> ((WebUIElement) element).getElement().isEnabled();
    }

    public static Function<UiElement, WebElement> ElementVisibility(Dom dom, int index) {
        return uiElement -> {
            List<WebElement> elementList = ((WebUIElement) uiElement).getElement().findElements(dom.getBy());
            AssertUtils.assertTrue(elementList.size() > 0, "no found but continue to search...");
            return elementList.get(index);
        };
    }

    public static Function<Driver, String> isTitle() {
        return Driver::getTitle;
    }

}
