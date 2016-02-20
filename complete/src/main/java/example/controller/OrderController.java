package example.controller;

import example.domain.Order;
import example.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
public class OrderController {

	@Autowired
	private OrderRepository orderRepository;

	@RequestMapping("/order")
	@ResponseBody
	public String test(@RequestBody Order order) {
		//User person = new User();
		//person.setFirstName("First");
		//person.setLastName("Test");
		orderRepository.save(order);
		return "hello, insert order into DB.";
	}
}