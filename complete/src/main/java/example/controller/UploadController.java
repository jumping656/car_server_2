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

	private static final String USERAVATAR_PATH  = "/usr/useravatar/";
	private static final String COACHAVATAR_PATH = "/usr/coachavatar/";
	private static final String COACHIDCARD_PATH = "/usr/coachidcard/";
	private static final String COACHCARD_PATH   = "/usr/coachcard/";

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CoachRepository coachRepository;

	//private static final String FILE_PATH = "C:\\Users\\EJIPING\\repository\\";
	private static final Logger logger = LoggerFactory.getLogger(UploadController.class);

	//dispatch different func to handler, return status
	@RequestMapping(value = UploadRestParamConstants.UPLOAD_FILE, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> uploadFileDispatcher(@PathVariable("registerphone")String registerPhone,
													   @RequestParam(value="type")String type,
													   @RequestParam(value="file")MultipartFile file){
		logger.info("Start uploadFileDispath.");

		if (null == file || file.isEmpty()){
			logger.info("MultipartFile empty");
			return new ResponseEntity<String>("MultipartFile empty",
					HttpStatus.NOT_FOUND);
		}
		if (null == registerPhone || registerPhone.isEmpty()){
			logger.info("registerPhone empty");
			return new ResponseEntity<String>("registerPhone empty",
					HttpStatus.NOT_FOUND);
		}

		if (type.equals(UploadRestParamConstants.FILE_TYPE_USERAVATAR))
		{
			if (!userAvatarHandler(registerPhone, file)){
				return new ResponseEntity<String>("upload file failed",
						HttpStatus.NOT_FOUND);
			}
		}
		else if (type.equals(UploadRestParamConstants.FILE_TYPE_COACHAVATAR)){
			if (!coachAvatarHandler(registerPhone, file)){
				return new ResponseEntity<String>("upload file failed",
						HttpStatus.NOT_FOUND);
			}
		}
		else if (type.equals(UploadRestParamConstants.FILE_TYPE_COACHIDCARD)){
			if (!coachIdCardAvatarHandler(registerPhone, file)){
				return new ResponseEntity<String>("upload file failed",
						HttpStatus.NOT_FOUND);
			}
		}
		else if (type.equals(UploadRestParamConstants.FILE_TYPE_COACHCARD)){
			if (!coachCardAvatarHandler(registerPhone, file)){
				return new ResponseEntity<String>("upload file failed",
						HttpStatus.NOT_FOUND);
			}
		}
		else {
			return new ResponseEntity<String>("file type invalid",
					HttpStatus.NOT_FOUND);
		}

		logger.info("uploadFileDispath Successfully!");
		return new ResponseEntity<String>("", HttpStatus.OK);
	}

	//useravatar handler
	public boolean userAvatarHandler(String registerphone, MultipartFile file){
		String fileName;

		String suffixName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		fileName = registerphone + suffixName;

		File dir = new File(USERAVATAR_PATH);
		if (!dir.exists()) {
			dir.mkdir();
		}

		try {
			file.transferTo(new File(dir + fileName));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		//update file path to DB
		User getUser = userRepository.findByUsername(registerphone);
		if (null == getUser){
			logger.info("user not exists");
			return false;
		}
		try {
			getUser.setPicture(dir + fileName);
			userRepository.save(getUser);
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("update user failed." + e.toString());
			return false;
		} finally {
//			try {
//				conn.close();
//			} catch (SQLException e1) {
//				e1.printStackTrace();
//			}
		}

		return true;
	}

	//coachavatar handler
	public boolean coachAvatarHandler(String registerphone, MultipartFile file){
		String fileName;

		String suffixName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		fileName = registerphone + suffixName;

		File dir = new File(COACHAVATAR_PATH);
		if (!dir.exists()) {
			dir.mkdir();
		}

		try {
			file.transferTo(new File(dir + fileName));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		//update file path to DB
		Coach getCoach = coachRepository.findByRegisterphone(registerphone);
		if (null == getCoach){
			logger.info("coach not exists");
			return false;
		}
		try {
			getCoach.setPicture(dir + fileName);
			coachRepository.save(getCoach);
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("update coach failed." + e.toString());
			return false;
		} finally {
//			try {
//				conn.close();
//			} catch (SQLException e1) {
//				e1.printStackTrace();
//			}
		}

		return true;
	}

	//coachidcard handler
	public boolean coachIdCardAvatarHandler(String registerphone, MultipartFile file){
		String fileName;
		String separator = "_coachid_";

		String suffixName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		fileName = registerphone + separator + suffixName;

		File dir = new File(COACHIDCARD_PATH);
		if (!dir.exists()) {
			dir.mkdir();
		}

		try {
			file.transferTo(new File(dir + fileName));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		//update file path to DB
		Coach getCoach = coachRepository.findByRegisterphone(registerphone);
		if (null == getCoach){
			logger.info("coach not exists");
			return false;
		}
		try {
			getCoach.setIdcardpicture(dir + fileName);
			coachRepository.save(getCoach);
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("update coach failed." + e.toString());
			return false;
		} finally {
//			try {
//				conn.close();
//			} catch (SQLException e1) {
//				e1.printStackTrace();
//			}
		}

		return true;
	}

	//coachcard handler
	public boolean coachCardAvatarHandler(String registerphone, MultipartFile file){
		String fileName;
		String separator = "_coachcard_";

		String suffixName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		fileName = registerphone + separator + suffixName;

		File dir = new File(COACHCARD_PATH);
		if (!dir.exists()) {
			dir.mkdir();
		}

		try {
			file.transferTo(new File(dir + fileName));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		//update file path to DB
		Coach getCoach = coachRepository.findByRegisterphone(registerphone);
		if (null == getCoach){
			logger.info("coach not exists");
			return false;
		}
		try {
			getCoach.setCoachpicture(dir + fileName);
			coachRepository.save(getCoach);
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("update coach failed." + e.toString());
			return false;
		} finally {
//			try {
//				conn.close();
//			} catch (SQLException e1) {
//				e1.printStackTrace();
//			}
		}

		return true;
	}
}
