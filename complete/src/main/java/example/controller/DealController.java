package example.controller;

import example.domain.Coach;
import example.domain.Deal;
import example.repository.DealRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController
public class DealController {

	private static final Logger logger = LoggerFactory.getLogger(DealController.class);

	@Autowired
	private DealRepository dealRepository;

	@RequestMapping(value = DealRestURIConstants.CREATE_DEAL, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> createDeal(@RequestBody Deal deal) {
		logger.info("Start createDeal.");

		if (null == deal.getUserid() || deal.getUserid() <= 0 ||
			null == deal.getCoachid() || deal.getCoachid() <= 0 ||
			null == deal.getPrice() || deal.getPrice() < 0){
			logger.info("invalid deal params");
			return new ResponseEntity<String>("invalid deal params",
					HttpStatus.NOT_FOUND);
		}

		Deal getDeal = dealRepository.findByUserid(deal.getUserid());
		if (null != getDeal){
			logger.info("userid exists");
			return new ResponseEntity<String>("userid exists",
					HttpStatus.CONFLICT);
		}

		try {
			deal.setState(Deal.DEAL_STATE.ONGOING);
			coachRepository.save(coach);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("save coach failed." + e.toString());
		} finally {
//			try {
//				conn.close();
//			} catch (SQLException e1) {
//				e1.printStackTrace();
//			}
		}

		logger.info("create coach Successfully!");
		return new ResponseEntity<String>("", HttpStatus.OK);
	}

	//update coach
	@RequestMapping(value = CoachRestURIConstants.UPDATE_COACH, method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<String> updateCoach(@RequestBody Coach coach) {
		logger.info("Start updateCoach.");

		if (coach.getRegisterphone().isEmpty() || null == coach.getRegisterphone()){
			logger.info("register phone is empty");
			return new ResponseEntity<String>("register phone is empty",
					HttpStatus.NOT_FOUND);
		}

		Coach getCoach = coachRepository.findByRegisterphone(coach.getRegisterphone());
		if (null == getCoach){
			logger.info("coach not exists");
			return new ResponseEntity<String>("coach not exists",
					HttpStatus.NOT_FOUND);
		}

		try {
			getCoach.updateAllowedAttribute(coach);

			//first time update to set
			if (!getCoach.getVerify())
			{
				getCoach.setVerify(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("update coach failed." + e.toString());
		} finally {
//			try {
//				conn.close();
//			} catch (SQLException e1) {
//				e1.printStackTrace();
//			}
		}

		logger.info("update coach Successfully!");
		return new ResponseEntity<String>("", HttpStatus.OK);
	}

	//delete coach
	@RequestMapping(value = CoachRestURIConstants.DELETE_COACH, method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<String> deleteUser(@RequestBody Coach coach) {
		logger.info("Start deleteCoach.");

		if (null == coach.getCoachid() || coach.getCoachid() <= 0){
			logger.info("coachid invalid");
			return new ResponseEntity<String>("coachid invalid",
					HttpStatus.NOT_FOUND);
		}

		Coach getCoach = coachRepository.findByCoachid(coach.getCoachid());
		if (null == getCoach){
			logger.info("coach not exists");
			return new ResponseEntity<String>("coach not exists",
					HttpStatus.NOT_FOUND);
		}

		try {
			coachRepository.delete(getCoach);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("delete coach failed." + e.toString());
		} finally {
//			try {
//				conn.close();
//			} catch (SQLException e1) {
//				e1.printStackTrace();
//			}
		}

		logger.info("delete coach Successfully!");
		return new ResponseEntity<String>("", HttpStatus.OK);
	}

	//get coach by registerphone
	@RequestMapping(value = CoachRestURIConstants.GET_COACH, method = RequestMethod.GET)
	@ResponseBody
	public Coach getCoach(@RequestBody Coach coach) {
		if (coach.getRegisterphone().isEmpty() || null == coach.getRegisterphone()){
			logger.info("register phone is empty");
			return null;
		}
		logger.info("Start getCoach. Registerphone= " + coach.getRegisterphone());

		Coach getCoach = coachRepository.findByRegisterphone(coach.getRegisterphone());
		if (null == getCoach){
			return null;
		}

		logger.info("get coach Successfully!");
		return getCoach;
	}

	//get all coachs
	@RequestMapping(value = CoachRestURIConstants.GET_ALL_COACH, method = RequestMethod.GET)
	@ResponseBody
	public List<Coach> getAllCoachs(@RequestBody Coach coach) {
		logger.info("Start getCoachs.");

		List<Coach> getCoachList = coachRepository.findAll();
		if (null == getCoachList){
			return null;
		}

		logger.info("get all coachs Successfully!");
		return getCoachList;
	}
}