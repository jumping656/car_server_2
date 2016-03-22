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

	@RequestMapping(value = DealRestURIConstants.CREATE_DEAL, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Object> createDeal(@RequestBody Deal deal) {
		logger.info("Start createDeal.");
		ResponseResult responseResult = new ResponseResult();

		if (null == deal.getUserid() || deal.getUserid() <= 0 ||
			null == deal.getCoachid() || deal.getCoachid() <= 0 ||
			null == deal.getTradeno() || "" == deal.getTradeno()){
			logger.info("invalid deal params");
			responseResult.setCode(ResponseResult.PARAM_ERROR);
			responseResult.setResult(deal);
			return new ResponseEntity<>(responseResult,
					HttpStatus.NOT_FOUND);
		}

		List<Deal> getDealList= dealRepository.findByUseridAndState(deal.getUserid(), Deal.DEAL_STATE.ONGOING);
		if (!getDealList.isEmpty()){
			logger.info("user ongoing deal exits");
			responseResult.setCode(ResponseResult.CONSISTENCY_ERROR);
			responseResult.setResult(deal);
			return new ResponseEntity<>(responseResult,
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
			responseResult.setCode(ResponseResult.OPERATION_ERROR);
			responseResult.setResult(deal);
			return new ResponseEntity<>(responseResult, HttpStatus.NOT_FOUND);
		} finally {
//			try {
//				conn.close();
//			} catch (SQLException e1) {
//				e1.printStackTrace();
//			}
		}

		logger.info("create deal Successfully!");
		responseResult.setCode(ResponseResult.SUCCESS);
		responseResult.setResult(deal);
		return new ResponseEntity<>(responseResult, HttpStatus.OK);
	}

	//update deal
	@RequestMapping(value = DealRestURIConstants.UPDATE_DEAL, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Object> updateDeal(@RequestBody Deal deal) {
		logger.info("Start updateDeal.");
		ResponseResult responseResult = new ResponseResult();

		if (deal.getDealid() <= 0 || null == deal.getDealid()){
			logger.info("dealid is empty");
			responseResult.setCode(ResponseResult.PARAM_ERROR);
			responseResult.setResult(deal);
			return new ResponseEntity<>(responseResult,
					HttpStatus.NOT_FOUND);
		}

		Deal getDeal = dealRepository.findByDealid(deal.getDealid());
		if (null == getDeal){
			logger.info("deal not exists");
			responseResult.setCode(ResponseResult.CONSISTENCY_ERROR);
			responseResult.setResult(deal);
			return new ResponseEntity<>(responseResult,
					HttpStatus.NOT_FOUND);
		}

		if ((getDeal.getUserid()  != deal.getUserid() && null != deal.getUserid()) ||
				(getDeal.getCoachid() != deal.getCoachid() && null != deal.getCoachid())) {
			logger.info("userid or coachid can not change");
			responseResult.setCode(ResponseResult.CONSISTENCY_ERROR);
			responseResult.setResult(getDeal);
			return new ResponseEntity<>(responseResult,
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
		responseResult.setCode(ResponseResult.SUCCESS);
		responseResult.setResult(getDeal);
		return new ResponseEntity<>(responseResult, HttpStatus.OK);
	}

	//delete deal
	@RequestMapping(value = DealRestURIConstants.DELETE_DEAL, method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<Object> deleteDeal(@RequestBody Deal deal) {
		logger.info("Start deleteDeal.");
		ResponseResult responseResult = new ResponseResult();

		if (null == deal.getDealid() || deal.getDealid() <= 0){
			logger.info("dealid invalid");
			responseResult.setCode(ResponseResult.PARAM_ERROR);
			responseResult.setResult(deal);
			return new ResponseEntity<>(responseResult,
					HttpStatus.NOT_FOUND);
		}

		Deal getDeal = dealRepository.findByDealid(deal.getDealid());
		if (null == getDeal){
			logger.info("deal not exists");
			responseResult.setCode(ResponseResult.CONSISTENCY_ERROR);
			responseResult.setResult(deal);
			return new ResponseEntity<>(responseResult,
					HttpStatus.NOT_FOUND);
		}

		try {
			dealRepository.delete(getDeal);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("delete deal failed");
			responseResult.setCode(ResponseResult.OPERATION_ERROR);
			responseResult.setResult(getDeal);
			return new ResponseEntity<>(responseResult,
					HttpStatus.NOT_FOUND);
		} finally {
//			try {
//				conn.close();
//			} catch (SQLException e1) {
//				e1.printStackTrace();
//			}
		}

		logger.info("delete deal Successfully!");
		responseResult.setCode(ResponseResult.SUCCESS);
		responseResult.setResult(getDeal);
		return new ResponseEntity<>(responseResult, HttpStatus.OK);
	}

	//get deal by dealid
	@RequestMapping(value = DealRestURIConstants.GET_DEAL, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Object> getDeal(@RequestBody Deal deal) {
		logger.info("haha, get deal : ");
		ResponseResult responseResult = new ResponseResult();

		if (null == deal.getDealid() || deal.getDealid() <= 0){
			logger.info("dealid is empty");
			responseResult.setCode(ResponseResult.PARAM_ERROR);
			responseResult.setResult(deal);
			return new ResponseEntity<>(responseResult,
					HttpStatus.NOT_FOUND);
		}
		logger.info("Start getDeal.dealid= " + deal.getDealid());

		Deal getDeal = dealRepository.findByDealid(deal.getDealid());
		if (null == getDeal){
			logger.info("deal not found!");
			responseResult.setCode(ResponseResult.CONSISTENCY_ERROR);
			responseResult.setResult(deal);
			return new ResponseEntity<>(responseResult,
					HttpStatus.NOT_FOUND);
		}

		logger.info("get deal Successfully!");
		responseResult.setCode(ResponseResult.SUCCESS);
		responseResult.setResult(getDeal);
		return new ResponseEntity<>(responseResult, HttpStatus.OK);
	}

	//get deal by coachid
	@RequestMapping(value = DealRestURIConstants.GET_DEAL_BY_COACH, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Object> getDealByCoachid(@PathVariable("coachid")Integer coachid) {
		ResponseResult responseResult = new ResponseResult();

		if (null == coachid || coachid <= 0){
			logger.info("coachid is empty");
			responseResult.setCode(ResponseResult.PARAM_ERROR);
			return new ResponseEntity<>(responseResult,
					HttpStatus.NOT_FOUND);
		}
		logger.info("Start getDeals.coachid= " + coachid);

		List<Deal> getDealList = dealRepository.findByCoachid(coachid);
		if (getDealList.isEmpty()){
			logger.info("deal not found!");
			responseResult.setCode(ResponseResult.CONSISTENCY_ERROR);
			return new ResponseEntity<>(responseResult,
					HttpStatus.NOT_FOUND);
		}

		logger.info("get deal Successfully!");
		responseResult.setCode(ResponseResult.SUCCESS);
		responseResult.setResult(getDealList);
		return new ResponseEntity<>(responseResult, HttpStatus.OK);
	}

	//get deal by userid
	@RequestMapping(value = DealRestURIConstants.GET_DEAL_BY_USER, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Object> getDealByUserid(@PathVariable("userid")Integer userid) {
		ResponseResult responseResult = new ResponseResult();

		if (null == userid || userid <= 0){
			logger.info("userid is empty");
			responseResult.setCode(ResponseResult.PARAM_ERROR);
			return new ResponseEntity<>(responseResult,
					HttpStatus.NOT_FOUND);
		}
		logger.info("Start getDeals.userid= " + userid);

		List<Deal> getDealList = dealRepository.findByUserid(userid);
		if (getDealList.isEmpty()){
			logger.info("deal not found!");
			responseResult.setCode(ResponseResult.CONSISTENCY_ERROR);
			return new ResponseEntity<>(responseResult,
					HttpStatus.NOT_FOUND);
		}

		logger.info("get deal Successfully!");
		responseResult.setCode(ResponseResult.SUCCESS);
		responseResult.setResult(getDealList);
		return new ResponseEntity<>(responseResult, HttpStatus.OK);
	}

	//get all deals
	@RequestMapping(value = DealRestURIConstants.GET_ALL_DEAL, method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Object> getAllDeals() {
		logger.info("Start getDeals.");
		ResponseResult responseResult = new ResponseResult();

		List<Deal> getDealList = dealRepository.findAll();
		if (null == getDealList){
			logger.info("no deal found");
			responseResult.setCode(ResponseResult.CONSISTENCY_ERROR);
			return new ResponseEntity<>(responseResult, HttpStatus.NOT_FOUND);
		}

		logger.info("get all deals Successfully!");
		responseResult.setCode(ResponseResult.SUCCESS);
		responseResult.setResult(getDealList);
		return new ResponseEntity<>(responseResult, HttpStatus.OK);
	}
}