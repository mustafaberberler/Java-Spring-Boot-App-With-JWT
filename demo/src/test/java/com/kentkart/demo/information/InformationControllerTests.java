/*
 * package com.kentkart.demo.information;
 * 
 * 
 * 
 * import static org.assertj.core.api.Assertions.assertThat; import static
 * org.junit.jupiter.api.Assertions.assertEquals;
 * 
 * import org.springframework.beans.factory.annotation.Autowired; import
 * java.io.IOException; import java.util.Optional;
 * 
 * 
 * import org.junit.jupiter.api.BeforeEach; import org.junit.jupiter.api.Test;
 * import org.mockito.InjectMocks;
 * 
 * 
 * 
 * 
 * import com.kentkart.demo.dataAccess.abstracts.InformationDao; import
 * org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
 * import
 * org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.
 * Replace; import
 * org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
 * import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
 * import
 * org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
 * import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
 * import org.springframework.boot.test.context.SpringBootTest; import
 * org.springframework.boot.test.mock.mockito.MockBean; import
 * org.springframework.data.jpa.repository.config.EnableJpaRepositories; import
 * org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; import
 * org.springframework.security.crypto.password.PasswordEncoder; import
 * org.springframework.test.annotation.Rollback; import
 * org.springframework.test.context.ActiveProfiles; import
 * org.springframework.test.context.BootstrapWith; import
 * org.springframework.test.context.ContextConfiguration; import
 * org.springframework.test.context.web.WebAppConfiguration; import
 * org.springframework.test.web.servlet.MockMvc;
 * 
 * import org.springframework.test.web.servlet.setup.MockMvcBuilders;
 * 
 * import org.springframework.web.context.WebApplicationContext;
 * 
 * 
 * import com.fasterxml.jackson.core.JsonParseException; import
 * com.fasterxml.jackson.core.JsonProcessingException; import
 * com.fasterxml.jackson.databind.JsonMappingException; import
 * com.fasterxml.jackson.databind.ObjectMapper;
 * 
 * import com.kentkart.demo.api.controllers.InformationsController; import
 * com.kentkart.demo.business.abstracts.InformationService;
 * 
 * import com.kentkart.demo.entities.concretes.Information; import
 * com.kentkart.demo.jwt.JwtTokenUtil;
 * 
 * import static
 * org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*; import
 * static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * @Rollback(false)
 * 
 * @WebMvcTest(InformationsController.class)
 * 
 * @ContextConfiguration(locations = {"classpath:testapplication-context.xml"})
 * 
 * @AutoConfigureMockMvc
 * 
 * @WebAppConfiguration //@ActiveProfiles("test") //@DataJpaTest
 * 
 * @AutoConfigureTestDatabase(replace = Replace.NONE) //@SpringBootTest
 * //@BootstrapWith //@AutoConfigureDataJpa //@EnableJpaRepositories
 * 
 * public class InformationControllerTests {
 * 
 * @Autowired
 * 
 * @MockBean InformationDao repo;
 * 
 * protected MockMvc mvc;
 * 
 * 
 * private JwtTokenUtil util = new JwtTokenUtil("Abcdefghi08105ybnon1985");
 * 
 * @Autowired WebApplicationContext webApplicationContext;
 * 
 * @Autowired
 * 
 * @MockBean JwtTokenUtil jwtTokenUtil;
 * 
 * 
 * 
 * @BeforeEach protected void setUp() { mvc =
 * MockMvcBuilders.webAppContextSetup(webApplicationContext).build(); }
 * 
 * protected String mapToJson(Object obj) throws JsonProcessingException{
 * ObjectMapper objectMapper = new ObjectMapper(); return
 * objectMapper.writeValueAsString(obj); }
 * 
 * protected <T> T mapFromJson(String json, Class<T> class1) throws
 * JsonParseException, JsonMappingException, IOException { ObjectMapper
 * objectMapper = new ObjectMapper(); return objectMapper.readValue(json,
 * class1); }
 * 
 * @InjectMocks InformationsController informationsController;
 * 
 * @MockBean private InformationService informationService;
 * 
 * 
 * 
 * //@MockBean private InformationDao dao;
 * 
 * 
 * 
 * 
 * @Test
 * 
 * public void testGetAllController() throws Exception {
 * 
 * Information newInfo = new Information("77777777775", "Mustafa", "Berberler",
 * "mustafa@hotmail.com", "AKSJDKASF", "mustafa");
 * 
 * repo.save(newInfo);
 * 
 * String token = jwtTokenUtil.generateAccessToken(newInfo);
 * 
 * 
 * mvc.perform(post("/auth/login").header("Authorization: Bearer ",
 * token)).andExpect(status().isOk());
 * 
 * 
 * String username = newInfo.getEmail(); String password =
 * newInfo.getPassword();
 * 
 * String body = "{\"email\":\"" + username + "\",\"password\":\"" + password +
 * "\"}";
 * 
 * 
 * 
 * MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/auth/login")
 * .content(body)) .andExpect(status().isOk()).andReturn();
 * 
 * /*String response = result.getResponse().getContentAsString(); response =
 * response.replace("{\"accessToken\": \"", ""); String token =
 * response.replace("\"}", "");
 * 
 * mvc.perform(MockMvcRequestBuilders.get("/getall") .header("Authorization",
 * "Bearer " + token)) .andExpect(status().isOk()); }
 * 
 * 
 * @Test
 * 
 * public void testCreateUserController() {
 * 
 * 
 * 
 * PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); String
 * rawPassword = "newpassword"; String encodedPassword =
 * passwordEncoder.encode(rawPassword);
 * 
 * Information newInformation = new Information("99999999999", "Mustafa",
 * "Berberler", "mustafa@hotmail.com", encodedPassword, "mustafa");
 * 
 * Information i = repo.save(newInformation);
 * 
 * Optional<Information> savedInformation = repo.findById("99999999999");
 * 
 * 
 * assertThat(savedInformation.get()).isNotNull();
 * 
 * }
 * 
 * @Test public void testDeleteUserController() {
 * 
 * 
 * Information newInformation = new Information("77777777777", "Ali",
 * "Berberler", "ali@hotmail.com", "askdjsakjf", "description");
 * 
 * repo.save(newInformation);
 * 
 * repo.deleteById("77777777777"); Optional<Information> information =
 * repo.findById("77777777777"); assertEquals(false, information.isPresent());
 * 
 * }
 * 
 * 
 * @Test public void testUpdateUserController() {
 * 
 * 
 * 
 * Information oldInfo = new Information("22222222222", "Ali", "Berberler23",
 * "ali@hotmail.com", "asdasfa", "description"); Information newInfo = new
 * Information("22222222222", "Ali", "Berberler", "ali@hotmail.com", "1231244",
 * "description");
 * 
 * repo.save(oldInfo); repo.save(newInfo); Optional<Information> foundInfo =
 * repo.findById("22222222222");
 * 
 * Information info1 = foundInfo.get(); assertEquals(newInfo, info1); }
 * 
 * 
 * 
 * 
 * 
 * }
 */