Feature: Login https://www.bbc.co.uk/

  Scenario Outline: Login into account with the correct details
    Given User navigates to "<url>"
    And User clicks on the login button on homepage
    And user enters a valid username "<username>"
    And user enters a valid password "<password>"
    When User clicks on the sign in button
    Then User should be taken to the succesfull login page
    And I navigate to TV guide page
    And User searches for the show "<show>"
    And User checks when the new episode is up


    Examples:
      | url                 | username           | password | show       |
      | http://www.bbc.com/ | afodor88@gmail.com | Test123# | EastEnders |
