package com.webui.framework.facade;


import com.webui.framework.facade.SearchContext;

public interface UiElement extends SearchContext {

    public void click();


    public void submit();


    public void sendKeys(CharSequence... charSequences);


    public void clear();


    public String getTagName();


    public String getAttribute(String s);


    public boolean isSelected();


    public boolean isEnabled();


    public String getText();


    public boolean isDisplayed();

}
