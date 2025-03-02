@spira_Segments @Segments @Dynamic @Creation @team_FARO @priority_5
Feature: Create a Segment of Individuals Interested in a Topic
    As a Business User, I should be able to create Interested in a Topic Segment

    Background: [Setup] Create a Dynamic Segment
        * I go to the "Home" page
        * I login as "test@faro.io:test"
        * I should see the "Sites" page
        * I click "Segments" in the sidebar
        * I should see the "Segments" page
        * I click the "Create Segment" button
        * I click the "Dynamic Segment" dropdown option

    Scenario: Create the Dynamic Interests Segment
        Given I select "Interests" from the criterion type dropdown
        When I create a criteria with the following conditions:
            | synergistic schemas | is |
        And I name the Dynamic segment "CreateDynamicInterestSegment - ${Random.1}" and save it
        When I go to the "Segments" page
        And I search for "CreateDynamicInterestSegment - ${Random.1}"
        Then I should see a "Segment" named "CreateDynamicInterestSegment - ${Random.1}" with "41" items