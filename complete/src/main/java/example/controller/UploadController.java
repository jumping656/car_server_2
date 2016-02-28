package example.controller;

import example.domain.Coach;
import example.domain.User;
import example.repository.CoachRepository;
import example.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * Created by ejiping on 2016/2/24.
 */

@Controller
@RestController
public class UploadController {

	//private static final String USERAVATAR_PATH  = "/usr/useravatar/";
	//private static final String COACHAVATAR_PATH = "/usr/coachavatar/";
	//private static final String COACHIDCARD_PATH = "/usr/coachidcard/";
	//private static final String COACHCARD_PATH   = "/usr/coachcard/";

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CoachRepository coachRepository;

	private static final String USERAVATAR_PATH = "C:\\Users\\EJIPING";
	private static final String SEPARATOR = "\\";
	private static final Logger logger = LoggerFactory.getLogger(UploadController.class);

	//dispatch different func to handler, return status
	@RequestMapping(value = UploadRestParamConstants.UPLOAD_FILE, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Object> uploadFileDispatcher(@PathVariable("registerphone")String registerPhone,
													   @RequestParam(value="type")String type,
													   @RequestParam(value="file")MultipartFile file){
		logger.info("Start uploadFileDispath.");
		logger.info("type: " + type);

		if (null == file || file.isEmpty()){
			logger.info("MultipartFile empty");
			return new ResponseEntity<Object>("MultipartFile empty",
					HttpStatus.NOT_FOUND);
		}
		if (null == registerPhone || registerPhone.isEmpty()){
			logger.info("registerPhone empty");
			return new ResponseEntity<Object>("registerPhone empty",
					HttpStatus.NOT_FOUND);
		}

		if (type.equals(UploadRestParamConstants.FILE_TYPE_USERAVATAR))
		{
			User getUser = userAvatarHandler(registerPhone, file);
			if (null != getUser){
				logger.info("upload file successfully, return object!");
				return new ResponseEntity<Object>(getUser,
						HttpStatus.OK);
			}
			return new ResponseEntity<Object>("upload user avatar fail, please check log!",
					HttpStatus.NOT_FOUND);
		}
		else if (type.equals(UploadRestParamConstants.FILE_TYPE_COACHAVATAR)){
			Coach getCoach = coachAvatarHandler(registerPhone, file);
			if (null != getCoach){
				logger.info("upload file successfully, return object!");
				return new ResponseEntity<Object>(getCoach,
						HttpStatus.OK);
			}
			return new ResponseEntity<Object>("upload coach avatar fail, please check log!",
					HttpStatus.NOT_FOUND);
		}
		else if (type.equals(UploadRestParamConstants.FILE_TYPE_COACHIDCARD)){
			Coach getCoach = coachIdCardAvatarHandler(registerPhone, file);
			if (null != getCoach){
				logger.info("upload file successfully, return object!");
				return new ResponseEntity<Object>(getCoach,
						HttpStatus.OK);
			}
			return new ResponseEntity<Object>("upload coach id card fail, please check log!",
					HttpStatus.NOT_FOUND);
		}
		else if (type.equals(UploadRestParamConstants.FILE_TYPE_COACHCARD)){
			Coach getCoach = coachCardAvatarHandler(registerPhone, file);
			if (null != getCoach){
				logger.info("upload file successfully, return object!");
				return new ResponseEntity<Object>(getCoach,
						HttpStatus.OK);
			}
			return new ResponseEntity<Object>("upload coach card fail, please check log!",
					HttpStatus.NOT_FOUND);
		}
		else {
			return new ResponseEntity<Object>("file type invalid",
					HttpStatus.NOT_FOUND);
		}
	}

	//useravatar handler
	public User userAvatarHandler(String registerphone, MultipartFile file){
		String fileName;

		String suffixName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		fileName = SEPARATOR + registerphone + suffixName;
		logger.info("hahaha");

		File dir = new File(USERAVATAR_PATH);
		if (!dir.exists()) {
			dir.mkdir();
		}

		try {
			file.transferTo(new File(dir + fileName));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		//update file path to DB
		User getUser = userRepository.findByRegisterphone(registerphone);
		logger.info("registerphone: " + registerphone);
		if (null == getUser){
			logger.info("user not exists");
			return null;
		}
		try {
			getUser.setPicture(dir + fileName);
			userRepository.save(getUser);
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("update user failed." + e.toString());
			return null;
		} finally {
//			try {
//				conn.close();
//			} catch (SQLException e1) {
//				e1.printStackTrace();
//			}
		}

		return getUser;
	}

	//coachavatar handler
	public Coach coachAvatarHandler(String registerphone, MultipartFile file){
		String fileName;

		String suffixName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		fileName = SEPARATOR + registerphone + suffixName;

		//File dir = new File(COACHAVATAR_PATH);
		File dir = new File(USERAVATAR_PATH);
		if (!dir.exists()) {
			dir.mkdir();
		}

		try {
			file.transferTo(new File(dir + fileName));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		//update file path to DB
		Coach getCoach = coachRepository.findByRegisterphone(registerphone);
		if (null == getCoach){
			logger.info("coach not exists");
			return null;
		}
		try {
			getCoach.setPicture(dir + fileName);
			coachRepository.save(getCoach);
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("update coach failed." + e.toString());
			return null;
		} finally {
//			try {
//				conn.close();
//			} catch (SQLException e1) {
//				e1.printStackTrace();
//			}
		}

		return getCoach;
	}

	//coachidcard handler
	public Coach coachIdCardAvatarHandler(String registerphone, MultipartFile file){
		String fileName;
		String separator = "_coachid_";

		String suffixName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		fileName = SEPARATOR + registerphone + separator + suffixName;

		//File dir = new File(COACHIDCARD_PATH);
		File dir = new File(USERAVATAR_PATH);
		if (!dir.exists()) {
			dir.mkdir();
		}

		try {
			file.transferTo(new File(dir + fileName));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		//update file path to DB
		Coach getCoach = coachRepository.findByRegisterphone(registerphone);
		if (null == getCoach){
			logger.info("coach not exists");
			return null;
		}
		try {
			getCoach.setIdcardpicture(dir + fileName);
			coachRepository.save(getCoach);
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("update coach failed." + e.toString());
			return null;
		} finally {
//			try {
//				conn.close();
//			} catch (SQLException e1) {
//				e1.printStackTrace();
//			}
		}

		return getCoach;
	}

	//coachcard handler
	public Coach coachCardAvatarHandler(String registerphone, MultipartFile file){
		String fileName;
		String separator = "_coachcard_";

		String suffixName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		fileName = SEPARATOR + registerphone + separator + suffixName;

		//File dir = new File(COACHCARD_PATH);
		File dir = new File(USERAVATAR_PATH);
		if (!dir.exists()) {
			dir.mkdir();
		}

		try {
			file.transferTo(new File(dir + fileName));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		//update file path to DB
		Coach getCoach = coachRepository.findByRegisterphone(registerphone);
		if (null == getCoach){
			logger.info("coach not exists");
			return null;
		}
		try {
			getCoach.setCoachpicture(dir + fileName);
			coachRepository.save(getCoach);
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("update coach failed." + e.toString());
			return null;
		} finally {
//			try {
//				conn.close();
//			} catch (SQLException e1) {
//				e1.printStackTrace();
//			}
		}

		return getCoach;
	}
}
