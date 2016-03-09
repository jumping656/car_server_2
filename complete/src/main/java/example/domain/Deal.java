package example.domain;

import javax.persistence.*;

/**
 * Created by ejiping on 2016/2/19.
 */

@Entity
@Table(name = "deal")
public class Deal {

	@Id
	@GeneratedValue
	private Integer dealid;

	@Column(nullable = true) private Integer userid;
	@Column(nullable = true) private Integer coachid;
	@Column(nullable = true) private DEAL_STATE state; //订单状态，0已取消，1订单正在进行还未接单，2订单完成，3已学完
	//@Column(nullable = true) private Integer price;
	@Column(nullable = true) private String date;
	@Column(nullable = true) private String out_trade_no; //商品订单编号，唯一，是Payorder表的主键,移动端在获取到加签后的订单信息后需要将订单编号填到这里
	@Column(nullable = true) private String dummy1;
	@Column(nullable = true) private String dummy2;

	public enum  DEAL_STATE {
		CANCELLED , ONGOING , DONE, FINISHED ;
	}

	public void updateAllowedAttribute(Deal deal){
		if (null != deal.getState()){ this.setState(deal.getState());	}
	}

	public Integer getDealid() {
		return dealid;
	}

	public void setDealid(Integer dealid) {
		this.dealid = dealid;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public Integer getCoachid() {
		return coachid;
	}

	public void setCoachid(Integer coachid) {
		this.coachid = coachid;
	}

	public DEAL_STATE getState() {
		return state;
	}

	public void setState(DEAL_STATE state) {
		this.state = state;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDummy1() {
		return dummy1;
	}

	public void setDummy1(String dummy1) {
		this.dummy1 = dummy1;
	}

	public String getDummy2() {
		return dummy2;
	}

	public void setDummy2(String dummy2) {
		this.dummy2 = dummy2;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
}