package com.webui.util;


import com.webui.framework.facade.Driver;
import com.webui.framework.facade.UiElement;

import java.util.function.Function;

public interface ExpectedCondition<T> extends Function<UiElement, T> {
}
