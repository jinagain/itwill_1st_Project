package com.itwillbs.db;

import java.sql.Timestamp;

/**
 * 주문정보를 저장하는 객체
 * @author 현솔
 *
 */
public class OrderDTO {

	// order 테이블 (select)
	private int order_id; // 주문번호
//	private int product_id; // 상품번호
	private Timestamp order_date; //날짜
	private String payment; // 결제수단 
	private int user_id; // 회원번호
//	private int post_number; //우편번호
	private String receiver_name; //구매자 이름
	private String receiver_phone; // 받는사람 전화번호
	private String receiver_addr1; //받는사람 주소
	private String receiver_addr2; //받는사람 주소
	private int receiver_post; // 받는사람 우편번호
	private String receiver_id; // 받는사람 
	private String seller_id; // 판매자 아이
	private int is_accept; // 구매여부
	private long tracking_number; //운송장번호
	private String order_status; //주문 상태
	private String paid_amount; // 결제 금액
	private String delivery_company;
	
	// product 테이블
	private int product_id; // 상품번호
	private String title; //상품명
	private int price ; //상품가격
	private int fee; //배송비
	private int charge; // 택배 or 직거래
	private String product_image;
	
	// user 테이블
	private String id; // 구매자 아이디
	private String name; // 구매자 이름
	private String phone_number; // 구매자 전화번호
	private String address; // 구매자 주소
	private String address2; // 구매자 주소
	private int post_number; // 구매자 주소
	private String email; // 구매자 이메일
	

	
	
	public String getDelivery_company() {
		return delivery_company;
	}
	public void setDelivery_company(String delivery_company) {
		this.delivery_company = delivery_company;
	}
	public Timestamp getOrder_date() {
		return order_date;
	}
	public void setOrder_date(Timestamp order_date) {
		this.order_date = order_date;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getProduct_image() {
		return product_image;
	}
	public void setProduct_image(String product_image) {
		this.product_image = product_image;
	}
	public String getOrder_status() {
		return order_status;
	}
	public void setOrder_status(String order_status) {
		this.order_status = order_status;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getPaid_amount() {
		return paid_amount;
	}
	public void setPaid_amount(String paid_amount) {
		this.paid_amount = paid_amount;
	}
	public int getOrder_id() {
		return order_id;
	}
	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}

	public String getPayment() {
		return payment;
	}
	public void setPayment(String string) {
		this.payment = string;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public int getPost_number() {
		return post_number;
	}
	public void setPost_number(int post_number) {
		this.post_number = post_number;
	}
	public String getReceiver_name() {
		return receiver_name;
	}
	public void setReceiver_name(String receiver_name) {
		this.receiver_name = receiver_name;
	}
	public String getReceiver_phone() {
		return receiver_phone;
	}
	public void setReceiver_phone(String receiver_phone) {
		this.receiver_phone = receiver_phone;
	}
	public String getReceiver_addr1() {
		return receiver_addr1;
	}
	public void setReceiver_addr1(String receiver_addr1) {
		this.receiver_addr1 = receiver_addr1;
	}
	public String getReceiver_addr2() {
		return receiver_addr2;
	}
	public void setReceiver_addr2(String receiver_addr2) {
		this.receiver_addr2 = receiver_addr2;
	}
	public int getReceiver_post() {
		return receiver_post;
	}
	public void setReceiver_post(int receiver_post) {
		this.receiver_post = receiver_post;
	}
	public String getReceiver_id() {
		return receiver_id;
	}
	public void setReceiver_id(String receiver_id) {
		this.receiver_id = receiver_id;
	}
	public String getSeller_id() {
		return seller_id;
	}
	public void setSeller_id(String seller_id) {
		this.seller_id = seller_id;
	}
	public int getIs_accept() {
		return is_accept;
	}
	public void setIs_accept(int is_accept) {
		this.is_accept = is_accept;
	}
	public long getTracking_number() {
		return tracking_number;
	}
	public void setTracking_number(long tracking_number) {
		this.tracking_number = tracking_number;
	}
	public int getProduct_id() {
		return product_id;
	}
	public void setProduct_id(int product_id) {
		this.product_id = product_id;
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
	public int getFee() {
		return fee;
	}
	public void setFee(int fee) {
		this.fee = fee;
	}
	public int getCharge() {
		return charge;
	}
	public void setCharge(int charge) {
		this.charge = charge;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone_number() {
		return phone_number;
	}
	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Override
	public String toString() {
		return "OrderDTO [order_id=" + order_id + ", order_date=" + order_date + ", payment=" + payment + ", user_id="
				+ user_id + ", receiver_name=" + receiver_name + ", receiver_phone=" + receiver_phone
				+ ", receiver_addr1=" + receiver_addr1 + ", receiver_addr2=" + receiver_addr2 + ", receiver_post="
				+ receiver_post + ", receiver_id=" + receiver_id + ", seller_id=" + seller_id + ", is_accept="
				+ is_accept + ", tracking_number=" + tracking_number + ", order_status=" + order_status
				+ ", paid_amount=" + paid_amount + ", delivery_company=" + delivery_company + ", product_id="
				+ product_id + ", title=" + title + ", price=" + price + ", fee=" + fee + ", charge=" + charge
				+ ", product_image=" + product_image + ", id=" + id + ", name=" + name + ", phone_number="
				+ phone_number + ", address=" + address + ", address2=" + address2 + ", post_number=" + post_number
				+ ", email=" + email + "]";
	}



	

	
}
