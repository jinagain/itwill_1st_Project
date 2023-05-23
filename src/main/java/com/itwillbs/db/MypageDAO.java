package com.itwillbs.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.itwillbs.db.BoardDTO;
import com.itwillbs.commons.JSForward;
import com.itwillbs.db.MemberDTO;

public class MypageDAO {
	
	// 공통변수 선언
	private Connection con = null;  		//디비 연결정보 저장(관리)
	private PreparedStatement pstmt = null; //SQL 작성, 실행
	private ResultSet rs = null; 			//select결과 데이터 저장
	private String sql ="";					//SQL구문 저장

	//디비 연결 메서드
	private Connection getCon() throws Exception{
		// Context 객체생성
		Context initCTX = new InitialContext();
		// 디비연결정보 불러와서 사용
		DataSource ds 
		   = (DataSource) initCTX.lookup("java:comp/env/jdbc/c7d2301t1");
		// 디비연결
		con = ds.getConnection();
		System.out.println(" DAO : 디비연결 성공! "+con);
		
		return con;
	}

	
	// 자원해제
	public void closeDB() {
		try {
			if(rs != null)  rs.close();
			if(pstmt != null)  pstmt.close();
			if(con != null)  con.close();			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	} //closeDB() 메서드끝
	
	
	// insertQNABoard() - QNA 글 작성 메서드
	public int insertQNABoard(QnADTO qdto){
		int result = 0;
		int bno = 0;

		try {
			getCon(); // con 인스턴스 변수에 저장완료
			// 글번호 계산
			sql = "select max(bno) from qna";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()){
				bno = rs.getInt(1)+1; //인덱스 사용 호출
				//rs.getInt("max(bno)"); // 컬럼명 사용 호출
			}
			
			
			MemberDTO member = new MemberDTO();
			System.out.println("DAO : 글번호 "+bno);
			sql ="insert into qna(bno,title,content,regdate,image,re_ref,re_lev,re_seq,id,nickname,qna_category,isanswered) "
					+ "values(?,?,?,now(),?,?,?,?,?,?,?,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, bno); // 글번호
			pstmt.setString(2, qdto.getTitle()); //제목
			pstmt.setString(3, qdto.getContent()); //내용
			pstmt.setString(4, qdto.getImage()); //사진
//			pstmt.setInt(5, 0); //조회수 0 
			pstmt.setInt(5, bno); // 일반글 == re_ref
			pstmt.setInt(6, 0); // 일반글 lev = 0
			pstmt.setInt(7, 0); // 일반글 seq = 0
//			pstmt.setInt(9, dto.getUserID()); // user_id
			pstmt.setString(8, qdto.getId()); // id
			pstmt.setString(9, qdto.getNickname()); // id
			pstmt.setString(10, qdto.getQnaCategory());
			pstmt.setBoolean(11, qdto.isIs_answered());
			// 실행			
			result = pstmt.executeUpdate();
			System.out.println("DAO : 글쓰기 완료! ");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		return result;
	}//end of insertBoard
	
	// getBoardCount() 
	public int getBoardCount(){
		int cnt = 0;
		try{
			getCon();
			sql="select count(*) from qna";
			pstmt = con.prepareStatement(sql);
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
	}// getBoardCount() 
	
	// UsergetBoardCount() 
	public int UsergetBoardCount(String id){
		int cnt = 0;
		try{
			getCon();
			sql="select count(*) from qna where id=? or id='admin@gmail.com'";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
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
	}// getBoardCount() 
	
	
	
//    ArrayList boardList = dao.getBoardList(startRow, pageSize);	
	
	public List<QnADTO> UsergetBoardList(String id, int startRow, int pageSize) {
		List<QnADTO> boardList = new ArrayList<QnADTO>();
		try{
			con = getCon();
			sql="SELECT a.* FROM qna a JOIN qna b ON a.re_ref = b.re_ref WHERE b.id =? order by re_ref desc, re_seq asc limit ?, ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id); //유저
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, pageSize);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				QnADTO qdto = new QnADTO();
				qdto.setUserID(rs.getInt("user_id"));
				qdto.setBno(rs.getInt("bno"));
				qdto.setTitle(rs.getString("title"));
				qdto.setContent(rs.getString("content"));
				qdto.setRegdate(rs.getDate("regdate"));
				qdto.setImage(rs.getString("image"));
				qdto.setRe_Lev(rs.getInt("re_lev"));
				qdto.setRe_Ref(rs.getInt("re_ref"));
				qdto.setRe_Seq(rs.getInt("re_seq"));
				qdto.setId(rs.getString("id"));
				qdto.setNickname(rs.getString("nickname"));
				qdto.setQnaCategory(rs.getString("qna_category"));
				qdto.setIs_answered(rs.getBoolean("isanswered"));
				boardList.add(qdto);
				System.out.println(qdto);
			}
			System.out.println("DAO: 게시글불러오기");
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeDB();
		}
		return boardList;
	}

	
	public List<QnADTO> getBoardList(int startRow, int pageSize) {
	    List<QnADTO> boardList = new ArrayList<QnADTO>();
	    try {
	        con = getCon();
	        sql = "SELECT * FROM qna ORDER BY re_ref DESC, re_seq ASC LIMIT ?,?";
	        pstmt = con.prepareStatement(sql);
	        pstmt.setInt(1, startRow - 1);
	        pstmt.setInt(2, pageSize);
	        rs = pstmt.executeQuery();
	        while (rs.next()) {
	            QnADTO qdto = new QnADTO();
	            qdto.setUserID(rs.getInt("user_id"));
	            qdto.setBno(rs.getInt("bno"));
	            qdto.setTitle(rs.getString("title"));
	            qdto.setContent(rs.getString("content"));
	            qdto.setRegdate(rs.getDate("regdate"));
	            qdto.setImage(rs.getString("image"));
	            qdto.setRe_Lev(rs.getInt("re_lev"));
	            qdto.setRe_Ref(rs.getInt("re_ref"));
	            qdto.setRe_Seq(rs.getInt("re_seq"));
	            qdto.setId(rs.getString("id"));
	            qdto.setNickname(rs.getString("nickname"));
	            qdto.setQnaCategory(rs.getString("qna_category"));
	            qdto.setIs_answered(rs.getBoolean("isanswered"));
	            boardList.add(qdto);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        closeDB();
	    }
	    return boardList;
	}
	
	
	
	// reInsertBoard()
	public int reInsertBoard(QnADTO qdto) {
	    // 지역변수 생성
	    int bno = 0;
	    int result = 0;
	    // 디비 연결
	    try {
	        getCon();
	        // sql(게시판의 글번호 중 최댓값 계산) & pstmt
	        // 1. 답글 번호 계산
	        sql = "SELECT MAX(bno) FROM qna";
	        pstmt = con.prepareStatement(sql);
	        // 실행
	        rs = pstmt.executeQuery();
	        // 데이터 처리
	        if (rs.next()) {
	            bno = rs.getInt(1) + 1;
	        } else {
	            bno = 1;
	        }
	        System.out.println("답글 번호 : " + bno);

	        // 2. 답글 순서 재배치
	        // re_ref(같은 그룹 기준)으로 re_seq값이 기존의 값보다 큰 값이 있을 경우 seq값을 1증가시킴
	        sql = "UPDATE qna SET re_seq = re_seq + 1 WHERE re_ref = ? AND re_seq > ?";
	        pstmt = con.prepareStatement(sql);
	        pstmt.setInt(1, qdto.getRe_Ref());
	        pstmt.setInt(2, qdto.getRe_Seq());

	        // 답글 추가 동작
	        result = pstmt.executeUpdate();
	        if (result >= 1) {
	            System.out.println("DAO : 답글 재배치 완료! seq update 완료");
	        }

	     // 3. 답변 처리상태 변경
	        sql = "UPDATE qna SET isanswered = true WHERE bno = ? or bno = ?";
	        pstmt = con.prepareStatement(sql);
	        pstmt.setInt(1, qdto.getRe_Ref()); // 원본 게시글의 번호를 설정합니다.
	        pstmt.setInt(2, bno); // 원본 게시글의 번호를 설정합니다.
	        result = pstmt.executeUpdate();
	        // 4. 답글 삽입
	        sql = "INSERT INTO qna(bno, title, content, regdate, image, re_ref, re_lev, re_seq, id, nickname, qna_category, isanswered) "
	            + "VALUES (?, ?, ?, NOW(), ?, ?, ?, ?, ?, ?, ?, ?)";
	        pstmt = con.prepareStatement(sql);
	        pstmt.setInt(1, bno);
	        pstmt.setString(2, qdto.getTitle());
	        pstmt.setString(3, qdto.getContent());
	        pstmt.setString(4, qdto.getImage());
	        pstmt.setInt(5, qdto.getRe_Ref()); // 일반글 == re_ref
	        pstmt.setInt(6, qdto.getRe_Lev() + 1); // 일반글 lev = 0
	        pstmt.setInt(7, qdto.getRe_Seq() + 1); // 일반글 seq = 0
	        pstmt.setString(8, qdto.getId()); // id
	        pstmt.setString(9, qdto.getNickname()); // nickname
	        pstmt.setString(10, qdto.getQnaCategory());
	        pstmt.setBoolean(11, qdto.isIs_answered());
	        result = pstmt.executeUpdate();

	        System.out.println("DAO : 글쓰기 완료!");
	        System.out.println("답글쓰기 성공");
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        closeDB();
	    }
	    return result;
	}//reInsertBoard 닫음
	
	public QnADTO getBoard(int bno) {
		QnADTO qdto = null;
		try {
			getCon();
			sql ="select * from qna where bno=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, bno);
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				qdto = new QnADTO();
				qdto.setBno(rs.getInt("bno"));
				qdto.setProductId(rs.getInt("product_id"));
				qdto.setContent(rs.getString("content"));
				qdto.setRegdate(rs.getDate("regdate"));
				qdto.setImage(rs.getString("image"));
				qdto.setId(rs.getString("id"));
				qdto.setQnaId(rs.getInt("qna_id"));
				qdto.setTitle(rs.getString("title"));
				qdto.setQnaCategory(rs.getString("qna_category"));
				qdto.setRe_Ref(rs.getInt("re_ref"));
				qdto.setRe_Seq(rs.getInt("re_seq"));
				qdto.setRe_Lev(rs.getInt("re_lev"));
				qdto.setNickname(rs.getString("nickname"));
				qdto.setQnaCategory(rs.getString("qna_category"));
				qdto.setIs_answered(rs.getBoolean("isanswered"));
			}
			System.out.println("DAO: 글정보저장완료");
			System.out.println(qdto);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		      closeDB();

		}
		return qdto;
	}
	
	
	
	
	
	// 마이페이지 - QNA 수정 updateQNA(QnADTO qdto)
		public int updateQNA(QnADTO qdto){
			int result = -1;  // -1 0 1
			
			try {
				//1.2. 디비연결
				con = getCon();
				sql = "select * from qna where bno=?";
				pstmt = con.prepareStatement(sql);
				
				pstmt.setInt(1, qdto.getBno());
				rs = pstmt.executeQuery();
				
				if(rs.next()){ 
					if(qdto.getBno()==rs.getInt("bno")){
				sql = "update qna set title=?,content=?,image=? where bno=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, qdto.getTitle());
				pstmt.setString(2, qdto.getContent());
				if(qdto.getImage()==null) {
					pstmt.setString(3, rs.getString("image"));
				}else {
					pstmt.setString(3, qdto.getImage());
				}
				pstmt.setInt(4, qdto.getBno());
				// 4. sql 실행
				result = pstmt.executeUpdate();
				//result = 1;					
				
				System.out.println(" DAO : 글 수정완료! ("+result+")");
					}
				}	
			}
			catch (Exception e) {
				e.printStackTrace();
				
			} finally {
				closeDB();
			}
			
			return result;
		}
		
		//updateNotice() 공지글 수정하는 메소드 시작
		public int updateNotice(NoticeDTO dto) {
			int result = -1;
			try {
				con = getCon();
				sql = "select * from notice where notice_id=?";
				pstmt = con.prepareStatement(sql);
				
				pstmt.setInt(1, dto.getNotice_id());
				rs = pstmt.executeQuery();
				
				if(rs.next()){ 
					if(dto.getNotice_id()==rs.getInt("notice_id")){
					
						sql = "update notice set title=?, content=?, notice_image=? where notice_id=?";
						pstmt = con.prepareStatement(sql);
						
						pstmt.setString(1, dto.getTitle());
						pstmt.setString(2, dto.getContent());
						if(dto.getNotice_image()==null) {
							pstmt.setString(3, rs.getString("notice_image"));
						}else {
							pstmt.setString(3, dto.getNotice_image());
						}
						pstmt.setInt(4, dto.getNotice_id());
						
						result = pstmt.executeUpdate();
						System.out.println("게시글 업데이트 성공!");
					}
				}
				
			} catch (Exception e) {
				result= 0;
				e.printStackTrace();
			} finally {
				result =-1;
				closeDB();
			}
			
			return result;
		}//updateNotice() 공지글 수정하는 메소드 시작
		
		
		// 마이페이지 - QNA 수정 updateQNA(QnADTO qdto)
		public int deleteQNA(int bno) {
		    int result = 0;
		    try {
		        con = getCon();
		        sql = "SELECT re_ref FROM qna WHERE bno=?";
		        pstmt = con.prepareStatement(sql);
		        pstmt.setInt(1, bno);
		        ResultSet rs = pstmt.executeQuery();
		        if(rs.next()) {
		            int re_ref = rs.getInt("re_ref");
		            sql = "UPDATE qna SET isanswered=false WHERE bno=?";
		            pstmt = con.prepareStatement(sql);
		            pstmt.setInt(1, re_ref);
		            result = pstmt.executeUpdate();
		        }
		        sql = "DELETE FROM qna WHERE re_ref=? OR bno=?";
		        pstmt = con.prepareStatement(sql);
		        pstmt.setInt(1, bno);
		        pstmt.setInt(2, bno);
		        result = pstmt.executeUpdate();
		    } catch (Exception e) {
		        e.printStackTrace();
		    } finally {
		        closeDB();
		    }
		    return result;
		}
		

		
		///////////////////////////////////////////////////////////////////////////////
		//검색처리 시작
		
		//페이징 처리 - getAdminProductCount()메소드 시작
		public int getAdminProductCount() {
		int result=0;
		try {
		con = getCon();
		System.out.println("디비연결, 드라이버 로드 성공");
		
		sql = "select count(*) from product";
		pstmt = con.prepareStatement(sql);
		
		//sql문 실행
		rs=pstmt.executeQuery();
		
		//데이타처리
		if(rs.next()) {
		result = rs.getInt(1);
		}
		} catch (Exception e) {
		e.printStackTrace();
		}finally {
		closeDB();
		}
		return result;
		}
		//getAdminProductCount()메소드 끝
		
		//검색처리 오버로딩- getBoradCount(String search)메소드 시작
		public int getBoardCount(String search, String category) {
		int result=0;
		try {
		con = getCon();
		System.out.println("디비연결, 드라이버 로드 성공");
		
		if(category.equals("title")) {
		//제목으로 검색할 때 
			sql = "SELECT COUNT(*) "
                    + "FROM product "
                    + "WHERE title LIKE ?";
	        
		}else {
		//내용으로 검색할 때
			sql = "SELECT COUNT(*) "
                    + "FROM product "
                    + "WHERE content LIKE ?";
	        
		}
		pstmt = con.prepareStatement(sql);
		
		pstmt.setString(1, "%"+search+"%");
		
		//sql문 실행
		rs=pstmt.executeQuery();
		
		//데이타처리
		if(rs.next()) {
		result = rs.getInt(1);
		}
		} catch (Exception e) {
		e.printStackTrace();
		}finally {
		closeDB();
		}
		return result;
		}
		//getBoradCount()메소드 끝
		
		//검색기능을 추가한 getAdminList()메소드 시작
		public List<ListDTO> getAdminList(int startRow, int pageSize, String search, String category) {
		
		List<ListDTO> getAdminList = new ArrayList<ListDTO>();
		try {
		con = getCon();
		if(category.equals("title")) {
		//제목으로 검색할 때
	        sql = "SELECT p.*, o.order_status "
	                + "FROM product p "
	                + "LEFT JOIN orderr o ON p.product_id = o.product_id "
	                + "WHERE p.title LIKE ? "
	                + "ORDER BY p.reg_date DESC "
	                + "LIMIT ?, ?";
		
		
		}else {
		//내용으로 검색할 때
	        sql = "SELECT p.*, o.order_status "
	                + "FROM product p "
	                + "LEFT JOIN orderr o ON p.product_id = o.product_id "
	                + "WHERE p.content LIKE ? "
	                + "ORDER BY p.reg_date DESC "
	                + "LIMIT ?, ?";
		}
		pstmt = con.prepareStatement(sql);
		
		pstmt.setString(1, "%"+search+"%");
		pstmt.setInt(2, startRow-1);
		pstmt.setInt(3, pageSize);
		
		rs = pstmt.executeQuery();
		
		// DB정보(rs) -> DTO -> list
		while (rs.next()) {

            ListDTO product = new ListDTO();
            product.setProduct_id(rs.getInt("product_id"));
            product.setTitle(rs.getString("title"));
            product.setPrice(rs.getInt("price"));
            product.setSeller_id(rs.getString("user_id"));
            product.setOrder_date(rs.getDate("reg_date"));
            product.setOrder_status(rs.getString("order_status"));
            getAdminList.add(product);

		}
		
		System.out.println(" DAO : 상품관리 조회성공! ");
		System.out.println(" DAO : 목록 수 " + getAdminList.size());
		} catch (Exception e) {
		e.printStackTrace();
		} finally {
		closeDB();
		}
		
		return getAdminList;		
		}//검색처리 -공지리스트를 불러오는 getNoticeList()메소드 시작
		
		
		public List<ListDTO> getAdminList(int startRow, int pageSize) {
		    List<ListDTO> getAdminList = new ArrayList<ListDTO>();
		    
		    try {
		        // 1.2. 디비연결
		        con = getCon();
		        // 3. sql & pstmt
		        sql = "SELECT p.*, o.order_status "
		                + "FROM product p "
		                + "LEFT JOIN orderr o ON p.product_id = o.product_id "
		                + "ORDER BY p.reg_date DESC "
		                + "LIMIT ?, ?";
		        
		        pstmt = con.prepareStatement(sql);
		        pstmt.setInt(1, startRow);
		        pstmt.setInt(2, pageSize);
		        // 4. sql 실행
		        rs = pstmt.executeQuery();
		        // 5. 데이터 처리
		        // DB정보(rs) -> DTO -> list
		        while (rs.next()) {
		            ListDTO product = new ListDTO();
		            product.setProduct_id(rs.getInt("product_id"));
		            product.setTitle(rs.getString("title"));
		            product.setPrice(rs.getInt("price"));
		            product.setSeller_id(rs.getString("user_id"));
		            product.setOrder_date(rs.getDate("reg_date"));
		            product.setOrder_status(rs.getString("order_status"));
		            getAdminList.add(product);
		        } // while
		        
		    } catch (Exception e) {
		        e.printStackTrace();
		    } finally {
		        closeDB();
		    }
		    
		    return getAdminList;
		}
		//검색처리 끝
		///////////////////////////////////////////////////////////////////////////////



		public int mypageDelete(int product_id) {
		    int result = -1; // 0: 상품 정보 없음, 1: 상품 삭제 성공
		    
		    try {
		        // 1.2 DB 연결
		        getCon();
		        
		        // 3. SQL 작성 (delete) & pstmt 객체
		        sql = "delete from mypage where product_id=?";
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

		
		// 찜 상태 수정 체크
		public boolean isProductLiked(String id, int product_id) {
		   
			boolean isLiked = false;
		    try {
		        con = getCon();
		        sql = "SELECT COUNT(*) FROM likee WHERE id = ? AND product_id = ?";
		        pstmt = con.prepareStatement(sql);
		        pstmt.setString(1, id);
		        pstmt.setInt(2, product_id);
		        rs = pstmt.executeQuery();
		        if (rs.next()) {
		            int count = rs.getInt(1);
		            if (count > 0) {
		                isLiked = true;
		            }
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		    } finally {
		        closeDB();
		    }
		    return isLiked;
		}
		


		
}