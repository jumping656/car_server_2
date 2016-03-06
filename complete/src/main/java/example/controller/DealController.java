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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@Controller
@RestController
public class DealController {

	private static final Logger logger = LoggerFactory.getLogger(DealController.class);

	@Autowired
	private DealRepository dealRepository;

	@RequestMapping(value = DealRestURIConstants.CREATE_DEAL, method = RequestMethod.POST, headers = "content-type=application/x-www-form-urlencoded")
	@ResponseBody
	public ResponseEntity<Object> createDeal(@ModelAttribute Deal deal) {
		logger.info("Start createDeal.");

		if (null == deal.getUserid() || deal.getUserid() <= 0 ||
			null == deal.getCoachid() || deal.getCoachid() <= 0 ||
			null == deal.getPrice() || deal.getPrice() < 0){
			logger.info("invalid deal params");
			return new ResponseEntity<Object>("invalid deal params",
					HttpStatus.NOT_FOUND);
		}

		List<Deal> getDealList= dealRepository.findByUseridAndState(deal.getUserid(), Deal.DEAL_STATE.ONGOING);
		if (!getDealList.isEmpty()){
			logger.info("user ongoing deal exits");
			return new ResponseEntity<Object>("user ongoing deal exits",
					HttpStatus.CONFLICT);
		}

		try {
			deal.setState(Deal.DEAL_STATE.ONGOING);
			//get current time
			String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
			deal.setDate(timeStamp);
			dealRepository.save(deal);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("create deal failed!");
			return new ResponseEntity<Object>("fail to create deal.", HttpStatus.NOT_FOUND);
		} finally {
//			try {
//				conn.close();
//			} catch (SQLException e1) {
//				e1.printStackTrace();
//			}
		}

		logger.info("create deal Successfully!");
		return new ResponseEntity<Object>(deal, HttpStatus.OK);
	}

