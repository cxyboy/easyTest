package com.webui.framework.facade;

import com.webui.framework.service.Dom;

public interface SearchContext {

    UiElement findUiElement(Dom dom, int index);
    UiElement findUiElement(Dom dom);

}
