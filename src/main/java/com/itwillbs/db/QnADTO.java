package com.itwillbs.db;

import java.sql.Date;

public class QnADTO {
	
	// ITWILL DB : QNA 테이블 정보 저장객체
	private int qnaId;
	private int userID;
	private int productId;
	private String qnaCategory;
	private String title;
	private Date regdate;
	private String content;
	private String image;
	private int re_Ref;
	private int re_Lev;
	private int re_Seq;
	private int bno;
	private String id;
	private String nickname;
	private boolean isanswered;
	
	public boolean isIs_answered() {
		return isanswered;
	}
	public void setIs_answered(boolean is_answered) {
		this.isanswered = is_answered;
	}
	public int getQnaId() {
		return qnaId;
	}
	public void setQnaId(int qnaId) {
		this.qnaId = qnaId;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getQnaCategory() {
		return qnaCategory;
	}
	public void setQnaCategory(String qnaCategory) {
		this.qnaCategory = qnaCategory;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getRegdate() {
		return regdate;
	}
	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public int getRe_Ref() {
		return re_Ref;
	}
	public void setRe_Ref(int re_Ref) {
		this.re_Ref = re_Ref;
	}
	public int getRe_Lev() {
		return re_Lev;
	}
	public void setRe_Lev(int re_Lev) {
		this.re_Lev = re_Lev;
	}
	public int getRe_Seq() {
		return re_Seq;
	}
	public void setRe_Seq(int re_Seq) {
		this.re_Seq = re_Seq;
	}
	public int getBno() {
		return bno;
	}
	public void setBno(int bno) {
		this.bno = bno;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	@Override
	public String toString() {
		return "QnADTO [qnaId=" + qnaId + ", userID=" + userID + ", productId=" + productId + ", qnaCategory="
				+ qnaCategory + ", title=" + title + ", regdate=" + regdate + ", content=" + content + ", image="
				+ image + ", re_Ref=" + re_Ref + ", re_Lev=" + re_Lev + ", re_Seq=" + re_Seq + ", bno=" + bno + ", id="
				+ id + ", nickname=" + nickname + ", is_answered=" + isanswered + "]";
	}
	
	

	
	
}