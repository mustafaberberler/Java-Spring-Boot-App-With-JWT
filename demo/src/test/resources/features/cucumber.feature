Feature: Get All Functionality With Authorization

	Scenario Outline: I want to get a token and get all users.
		Given I create a new function
		And I pass the parameters with email <email> and password <password>
		Then I should be able to get the token and get all users
		
		Examples:
		| email 							 | password 				|
		| register@hotmail.com | registerpassword |
 
 	
 	Scenario Outline: I want to get JWT token and add user.
 		Given I create a new function
 		And I pass the parameters with email <email> and password <password>
 		Then I should be able to get the token and add user
 		
 		Examples:
		| email 							 | password 				|
		| register@hotmail.com | registerpassword |
		
		
	Scenario Outline: I want to get JWT token and delete user.
		Given I create a new function
		And I pass the parameters with email <email> and password <password>
		Then I should be able to get the token and delete user
		
		Examples:
		| email 							 | password 				|
		| register@hotmail.com | registerpassword |
		
		
	Scenario Outline: I want to get JWT token and update user.
		Given I create a new function
		And I pass the parameters with email <email> and password <password>
		Then I should be able to get the token and update user
		
		Examples:
		| email 							 | password 				|
		| register@hotmail.com | registerpassword |
		