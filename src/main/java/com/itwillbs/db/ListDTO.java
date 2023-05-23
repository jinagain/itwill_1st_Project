package com.itwillbs.db;

import java.sql.Date;

public class ListDTO {
	// MYPAGE 테이블 정보 저장객체
	private int product_id;
	private String user_id;
	private int like_id;
	private String order_status;
	private int order_id;
	private String id;
	private String title;
	private int price;
	private String seller_id;
	private String buyer_id;
	private Date order_date;
	
	// 링크 연결 정보 저장객체
	private String productLink;
	private String orderLink;
	
	
	
	public int getProduct_id() {
		return product_id;
	}
	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public int getLike_id() {
		return like_id;
	}
	public void setLike_id(int like_id) {
		this.like_id = like_id;
	}
	public String getOrder_status() {
		return order_status;
	}
	public void setOrder_status(String order_status) {
		this.order_status = order_status;
	}
	public int getOrder_id() {
		return order_id;
	}
	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getSeller_id() {
		return seller_id;
	}
	public void setSeller_id(String seller_id) {
		this.seller_id = seller_id;
	}
	public String getBuyer_id() {
		return buyer_id;
	}
	public void setBuyer_id(String buyer_id) {
		this.buyer_id = buyer_id;
	}
	public Date getOrder_date() {
		return order_date;
	}
	public void setOrder_date(Date order_date) {
		this.order_date = order_date;
	}
	public String getProductLink() {
		return productLink;
	}
	public void setProductLink(String productLink) {
		this.productLink = productLink;
	}
	public String getOrderLink() {
		return orderLink;
	}
	public void setOrderLink(String orderLink) {
		this.orderLink = orderLink;
	}
	
	
	@Override
	public String toString() {
		return "ListDTO [product_id=" + product_id + ", user_id=" + user_id + ", like_id=" + like_id + ", order_status="
				+ order_status + ", order_id=" + order_id + ", id=" + id + ", title=" + title + ", price=" + price
				+ ", seller_id=" + seller_id + ", buyer_id=" + buyer_id + ", order_date=" + order_date
				+ ", productLink=" + productLink + ", orderLink=" + orderLink + "]";
	}	
	
	
}