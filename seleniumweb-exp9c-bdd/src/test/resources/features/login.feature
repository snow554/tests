Feature: Login Functionality

  Scenario: Valid login
    Given user is on login page
    When user enters username "tomsmith"
    And user enters password "SuperSecretPassword!"
    And clicks login button
    Then login should be successful
