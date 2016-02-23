package example.controller;

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
			logger.info("dealid exists");
			return new ResponseEntity<String>("userid exists",
					HttpStatus.CONFLICT);
		}

		try {
			deal.setState(Deal.DEAL_STATE.ONGOING);
			dealRepository.save(deal);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("create deal failed." + e.toString());
		} finally {
//			try {
//				conn.close();
//			} catch (SQLException e1) {
//				e1.printStackTrace();
//			}
		}

		logger.info("create deal Successfully!");
		return new ResponseEntity<String>("", HttpStatus.OK);
	}

	//update deal
	@RequestMapping(value = DealRestURIConstants.UPDATE_DEAL, method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<String> updateDeal(@RequestBody Deal deal) {
		logger.info("Start updateDeal.");

		if (deal.getDealid() <= 0 || null == deal.getDealid()){
			logger.info("dealid is empty");
			return new ResponseEntity<String>("dealid is empty",
					HttpStatus.NOT_FOUND);
		}

		Deal getDeal = dealRepository.findByDealid(deal.getDealid());
		if (null == getDeal){
			logger.info("deal not exists");
			return new ResponseEntity<String>("deal not exists",
					HttpStatus.NOT_FOUND);
		}

		if (getDeal.getUserid()  != deal.getUserid() ||
			getDeal.getCoachid() != deal.getCoachid()) {
			logger.info("userid or coachdi can not change");
			return new ResponseEntity<String>("userid or coachdi can not change",
					HttpStatus.NOT_FOUND);
		}

		try {
			getDeal.updateAllowedAttribute(deal);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("update deal failed." + e.toString());
		} finally {
//			try {
//				conn.close();
//			} catch (SQLException e1) {
//				e1.printStackTrace();
//			}
		}

		logger.info("update deal Successfully!");
		return new ResponseEntity<String>("", HttpStatus.OK);
	}

	//delete deal
	@RequestMapping(value = DealRestURIConstants.DELETE_DEAL, method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<String> deleteDeal(@RequestBody Deal deal) {
		logger.info("Start deleteDeal.");

		if (null == deal.getDealid() || deal.getDealid() <= 0){
			logger.info("dealid invalid");
			return new ResponseEntity<String>("dealdi invalid",
					HttpStatus.NOT_FOUND);
		}

		Deal getDeal = dealRepository.findByDealid(deal.getDealid());
		if (null == getDeal){
			logger.info("deal not exists");
			return new ResponseEntity<String>("deal not exists",
					HttpStatus.NOT_FOUND);
		}

		try {
			dealRepository.delete(getDeal);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("delete deal failed." + e.toString());
		} finally {
//			try {
//				conn.close();
//			} catch (SQLException e1) {
//				e1.printStackTrace();
//			}
		}

		logger.info("delete deal Successfully!");
		return new ResponseEntity<String>("", HttpStatus.OK);
	}

	//get deal by dealid
	@RequestMapping(value = DealRestURIConstants.GET_DEAL, method = RequestMethod.GET)
	@ResponseBody
	public Deal getDeal(@RequestBody Deal deal) {
		if (deal.getDealid() <= 0 || null == deal.getDealid()){
			logger.info("dealid is empty");
			return null;
		}
		logger.info("Start getDeal.dealid= " + deal.getDealid());

		Deal getDeal = dealRepository.findByDealid(deal.getDealid());
		if (null == getDeal){
			return null;
		}

		logger.info("get deal Successfully!");
		return getDeal;
	}

	//get all deals
	@RequestMapping(value = DealRestURIConstants.GET_ALL_DEAL, method = RequestMethod.GET)
	@ResponseBody
	public List<Deal> getAllDeals(@RequestBody Deal deal) {
		logger.info("Start getDeals.");

		List<Deal> getDealList = dealRepository.findAll();
		if (null == getDealList){
			return null;
		}

		logger.info("get all deals Successfully!");
		return getDealList;
	}
}