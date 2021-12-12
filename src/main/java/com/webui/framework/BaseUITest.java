package com.webui.framework;


import com.webui.framework.dataprovider.JsonDataProvider;
import com.webui.framework.facade.Driver;
import com.webui.framework.service.DriverClass;
import com.webui.framework.service.DriverFactory;
import com.webui.util.LogUtils;
import org.apache.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;

import java.lang.reflect.Method;
import java.util.Iterator;


public class BaseUITest {

    static Logger logger = Logger.getLogger(BaseUITest.class);

    public DriverClass type;
    public String appName;
    public Driver driver;

    public BaseUITest() {

    }

    @BeforeClass
    public void beforeClass() {
        readTestDeploy();
        DriverFactory.initDriverContext();
        driver = DriverFactory.getDriverContext();


    }

    public void readTestDeploy() {
        TestDeploy testDeploy = this.getClass().getAnnotation(TestDeploy.class);
        type = testDeploy.driver();
        appName = testDeploy.app();
        System.setProperty("appName", appName);
    }

    @BeforeMethod
    public void printHelp(Method method) {
        LogUtils.info(logger, "TestCase", method.getName(), "start");

    }

    @DataProvider(name = "jsonProvider")
    public Iterator<Object> JsonDataProvider(Method method) {
        return new JsonDataProvider(method);
    }

}
