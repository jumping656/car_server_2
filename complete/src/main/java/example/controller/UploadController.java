package example.controller;

import com.sun.deploy.association.utility.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by ejiping on 2016/2/24.
 */

@Controller
@RestController
public class UploadController {

	//private static final String FILE_PATH = "/usr/picture";
	private static final String FILE_PATH = "C:\\Users\\EJIPING\\repository\\";
	private static final Logger logger = LoggerFactory.getLogger(UploadController.class);

	//dispatch different func to handler, return status
	@RequestMapping(value = UploadRestParamConstants.UPLOAD_FILE, method = RequestMethod.POST)
	@ResponseBody
	public void uploadFileDispatcher(@PathVariable("registerphone")String registerPhone,
						   @RequestParam(value="type")String type,
						   @RequestParam(value="file")MultipartFile file){

		if (null!=file&&!file.isEmpty()){
			//dispatch

		}



		try {
			AppConstants

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//useravatar handler
	//coachavatar handler
	//coachidcard handler
	//coachcard handler
}
