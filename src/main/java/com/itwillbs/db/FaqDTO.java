package com.itwillbs.db;

public class FaqDTO {
	private int faq_id;
	private String title;
	private String category;
	private String content;
	
	public int getFaq_id() {
		return faq_id;
	}
	public void setFaq_id(int faq_id) {
		this.faq_id = faq_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	@Override
	public String toString() {
		return "FaqDTO [faq_id=" + faq_id + ", title=" + title + ", category=" + category + ", Content=" + content
				+ "]";
	}
	
	
}
