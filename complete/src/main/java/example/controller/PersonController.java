package example.controller;

import example.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
public class PersonController {

	@Autowired
	private PersonRepository personRepository;

	@RequestMapping("/user")
	@ResponseBody
	public String test(@RequestBody User user) {
		//User user = new User();
		//user.setFirstName("First");
		//user.setLastName("Test");
		personRepository.save(user);
		return "hello, insert user into DB.";
	}
}