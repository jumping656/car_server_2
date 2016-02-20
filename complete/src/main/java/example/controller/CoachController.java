package example.controller;

import example.domain.Coach;
import example.repository.CoachRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
public class CoachController {

	@Autowired
	private CoachRepository coachRepository;

	@RequestMapping("/coach")
	@ResponseBody
	public String test(@RequestBody Coach coach) {
		//User person = new User();
		//person.setFirstName("First");
		//person.setLastName("Test");
		coachRepository.save(coach);
		return "hello, insert coach into DB.";
	}
}