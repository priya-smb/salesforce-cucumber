package com.sf.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ForgotPwdPage {

    public ForgotPwdPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "un")
    public WebElement username;

    @FindBy(id = "continue")
    public WebElement continueBtn;

    @FindBy(xpath = "//h1[@id='header']")
    public WebElement checkEmailMsg;

    public void setUsername(String username) {
        this.username.sendKeys(username);
    }

    public void submit() {
        continueBtn.click();

    }

}
