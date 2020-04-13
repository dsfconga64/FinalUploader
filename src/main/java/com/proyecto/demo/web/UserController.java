package com.proyecto.demo.web;

import java.io.IOException;
import java.io.OutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.WritableResource;
import org.springframework.data.util.StreamUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import java.nio.charset.Charset;

import com.proyecto.demo.domain.User;
import com.proyecto.demo.service.UserService;

import reactor.core.publisher.Flux;

@Controller
public class UserController {

	private static final String DESTINATION_NAME = "testqueue";
	
	@Autowired
	UserService serv;

	@Autowired
    private JmsTemplate jmsTemplate;
	
	@Value("blob://testcont/myfile.txt")
	private Resource blobFile;

	   @GetMapping(value = "/")
	   	public String readBlobFile() throws IOException {
	      return org.springframework.util.StreamUtils.copyToString(
	         this.blobFile.getInputStream(),
	         Charset.defaultCharset()) + "\n";
	   }

	   @PostMapping(value = "/")
	   public String writeBlobFile(@RequestBody String data) throws IOException {
	      try (OutputStream os = ((WritableResource) this.blobFile).getOutputStream()) {
	         os.write(data.getBytes());
	      }
	      return "File was updated.\n";
	   }
	
	   @PostMapping("/user")
	   public ResponseEntity<String> addUser(@RequestBody User newUser) {
		   serv.saveUser(newUser);
		   jmsTemplate.convertAndSend(DESTINATION_NAME, newUser.getFirstName());
		return new ResponseEntity<>(HttpStatus.CREATED);
	   }
	
	   @RequestMapping(value = "/user", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	   public Flux<User> getUser() {
		
		   return serv.getAllUsers();
	   }
	
}
