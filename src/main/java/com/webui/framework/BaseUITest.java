package com.webui.framework;


import com.webui.framework.service.DriverClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;


public class BaseUITest {

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
    public void printHelp() {
        System.out.printf("============= TestCase %s start ==============", this.getClass().getSimpleName());

    }

}
