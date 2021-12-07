package com.webui.framework.proxy;

import com.webui.framework.facade.UiElement;
import com.webui.framework.service.DriverFactory;
import com.webui.util.AssertUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


public class UiEasyTestProxy {

    public static Object getUiElementProxy(InvocationHandler handler) {
        AssertUtils.assertNotNull(handler, "params Object is null!");
//        InvocationHandler handler = (proxy, method, args) -> method.invoke(object, args);
        Class<?> aClass = UiElement.class;
        return Proxy.newProxyInstance(aClass.getClassLoader(), new Class[]{aClass}, handler);
    }

    public static Object getProxy(Class<?> clazz) {
        InvocationHandler handler = (proxy, method, args) -> method.invoke(args);
        return Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, handler);

    }

    public static void main(String[] args) throws Exception {
//        new UiEasyTestProxy().getProxy(new WebUIElement.Element(null, null, 0));
    }

}

