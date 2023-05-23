package com.itwillbs.db;

import java.util.Date;
/**
 * 공지사항과 관련된 액션을 처리하는 객체
 * 공지사항 쓰기, 업데이트, 삭제, 조회수증가 기능 탑재
 */
public class NoticeDTO {
	private int notice_id;
	private String title;
	private Date date;
	private int count;
	private String notice_image;
	private String content;
	
	
	public int getNotice_id() {
		return notice_id;
	}



	public void setNotice_id(int notice_id) {
		this.notice_id = notice_id;
	}



	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}



	public Date getDate() {
		return date;
	}



	public void setDate(Date date) {
		this.date = date;
	}



	public int getCount() {
		return count;
	}



	public void setCount(int count) {
		this.count = count;
	}




	public String getNotice_image() {
		return notice_image;
	}



	public void setNotice_image(String notice_image) {
		this.notice_image = notice_image;
	}
	



	public String getContent() {
		return content;
	}



	public void setContent(String content) {
		this.content = content;
	}



	@Override
	public String toString() {
		return "NoticeDTO [notice_id=" + notice_id + ", title=" + title + ", date=" + date + ", count=" + count
				+ ", notice_image=" + notice_image + ", content=" + content + "]";
	}
	
}
