package com.sf.stepdefs;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.sf.constants.FileConstants;
import com.sf.pages.LoginPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import com.sf.utils.ConfigUtil;
import com.sf.utils.FileUtils;

import java.io.File;
import java.time.Duration;
import java.util.HashMap;
import java.util.Properties;


//@Listeners({com.sf.TestListener.class})
public class BaseStepDefinition {
    protected static Logger logger = LogManager.getLogger(BaseStepDefinition.class);

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


    public static void setConfig() {
        logger.info("---- Set configurations --------");
        spark = new ExtentSparkReporter(new File(FileConstants.REPORT_PATH));
        extent.attachReporter(spark);

        testData = FileUtils.readAllProperties(FileConstants.LOGIN_TESTDATA_FILE_PATH2);

        logger.info(testData.get("salesforce.url"));

    }


    public void setup() {

        WebDriver driver = BaseStepDefinition.getBrowserType("chrome", false);
        // WebDriver driver = com.sf.BaseTest.getBrowserType("firefox", false);
        threadLocalDriver.set(driver);
        logger.info("---- Driver created --------");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        setConfig();
//        test = extent.createTest(methodInfo.getName());
    }

    public String getProperty(String key) {
        return testData.getProperty(key);
    }

    public static WebDriver getDriver() {
        return threadLocalDriver.get();
    }

    public void removeDriver() {
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


        public LoginPage login(WebDriver driver, boolean rememberMe) {
            LoginPage loginPage = new LoginPage(driver);
            loginPage.setUsername(getProperty("username"));
            loginPage.setPassword(getProperty("password"));
            if (rememberMe) {
                loginPage.enableRememberMe();
            } else {
                loginPage.disableRememberMe();
            }

            loginPage.submit();
            return loginPage;

        }

        public LoginPage login(WebDriver driver) {

            return login(driver, false);
        }

}
