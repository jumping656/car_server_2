package example.domain;

import javax.persistence.*;

/**
 * Created by ejiping on 2016/2/19.
 */

@Entity
@Table(name = "subject_one")
public class SubjectOne {

	@Id
	private Integer id;

	@Column(nullable = true) private Integer chapter;
	@Lob @Basic(fetch = FetchType.LAZY)
	@Column(nullable = true) private String question;
	@Lob @Basic(fetch = FetchType.LAZY)
	@Column(nullable = true) private String option1;
	@Lob @Basic(fetch = FetchType.LAZY)
	@Column(nullable = true) private String option2;
	@Lob @Basic(fetch = FetchType.LAZY)
	@Column(nullable = true) private String option3;
	@Lob @Basic(fetch = FetchType.LAZY)
	@Column(nullable = true) private String option4;
	@Column(nullable = true) private Integer answer;
	@Column(nullable = true) private String image;
	@Lob @Basic(fetch = FetchType.LAZY)
	@Column(nullable = true) private String link;
	@Lob @Basic(fetch = FetchType.LAZY)
	@Column(nullable = true) private String analyze;
	@Column(nullable = true) private String dummy1;
	@Column(nullable = true) private String dummy2;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getChapter() {
		return chapter;
	}

	public void setChapter(Integer chapter) {
		this.chapter = chapter;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getOption1() {
		return option1;
	}

	public void setOption1(String option1) {
		this.option1 = option1;
	}

	public String getOption2() {
		return option2;
	}

	public void setOption2(String option2) {
		this.option2 = option2;
	}

	public String getOption3() {
		return option3;
	}

	public void setOption3(String option3) {
		this.option3 = option3;
	}

	public String getOption4() {
		return option4;
	}

	public void setOption4(String option4) {
		this.option4 = option4;
	}

	public Integer getAnswer() {
		return answer;
	}

	public void setAnswer(Integer answer) {
		this.answer = answer;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getAnalyze() {
		return analyze;
	}

	public void setAnalyze(String analyze) {
		this.analyze = analyze;
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