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

	@Column(nullable = false) private Integer userid = 1;
	@Column(nullable = false) private Integer coachid = 1;
	@Column(nullable = false) private String state = "ongoing";
	@Column(nullable = false) private Integer price = 4000;
	@Column(nullable = true) private String dummy1;
	@Column(nullable = true) private String dummy2;

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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
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
}