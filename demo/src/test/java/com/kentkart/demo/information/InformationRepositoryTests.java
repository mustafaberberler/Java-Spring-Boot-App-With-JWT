package com.kentkart.demo.information;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kentkart.demo.dataAccess.abstracts.InformationDao;
import com.kentkart.demo.entities.concretes.Information;
import com.kentkart.demo.jwt.JwtTokenUtil;

@ActiveProfiles("test")
//@ExtendWith(SpringExtension.class)
//@RunWith(SpringRunner.class)

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
//@SpringBootTest
//@WebMvcTest(controllers = AuthApi.class)
@AutoConfigureMockMvc
//@WebAppConfiguration
//@EnableWebMvc
//@ExtendWith(SpringExtension.class)
@ContextConfiguration

public class InformationRepositoryTests {

	@Autowired
	InformationDao repo;
	
	@Autowired
	@MockBean
	JwtTokenUtil jwtTokenUtil;
	
	
	@Autowired
	@MockBean
	WebApplicationContext webApplicationContext;
	
	
	@Autowired
	@MockBean
	AuthenticationManager authenticationManager;
	
	protected MockMvc mvc;	
	
	@Before
	protected void setUp() {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	
	protected String mapToJson(Object obj) throws JsonProcessingException{
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(obj);
	}
	
	@Test
	public void testCreateUser() {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String rawPassword = "registerpassword";
		String encodedPassword = passwordEncoder.encode(rawPassword);

		Information newInformation = new Information("registername", "registersurname", "register@hotmail.com",
				encodedPassword, "registerdescription");
		
		Information i = repo.save(newInformation);
		
	
		Optional<Information> savedInformation = repo.findById(i.getId());	
		

		assertThat(savedInformation.get()).isNotNull();
		
	}
	

	@Test
	public void testDeleteUser() {

		Information newInformation = new Information( "Ali", "Berberler", "ali@hotmail.com",
				"askdjsakjf", "description");

		Information i= repo.save(newInformation);
		repo.deleteById(i.getId());

		
		Optional<Information> information = repo.findById(i.getId());
		assertEquals(false, information.isPresent());
	}

	@Test
	public void testUpdateUser() {
		Information oldInfo = new Information("Ali", "Berberler23", "ali@hotmail.com", "asdasfa", "description");
		
		Information newInfo = new Information( "Ali", "Berberler", "ali@hotmail.com", "1231244", "description");
		
		newInfo.setEmail(oldInfo.getEmail());

		repo.save(oldInfo);
		repo.save(newInfo);
		Optional<Information> foundInfo = repo.findById(newInfo.getId());

		Information info1 = foundInfo.get();
		assertEquals(newInfo, info1);

	}

	@Test
	public void testGetAllUsers() {
		Information newInfo = new Information( "Ali", "Berberler", "ali@hotmail.com", "1231244", "description");
		repo.save(newInfo);
		List<Information> maybeInfo = repo.findAll();
		assertThat(maybeInfo.contains(newInfo));		  
	  
	  }
	
	@Test
	public void testGetByNameAndIdUsers() {
	
		Information newInfo = new Information("Ali", "Berberler", "ali@hotmail.com", "1231244", "description");
		repo.save(newInfo);
		List<Information> maybeInfo = repo.getByNameAndId("Ali", newInfo.getEmail());
		assertThat(maybeInfo.contains(newInfo));
		
	}
	
	@Test
	public void testGetByNameOrIdUsers() {
		
		Information newInfo = new Information("Ali", "Berberler", "ali@hotmail.com", "1231244", "description");
		List<Information> maybeInfo = repo.getByNameOrId("Ali", newInfo.getEmail());
		assertThat(maybeInfo.equals(newInfo));
	}
	

}
