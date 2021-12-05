package com.webui.util;

import com.webui.framework.facade.UiElement;
import com.webui.util.ExpectedCondition;

public class ExpectedConditions {

    public static ExpectedCondition<Boolean> isDisplayed() {
        return new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(UiElement uiElement) {
                return uiElement.isDisplayed();
            }
        };
    }

}
