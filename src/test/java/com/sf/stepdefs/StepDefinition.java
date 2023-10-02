package com.sf.stepdefs;

import com.sf.pages.*;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.util.List;

public class StepDefinition extends BaseStepDefinition {

    LoginPage loginPage;
    WebDriver driver;
    UserMenuPage userMenuPage;
    ForgotPwdPage forgotPwdPage;
    HomePage homePage;
    MyProfilePage myProfilePage;

    @Before
    public void setup() {
        super.setup();
        driver = getDriver();
        userMenuPage = new UserMenuPage(driver);
        loginPage = new LoginPage(driver);
        forgotPwdPage = new ForgotPwdPage(driver);
        homePage = new HomePage(driver);
        myProfilePage = new MyProfilePage(driver);
    }

    @After
    public void tearDown() {
        super.removeDriver();
    }

    @Given("user opens the login page")
    public void user_opens_the_login_page() {

        openLoginPage();
    }

    @When("user enters valid user name")
    public void user_enters_valid_user_name() {
        loginPage.setUsername(getProperty("username"));

    }

    @When("user enters valid password")
    public void user_enters_valid_password() {

        loginPage.setPassword(getProperty("password"));
    }

    @When("user clicks on login button")
    public void user_clicks_on_login_button() {
        loginPage.submit();

    }

    @Then("user should be navigated to the home page")
    public void user_should_be_navigated_to_the_home_page() {
        Assert.assertEquals(userMenuPage.userMenuDDBtn.getText(), "Priyanka Selenium");

    }

    @When("user enters invalid password")
    public void user_enters_invalid_password() {
        loginPage.setPassword("abcd");
    }

    @Then("user should be be displayed with an error message")
    public void user_should_be_be_displayed_with_an_error_message() {
        Assert.assertEquals(loginPage.getErrorMessage(), "Please check your username and password. If you still can't log in, contact your Salesforce administrator.");

    }

    @When("user enables remember me")
    public void user_enables_remember_me() {

        loginPage.enableRememberMe();

    }

    @When("user clicks on userMenu dropdown and clicks on logout")
    public void user_clicks_on_user_menu_dropdown_and_clicks_on_logout() {

        homePage.showUserMenu();
        homePage.logout();
    }

    @Then("user should go to login page with the username displayed already")
    public void user_should_go_to_login_page_with_the_username_displayed_already() {
        Assert.assertTrue(loginPage.isRememberMeSelected());
    }


    @When("user clicks on forgot password")
    public void user_clicks_on_forgot_password() {

        loginPage.clickForgotPwd();
    }

    @When("user enters username and submit continue")
    public void user_enters_username_and_submit_continue() {

        forgotPwdPage.setUsername(getProperty("username"));
        forgotPwdPage.submit();

    }

    @Then("user should be displayed with an error message")
    public void user_should_be_displayed_with_an_error_message() {

        Assert.assertEquals(forgotPwdPage.checkEmailMsg.getText(), "Check Your Email");
    }

    @When("user checks for user menu for <username> drop down")
    public void user_checks_for_user_menu_for_username_drop_down() {

        Assert.assertEquals(userMenuPage.userMenuDDBtn.getText(), "Priyanka Selenium");
    }

    @Then("user clicks on the user menu for <username> drop down")
    public void userClicksOnTheUserMenuForUsernameDropDown() {
        selectUserNameMenu();
    }

    public void selectUserNameMenu() {
        userMenuPage.showUserMenu();
        List<String> userMenuOptions = userMenuPage.getUserMenuOptions();
        String[] expectedMenuItems = {
                "My Profile",
                "My Settings",
                "Developer Console",
                "Switch to Lightning Experience",
                "Logout"
        };

        for (String expectedValue : expectedMenuItems) {
            Assert.assertListContainsObject(userMenuOptions, expectedValue, "missing an expected item");
        }
    }

    @When("user selects user menu for <username> drop down[TC01]")
    public void user_selects_user_menu_for_username_drop_down_tc01() {
        selectUserNameMenu();
    }

    @When("user clicks {string} option from user menu")
    public void user_clicks_option_from_user_menu(String string) {
        userMenuPage.selectUserMenu("My Profile");
        Assert.assertEquals(myProfilePage.getProfileName(), "Priyanka Selenium ");
        myProfilePage.closeRemindersWindow();
    }
    @When("user clicks on edit profile button to edit contact information")
    public void user_clicks_on_edit_profile_button_to_edit_contact_information() {
        myProfilePage.editProfile("Selenium");
    }
    @When("Click on post link")
    public void click_on_post_link() throws InterruptedException {
        myProfilePage.postLink("Automation FrameWork");

    }
    @When("Click on file link")
    public void click_on_file_link() {
        myProfilePage.clickFileLink("/Users/priya/Downloads/QA picture.png");
    }

    @When("Click on Add photo link")
    public void click_on_add_photo_link() {
        myProfilePage.clickAddPhotoLink("/Users/priya/Downloads/QA picture.png");
    }

}