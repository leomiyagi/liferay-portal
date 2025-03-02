@spira_Segments @Segments @Dynamic @Creation @team_FARO @priority_4
Feature: Create a Web Behavior Segment Submitting a Form
  As a Business User, I should be able to create a Web Behavior Submitting a Form Segment

    Background: [Setup] Create a Dynamic Segment
        * I go to the "Home" page
        * I login as "test@faro.io:test"
        * I should see the "Sites" page
        * I click "Segments" in the sidebar
        * I should see the "Segments" page
        * I click the "Create Segment" button
        * I click the "Dynamic Segment" dropdown option

    Scenario: Create a Web Behavior Segment Submitting a Form
        Given I select "Web Behaviors" from the criterion type dropdown
        When I create a criteria with the following Web Behavior condition:
          | Submitted Form | has | grow granular synergies | at least | 2 | ever |
        And I name the Dynamic segment "CreateDynamicWebBehaviorSegment - ${Random.1}" and save it
        And I go to the "Segments" page
        And I search for "CreateDynamicWebBehaviorSegment - ${Random.1}"
        Then I should see a "Segment" named "CreateDynamicWebBehaviorSegment - ${Random.1}" with 4 items
