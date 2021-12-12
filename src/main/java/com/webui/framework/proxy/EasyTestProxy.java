package com.webui.framework.proxy;

import com.webui.framework.facade.UiElement;
import com.webui.framework.service.DriverFactory;
import com.webui.util.AssertUtils;
import com.webui.util.LogUtils;
import org.apache.log4j.Logger;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;


public class EasyTestProxy {

    private static final Logger logger = Logger.getLogger(EasyTestProxy.class);

    public static Object getUiElementProxy(InvocationHandler handler) {
        AssertUtils.assertNotNull(handler, "params Object is null!");
        Class<?> aClass = UiElement.class;
        return Proxy.newProxyInstance(aClass.getClassLoader(), new Class[]{aClass}, handler);
    }

    public static Object createProxy(Object object, InvocationHandler... handlers) {
        InvocationHandler handler;
        if (object == null) {
            logger.warn("The null object does not have a proxy!");
            return null;
        }
        handler = (proxy, method, args) -> method.invoke(object, args);
        // 如果实现了InvocationHandler 那么使用对象本身实现的处理逻辑
        if (object instanceof InvocationHandler) {
            handler = (InvocationHandler) object;
        }
        // 作为参数传进来的handle 优先级最高
        if (handlers.length != 0 && handlers[0] != null) {
            handler = handlers[0];
        }
        return Proxy.newProxyInstance(object.getClass().getClassLoader(), object.getClass().getInterfaces()
                , handler);
    }

}

