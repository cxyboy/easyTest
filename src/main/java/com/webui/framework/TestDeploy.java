package com.webui.framework;

import com.webui.framework.service.DriverClass;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface TestDeploy {

    DriverClass driver() default DriverClass.CHROME;

    String app() default "";

}
