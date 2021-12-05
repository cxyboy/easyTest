package com.webui.framework;


import com.webui.framework.service.DriverClass;
import com.webui.util.LogUtils;
import org.apache.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.lang.reflect.Method;


public class BaseUITest {

    static Logger logger = Logger.getLogger(BaseUITest.class);

    public DriverClass type;
    public String appName;

    public BaseUITest() {

    }


    @BeforeClass
    public void parseTestDeploy() {
        TestDeploy testDeploy = this.getClass().getAnnotation(TestDeploy.class);
        type = testDeploy.driver();
        appName = testDeploy.app();
    }

    @BeforeMethod
    public void printHelp(Method method) {
        LogUtils.info(logger,"============= TestCase {} start ==============",method.getName());

    }

}
