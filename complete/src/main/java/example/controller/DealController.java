package example.controller;

import example.domain.Deal;
import example.repository.DealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
public class DealController {

	@Autowired
	private DealRepository dealRepository;

	@RequestMapping("/deal")
	@ResponseBody
	public String test(@RequestBody Deal deal) {
		//User person = new User();
		dealRepository.save(deal);
		return "hello, insert deal into DB.";
	}
}