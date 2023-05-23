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
/**
 * 공지사항 과 관련된 액션을 수행하는 객체
 * 공지사항 쓰기,읽기, 수정, 조회수 증가기능 탑재
 * 이름은 memberdao이지만 언제 수정될지 모름. 
 * @author 성찬스키
 *
 */
public class NoticeDAO {
	// 공통변수 선언
	
	private Connection con = null;			// DB 연결정보 저장(관리)
	private PreparedStatement pstmt = null;	// SQL 작성, 실행
	private ResultSet rs = null;			// select결과 데이터 저장
	private String sql = "";				// SQL구문 저장
	
	// DB 연결 메서드
	private Connection getCon() throws Exception {
		// Context 객체생성
		Context initCTX = new InitialContext();
		// DB 연결정보 로드
		DataSource ds = (DataSource)initCTX.lookup("java:comp/env/jdbc/c7d2301t1");
		// DB 연결
		con = ds.getConnection();
		System.out.println(" DAO : DB 연결 성공! " + con);
		return con;	
	}
	
	// 자원해제
	public void closeDB() {
		try {
			if(rs != null) rs.close();
			if(pstmt != null) pstmt.close();		
			if(con != null) con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//공지사항 글쓰기 noticeWrite()메소드 시작
		public int noticeWrite(NoticeDTO dto) {
			int result = -1;
			try {
				con = getCon();
				int bno=0;
				
				sql = "select max(notice_id) from notice";
				pstmt = con.prepareStatement(sql);

				rs= pstmt.executeQuery();

				if(rs.next()){
					bno = rs.getInt(1)+1;
					System.out.println("참");
				}
				System.out.println("bno: "+bno);

				sql = "insert into notice (notice_id, title, date, count, content, notice_image) "
						+ " values(?,?,current_date(), ?,?,?)";
				pstmt = con.prepareStatement(sql);

				pstmt.setInt(1, bno);
				pstmt.setString(2, dto.getTitle());
				pstmt.setInt(3, dto.getCount());
				pstmt.setString(4, dto.getContent());
				pstmt.setString(5, dto.getNotice_image());
				
				result= pstmt.executeUpdate();
				System.out.println(dto.getTitle());
				System.out.println("공지쓰기 완료");
				
			} catch (Exception e) {
				result =0;
				e.printStackTrace();
				
			} finally {
				closeDB();
				result=-1;
			}
			
			return result;
		}	//공지사항 글쓰기 noticeWrite()메소드 끝

		/*
		 * 검색처리 메소드 시작!
		 */
		
		//페이징 처리 - getBoradCount()메소드 시작
			public int getBoardCount() {
				int result=0;
				try {
					con = getCon();
					System.out.println("디비연결, 드라이버 로드 성공");
					
					sql = "select count(*) from notice";
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
			//getBoradCount()메소드 끝
			
			//검색처리 오버로딩- getBoradCount(String search)메소드 시작
			public int getBoardCount(String search, String category) {
				int result=0;
				try {
					con = getCon();
					System.out.println("디비연결, 드라이버 로드 성공");
					
					if(category.equals("title")) {
						//제목으로 검색할 때 
						sql = "select count(*) from notice where title like ?";
					}else {
						//내용으로 검색할 때
						sql = "select count(*) from notice where content like ?";
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
			
			//페이징처리 - 공지리스트를 불러오는 getNoticeList()메소드 시작
				public List<NoticeDTO> getNoticeList(int startRow, int pageSize) {
					
					List<NoticeDTO> noticeList = new ArrayList<NoticeDTO>();
					
					try {
						con = getCon();
						sql = "select * from notice order by notice_id desc limit ?,?";
						pstmt = con.prepareStatement(sql);
						
						pstmt.setInt(1, startRow-1);
						pstmt.setInt(2, pageSize);
						
						rs = pstmt.executeQuery();
						// DB정보(rs) -> DTO -> list
						while (rs.next()) {
							NoticeDTO dto = new NoticeDTO();
							
							dto.setNotice_id(rs.getInt("notice_id"));
							dto.setContent(rs.getString("content"));
							dto.setCount(rs.getInt("count"));
							dto.setDate(rs.getDate("date"));
							dto.setNotice_image(rs.getString("notice_image"));
							dto.setTitle(rs.getString("title"));
							
							noticeList.add(dto);
						}
						
						System.out.println(" DAO : 공지목록 조회성공! ");
						System.out.println(" DAO : 목록 수 " + noticeList.size());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} finally {
						closeDB();
					}
					
					return noticeList;		
				}//공지리스트를 불러오는 getNoticeList()메소드 시작
			
				//검색기능을 추가한 getNoticeList()메소드 시작
				public List<NoticeDTO> getNoticeList(int startRow, int pageSize, String search, String category) {
				
					List<NoticeDTO> noticeList = new ArrayList<NoticeDTO>();
					try {
						con = getCon();
						
						if(category.equals("title")) {
							//제목으로 검색할 때
							sql = "select * from notice where title like ? order by notice_id desc limit ?,?";
							
						}else {
							//내용으로 검색할 때
							sql = "select * from notice where content like ? order by notice_id desc limit ?,?";
						}
						pstmt = con.prepareStatement(sql);
						
						pstmt.setString(1, "%"+search+"%");
						pstmt.setInt(2, startRow-1);
						pstmt.setInt(3, pageSize);
						
						rs = pstmt.executeQuery();
						
						// DB정보(rs) -> DTO -> list
						while (rs.next()) {
							NoticeDTO dto = new NoticeDTO();
							
							dto.setNotice_id(rs.getInt("notice_id"));
							dto.setContent(rs.getString("content"));
							dto.setCount(rs.getInt("count"));
							dto.setDate(rs.getDate("date"));
							dto.setNotice_image(rs.getString("notice_image"));
							dto.setTitle(rs.getString("title"));
							
							noticeList.add(dto);
						}
						
						System.out.println(" DAO : 공지목록 조회성공! ");
						System.out.println(" DAO : 목록 수 " + noticeList.size());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} finally {
						closeDB();
					}
					
					return noticeList;		
				}//검색처리 -공지리스트를 불러오는 getNoticeList()메소드 시작
				
				//검색처리 끝
				///////////////////////////////////////////////////////////////////////////////
			
			//공지리스트의 공지글을 불러오는 getNoticeContent()메소드 시작
			public NoticeDTO getNoticeContent(String notice_id) {
				
				NoticeDTO dto = new NoticeDTO();
				
				try {
					con = getCon();
					sql = "select * from notice where notice_id=?";
					pstmt = con.prepareStatement(sql);
					
					pstmt.setString(1, notice_id);
					
					rs = pstmt.executeQuery();
					// DB정보(rs) -> DTO -> list
					while (rs.next()) {
						
						dto.setNotice_id(rs.getInt("notice_id"));
						dto.setContent(rs.getString("content"));
						dto.setCount(rs.getInt("count"));
						dto.setDate(rs.getDate("date"));
						dto.setNotice_image(rs.getString("notice_image"));
						dto.setTitle(rs.getString("title"));
						
					}
					
					System.out.println(" DAO : 공지정보 조회 조회성공! ");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					closeDB();
				}
				
				return dto;
			}//공지리스트의 공지글을 불러오는 getNoticeContent()메소드 시작
		
			//조회수를 올리는 updateCount()메소드 시작
			public void updateReadCount(String notice_id) {
				//디비 연결
				try {
					con = getCon();
					//sql(update) pstmt 객체 생성
					sql = "update notice set count=count+1 where notice_id=?";
					pstmt = con.prepareStatement(sql);
					
					pstmt.setString(1, notice_id);
					
					//sql문 실행
					int cnt= pstmt.executeUpdate();
					//executeUpdate()메소드의 리턴값은 update 쿼리로 인해 영향받은 행의 숫자를 반환
					//
					if(cnt==1) {
						System.out.println("조회수가 1 증가했습니다");
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					closeDB();
				}
			}//조회수를 올리는 updateCount()메소드 끝
			
			
			
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
			
			//deleteNotice() 공지글을 삭제하는 메소드 시작
			public int deleteNotice(NoticeDTO dto) {
				int result = -1;
				
				try {
					con = getCon();
					sql = "select * from notice where notice_id=?";
					pstmt = con.prepareStatement(sql);
					int notice_id = dto.getNotice_id();
					
					pstmt.setInt(1, dto.getNotice_id());
					rs= pstmt.executeQuery();
					
					if(rs.next()) {
							sql = "delete from notice where notice_id=?";
							pstmt = con.prepareStatement(sql);
							
							pstmt.setInt(1, notice_id);
							
							result = pstmt.executeUpdate();
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					closeDB();
				}
				
				return result;
			}
			
			//deleteNotice() 공지글을 삭제하는 메소드 끝
			
			
}