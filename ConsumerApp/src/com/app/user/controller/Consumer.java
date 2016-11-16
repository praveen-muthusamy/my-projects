package com.app.user.controller;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.app.user.models.*;
@Controller
public class Consumer {
	@RequestMapping(value = "/users")
	public @ResponseBody static void getConsumers() throws JAXBException
    {
		final String uri = "http://localhost:8080/UserManagement/rest/UserService/users";
	     
	    RestTemplate restTemplate = new RestTemplate();
	    String result = restTemplate.getForObject(uri, String.class);
	    StringReader userXML = new StringReader(result);


	    JAXBContext jaxbContext = JAXBContext.newInstance(Users.class);
	    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
	    Users users = (Users) jaxbUnmarshaller.unmarshal( userXML );
	     
	    for(User user : users.getUsers())
	    {
	        System.out.println(user.getId());
	        System.out.println(user.getMessage());
	        System.out.println(user.getName());
	    }
    }
	

}
