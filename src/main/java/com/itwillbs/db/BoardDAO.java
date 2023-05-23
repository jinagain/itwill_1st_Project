package com.itwillbs.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class BoardDAO {
	// DAO (Data Access Object) : DB 데이터 처리 객체
	// => DB에 관한 모든 동작 수행
	
	// 공통사용 변수
	private Connection con = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	private String sql = "";
	
	// 디비연결에 대한 모든 처리 
	private Connection getCon() throws Exception {
		// 0 - 디비 연결정보 (context.xml)
		// 1 드라이버로드 2 디비연결
		Context initCTX = new InitialContext();
		DataSource ds 
		       = (DataSource) initCTX.lookup("java:comp/env/jdbc/c7d2301t1");
		con = ds.getConnection();
		System.out.println(" DAO : 디비 연결 성공(CP) ");
		System.out.println(" DAO : "+con);
		return con;
	}
	
	// DB 자원해제
	private void closeDB() {
		try {
			if(rs != null) rs.close();
			if(pstmt != null) pstmt.close();
			if(con != null) con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void insertBoard(BoardDTO dto) throws Exception {
		// 디비연결정보 (상수)
		final String DRIVER = "com.mysql.cj.jdbc.Driver";
		final String DBURL = "jdbc:mysql://localhost:3306/jspdb";
		final String DBID = "root";
		final String DBPW = "1234";

		// 1.드라이버 로드
		Class.forName(DRIVER);
		System.out.println(" 드라이버 로드 성공! ");
		// 2.디비연결
		con = DriverManager.getConnection(DBURL, DBID, DBPW);
		System.out.println(" 디비연결 성공! ");
		// 3.sql 작성 & pstmt 객체
		// bno 계산
		String sql = "select max(bno) from itwill_board";
		pstmt = con.prepareStatement(sql);

		// 4.sql 실행
		rs = pstmt.executeQuery();

		// 5. 데이터 처리 (글번호 최대값 + 1)
		int bno = 0;
		if (rs.next()) {
			// 데이터가 sql null 인경우 0을 리턴
			// 내장함수 호출시 가능한 컬럼명보단, 인덱스 접근이 유리하다.
			bno = rs.getInt(1) + 1;
			// bno = rs.getInt("max(bno)")+1;
		}

		System.out.println("bno : " + bno);

		// 정보 저장( 글쓰기 )
		// 3. sql(insert) - pstmt
		sql = "insert into itwill_board (bno,name,pass,subject,content,readcount,re_ref,re_lev,re_seq,date,ip,file) values(?,?,?,?,?,?,?,?,?,now(),?,?)";
		pstmt = con.prepareStatement(sql);

		pstmt.setInt(1, bno);
		pstmt.setString(2, dto.getName());
		pstmt.setString(3, dto.getPass());
		pstmt.setString(4, dto.getSubject());
		pstmt.setString(5, dto.getContent());

		pstmt.setInt(6, 0); // 모든글의 조회수는 0 초기화
		pstmt.setInt(7, bno); // re_ref 그룹번호 - 일반글번호 == 그룹번호
		pstmt.setInt(8, 0); // re_lev 레벨값 - 일반글 0
		pstmt.setInt(9, 0); // re_seq 순서값 - 일반글 0

		pstmt.setString(10, dto.getIp());
		pstmt.setString(11, dto.getFile());

		// 4. sql
		pstmt.executeUpdate();
		closeDB();

		System.out.println(" 글쓰기 완료! ");

	} // insertBoard()

	// getBoardListAll()
	/**
	 * 디비에 저장된 모든 게시판 정보를 조회
	 * 
	 * @return ArrayList 형태로 리턴
	 */
	public ArrayList getBoardListAll() throws Exception {

		//List boardList = new ArrayList();
		ArrayList boardList = new ArrayList();
		
		// 디비연결정보 (상수)
		final String DRIVER = "com.mysql.cj.jdbc.Driver";
		final String DBURL = "jdbc:mysql://localhost:3306/jspdb";
		final String DBID = "root";
		final String DBPW = "1234";

		// 1.드라이버 로드
		Class.forName(DRIVER);
		System.out.println(" 드라이버 로드 성공! ");
		// 2.디비연결
		Connection con = DriverManager.getConnection(DBURL, DBID, DBPW);
		System.out.println(" 디비연결 성공! ");
		// 3. SQL 작성(select) & pstmt 객체
		String sql = "select * from itwill_board "
				+ " where bno > 0 "
				+ " order by bno desc";
		PreparedStatement pstmt = con.prepareStatement(sql);
		
		// 4. SQL 실행
		ResultSet rs = pstmt.executeQuery();
		// 5. 데이터 처리 
		//    DB(rs) -> DTO -> ArrayList
		//    1 row -> 1 DTO -> 1 ArrayList
		while(rs.next()) {
			//rs -> dto
			BoardDTO dto = new BoardDTO();
			dto.setBno(rs.getInt("bno"));
			dto.setContent(rs.getString("content"));
			dto.setDate(rs.getDate("date"));
			dto.setFile(rs.getString("file"));
			dto.setIp(rs.getString("ip"));
			dto.setName(rs.getString("name"));
			dto.setPass(rs.getString("pass"));
			dto.setRe_lev(rs.getInt("re_lev"));
			dto.setRe_ref(rs.getInt("re_ref"));
			dto.setRe_seq(rs.getInt("re_seq"));
			dto.setReadcount(rs.getInt("readcount"));
			dto.setSubject(rs.getString("subject"));
			
			// dto 1개 -> list 1칸에 저장`
			boardList.add(dto);
		}// while	
		
		System.out.println(" DAO : 게시판 글 정보 모두 저장완료! ");
		closeDB();
		return boardList;
	}
	// getBoardListAll()
	
	
	// getBoardList()
	/**
	 * 디비에 저장된 모든 게시판 정보를 조회
	 * 커넥션풀 사용
	 * @return ArrayList 형태로 리턴
	 */
	public ArrayList getBoardList(int startRow,int pageSize) {

		//List boardList = new ArrayList();
		ArrayList boardList = new ArrayList();

		// 1.2. 디비연결
		try {
			con = getCon();
			// 3. SQL 작성(select) & pstmt 객체
			String sql = "select * from itwill_board "
					+ " order by re_ref desc, re_seq asc limit ?,?";
			//	+ " where bno > 0 ";
			//	+ " order by bno desc";
			pstmt = con.prepareStatement(sql);
			
			// ??? 
			pstmt.setInt(1, startRow-1);  // 시작위치-1
			pstmt.setInt(2, pageSize);  // 개수
			
			// 4. SQL 실행
			rs = pstmt.executeQuery();
			// 5. 데이터 처리 
			//    DB(rs) -> DTO -> ArrayList
			//    1 row -> 1 DTO -> 1 ArrayList
			while(rs.next()) {
				//rs -> dto
				BoardDTO dto = new BoardDTO();
				dto.setBno(rs.getInt("bno"));
				dto.setContent(rs.getString("content"));
				dto.setDate(rs.getDate("date"));
				dto.setFile(rs.getString("file"));
				dto.setIp(rs.getString("ip"));
				dto.setName(rs.getString("name"));
				dto.setPass(rs.getString("pass"));
				dto.setRe_lev(rs.getInt("re_lev"));
				dto.setRe_ref(rs.getInt("re_ref"));
				dto.setRe_seq(rs.getInt("re_seq"));
				dto.setReadcount(rs.getInt("readcount"));
				dto.setSubject(rs.getString("subject"));
				
				// dto 1개 -> list 1칸에 저장`
				boardList.add(dto);			
			}// while	
			
			System.out.println(" DAO : 게시판 글 정보 모두 저장완료! ");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeDB();
		}

		return boardList;
	}
	// getBoardList()
	
	// getBoardCount()
	public int getBoardCount() {
		int result = 0;
		
		try {
			// 1.2. 디비연결
			con = getCon();
			// 3. SQL 작성 & pstmt 객체
			sql = "select count(*) from itwill_board";
			pstmt = con.prepareStatement(sql);
			// 4. sql 실행
			rs = pstmt.executeQuery();
			// 5. 데이터 처리
			if(rs.next()) {
				result = rs.getInt(1);
				rs.close();
				pstmt.close();
				con.close();
			}
			System.out.println(" DAO : 전체 글 개수 - "+result);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 예외 발생 여부와 상관없이 반드시 한번 실행하는 구문
			// => 자원 해제 (DB 연결 정보 해제 - 사용한 객체의 역순 종료)
			closeDB();
		}
		
		return result;
	}
	// getBoardCount()
	
	// getBoard(bno)
	public BoardDTO getBoard(int bno) {
		BoardDTO dto = null;
		try {
			// 1.2. DB연결
			con = getCon();
			// 3. sql(select) & pstmt
			sql = "select * from itwill_board where bno = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, bno);
			// 4. sql 실행
			rs = pstmt.executeQuery();
			// 5. 데이터 처리
			if(rs.next()) {
				dto = new BoardDTO();
				dto.setBno(rs.getInt("bno"));
				dto.setContent(rs.getString("content"));
				dto.setDate(rs.getDate("date"));
				dto.setFile(rs.getString("file"));
				dto.setIp(rs.getString("ip"));
				dto.setName(rs.getString("name"));
				dto.setPass(rs.getString("pass"));
				dto.setRe_lev(rs.getInt("re_lev"));
				dto.setRe_ref(rs.getInt("re_ref"));
				dto.setRe_seq(rs.getInt("re_seq"));
				dto.setReadcount(rs.getInt("readcount"));
				dto.setSubject(rs.getString("subject"));
				
				System.out.println("DAO : " + bno + "번 글정보 저장완료!");
			}
		} catch (Exception e) {
			System.out.println("DAO : getBoard(bno) 메서드 실행 에러!!!");
			e.printStackTrace();
		} finally {
			closeDB();
		}
		return dto;
	}
	// getBoard(bno)
	
	// updateReadcount(bno)
	public void updateReadcount(int bno) {
		try {
			// 1.2. DB 연결
			con = getCon();
			// 3. sql & pstmt 객체
			sql = "update itwill_board set readcount = readcount+1 "
					+ " where bno=? ";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, bno);
			
			// 4. sql 실행
			int cnt = pstmt.executeUpdate();
			if(cnt == 1) {
				System.out.println("DAO : 글 조회수 1증가 완료!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
	}
	// updateReadcount(bno)
	
	//updateBoard(dto)
	public int updateBoard(BoardDTO dto) {
		int result = -1; // -1 0 1
		try {
			// 1.2. DB연결
			con = getCon();
			// 3.sql 작성 & pstmt 객체
			sql = "select pass from itwill_board where bno=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getBno());
			// 4.sql 실행
			rs = pstmt.executeQuery();
			
			// 5. 데이터 처리
			if (rs.next()) {
				// 글이 있다.
				if(dto.getPass().equals(rs.getString("pass"))) {
					// 본인O - 정보 수정
					// 3. sql& pstmt
					sql = "update itwill_board set name=?, subject=?, content=? where bno=?";
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, dto.getName());
					pstmt.setString(2, dto.getSubject());
					pstmt.setString(3, dto.getContent());
					pstmt.setInt(4, dto.getBno());					
					// 4. sql 실행
					result = pstmt.executeUpdate();
					
				}else {
					// 본인X
					result = 0;
				}
			}else {
				// 글이 없다
				result = -1;
			}
			System.out.println("DAO : 글 수정완료! (" + result + ")");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();			
		}
		return result;
	} //updateBoard(dto)
	
	//deleteBoard(dto)
	public int deleteBoard(BoardDTO dto) {
		int result = -1; // -1 0 1
		try {
			// 1.2. DB연결
			con = getCon();
			// 3.sql 작성 & pstmt 객체
			sql = "select pass from itwill_board where bno=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getBno());
			// 4.sql 실행
			rs = pstmt.executeQuery();
			
			// 5. 데이터 처리
			if (rs.next()) {
				// 글이 있다.
				if(dto.getPass().equals(rs.getString("pass"))) {
					// 본인O - 정보 수정
					// 3. sql& pstmt
					sql = "delete from itwill_board where bno=?";
					pstmt = con.prepareStatement(sql);
					pstmt.setInt(1, dto.getBno());					
					// 4. sql 실행
					result = pstmt.executeUpdate();
					
				}else {
					// 본인X
					result = 0;
				}
			}else {
				// 글이 없다
				result = -1;
			}
			System.out.println("DAO : 글 삭제완료! (" + result + ")");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();			
		}
		return result;
	} //deleteBoard(dto)
	
	// reInsertBoard(dto)
	public void reInsertBoard(BoardDTO dto) {
		int bno = 0;
		
		// 1.2. DB연결
		try {
			con = getCon();
			// 3. sql 작성 pstmt객체
			// 4. sql 실행
				/*********************************************************/
				// 1. 글번호 계산
				sql = "select max(bno) from itwill_board";
				pstmt = con.prepareStatement(sql);
				rs = pstmt.executeQuery();
				if(rs.next()) {
					bno = rs.getInt(1)+1;
				}
				System.out.println("bno(답글) : " + bno);
				/*********************************************************/
				
				/*********************************************************/
				// 2. re_seq 1증가 update(기존의 값보다 큰 값이 있을 경우)
				
				sql = "update itwill_board set re_seq=re_seq+1 "
						+ " where re_ref=? and re_seq>?";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, dto.getRe_ref());
				pstmt.setInt(2, dto.getRe_seq());
				int result = pstmt.executeUpdate();
				if(result >= 1) {
					System.out.println(" DAO : 답글 재배치 완료! seq update완료");
				}
				/*********************************************************/
				
				/*********************************************************/
				// 3. 답글정보 저장 insert
				sql = "insert into itwill_board "
						+ "(bno,name,pass,subject,content,readcount,re_ref,re_lev,re_seq,date,ip,file)"
						+ "value(?,?,?,?,?,?,?,?,?,now(),?,?)";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, bno);
				pstmt.setString(2, dto.getName());
				pstmt.setString(3, dto.getPass());
				pstmt.setString(4, dto.getSubject());
				pstmt.setString(5, dto.getContent());
				pstmt.setInt(6, 0); // 글 조회수 - 0
				pstmt.setInt(7, dto.getRe_ref()); // re_ref : 부모글 bno == 부모글 re_ref
				pstmt.setInt(8, dto.getRe_lev()+1); // re_lev : 부모글 lev + 1
				pstmt.setInt(9, dto.getRe_seq()+1); // re_seq : 부모글 seq + 1
				pstmt.setString(10, dto.getIp());
				pstmt.setString(11, dto.getFile());
				pstmt.executeUpdate();
				
				System.out.println(" DAO : 답글쓰기 완료! ");
				/*********************************************************/
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeDB();
		}
		
		
	}
	// reInsertBoard(dto)
	
}// DAO
