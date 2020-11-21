package es.uv.tfm.userservice.test.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import es.uv.tfm.userservice.controller.HelloResource;

public class HelloControllerTest {

private HelloResource helloController;

@Autowired
private MockMvc mockMvc;


@Autowired
private ObjectMapper objectMapper;


@Test
void hello() {
	helloController = new HelloResource();
	ResponseEntity<String> res = helloController.hello();
	assertEquals(new ResponseEntity<>("ola", HttpStatus.OK),res);
}

}
