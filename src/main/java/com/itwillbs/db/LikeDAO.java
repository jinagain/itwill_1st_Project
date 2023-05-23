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

		public class LikeDAO {	private Connection con = null;
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
	
		}
		

		//자원해제
		public void closeDB( ) {
			try {
				if(rs != null) rs.close();
				if(pstmt != null)  pstmt.close();
				if(con != null)  con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				}
		}
		
		
		//getLikeCount() 내찜목록 수
		public int getLikeCount(String id){
			int cnt = 0;
			try{
				getCon();
				sql="select count(*) from likee where id=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1,id);
				rs = pstmt.executeQuery();
				if(rs.next()){
					cnt = rs.getInt(1);
				}
				System.out.println("DAO: DB에 저장된 글의 수 "+cnt);
			} catch(Exception e) {
				e.printStackTrace();
			} finally{
				closeDB();
			}
			return cnt;
		}		//getMemberCount()

		
		
		// 찜목록 리스트
		public List<ProductDTO> getLikeList(int startRow, int pageSize, String id){
		    List<ProductDTO> getLikeList = new ArrayList<ProductDTO>();
		    
		    try {
		        // 1.2. 디비연결
		        con = getCon();
		        // 3. sql & pstmt
		        sql = "SELECT p.* FROM product p INNER JOIN likee l ON p.product_id = l.product_id WHERE l.id = ? LIMIT ?,?";
		        
		        pstmt = con.prepareStatement(sql);
		        pstmt.setString(1, id);
		        pstmt.setInt(2, startRow);
		        pstmt.setInt(3, pageSize);
		        // 4. sql 실행
		        rs = pstmt.executeQuery();
		        // 5. 데이터 처리
		        // DB정보(rs) -> DTO -> list
		        while (rs.next()) {
		            ProductDTO dto = new ProductDTO();
		            
		            dto.setProduct_id(rs.getInt("product_id"));
		            dto.setTitle(rs.getString("title"));
		            dto.setProduct_image(rs.getString("product_image"));
		            dto.setPrice(rs.getInt("price"));
		            
		            // set other fields as needed
					
		            getLikeList.add(dto);
		            System.out.println("LikedList: 찜목록 조회 성공! " + dto);
		        }
		        
		    } catch (Exception e) {
		        e.printStackTrace();
		    } finally {
		        closeDB();
		    }
		    
		    return getLikeList;
		}


		public void Likeadd(String id, int product_id) {
	        // 1.2. 디비연결
	        try {
				con = getCon();
		        // 3. sql & pstmt
		        sql = "insert into likee(product_id,id) values(?,?)";
		        pstmt = con.prepareStatement(sql);
		        pstmt.setInt(1, product_id);
		        pstmt.setString(2, id);
		        pstmt.executeUpdate();
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				closeDB();
			}

			
		}

		//삭제
		public void Likedel(String id, int product_id) {
		    // 1.2. 디비연결
		    try {
		        con = getCon();
		        // 3. sql & pstmt
		        sql = "DELETE FROM likee WHERE product_id=? AND id=?";
		        pstmt = con.prepareStatement(sql);
		        pstmt.setInt(1, product_id);
		        pstmt.setString(2, id);
		        pstmt.executeUpdate();

		    } catch (Exception e) {
		        // TODO Auto-generated catch block
		        e.printStackTrace();
		    } finally {
		        closeDB();
		    }   
		}

		
		// Likecountadd(product_id); 찜수 증가
		
		public void Likecountadd(int product_id) {
		    try {
		        con = getCon();
		        // 3. sql & pstmt
		        sql = "update product set like_count = IFNULL(like_count, 0) + 1 where product_id=?";
		        pstmt = con.prepareStatement(sql);
		        pstmt.setInt(1, product_id);
		        pstmt.executeUpdate();

		    } catch (Exception e) {
		        // TODO Auto-generated catch block
		        e.printStackTrace();
		    } finally {
		        closeDB();
		    }   
		}
		
		// Likecountdel(product_id); 찜수 감소
		public void Likecountdel(int product_id) {
		    try {
		        con = getCon();
		        // 3. sql & pstmt
		        sql = "UPDATE product SET like_count = CASE WHEN like_count > 0 THEN like_count - 1 ELSE 0 END WHERE product_id = ?";
		        pstmt = con.prepareStatement(sql);
		        pstmt.setInt(1, product_id);
		        pstmt.executeUpdate();

		    } catch (Exception e) {
		        // TODO Auto-generated catch block
		        e.printStackTrace();
		    } finally {
		        closeDB();
		    }   
		}
		
		
		// 찜목록 리스트
		public List<ProductDTO> getLikeList(String id) {
		    List<ProductDTO> getLikeList = new ArrayList<ProductDTO>();

		    try {
		        // 1. 디비연결
		        con = getCon();
		        // 2. SQL 쿼리
		        sql = "SELECT p.* FROM product p INNER JOIN likee l ON p.product_id = l.product_id WHERE l.id = ?";
		        
		        pstmt = con.prepareStatement(sql);
		        pstmt.setString(1, id);
		        // 3. SQL 실행
		        rs = pstmt.executeQuery();
		        // 4. 데이터 처리
		        // DB 정보(rs) -> DTO -> List
		        while (rs.next()) {
		            ProductDTO dto = new ProductDTO();
		            dto.setProduct_id(rs.getInt("product_id"));
		            dto.setTitle(rs.getString("title"));
		            dto.setProduct_image(rs.getString("product_image"));
		            dto.setPrice(rs.getInt("price"));
		            
		            // 필요한 필드들을 가져와 ProductDTO 객체를 생성하고 설정합니다.
		            // 필드명은 데이터베이스 테이블의 컬럼명에 맞춰서 설정해야 합니다.
		            
		            getLikeList.add(dto);
		            System.out.println("LikedList: 찜목록 조회 성공! " + dto);
		        }
		        
		    } catch (Exception e) {
		        e.printStackTrace();
		    } finally {
		        // 5. 리소스 해제
		        closeDB();
		    }
		    
		    return getLikeList;
		}

		
}
		
		
		
		
		
		
		