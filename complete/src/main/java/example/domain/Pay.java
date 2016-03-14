package example.domain;

import alipay.config.AlipayConfig;
import alipay.sign.RSA;
import alipay.util.Constants;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

/**
 * Created by ejiping on 2016/3/8.
 */
@Entity
@Table(name = "payorder")
public class Pay {

	@Id
	@GeneratedValue
	private Integer payorderid;

	//强烈建议属性名不能有下划线
	@Column(nullable = false) private String tradeno; //支付订单唯一编号
    @Column(nullable = true) private Integer total_fee;   //总付款金额,移动端提供
	@Column(nullable = true) private Integer price;       //商品单价，本系统可以不用，使用上面的总付款金额
	@Column(nullable = true) private String statusCode;   //操作状态代码，0-未支付，10-已支付，4000-退款中，5000-已退款，6000-付款失败，6001-取消付款，7000-已消费
	@Column(nullable = true) private String orderNo;      //支付宝交易流水号，可空
	@Column(nullable = true) private String isRefund;     //是否申请退款，可空,暂时不用
	@Column(nullable = true) private String createTime;   //创建时间，可空
	@Column(nullable = true) private String modifyTime;   //修改时间，可空
	@Column(nullable = true) private String buyeremail;  //买家支付宝账号，可空
	@Column(nullable = true) private int quantity;        //购买数量，可空
	//@Column(nullable = true) private Integer dealid;    //对应的deal id, 必须有, 移动端提供
	@Column(nullable = true) private String subject; //商品名称， 可空,移动端提供
	@Column(nullable = true) private String body; //商品详情，可空,移动端提供

	/**
	 * create the order info. 创建订单信息
	 *
	 */
	public String getOrderInfo() {

		// 签约合作者身份ID
		String orderInfo = "partner=" + "\"" + AlipayConfig.partner + "\"";

		// 签约卖家支付宝账号
		orderInfo += "&seller_id=" + "\"" + Constants.SELLER_EMAIL + "\"";

		// 商户网站唯一订单号
		orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";

		// 商品名称
		orderInfo += "&subject=" + "\"" + subject + "\"";

		// 商品详情
		orderInfo += "&body=" + "\"" + body + "\"";

		// 商品金额
		orderInfo += "&total_fee=" + "\"" + price + "\"";

		// 服务器异步通知页面路径
		orderInfo += "&notify_url=" + "\"" + Constants.NOTIFY_URL + "\"";

		// 服务接口名称， 固定值
		orderInfo += "&service=\"mobile.securitypay.pay\"";

		// 支付类型， 固定值
		orderInfo += "&payment_type=\"1\"";

		// 参数编码， 固定值
		orderInfo += "&_input_charset=\"utf-8\"";

		// 设置未付款交易的超时时间
		// 默认30分钟，一旦超时，该笔交易就会自动被关闭。
		// 取值范围：1m～15d。
		// m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
		// 该参数数值不接受小数点，如1.5h，可转换为90m。
		orderInfo += "&it_b_pay=\"30m\"";

		// extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
		// orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

		// 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
		orderInfo += "&return_url=\"m.alipay.com\"";

		// 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
		// orderInfo += "&paymethod=\"expressGateway\"";

		return orderInfo;
	}

	/**
	 * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
	 *
	 */
	public String getOutTradeNo() {
		SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
		Date date = new Date();
		String key = format.format(date);

		Random r = new Random();
		key = key + r.nextInt();
		key = key.substring(0, 15);
		return key;
	}

	/**
	 * sign the order info. 对订单信息进行签名
	 *
	 * @param content
	 *            待签名订单信息
	 */
	public static String sign(String content) {
		return RSA.sign(content, AlipayConfig.private_key, AlipayConfig.input_charset);
	}

	/**
	 * get the sign type we use. 获取签名方式
	 *
	 */
	public static String getSignType() {
		return "sign_type=\"RSA\"";
	}

	public Integer getPayorderid() {
		return payorderid;
	}

	public void setPayorderid(Integer payorderid) {
		this.payorderid = payorderid;
	}

	public String getTradeno() {
		return tradeno;
	}

	public void setTradeno(String tradeno) {
		this.tradeno = tradeno;
	}

	public Integer getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(Integer total_fee) {
		this.total_fee = total_fee;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getIsRefund() {
		return isRefund;
	}

	public void setIsRefund(String isRefund) {
		this.isRefund = isRefund;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getBuyeremail() {
		return buyeremail;
	}

	public void setBuyeremail(String buyeremail) {
		this.buyeremail = buyeremail;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
}
