@spira_Search @Search @Individuals @List @team_FARO @priority_3
Feature: Assert the No Results Found message on an Individual's Associated Segments List
	As an Business User, I should see a "No Associated Segments Found" message when searching an Individual's Associated Segments List using a non-existing query

	Background: [Setup]
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I click "Individuals" in the sidebar
		* I click the "Known Individuals" tab

	Scenario: Search an Individual's Interest List with a query that won't return results
		Given I click "Abram Bauch" in the table
		And I click the "Segments" tab
		When I search for "non-existent segment"
		Then I should see a message that there are no "Associated Segments" found
