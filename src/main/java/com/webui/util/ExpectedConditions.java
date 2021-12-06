package com.webui.util;

import com.webui.framework.facade.Driver;
import com.webui.framework.service.Dom;
import com.webui.framework.service.DriverFactory;

public class ExpectedConditions {

    public static ExpectedCondition<Boolean> isDisplayed(Dom dom) {
        return new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(Driver driver) {
                return ((DriverFactory) driver).getWebDriver().findElement(dom.getBy()).isDisplayed();
            }
        };
    }

    public static ExpectedCondition<Boolean> isEnabled(Dom dom) {
        return new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(Driver driver) {
                return ((DriverFactory) driver).getWebDriver().findElement(dom.getBy()).isEnabled();
            }
        };
    }

}
