package com.sf.utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import com.sf.constants.FileConstants;
import com.sf.constants.WaitConstants;


public class CommonUtils {
        public static String getStringDateAndTimeStamp() {
            String value = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            return value;
        }

        public static String getScreenshot(WebDriver driver) throws IOException {
            String path = FileConstants.SCREENSHOT_PATH;
            TakesScreenshot screen = (TakesScreenshot) driver;
            if (screen != null) {
                File src = screen.getScreenshotAs(OutputType.FILE);
                File dst = new File(path);
                FileUtils.copyFile(src, dst);
            }
            return path;
        }

        public static boolean waitForElement(WebDriver driver, WebElement element) {
            boolean isElementClickable = false;
            WebDriverWait wait = new WebDriverWait(driver, WaitConstants.WAIT_FOR_ELEMENT);
            try {
                wait.until(ExpectedConditions.elementToBeClickable(element));
                isElementClickable = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return isElementClickable;
        }

        public static void waitForAll(WebDriver driver) {

            FluentWait wait = new FluentWait(driver);

            //Specify the timout of the wait

            wait.withTimeout(Duration.ofSeconds(5));

            //Specify polling time
            wait.pollingEvery(Duration.ofMillis(250));

            //Specify what exceptions to ignore
            wait.ignoring(NoSuchElementException.class);

//        wait.until(ExpectedConditions.vis());
        }


        public static boolean waitForElementToDisappear(WebDriver driver, WebElement element) {
            boolean isElementFound = false;
            WebDriverWait wait = new WebDriverWait(driver, WaitConstants.WAIT_FOR_ELEMENT_TO_DISAPPEAR);
            try {
                wait.until(ExpectedConditions.invisibilityOf(element));
                isElementFound = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return isElementFound;
        }

        public static void moveToElement(WebDriver driver, WebElement element) {
            Actions action = new Actions(driver);
            action.moveToElement(element).build().perform();
        }

        public static boolean isAlertPresent(WebDriver driver){
            boolean foundAlert = false;
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(0) /*timeout in seconds*/);
            try {
                wait.until(ExpectedConditions.alertIsPresent());
                foundAlert = true;
            } catch (TimeoutException eTO) {
                foundAlert = false;
            }
            return foundAlert;
        }

    }
