package com.sf.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends UserMenuPage {

    @FindBy(xpath = "//li[@id='Opportunity_Tab']//a")
    private WebElement optyTab;

    public HomePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void selectOptyTab() {
        optyTab.click();
    }


}
