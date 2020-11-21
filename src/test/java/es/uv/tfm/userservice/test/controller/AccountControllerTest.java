package es.uv.tfm.userservice.test.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import es.uv.tfm.userservice.controller.AccountController;
import es.uv.tfm.userservice.entities.User;
import es.uv.tfm.userservice.repository.UserRepository;

//@RunWith(SpringRunner.class)
@WebMvcTest(AccountController.class)
public class AccountControllerTest {

	 @Autowired
	    private MockMvc mockMvc;
	 
	    @MockBean
	    private UserRepository userRepository;
	 
	    @Autowired
	    private ObjectMapper objectMapper;
	    
	    private static final User USER = new User(9999, "prueba", "123456", "prueba@prueba", true);

	    
//	    @Test
//	    public void testShouldReturnCreatedWhenValidUser() throws Exception {
////	        when(userRepository.save(Mockito.any())).thenReturn(USER);
//	 
////	        this.mockMvc.perform(post("/account/")
////	                .content(objectMapper.writeValueAsString(USER))
////	                .contentType(MediaType.APPLICATION_JSON)
////	                .accept(MediaType.APPLICATION_JSON))
////	                .andExpect(status().isCreated());
//	    }
	    
}
