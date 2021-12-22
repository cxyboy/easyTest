package com.webui.util;


import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.proxy.CaptureType;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;


public class ProxySelena {


    /**
     * BrowserMobProxy
     */
    private BrowserMobProxy proxy;

    /**
     * WebDriver
     */
    private WebDriver driver;


    public BrowserMobProxy getProxy() {
        return proxy;
    }

    private void setProxy(BrowserMobProxy proxy) {
        this.proxy = proxy;
    }

    public WebDriver getDriver() {
        return driver;
    }

    private void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * 无参构造 初始化参数
     * 私有构造 禁止外部new
     */
    private ProxySelena() {
        System.setProperty("webdriver.chrome.driver", "D:\\BrowserDowload\\chromedriver_win32\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        // 禁用阻止弹出窗口
        options.addArguments("--disable-popup-blocking");
        // 启动无沙盒模式运行
        options.addArguments("no-sandbox");
        // 禁用扩展
        options.addArguments("disable-extensions");
        // 默认浏览器检查
        options.addArguments("no-default-browser-check");
        options.addArguments("ignore-certificate-errors");

        Map<String, Object> prefs = new HashMap();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        // 禁用保存密码提示框
        options.setExperimentalOption("prefs", prefs);

        // set performance logger
        // this sends Network.enable to chromedriver
        LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
        options.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);

        // start the proxy
        BrowserMobProxy proxy = new BrowserMobProxyServer();
        // 端口号
        proxy.start(4566);

        // get the Selenium proxy object
        Proxy seleniumProxy = ClientUtil.createSeleniumProxy(proxy);
        options.setCapability(CapabilityType.PROXY, seleniumProxy);
        // start the browser up
        WebDriver driver = new ChromeDriver(options);
        // 等待10秒
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        // enable more detailed HAR capture, if desired (see CaptureType for the complete list)
        proxy.enableHarCaptureTypes(CaptureType.REQUEST_CONTENT, CaptureType.RESPONSE_CONTENT);
        // enable cookies  --> REQUEST_COOKIES, RESPONSE_COOKIES
        proxy.enableHarCaptureTypes(CaptureType.getCookieCaptureTypes());
        // enable headers --> REQUEST_HEADERS, RESPONSE_HEADERS
        //proxy.enableHarCaptureTypes(CaptureType.getHeaderCaptureTypes());
        // 向对象赋值
        this.setProxy(proxy);
        this.setDriver(driver);
    }

    /**
     * 类级的内部类，也就是静态的成员式内部类，该内部类的实例与外部类的实例没有绑定关系，而且只有被调用到才会装载，从而实现了延迟加载
     */
    private static class BrowserProxyHolder {
        private final static ProxySelena instance = new ProxySelena();
    }

    /**
     * 提供公有调用方法
     *
     * @return
     */
    public static ProxySelena getInstance() {
        return BrowserProxyHolder.instance;
    }
}

/**
 * BrowserProxy 单例
 *
 * @author ant
 * @date 2019-11-21 10:38:20
 */



/*
public class TestProxy {

    public void tearDown(WebDriver driver, BrowserMobProxy browserMobProxy) {
        if (browserMobProxy.isStarted()) {
            browserMobProxy.stop();
        }
        driver.close();

    }


    public void testMethod(WebDriver driver, BrowserMobProxy proxy) {
        // 这里必需在driver.get(url)之前
        proxy.newHar("test_har");

        driver.get("https://mail.163.com/");

        String text = "selenium";
        //
//        WebElement element = driver.findElement(By.xpath("//*[@id=\"login-form\"]"));
//        if (element==null){
//            return;
//        }
//        driver.switchTo().frame(element);
//        driver.findElement(By.id("account-box")).sendKeys("844515154");
//        driver.findElement(By.linkText("登录")).click();

        Actions actions = new Actions(driver);
        actions.sendKeys("18573208753").perform();
        actions.sendKeys(Keys.TAB).sendKeys("xlc18573208753").sendKeys(Keys.ENTER).perform();

        Har har = proxy.getHar();
        List<HarEntry> list = har.getLog().getEntries();
        for (HarEntry harEntry : list) {
            HarResponse harResponse = harEntry.getResponse();
            if (harEntry.getRequest() != null && harEntry.getRequest().getMethod().equals("POST")) {
                System.out.println("请求体：" + harEntry.getRequest().getPostData().getText());
                HarContent content = harResponse.getContent();
                String responseBody = content.getText();
                System.out.println(content);
//            List<HarCookie> cookies = harResponse.getCookies();
                System.out.println("responseBody:" + responseBody);
            }



            // 可获取httpOnly 类型的cookie
//            System.out.println("cookies:"+cookies);
        }
    }


    public static void main(String[] args) throws Exception{
        TestProxy testProxy = new TestProxy();
        ProxySelena instance = ProxySelena.getInstance();
        WebDriver driver = instance.getDriver();
        BrowserMobProxy proxy = instance.getProxy();
        testProxy.testMethod(driver, proxy);

        Thread.sleep(5000);
        testProxy.tearDown(driver, proxy);


    }

}

 */