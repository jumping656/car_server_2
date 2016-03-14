package example.controller;

import Push.MessagePush;
import alipay.util.AlipayNotify;
import example.domain.Deal;
import example.domain.Pay;
import example.domain.User;
import example.repository.DealRepository;
import example.repository.PayRepository;
import example.repository.UserRepository;
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
import java.util.*;


/**
 * Handles requests for the Pay service.
 */

@Controller
@RestController
public class PayController {

	private static final Logger logger = LoggerFactory.getLogger(PayController.class);

	@Autowired
	private PayRepository payRepository;
	@Autowired
	private DealRepository dealRepository;
	@Autowired
	private UserRepository userRepository;

	@RequestMapping(value = "/car/pay/getpaystring", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Object> getPayInfo(HttpServletRequest request, HttpServletResponse response) throws ServletRequestBindingException {
		logger.info("Start getPayInfo.");
		Pay pay = new Pay();

		pay.setBody(ServletRequestUtils.getStringParameter(request, "body", ""));
		pay.setPrice(ServletRequestUtils.getIntParameter(request, "price"));
		pay.setSubject(ServletRequestUtils.getStringParameter(request, "subject", ""));

		String orderInfo = pay.getOrderInfo();
		logger.info("orderInfo: " + orderInfo);

		/**
		 * 特别注意，这里的签名逻辑需要放在服务端，切勿将私钥泄露在代码中！
		 */
		String sign = Pay.sign(orderInfo);
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
		final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + Pay.getSignType();
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
					Pay pay = new Pay();
					pay.setTradeno(tradeNo); //此处需要转换
					pay.setStatusCode("10"); //状态码，表示已支付
					pay.setTotal_fee(Integer.parseInt(total_fee));
					pay.setOrderNo(orderNo);
					pay.setBuyeremail(buyer_email);
					pay.setQuantity(Integer.parseInt(quantity));
					pay.setSubject(subject);
					pay.setBody(body);
					payRepository.save(pay);

					//根据out_trade_no获取当前用户名
					List<Deal> dealList = dealRepository.findBytradeno(tradeNo);
					User user = userRepository.findByUserid(dealList.get(0).getUserid());
					//初始化消息发送类
					String message = "[风火轮支付消息]您已支付成功" + total_fee + "元。";
					String title = "风火轮驾考平台通知";
					MessagePush messagePush = new MessagePush(message, title);
					Set<String> alias = new HashSet<>();
					alias.add(user.getUsername());
					long status = messagePush.sendPushAlias(alias);
					if (0 == status){
						//发送消息通知失败，考虑使用短信发送；
						return "success";
					}

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

	//get pay info by out_trade_no
	@RequestMapping(value = "/car/pay/getpayinfo", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Object> getUser(@RequestBody Pay pay) {
		if (null == pay.getTradeno() || "" == pay.getTradeno()){
			logger.info("out_trade_no is empty");
			return new ResponseEntity<Object>("out_trade_no is empty",
					HttpStatus.NOT_FOUND);
		}
		logger.info("Start getUser. out_trade_no= " + pay.getTradeno());

		List<Pay> getPayList = payRepository.findBytradeno(pay.getTradeno());
		if (getPayList.isEmpty()){
			logger.info("payorder not found!");
			return new ResponseEntity<Object>("payorder not found!",
					HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Object>(getPayList, HttpStatus.OK);
	}
}