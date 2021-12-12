package com.webui.framework.facade;


public interface UiElement extends SearchContext<UiElement> {



    public void click();


    public void submit();


    public void sendKeys(CharSequence... charSequences);


    public void clear();


    public String getTagName();


    public String getAttribute(String s);


    public boolean isSelected();


    public String getText();


}
