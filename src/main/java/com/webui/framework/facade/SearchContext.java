package com.webui.framework.facade;

import com.webui.framework.service.Dom;

public interface SearchContext<T> {

    T findUiElement(Dom dom, int index);

    T findUiElement(Dom dom);

}
