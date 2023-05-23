package com.itwillbs.db;

public class LikeDTO {
	
	private int like_id;
	private int product_id;
	private int user_id;
	private String id;
	
	public int getLike_id() {
		return like_id;
	}
	public void setLike_id(int like_id) {
		this.like_id = like_id;
	}
	public int getProduct_id() {
		return product_id;
	}
	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
	@Override
	public String toString() {
		return "LikeDTO [like_id=" + like_id + ", product_id=" + product_id + ", user_id=" + user_id + ", id=" + id
				+ "]";
	}
	
	
	

}
