package com.itwillbs.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class ListDAO {
	private Connection con = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	private String sql = "";
	
	// 디비 연결 메서드
	private Connection getCon() throws Exception{
		Context initCTX = new InitialContext();
		DataSource ds
			= (DataSource) initCTX.lookup("java:comp/env/jdbc/c7d2301t1");
		// 디비연결
		con = ds.getConnection();
		System.out.println(" DAO : 디비연결 성공! " +con);
		
		return con;
	} // 디비 연결 메서드
	
	// 자원해제
	public void closeDB( ) {
		try {
			if(rs != null) rs.close();
			if(pstmt != null)  pstmt.close();
			if(con != null)  con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// 구매목록 조회 - dao.getBuyList()
	public List<ListDTO> getBuyList(String id) {
	    List<ListDTO> getBuyList = new ArrayList<>();

	    try {
	        // 1.2. 디비연결
	        con = getCon();
	        // 3. sql & pstmt
	        sql = "SELECT p.product_id, p.user_id, o.order_status, o.order_id, p.title, p.price, o.receiver_id, o.order_date, o.seller_id "
	                + "FROM product AS p "
	                + "LEFT JOIN orderr AS o ON p.product_id = o.product_id "
	                + "WHERE o.receiver_id = ?";
	        pstmt = con.prepareStatement(sql);
	        pstmt.setString(1, id);
	        // 4. sql 실행
	        rs = pstmt.executeQuery();
	        // 5. 데이터 처리
	        // DB정보(rs) -> DTO -> list
	        while (rs.next()) {
	            ListDTO dto = new ListDTO();

	            dto.setProduct_id(rs.getInt("product_id"));
	            dto.setUser_id(rs.getString("user_id"));
	            dto.setSeller_id(rs.getString("seller_id"));
	            dto.setOrder_status(rs.getString("order_status"));
	            dto.setOrder_id(rs.getInt("order_id"));
	            dto.setTitle(rs.getString("title"));
	            dto.setPrice(rs.getInt("price"));
	            dto.setBuyer_id(rs.getString("user_id"));
	            dto.setOrder_date(rs.getDate("order_date"));

	            // 상품별 링크 정보 설정
	            // 현재는 product_id 값을 받아 해당 페이지로 이동하게 설정해두었고 이후 적절한 컬럼으로 수정하면 됩니다
	            String productId = rs.getString("product_id");
	            String productLink = "/productContent.pr/" + productId;
	            dto.setProductLink(productLink);

	            // 주문서번호별 링크 정보 설정
	            // 현재는 하나의 페이지로 통일했으나 주문서번호별 링크로 수정해야 합니다
	            String orderId = rs.getString("order_id");
	            String orderLink = "/orderContent.pr/" + orderId;
	            dto.setOrderLink(orderLink);

	            getBuyList.add(dto);
	            System.out.println("ListDAO: 판매목록 조회 성공! " + dto);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        closeDB();
	    }

	    return getBuyList;
	}
	// 구매목록 조회 - dao.getBuyList()
	
	
	// 판매목록 조회 - dao.SaleList()s
	public List<ListDTO> getSaleList(String id) {
	    List<ListDTO> saleList = new ArrayList<>();

	    try {
	        // 1.2. 디비연결
	        con = getCon();
	        // 3. sql & pstmt
	        sql = "SELECT p.product_id, p.user_id, o.order_status, o.order_id, p.title, p.price, o.receiver_id, o.order_date "
	                + "FROM product AS p "
	                + "LEFT JOIN orderr AS o ON p.product_id = o.product_id "
	                + "WHERE p.user_id = ?";
	        pstmt = con.prepareStatement(sql);
	        pstmt.setString(1, id);
	        // 4. sql 실행
	        rs = pstmt.executeQuery();
	        // 5. 데이터 처리
	        // DB정보(rs) -> DTO -> list
	        while (rs.next()) {
	            ListDTO dto = new ListDTO();

	            dto.setProduct_id(rs.getInt("product_id"));
	            dto.setUser_id(rs.getString("user_id"));
	            dto.setSeller_id(rs.getString("user_id"));
	            dto.setOrder_status(rs.getString("order_status"));
	            dto.setOrder_id(rs.getInt("order_id"));
	            dto.setTitle(rs.getString("title"));
	            dto.setPrice(rs.getInt("price"));
	            dto.setBuyer_id(rs.getString("receiver_id"));
	            dto.setOrder_date(rs.getDate("order_date"));

	            // 상품별 링크 정보 설정
	            // 현재는 product_id 값을 받아 해당 페이지로 이동하게 설정해두었고 이후 적절한 컬럼으로 수정하면 됩니다
	            String productId = rs.getString("product_id");
	            String productLink = "/productContent.pr/" + productId;
	            dto.setProductLink(productLink);

	            // 주문서번호별 링크 정보 설정
	            // 현재는 하나의 페이지로 통일했으나 주문서번호별 링크로 수정해야 합니다
	            String orderId = rs.getString("order_id");
	            String orderLink = "/orderContent.pr/" + orderId;
	            dto.setOrderLink(orderLink);

	            saleList.add(dto);
	            System.out.println("ListDAO: 판매목록 조회 성공! " + dto);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        closeDB();
	    }

	    return saleList;
	}
		// 판매목록 조회 - dao.SaleList()
	

}