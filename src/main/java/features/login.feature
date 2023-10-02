Feature: salesforce


  Scenario: Login with valid credentials
    Given user opens the login page
    When user enters valid user name
    When user enters valid password
    When user clicks on login button
    Then user should be navigated to the home page

  @ignore
  Scenario: Login with invalid credentials
    Given user opens the login page
    When user enters valid user name
    And user enters invalid password
    And user clicks on login button
    Then user should be be displayed with an error message

  @ignore
  Scenario: Check remember me
    Given user opens the login page
    When user enters valid user name
    And user enters valid password
    When user enables remember me
    When user clicks on login button
    When user clicks on userMenu dropdown and clicks on logout
    Then user should go to login page with the username displayed already

  @ignore
  Scenario: Forgot Password
    Given user opens the login page
    When user clicks on forgot password
    When user enters username and submit continue
    Then user should be displayed with an error message

  @ignore
  Scenario: Select user menu for <username> drop down
    Given user opens the login page
    When user enters valid user name
    And user enters valid password
    When user clicks on login button
    When user checks for user menu for <username> drop down
    Then user clicks on the user menu for <username> drop down

    @smoke
  Scenario: Select "My Profile" option from user menu for <username> drop down
    Given user opens the login page
    When user enters valid user name
    And user enters valid password
    And user clicks on login button
    And user selects user menu for <username> drop down[TC01]
    And user clicks "My profile" option from user menu
    And user clicks on edit profile button to edit contact information
    And Click on post link
    And Click on file link
    And Click on Add photo link







