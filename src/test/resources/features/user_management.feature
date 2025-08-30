Feature: User Management
  As an application owner
  I want to manage user accounts
  So that I can control who has access to the system

  # Add User Scenario
  Scenario: Successfully add new users
    Given I have a user with name "Ushan", email "ushanviduranga123@gmail.com", and phone "0712345678"
    When I request to add this user
    Then the user should be saved with a system-generated ID

  # Delete User Scenario
  Scenario: Successfully delete an existing user
    Given a user exists in the system
    When I request to delete that user
    Then the user should be removed from the system
