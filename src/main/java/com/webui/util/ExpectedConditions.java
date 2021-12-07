package com.webui.util;

import com.webui.framework.facade.Driver;
import com.webui.framework.facade.UiElement;
import com.webui.framework.service.Dom;
import com.webui.framework.service.DriverFactory;
import com.webui.framework.service.WebUIElement;

public class ExpectedConditions {

    public static ExpectedCondition<Boolean> isDisplayed() {
        return new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(UiElement element) {
                return ((WebUIElement) element).getElement().isDisplayed();
            }
        };
    }

    public static ExpectedCondition<Boolean> isEnabled() {
        return new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(UiElement element) {
                return ((WebUIElement) element).getElement().isEnabled();
            }
        };
    }

}
