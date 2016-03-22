package example.controller;

import example.domain.ErrorQuestion;
import example.repository.ErrorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by ejiping on 2016/2/21.
 * 该类处理模拟考试模块错题记录的controller类
 */
@Controller
@RestController
public class ErrorController {

	private static final Logger logger = LoggerFactory.getLogger(ErrorController.class);

	@Autowired
	private ErrorRepository errorRepository;

	//create subject_one error question
	@RequestMapping(value = ErrorRestURIConstants.CREATE_ERROR_ONE, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Object> createErrorForOne(@RequestBody ErrorQuestion errorQuestion) {
		logger.info("Start createErrorQuestion for subject_one.");

		if (null == errorQuestion.getSubjectoneid() || errorQuestion.getSubjectoneid() <= 0 ||
				null == errorQuestion.getUserid() || errorQuestion.getUserid() <= 0) {
			logger.info("invalid error question record params");
			return new ResponseEntity<>("invalid error question record params",
					HttpStatus.NOT_FOUND);
		}

		List<ErrorQuestion> errorQuestionList = errorRepository.findByUserid(errorQuestion.getUserid());
		for (int i = 0; i < errorQuestionList.size(); i++) {
			ErrorQuestion errorIterator = errorQuestionList.get(i);
			if (errorIterator.getSubjectoneid() == errorQuestion.getSubjectoneid()) {
				logger.info("error question record exists.");
				return new ResponseEntity<>("error question record exists.",
						HttpStatus.CONFLICT);
			}
		}

		try {
			errorQuestion.setSubjectfourid(null);
			errorRepository.save(errorQuestion);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("create error question record failed.");
			return new ResponseEntity<>("create error question record failed.", HttpStatus.NOT_FOUND);
		} finally {
//			try {
//				conn.close();
//			} catch (SQLException e1) {
//				e1.printStackTrace();
//			}
		}

		logger.info("create error question record Successfully!");
		return new ResponseEntity<>(errorQuestion, HttpStatus.OK);
	}

	//create subject_four error question
	@RequestMapping(value = ErrorRestURIConstants.CREATE_ERROR_FOUR, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Object> createErrorForFour(@RequestBody ErrorQuestion errorQuestion) {
		logger.info("Start createErrorQuestion for subject_four.");

		if (null == errorQuestion.getSubjectfourid() || errorQuestion.getSubjectfourid() <= 0 ||
				null == errorQuestion.getUserid() || errorQuestion.getUserid() <= 0) {
			logger.info("invalid error question record params");
			return new ResponseEntity<>("invalid error question record params",
					HttpStatus.NOT_FOUND);
		}

		List<ErrorQuestion> errorQuestionList = errorRepository.findByUserid(errorQuestion.getUserid());
		for (int i = 0; i < errorQuestionList.size(); i++) {
			ErrorQuestion errorIterator = errorQuestionList.get(i);
			if (errorIterator.getSubjectfourid() == errorQuestion.getSubjectfourid()) {
				logger.info("error question record exists.");
				return new ResponseEntity<>("error question record exists.",
						HttpStatus.CONFLICT);
			}
		}

		try {
			errorQuestion.setSubjectoneid(null);
			errorRepository.save(errorQuestion);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("create error question record failed.");
			return new ResponseEntity<>("create error question record failed.", HttpStatus.NOT_FOUND);
		} finally {
//			try {
//				conn.close();
//			} catch (SQLException e1) {
//				e1.printStackTrace();
//			}
		}

		logger.info("create error question record Successfully!");
		return new ResponseEntity<Object>(errorQuestion, HttpStatus.OK);
	}

	//dispatch different func to handler, return status
	@RequestMapping(value = ErrorRestURIConstants.GET_ERRORS, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Object> getError(@PathVariable(value = "type") String type,
										   @RequestBody ErrorQuestion errorQuestion) {
		logger.info("Start get error question records Dispath.");
		logger.info("type: " + type);

		if (null == errorQuestion) {
			logger.info("errorQuestion empty");
			return new ResponseEntity<>("errorQuestion empty",
					HttpStatus.NOT_FOUND);
		}

		if (type.equals(ErrorRestURIConstants.GETTYPE_ERRORS_BY_ONEID)) {
			List<ErrorQuestion> getErrorQuestionList = getRecordByOneIdHandler(errorQuestion);
			if (null != getErrorQuestionList && !getErrorQuestionList.isEmpty()) {
				logger.info("get records successfully, return object!");
				return new ResponseEntity<>(getErrorQuestionList,
						HttpStatus.OK);
			}
			return new ResponseEntity<>("get records fail, please check log!",
					HttpStatus.NOT_FOUND);
		} else if (type.equals(ErrorRestURIConstants.GETTYPE_ERRORS_BY_FOUREID)) {
			List<ErrorQuestion> getErrorQuestionList = getRecordByFourIdHandler(errorQuestion);
			if (null != getErrorQuestionList && !getErrorQuestionList.isEmpty()) {
				logger.info("get records successfully, return object!");
				return new ResponseEntity<>(getErrorQuestionList,
						HttpStatus.OK);
			}
			return new ResponseEntity<>("get records fail, please check log!!",
					HttpStatus.NOT_FOUND);
		} else if (type.equals(ErrorRestURIConstants.GETTYPE_ERRORS_BY_USERID)) {
			List<ErrorQuestion> getErrorQuestionList = getRecordByUserIdHandler(errorQuestion);
			if (null != getErrorQuestionList && !getErrorQuestionList.isEmpty()) {
				logger.info("get records successfully, return object!");
				return new ResponseEntity<>(getErrorQuestionList,
						HttpStatus.OK);
			}
			return new ResponseEntity<>("get records fail, please check log!!",
					HttpStatus.NOT_FOUND);
		}
		else if (type.equals(ErrorRestURIConstants.GETTYPE_ERRORS_BY_ERRORID)) {
			List<ErrorQuestion> getErrorQuestionList = getRecordByErrorIdHandler(errorQuestion);
			if (null != getErrorQuestionList && !getErrorQuestionList.isEmpty()) {
				logger.info("get records successfully, return object!");
				return new ResponseEntity<>(getErrorQuestionList,
						HttpStatus.OK);
			}
			return new ResponseEntity<>("get records fail, please check log!!",
					HttpStatus.NOT_FOUND);
		}
		else if (type.equals(ErrorRestURIConstants.GETTYPE_ERRORS_BY_USERID_AND_ONEID)) {
			List<ErrorQuestion> getErrorQuestionList = getRecordByUserIdAndOneIdHandler(errorQuestion);
			if (null != getErrorQuestionList && !getErrorQuestionList.isEmpty()) {
				logger.info("get records successfully, return object!");
				return new ResponseEntity<>(getErrorQuestionList,
						HttpStatus.OK);
			}
			return new ResponseEntity<>("get records fail, please check log!!",
					HttpStatus.NOT_FOUND);
		}
		else if (type.equals(ErrorRestURIConstants.GETTYPE_ERRORS_BY_USERID_AND_FOURID)) {
			List<ErrorQuestion> getErrorQuestionList = getRecordByUserIdAndFourIdHandler(errorQuestion);
			if (null != getErrorQuestionList && !getErrorQuestionList.isEmpty()) {
				logger.info("get records successfully, return object!");
				return new ResponseEntity<>(getErrorQuestionList,
						HttpStatus.OK);
			}
			return new ResponseEntity<>("get records fail, please check log!!",
					HttpStatus.NOT_FOUND);
		}
		else{
			return new ResponseEntity<>("file type invalid",
					HttpStatus.NOT_FOUND);
		}
	}

	//delete record
	@RequestMapping(value = ErrorRestURIConstants.DELETE_ERROR_BY_ERRORID, method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<Object> deleteError(@RequestBody ErrorQuestion errorQuestion) {
		logger.info("Start delete error question record.");

		if (null == errorQuestion.getErrorid() || errorQuestion.getErrorid() <= 0) {
			logger.info("errorid invalid");
			return new ResponseEntity<>("errorid invalid",
					HttpStatus.NOT_FOUND);
		}

		List<ErrorQuestion> getErrorQuestionList = errorRepository.findByErrorid(errorQuestion.getErrorid());
		if (getErrorQuestionList.isEmpty()) {
			logger.info("error not exists");
			return new ResponseEntity<>("error not exists",
					HttpStatus.NOT_FOUND);
		}

		try {
			//only one record
			errorRepository.delete(getErrorQuestionList.get(0));
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("delete error record failed");
			return new ResponseEntity<>("delete error record failed",
					HttpStatus.NOT_FOUND);
		} finally {
//			try {
//				conn.close();
//			} catch (SQLException e1) {
//				e1.printStackTrace();
//			}
		}

		logger.info("delete record Successfully!");
		return new ResponseEntity<>("record deleted", HttpStatus.OK);
	}

	//get error record by oneid
	public List<ErrorQuestion> getRecordByOneIdHandler(ErrorQuestion errorQuestion) {
		if (null == errorQuestion.getSubjectoneid() || errorQuestion.getSubjectoneid() <= 0){
			logger.info("oneid is empty");
			return null;
		}

		List<ErrorQuestion> getErrorQuestionList = errorRepository.findBySubjectoneid(errorQuestion.getSubjectoneid());
		if (getErrorQuestionList.isEmpty()){
			logger.info("record not found!");
			return null;
		}

		logger.info("get records Successfully!");
		return getErrorQuestionList;
	}

	//get error record by fourid
	public List<ErrorQuestion> getRecordByFourIdHandler(ErrorQuestion errorQuestion) {
		if (null == errorQuestion.getSubjectfourid() || errorQuestion.getSubjectfourid() <= 0){
			logger.info("fourid is empty");
			return null;
		}

		List<ErrorQuestion> getErrorQuestionList = errorRepository.findBySubjectfourid(errorQuestion.getSubjectfourid());
		if (getErrorQuestionList.isEmpty()){
			logger.info("record not found!");
			return null;
		}

		logger.info("get records Successfully!");
		return getErrorQuestionList;
	}

	//get error record by userid
	public List<ErrorQuestion> getRecordByUserIdHandler(ErrorQuestion errorQuestion) {
		if (null == errorQuestion.getUserid() || errorQuestion.getUserid() <= 0){
			logger.info("userid is empty");
			return null;
		}

		List<ErrorQuestion> getErrorQuestionList = errorRepository.findByUserid(errorQuestion.getUserid());
		if (getErrorQuestionList.isEmpty()){
			logger.info("record not found!");
			return null;
		}

		logger.info("get records Successfully!");
		return getErrorQuestionList;
	}

	//get error record by errorid
	public List<ErrorQuestion> getRecordByErrorIdHandler(ErrorQuestion errorQuestion) {
		if (null == errorQuestion.getErrorid() || errorQuestion.getErrorid() <= 0){
			logger.info("errorid is empty");
			return null;
		}

		List<ErrorQuestion> getErrorQuestionList = errorRepository.findByErrorid(errorQuestion.getErrorid());
		if (getErrorQuestionList.isEmpty()){
			logger.info("record not found!");
			return null;
		}

		logger.info("get records Successfully!");
		return getErrorQuestionList;
	}

	//get error record by userid and oneid
	public List<ErrorQuestion> getRecordByUserIdAndOneIdHandler(ErrorQuestion errorQuestion) {
		if (null == errorQuestion.getUserid() || errorQuestion.getUserid() <= 0 ||
			null == errorQuestion.getSubjectoneid() || errorQuestion.getSubjectoneid() <= 0 ){
			logger.info("params invalid");
			return null;
		}

		List<ErrorQuestion> getErrorQuestionList = errorRepository.findByUseridAndSubjectoneid(errorQuestion.getUserid(), errorQuestion.getSubjectoneid());
		if (getErrorQuestionList.isEmpty()){
			logger.info("record not found!");
			return null;
		}

		logger.info("get records Successfully!");
		return getErrorQuestionList;
	}

	//get error record by userid and oneid
	public List<ErrorQuestion> getRecordByUserIdAndFourIdHandler(ErrorQuestion errorQuestion) {
		if (null == errorQuestion.getUserid() || errorQuestion.getUserid() <= 0 ||
				null == errorQuestion.getSubjectfourid() || errorQuestion.getSubjectfourid() <= 0 ){
			logger.info("params invalid");
			return null;
		}

		List<ErrorQuestion> getErrorQuestionList = errorRepository.findByUseridAndSubjectfourid(errorQuestion.getUserid(), errorQuestion.getSubjectfourid());
		if (getErrorQuestionList.isEmpty()){
			logger.info("record not found!");
			return null;
		}

		logger.info("get records Successfully!");
		return getErrorQuestionList;
	}
}