package example.controller;

import alipay.util.AlipayNotify;
import example.domain.PayOrder;
import example.repository.DealRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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

	@RequestMapping(value = "/car/pay/getpayinfo", method = RequestMethod.POST, headers = "content-type=application/x-www-form-urlencoded")
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

	@RequestMapping(value = "/car/pay/async")
	@ResponseBody
	public String payOver(HttpServletRequest request, HttpServletResponse response) {
		Map<String,String> params = new HashMap<String,String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]: valueStr + values[i] + ",";
			}
			params.put(name, valueStr);
		}
		String tradeNo = request.getParameter("out_trade_no");
		String tradeStatus = request.getParameter("trade_status");
		//String notifyId = request.getParameter("notify_id");
		if(AlipayNotify.verify(params)){//验证成功
			if(tradeStatus.equals("TRADE_FINISHED") || tradeStatus.equals("TRADE_SUCCESS")) {
				//要写的逻辑。自己按自己的要求写
				//payorder的参数信息全部来自异步通知参数
				logger.info("ok.......");
				System.out.println(">>>>>交易成功" + tradeNo);
			}
			return "success";
		}else{//验证失败
			return "fail";
		}
	}
}