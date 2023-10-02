package com.sf;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import constants.FileConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import java.io.File;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.HashMap;
import java.util.Properties;

//import com.salesforce.constants.FileConstants;
//import com.salesforce.pages.LoginPage;
//import com.salesforce.utils.ConfigUtil;
//import com.salesforce.utils.FileUtils;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
import utils.ConfigUtil;
import utils.FileUtils;


//@Listeners({com.sf.TestListener.class})
public class BaseTest {
    protected static Logger logger = LogManager.getLogger(BaseTest.class);

    private static ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<>();

    private static Properties testData;

    //Extent Report
    static ExtentReports extent = new ExtentReports();
    static ExtentSparkReporter spark = null;
    public static ExtentTest test = null;


    public WebDriver openLoginPage() {
        WebDriver driver = getDriver();
        driver.get(getProperty("salesforce.url"));
        return driver;
    }

    @BeforeTest
    public static void setConfig() {
        logger.info("---- Set configurations --------");
        spark = new ExtentSparkReporter(new File(FileConstants.REPORT_PATH));
        extent.attachReporter(spark);

        testData = FileUtils.readAllProperties(FileConstants.LOGIN_TESTDATA_FILE_PATH2);

        logger.info(testData.get("salesforce.url"));

    }


    @BeforeMethod
    public void setup(Method methodInfo) {

        WebDriver driver = BaseTest.getBrowserType("chrome", false);
        // WebDriver driver = com.sf.BaseTest.getBrowserType("firefox", false);
        threadLocalDriver.set(driver);
        logger.info("---- Driver created --------");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));


        test = extent.createTest(methodInfo.getName());
    }

    public String getProperty(String key) {
        return testData.getProperty(key);
    }

    public static WebDriver getDriver() {
        return threadLocalDriver.get();
    }

    @AfterMethod
    public static void removeDriver() {
        getDriver().close();
        threadLocalDriver.remove();
        extent.flush();
    }

    public static WebDriver getBrowserType(String browserName, boolean headless) {
        WebDriver driver;


        String browserType = browserName.toLowerCase();

        switch (browserType) {
            case "chrome":
                if (headless) {
                    ChromeOptions co = new ChromeOptions();
                    co.addArguments("--headless");
                    driver = new ChromeDriver(co);
                } else {
                    ChromeOptions options = new ChromeOptions();
                    options.addArguments("--remote-allow-origins=*");

                    DesiredCapabilities dc = new DesiredCapabilities();
                    dc.setCapability(ChromeOptions.CAPABILITY, options);
                    options.merge(dc);

                    HashMap<String, Object> chromePref = new HashMap<>();
                    String downloadPath = ConfigUtil.getProperty("download.path");
                    chromePref.put("download.default_directory", downloadPath);
                    options.setExperimentalOption("prefs", chromePref);

                    driver = new ChromeDriver(options);
                }
                break;

            case "firefox":
                driver = new FirefoxDriver();
                break;

            case "safari":
                driver = new SafariDriver();
                break;
            case "edge":
                ;
                driver = new EdgeDriver();
                break;

            default:
                driver = null;
                break;
        }
        return driver;
    }


//        public LoginPage login(WebDriver driver, boolean rememberMe) {
//            LoginPage loginPage = new LoginPage(driver);
//            loginPage.setUsername(getProperty("username"));
//            loginPage.setPassword(getProperty("password"));
//            if (rememberMe) {
//                loginPage.enableRememberMe();
//            } else {
//                loginPage.disableRememberMe();
//            }
//
//            loginPage.submit();
//            return loginPage;
//
//        }
//
//        public LoginPage login(WebDriver driver) {
//
//            return login(driver, false);
//        }

}
