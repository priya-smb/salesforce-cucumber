package stepDefinitions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "username")
    public WebElement username;

    @FindBy(id = "password")
    public WebElement password;

    @FindBy(id = "Login")
    public WebElement loginButton;

    @FindBy(xpath = "//*[@id='rememberUn']")
    public WebElement rememberMe;

    @FindBy(id = "error")
    public WebElement errorMessage;

    @FindBy(partialLinkText = "Forgot")
    public WebElement forgotPassword;

    @FindBy(id = "idcard-identity")
    public WebElement savedUsername;

    @FindBy(id = "un")
    public WebElement forgotUsername;

    @FindBy(xpath = "//a[text()='Return to Login']")
    public WebElement returnToLoginButton;

    @FindBy(id = "hint_back_chooser")
    public WebElement savedUsernameLink;

    @FindBy(id = "edit")
    public WebElement editListLink;

    @FindBy(className = "hintclearlink")
    public WebElement clearLink;


    @FindBy(id = "hint_save_edit")
    public WebElement saveChange;

    public void clearRememberMe() {
        this.savedUsernameLink.click();
        this.editListLink.click();
        this.clearLink.click();
        this.saveChange.click();
        disableRememberMe();
    }


    public void setUsername(String username) {
        this.username.clear();
        this.username.sendKeys(username);
    }

    public void setPassword(String password) {
        this.password.sendKeys(password);
    }

    public void submit() {
        this.loginButton.click();
    }

    public String getErrorMessage() {
        return this.errorMessage.getText();
    }

    public String getUsername() {
        return this.username.getText();
    }

    public boolean isRememberMeSelected() {
        return this.rememberMe.isSelected();
    }

    public void enableRememberMe() {
        if (!isRememberMeSelected()) {
            this.rememberMe.click();
        }
    }

    public void disableRememberMe() {
        if (isRememberMeSelected()) {
            this.rememberMe.click();
        }
    }

    public void clickForgotPwd() {
        this.forgotPassword.click();
    }


}

