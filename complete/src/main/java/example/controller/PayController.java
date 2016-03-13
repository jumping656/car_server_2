package example.controller;

import alipay.util.AlipayNotify;
import example.domain.PayOrder;
import example.repository.DealRepository;
import example.repository.PayRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * Handles requests for the Pay service.
 */

@Controller
@RestController
public class PayController {

	private static final Logger logger = LoggerFactory.getLogger(PayController.class);

	@Autowired
	private DealRepository dealRepository;
	@Autowired
	private PayRepository payRepository;

	@RequestMapping(value = "/car/pay/getpaystring", method = RequestMethod.POST, headers = "content-type=application/x-www-form-urlencoded")
	@ResponseBody
	public ResponseEntity<Object> getPayInfo(HttpServletRequest request, HttpServletResponse response) throws ServletRequestBindingException {
		logger.info("Start getPayInfo.");
		PayOrder payOrder = new PayOrder();

		payOrder.setBody(ServletRequestUtils.getStringParameter(request, "body", ""));
		payOrder.setPrice(ServletRequestUtils.getIntParameter(request, "price"));
		payOrder.setSubject(ServletRequestUtils.getStringParameter(request, "subject", ""));

		String orderInfo = payOrder.getOrderInfo();
		logger.info("orderInfo: " + orderInfo);

		/**
		 * 特别注意，这里的签名逻辑需要放在服务端，切勿将私钥泄露在代码中！
		 */
		String sign = PayOrder.sign(orderInfo);
		try {
			/**
			 * 仅需对sign 做URL编码
			 */
			sign = URLEncoder.encode(sign, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		/**
		 * 完整的符合支付宝参数规范的订单信息
		 */
		final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + PayOrder.getSignType();
		return new ResponseEntity<>(payInfo, HttpStatus.OK);
	}

	@RequestMapping(value = "/car/pay/async", method = RequestMethod.POST, headers = "content-type=application/x-www-form-urlencoded")
	@ResponseBody
	public String payOver(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> params = new HashMap<String, String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			params.put(name, valueStr);
		}
		String tradeNo = request.getParameter("out_trade_no");
		String tradeStatus = request.getParameter("trade_status");
		//String notifyId = request.getParameter("notify_id");
		String total_fee = request.getParameter("total_fee");
		String orderNo = request.getParameter("trade_no");
		String buyer_email = request.getParameter("buyer_email");
		String quantity = request.getParameter("quantity");
		String subject = request.getParameter("subject");
		String body = request.getParameter("body");

		if (AlipayNotify.verify(params)) {//验证成功
			if (tradeStatus.equals("TRADE_FINISHED") || tradeStatus.equals("TRADE_SUCCESS")) {
				//要写的逻辑。自己按自己的要求写
				logger.info("pay success, Start update payorder info.");

				if (null == tradeNo || tradeNo.isEmpty()) {
					logger.info("out_trade_no is empty");
					return "fail";
				}

				try {
					PayOrder payOrder = new PayOrder();
					payOrder.setOut_trade_no(tradeNo); //此处需要转换
					payOrder.setStatusCode("10"); //状态码，表示已支付
					payOrder.setTotal_fee(Integer.parseInt(total_fee));
					payOrder.setOrderNo(orderNo);
					payOrder.setBuyer_email(buyer_email);
					payOrder.setQuantity(Integer.parseInt(quantity));
					payOrder.setSubject(subject);
					payOrder.setBody(body);
					payRepository.save(payOrder);
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("update payorder failed." + e.toString());
				} finally {
//			try {
//				conn.close();
//			} catch (SQLException e1) {
//				e1.printStackTrace();
//			}
				}
			}
			return "success";
		} else {//验证失败
			return "fail";
		}
	}

	//get user by userid
	@RequestMapping(value = UserRestURIConstants.GET_USER, method = RequestMethod.POST, headers = "content-type=application/x-www-form-urlencoded")
	@ResponseBody
	public ResponseEntity<Object> getUser(@ModelAttribute User user) {
		if (null == user.getUserid() || user.getUserid() <= 0){ //null == user.getUserid() must be ahead of <=0, or will be NullPointerException
			logger.info("userid is empty");
			return new ResponseEntity<Object>("userid invalid",
					HttpStatus.NOT_FOUND);
		}
		logger.info("Start getUser. userid= " + user.getUserid());

		User getUser = userRepository.findByUserid(user.getUserid());
		if (null == getUser){
			logger.info("user not found!");
			return new ResponseEntity<Object>("userid invalid",
					HttpStatus.NOT_FOUND);
		}

		logger.info("get user Successfully!");
		return new ResponseEntity<Object>(getUser, HttpStatus.OK);
	}
}