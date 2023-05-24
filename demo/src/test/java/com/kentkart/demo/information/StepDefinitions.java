package com.kentkart.demo.information;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.concurrent.TimeUnit;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kentkart.demo.api.controllers.AuthResponse;
import com.kentkart.demo.dataAccess.abstracts.InformationDao;
import com.kentkart.demo.entities.concretes.Information;
import com.kentkart.demo.jwt.JwtTokenUtil;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
//@ActiveProfiles("test")
//@Log4j2
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class StepDefinitions {

	@LocalServerPort
	private String port;
	private RestTemplate restTemplate = new RestTemplate();
	
	
	String url = "http://localhost:8080/auth/login";
	
	String token;
	HttpEntity<String> jwtEntity;
	
	@MockBean
	AuthenticationManager authManager;
	
	@MockBean
	JwtTokenUtil jwtUtil;
	
	@MockBean
	InformationDao repo;
	
	Information newinformation = new Information();
	
	private String getBody(final Information information) throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(information);
	}
	
	private HttpHeaders getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		return headers;
	}
	
	@Given("I create a new function")
	public void i_create_a_new_function() {
	    
	}

	@Given("^I pass the parameters with email (.*) and password (.*)$")
	public void i_pass_the_parameters_with_email_register_hotmail_com_and_password_registerpassword(String email, String password) {
				
		newinformation.setEmail(email);
		newinformation.setPassword(password);
		newinformation.setId("asjdkj129144");
						
	}	
		
	@Then("I should be able to get the token and get all users")
	public void i_should_be_able_to_get_the_token_and_get_all_users() {
		try {
			String url2 = "http://localhost:8080/api/informations/getall";
			String informationBody = getBody(newinformation);
			HttpHeaders authenticationHeaders = getHeaders();
			HttpEntity<String> authenticationEntity = new HttpEntity<String>(informationBody, authenticationHeaders);
			
			ResponseEntity<AuthResponse> authResponse = restTemplate.exchange(url, HttpMethod.POST, authenticationEntity, AuthResponse.class);
			
			token = "Bearer " + authResponse.getBody().getAccessToken();
			
			System.out.println(token);
			
			
			HttpHeaders headers = getHeaders();
			headers.set("Authorization", token);
			
			jwtEntity = new HttpEntity<String>(headers);
			
		
			ResponseEntity<String> getAllResponse = restTemplate.exchange(url2, HttpMethod.GET, jwtEntity, String.class);
			
			if (getAllResponse.getStatusCode().equals(HttpStatus.OK)) {
				System.out.println(getAllResponse.getBody());
			}
			
		} catch (Exception e) {
			System.out.println("ERROR " + e.toString());
		}
	}
	
	@Then("I should be able to get the token and add user")
	public void i_should_be_able_to_get_the_token_and_add_user() {
		try {				    
		       
		    String informationBody = getBody(newinformation);
		    HttpHeaders authenticationHeaders = getHeaders();
			HttpEntity<String> authenticationEntity = new HttpEntity<String>(informationBody, authenticationHeaders);
			
			ResponseEntity<AuthResponse> authResponse = restTemplate.exchange(url, HttpMethod.POST, authenticationEntity, AuthResponse.class );
			
			token = "Bearer " + authResponse.getBody().getAccessToken();
							
			HttpHeaders headers = getHeaders();
			headers.set("Authorization", token);			
			
			String command = "curl -X POST -H \"Content-Type: application/json\" -H \"Authorization: Bearer " + authResponse.getBody().getAccessToken() + "\" -d \"{\\\"name\\\": \\\"curlname3agustos\\\", \\\"surname\\\": \\\"curlname3agustos\\\", \\\"email\\\": \\\"curlname3agustos@hotmail.com\\\", \\\"password\\\": \\\"curlname3agustospassword\\\", \\\"description\\\": \\\"curlname3agustosdescription\\\"}\" localhost:8080/api/informations/add";
			
			Process process = Runtime.getRuntime().exec(command);
			process.getInputStream();
			
			process.waitFor(5000, TimeUnit.MILLISECONDS);
			
			
			int exitCode = process.exitValue();
			System.out.println("CODE: " + exitCode);
			
			process.destroy();
			
			
			
		} catch (Exception e) {
			System.out.println("ERROR " +  e.toString());
		}
		
	}
	
	@Then("I should be able to get the token and delete user")
	public void i_should_be_able_to_get_the_token_and_delete_user() {
		try {
			String id = "";			
		    String informationBody = getBody(newinformation);
		    HttpHeaders authenticationHeaders = getHeaders();
			HttpEntity<String> authenticationEntity = new HttpEntity<String>(informationBody, authenticationHeaders);
			
			ResponseEntity<AuthResponse> authResponse = restTemplate.exchange(url, HttpMethod.POST, authenticationEntity, AuthResponse.class );
			
			token = "Bearer " + authResponse.getBody().getAccessToken();
							
			HttpHeaders headers = getHeaders();
			headers.set("Authorization", token);			
			
			jwtEntity = new HttpEntity<String>(headers);
			
			String command = "curl -X POST -H \"Content-Type: application/json\" -H \"Authorization: Bearer " + authResponse.getBody().getAccessToken() + "\" -d \"{\\\"id\\\": \\\"1\\\", \\\"name\\\": \\\"curlnamedeleted\\\", \\\"surname\\\": \\\"curlsurnamedeleted\\\", \\\"email\\\": \\\"curldeleted@hotmail.com\\\", \\\"password\\\": \\\"curlpassworddeleted\\\", \\\"description\\\": \\\"curldescriptiondeleted\\\"}\" localhost:8080/api/informations/add";
			
			Process process = Runtime.getRuntime().exec(command);
			process.getInputStream();
			
			process.waitFor(5000, TimeUnit.MILLISECONDS);
			
			
			int exitCode = process.exitValue();
			System.out.println("CODE: " + exitCode);
			
			process.destroy();
			
			
			try {
				Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/users","root","1234");
				Statement statement = connection.createStatement();
				ResultSet rs;
				
				rs = statement.executeQuery("SELECT id FROM users WHERE email='curldeleted@hotmail.com'");
				while(rs.next()) {
					id = rs.getString("id");
					System.out.println("AJDJASHJAFHJ ID " + id);
				}
			} catch (Exception e) {
				System.err.println("Got an exception! ");
	            System.err.println(e.getMessage());
			}
			
			String command2 = "curl -X DELETE -H \"Authorization: Bearer " + authResponse.getBody().getAccessToken() + "\" localhost:8080/api/informations/delete/" + id;
				
			Process process2 = Runtime.getRuntime().exec(command2);
			process2.getInputStream();
			
			process2.waitFor(5000, TimeUnit.MILLISECONDS);
			
			int exitCode2 = process.exitValue();
			System.out.println("CODE: " + exitCode2);
			
			process2.destroy();
			
			
		} catch (Exception e) {
			System.out.println("ERROR " +  e.toString());
		}	
		
	}
	
	
	@Then("I should be able to get the token and update user")
	public void i_should_be_able_to_get_the_token_and_update_user() {
		try {
			String id = "";
			String informationBody = getBody(newinformation);
		    HttpHeaders authenticationHeaders = getHeaders();
			HttpEntity<String> authenticationEntity = new HttpEntity<String>(informationBody, authenticationHeaders);
			
			ResponseEntity<AuthResponse> authResponse = restTemplate.exchange(url, HttpMethod.POST, authenticationEntity, AuthResponse.class );
			
			token = "Bearer " + authResponse.getBody().getAccessToken();
							
			HttpHeaders headers = getHeaders();
			headers.set("Authorization", token);
			
			String command = "curl -X POST -H \"Content-Type: application/json\" -H \"Authorization: Bearer " + authResponse.getBody().getAccessToken() + "\" -d \"{\\\"id\\\": \\\"11122233344\\\", \\\"name\\\": \\\"curlname1\\\", \\\"surname\\\": \\\"curlsurname1\\\", \\\"email\\\": \\\"curl1@hotmail.com\\\", \\\"password\\\": \\\"curlpassword1\\\", \\\"description\\\": \\\"curldescription1\\\"}\" localhost:8080/api/informations/add"; 
			
			Process process = Runtime.getRuntime().exec(command);
			process.getInputStream();
			
			process.waitFor(5000, TimeUnit.MILLISECONDS);
			
			
			int exitCode = process.exitValue();
			System.out.println("CODE: " + exitCode);
			
			process.destroy();
			
			
			try {
				Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/users","root","1234");
				Statement statement = connection.createStatement();
				ResultSet rs;
				
				rs = statement.executeQuery("SELECT id FROM users WHERE email='curl1@hotmail.com'");
				while(rs.next()) {
					id = rs.getString("id");
					System.out.println("AJDJASHJAFHJ ID " + id);
				}
			} catch (Exception e) {
				System.err.println("Got an exception! ");
	            System.err.println(e.getMessage());
			}
			
			String command2 = "curl -X PUT -H \"Content-Type: application/json\" -H \"Authorization: Bearer " + authResponse.getBody().getAccessToken() + "\" -d \"{\\\"name\\\": \\\"updatedcurlname1\\\", \\\"surname\\\": \\\"updatedcurlsurname1\\\", \\\"email\\\": \\\"updatedcurl1@hotmail.com\\\", \\\"password\\\": \\\"updatedcurlpassword1\\\", \\\"description\\\": \\\"updatedcurldescription1\\\"}\" localhost:8080/api/informations/update/" + id;
			
			Process process2 = Runtime.getRuntime().exec(command2);
			process2.getInputStream();
			
			process2.waitFor(5000, TimeUnit.MILLISECONDS);
			
			int exitCode2 = process.exitValue();
			System.out.println("CODE2: " + exitCode2);
			
			process2.destroy();
			
		
		} catch (Exception e) {
			System.out.println("ERROR " + e.toString());
		}
	}
	
	
	
	
}
