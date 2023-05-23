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

public class ProductDAO {
	
	// 공통변수 선언
	private Connection con = null;			// DB 연결정보 저장(관리)
	private PreparedStatement pstmt = null;	// SQL 작성, 실행
	private ResultSet rs = null;			// select결과 데이터 저장
	private String sql = "";				// SQL구문 저장
	
	// 디비연결에 대한 모든 처리 
	private Connection getCon() throws Exception {
		// 0 - 디비 연결정보 (context.xml)
		// 1 드라이버로드 2 디비연결
		Context init = new InitialContext();
		DataSource ds = (DataSource) init.lookup("java:comp/env/jdbc/c7d2301t1");
		con = ds.getConnection();
		System.out.println(" DAO : 디비 연결 성공(CP) ");
		System.out.println(" DAO : "+con);
		return con;
	}
	
	// DB 자원해제
	private void closeDB() {
		try {
			if (rs != null) rs.close();
			if (pstmt != null) pstmt.close();
			if (con != null) con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// 상품 등록 - productWrite()
		public void productWrite(ProductDTO dto) {
			try {
				// 1.2 DB 연결
				getCon();
				// 3. SQL 작성 & pstmt 객체
				sql = "insert into product(title,model,content,price,grade,city,parts,method,charge,fee,user_id,reg_date,product_image,brand,color) "
						+ " values (?,?,?,?,?,?,?,?,?,?,?,now(),?,?,?)";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, dto.getTitle());
				pstmt.setInt(2, dto.getModel());
				pstmt.setString(3, dto.getContent());
				pstmt.setInt(4, dto.getPrice());
				pstmt.setInt(5, dto.getGrade());
				pstmt.setString(6, dto.getCity());
				pstmt.setString(7, dto.getParts());
				pstmt.setInt(8, dto.getMethod());
				pstmt.setInt(9, dto.getCharge());
				pstmt.setInt(10, dto.getFee());
				pstmt.setString(11, dto.getUser_id());
				pstmt.setString(12, dto.getProduct_image());
				pstmt.setInt(13, dto.getBrand());
				pstmt.setInt(14, dto.getColor());
				// 4. SQL 실행
				pstmt.executeUpdate();
				System.out.println(" DAO : 상품 등록 완료! ");
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				closeDB();
			}
		}
		// 상품 등록 - productWrite()
	
	//getProductList()
	public List<ProductDTO> getProductList(String sql) {
		List<ProductDTO> productList = new ArrayList<ProductDTO>();
		
		try {
			con = getCon();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				ProductDTO product= new ProductDTO();
				product.setProduct_id(rs.getInt("product_id"));
	            product.setTitle(rs.getString("title"));
	            product.setModel(rs.getInt("model"));
	            product.setContent(rs.getString("content"));
	            product.setPrice(rs.getInt("price"));
	            product.setParts(rs.getString("parts"));
	            product.setProduct_image(rs.getString("product_image"));
	            product.setGrade(rs.getInt("grade"));
	            product.setCity(rs.getString("city"));
	            product.setMethod(rs.getInt("method"));
	            product.setCharge(rs.getInt("charge"));
	            product.setFee(rs.getInt("fee"));
	            product.setReg_date(rs.getDate("reg_date"));
	            product.setRead_count(rs.getInt("read_count"));
	            product.setLike_count(rs.getInt("like_count"));
	            product.setChat_count(rs.getInt("chat_count"));
	            product.setUser_id(rs.getString("user_id"));
	            product.setBrand(rs.getInt("brand"));
	            product.setColor(rs.getInt("color"));
				productList.add(product);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		
		return productList;
	}
	//getProductList()
	
	//productContent()
	public ProductDTO productContent(int product_id) {
	    ProductDTO product = new ProductDTO();
	    try {
	        con = getCon();
	        sql = "select * from product where product_id=?";
	        pstmt = con.prepareStatement(sql);
	        pstmt.setInt(1, product_id);
	        rs = pstmt.executeQuery();
	        if (rs.next()) {
	            product = new ProductDTO();
				product.setProduct_id(rs.getInt("product_id"));
	            product.setTitle(rs.getString("title"));
	            product.setModel(rs.getInt("model"));
	            product.setContent(rs.getString("content"));
	            product.setPrice(rs.getInt("price"));
	            product.setParts(rs.getString("parts"));
	            product.setProduct_image(rs.getString("product_image"));
	            product.setGrade(rs.getInt("grade"));
	            product.setCity(rs.getString("city"));
	            product.setMethod(rs.getInt("method"));
	            product.setCharge(rs.getInt("charge"));
	            product.setFee(rs.getInt("fee"));
	            product.setReg_date(rs.getDate("reg_date"));
	            product.setRead_count(rs.getInt("read_count"));
	            product.setLike_count(rs.getInt("like_count"));
	            product.setChat_count(rs.getInt("chat_count"));
	            product.setUser_id(rs.getString("user_id"));
	            product.setBrand(rs.getInt("brand"));
	            product.setColor(rs.getInt("color"));
	            product.setProduct_status(rs.getInt("product_status"));
	        }
	        
	        System.out.println(" DAO : 상품정보 저장완료! ");
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        closeDB();
	    }
	    return product;
	}//productContent()

	//productUpdate()
	public int productUpdate(ProductDTO dto) {
	    int result = -1; // -1 0 1
	    try {
	    	 System.out.println(dto.getProduct_id());
	        //1.2 DB 연결
	        getCon();
	        String sql = "select * from product where product_id=?";
	        pstmt = con.prepareStatement(sql);
	        pstmt.setInt(1, dto.getProduct_id());
	        // 4. SQL 실행(select)
	        rs = pstmt.executeQuery();
	        System.out.println(dto.getProduct_id());
	           
	        // 5. 데이터 처리
	        if (rs.next()) {
	            // 상품번호 동일 //수정중
	            // 3. sql작성 (update) & pstmt 객체
	            // 상품명, 가격, 수량 수정
	        	
	            sql = "update product set title=?, model=?, parts=?, product_image=?, content=?, "
	                    + " price=?,grade=?,city=?, method=?, charge=?, fee=?, "
	                    + " user_id=?, product_image=?, brand=?, color=?, reg_date=now() where product_id=?";

	            pstmt = con.prepareStatement(sql);

	            pstmt.setString(1, dto.getTitle());
	            pstmt.setInt(2, dto.getModel());
	            pstmt.setString(3, dto.getParts());
	            pstmt.setString(4, dto.getProduct_image());
	            pstmt.setString(5, dto.getContent());
	            pstmt.setInt(6, dto.getPrice());
	            pstmt.setInt(7, dto.getGrade());
	            pstmt.setString(8, dto.getCity());
	            pstmt.setInt(9, dto.getMethod());
	            pstmt.setInt(10, dto.getCharge());
	            pstmt.setInt(11, dto.getFee());
	            pstmt.setString(12, dto.getUser_id());
	            pstmt.setString(13, dto.getProduct_image());
	            pstmt.setInt(14, dto.getBrand());
	            pstmt.setInt(15, dto.getColor());
	            pstmt.setInt(16, dto.getProduct_id());
	            System.out.println(" P : " + dto);
	            // 4. SQL 실행(update)
	            result = pstmt.executeUpdate();
	            System.out.println("DAO : 상품 정보 수정 완료 (" + result + ")");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        // 5. DB 연결 해제
	        closeDB();
	    }

	    return result;
	}
	//productUpdate()
	
	// productDelete()
	public int productDelete(int product_id) {
	    int result = -1; // 0: 상품 정보 없음, 1: 상품 삭제 성공
	    
	    try {
	        // 1.2 DB 연결
	        getCon();
	        
	        // 3. SQL 작성 (delete) & pstmt 객체
	        sql = "delete from product where product_id=?";
	        pstmt = con.prepareStatement(sql);
	        pstmt.setInt(1, product_id);
	        
	        // 4. SQL 실행(delete)
	        result = pstmt.executeUpdate();
	        
	        if (result == 0) {
	            System.out.println("DAO : 해당 상품 정보가 존재하지 않습니다.");
	        } else {
	            System.out.println("DAO : 상품 정보 삭제 완료 (" + result + ")");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        // 5. DB 연결 해제
	        closeDB();
	    }
	    
	    return result;
	}
	// productDelete()
	
	// updateReadcount()	
	public void updateReadcount(int product_id) {
		try {
			// 1.2.  디비연결
			con = getCon();
			// 3. sql(update) & pstmt 객체
			// 특정 글의 조회수 기존값 보다 1증가
			sql = "update product set read_count=read_count+1 "
					+ " where product_id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, product_id);

			// 4. sql 실행
			int cnt = pstmt.executeUpdate();

			if(cnt == 1) {
				System.out.println(" DAO : 글 조회수 1증가 완료!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
	}	
	// updateReadcount()	
	
	//productSold()
	public int productSold(int product_id) {
	    int result = -1; // 0: 상품 정보 없음, 1: 상품 판매완료 성공
	    
	    try {
	        // 1.2 DB 연결
	        getCon();
	        
	        // 3. SQL 작성 (SET FOREIGN_KEY_CHECKS = 0; UPDATE) & pstmt 객체
	        sql = "update product set product_status = 1 where product_id = ?;";
	        pstmt = con.prepareStatement(sql);
	        pstmt.setInt(1, product_id);
	        
	        // 4. SQL 실행(delete)
	        result = pstmt.executeUpdate();
	        
	        if (result == 0) {
	            System.out.println("DAO : 해당 상품 정보가 존재하지 않습니다.");
	        } else {
	            System.out.println("DAO : 상품 판매 완료 처리 성공(" + result + ")");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        // 5. DB 연결 해제
	        closeDB();
	    }
	    return result;
	}
	//productSold()
	
	public String getBrandName(int brandId) {
	    String brandName = null;
	    try {
	        con = getCon();
	        sql = " select brand from brand where brand_id = ? ";
	        pstmt = con.prepareStatement(sql);
	        pstmt.setInt(1, brandId);
	        rs = pstmt.executeQuery();
	        if (rs.next()) {
	            brandName = rs.getString("brand");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        closeDB();
	    }
	    return brandName;
	}
	public String getModelName(int modelId) {
	    String modelName = null;
	    try {
	        con = getCon();
	        sql = " select model from model where model_id = ? ";
	        pstmt = con.prepareStatement(sql);
	        pstmt.setInt(1, modelId);
	        rs = pstmt.executeQuery();
	        if (rs.next()) {
	        	modelName = rs.getString("model");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        closeDB();
	    }
	    return modelName;
	}
	public String getColorName(int colorId) {
	    String colorName = null;
	    try {
	        con = getCon();
	        sql = " select color from color where color_id = ? ";
	        pstmt = con.prepareStatement(sql);
	        pstmt.setInt(1, colorId);
	        rs = pstmt.executeQuery();
	        if (rs.next()) {
	        	colorName = rs.getString("color");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        closeDB();
	    }
	    return colorName;
	}
	
}