	//update deal
	@RequestMapping(value = DealRestURIConstants.UPDATE_DEAL, method = RequestMethod.POST, headers = "content-type=application/x-www-form-urlencoded")
	@ResponseBody
	public ResponseEntity<Object> updateDeal(@ModelAttribute Deal deal) {
		logger.info("Start updateDeal.");

		if (deal.getDealid() <= 0 || null == deal.getDealid()){
			logger.info("dealid is empty");
			return new ResponseEntity<Object>("dealid is empty",
					HttpStatus.NOT_FOUND);
		}

		Deal getDeal = dealRepository.findByDealid(deal.getDealid());
		if (null == getDeal){
			logger.info("deal not exists");
			return new ResponseEntity<Object>("deal not exists",
					HttpStatus.NOT_FOUND);
		}

		if ((getDeal.getUserid()  != deal.getUserid() && null != deal.getUserid()) ||
				(getDeal.getCoachid() != deal.getCoachid() && null != deal.getCoachid())) {
			logger.info("userid or coachid can not change");
			return new ResponseEntity<Object>("userid or coachid can not change",
					HttpStatus.NOT_FOUND);
		}

		try {
			getDeal.updateAllowedAttribute(deal);
			dealRepository.save(getDeal);
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
		return new ResponseEntity<Object>(getDeal, HttpStatus.OK);
	}

	//delete deal
	@RequestMapping(value = DealRestURIConstants.DELETE_DEAL, method = RequestMethod.DELETE, headers = "content-type=application/x-www-form-urlencoded")
	@ResponseBody
	public ResponseEntity<Object> deleteDeal(@ModelAttribute Deal deal) {
		logger.info("Start deleteDeal.");

		if (null == deal.getDealid() || deal.getDealid() <= 0){
			logger.info("dealid invalid");
			return new ResponseEntity<Object>("dealdi invalid",
					HttpStatus.NOT_FOUND);
		}

		Deal getDeal = dealRepository.findByDealid(deal.getDealid());
		if (null == getDeal){
			logger.info("deal not exists");
			return new ResponseEntity<Object>("deal not exists",
					HttpStatus.NOT_FOUND);
		}

		try {
			dealRepository.delete(getDeal);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("delete deal failed");
			return new ResponseEntity<Object>("delete deal failed",
					HttpStatus.NOT_FOUND);
		} finally {
//			try {
//				conn.close();
//			} catch (SQLException e1) {
//				e1.printStackTrace();
//			}
		}

		logger.info("delete deal Successfully!");
		return new ResponseEntity<Object>("deal deleted", HttpStatus.OK);
	}

	//get deal by dealid
	@RequestMapping(value = DealRestURIConstants.GET_DEAL, method = RequestMethod.POST, headers = "content-type=application/x-www-form-urlencoded")
	@ResponseBody
	public ResponseEntity<Object> getDeal(@ModelAttribute Deal deal) {
		logger.info("haha, get deal : ");
		if (null == deal.getDealid() || deal.getDealid() <= 0){
			logger.info("dealid is empty");
			return new ResponseEntity<Object>("dealid is empty!",
					HttpStatus.NOT_FOUND);
		}
		logger.info("Start getDeal.dealid= " + deal.getDealid());

		Deal getDeal = dealRepository.findByDealid(deal.getDealid());
		if (null == getDeal){
			logger.info("deal not found!");
			return new ResponseEntity<Object>("deal not found!",
					HttpStatus.NOT_FOUND);
		}

		logger.info("get deal Successfully!");
		return new ResponseEntity<Object>(getDeal, HttpStatus.OK);
	}

	//get deal by coachid
	@RequestMapping(value = DealRestURIConstants.GET_DEAL_BY_COACH, method = RequestMethod.POST, headers = "content-type=application/x-www-form-urlencoded")
	@ResponseBody
	public ResponseEntity<Object> getDealByCoachid(@PathVariable("coachid")Integer coachid) {
		if (null == coachid || coachid <= 0){
			logger.info("coachid is empty");
			return new ResponseEntity<Object>("coachid is empty!",
					HttpStatus.NOT_FOUND);
		}
		logger.info("Start getDeals.coachid= " + coachid);

		List<Deal> getDealList = dealRepository.findByCoachid(coachid);
		if (getDealList.isEmpty()){
			logger.info("deal not found!");
			return new ResponseEntity<Object>("deal not found!",
					HttpStatus.NOT_FOUND);
		}

		logger.info("get deal Successfully!");
		return new ResponseEntity<Object>(getDealList, HttpStatus.OK);
	}

	//get deal by userid
	@RequestMapping(value = DealRestURIConstants.GET_DEAL_BY_USER, method = RequestMethod.POST, headers = "content-type=application/x-www-form-urlencoded")
	@ResponseBody
	public ResponseEntity<Object> getDealByUserid(@PathVariable("userid")Integer userid) {
		if (null == userid || userid <= 0){
			logger.info("userid is empty");
			return new ResponseEntity<Object>("userid is empty!",
					HttpStatus.NOT_FOUND);
		}
		logger.info("Start getDeals.userid= " + userid);

		List<Deal> getDealList = dealRepository.findByUserid(userid);
		if (getDealList.isEmpty()){
			logger.info("deal not found!");
			return new ResponseEntity<Object>("deal not found!",
					HttpStatus.NOT_FOUND);
		}

		logger.info("get deal Successfully!");
		return new ResponseEntity<Object>(getDealList, HttpStatus.OK);
	}

	//get all deals
	@RequestMapping(value = DealRestURIConstants.GET_ALL_DEAL, method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Object> getAllDeals() {
		logger.info("Start getDeals.");

		List<Deal> getDealList = dealRepository.findAll();
		if (null == getDealList){
			logger.info("no deal found");
			return new ResponseEntity<Object>("no deal found!", HttpStatus.NOT_FOUND);
		}

		logger.info("get all deals Successfully!");
		return new ResponseEntity<Object>(getDealList, HttpStatus.OK);
	}
}