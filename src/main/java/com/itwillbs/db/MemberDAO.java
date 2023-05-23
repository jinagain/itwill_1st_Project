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

public class MemberDAO {
	private Connection con = null;  	
	private PreparedStatement pstmt = null;
	private ResultSet rs = null; 			
	private String sql ="";				

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
	}//디비 연결 메서드
	
	// 자원해제
	public void closeDB() {
		try {
			if(rs != null)  rs.close();
			if(pstmt != null)  pstmt.close();
			if(con != null)  con.close();			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}//자원해제
	
		
		// ------------------------------- 비밀번호 업데이트, 회원 탈퇴, 회원 리스트 등 ---------------------------------
		
		// 회원정보 조회 - getMember()
		public MemberDTO getMember(String id) {
			MemberDTO dto = null;
			try {
				// 1.2. 디비연결
				con = getCon();
				// 3. sql & pstmt
				sql = "select * from user where id=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, id);
				// 4. sql 실행
				rs = pstmt.executeQuery();
				// 5. 데이터 처리
				if(rs.next()) {
					
					dto = new MemberDTO();
					dto.setId(rs.getString("id"));
					dto.setPassword(rs.getString("password"));
					dto.setName(rs.getString("name"));
					dto.setNickname(rs.getString("nickname"));
					dto.setAddress(rs.getString("address"));
					dto.setAddress2(rs.getString("address2"));
					dto.setRegdate(rs.getDate("regdate"));
					dto.setPhone_number(rs.getString("phone_number"));
					dto.setPost_number(rs.getInt("post_number"));
					dto.setUser_image(rs.getString("user_image"));
					dto.setBirth_date(rs.getString("birth_date"));
					dto.setEmail(rs.getString("email"));
					dto.setBlocked(rs.getBoolean("blocked"));
					dto.setWithdrawal(rs.getBoolean("withdrawal"));
				}// if
				
				System.out.println(" DAO : 회원정보 저장완료! ");
//				System.out.println(dto);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				closeDB();
			}
			return dto;
		}
		// 회원정보 조회 - getMember()
		

		//id,pw,name,nickname,tel,address,photo,regdate	
		

		public int memberUpdate(MemberDTO dto) {
		    int result = -1; // -1: 오류, 0: 수정 실패, 1: 수정 성공
		    
		    try {
		        // 1. 디비 연결
		        con = getCon();
		        
		        // 2. 기존 회원 정보 조회
		        sql = "SELECT * FROM user WHERE id=?";
		        pstmt = con.prepareStatement(sql);
		        pstmt.setString(1, dto.getId());
		        rs = pstmt.executeQuery();
		        
		        if (rs.next()) { 
		            if (dto.getId().equals(rs.getString("id"))) {
		                // 3. 회원 정보 업데이트
		                sql = "UPDATE user SET name=?, nickname=?, phone_number=?, address=?, user_image=?, address2=?, post_number=?,birth_date=? WHERE id=?";
		                pstmt = con.prepareStatement(sql);
		                
		                // 이름, 닉네임, 연락처, 주소, 사용자 이미지, 주소2, 우편번호 수정
		                pstmt.setString(1, dto.getName());
		                pstmt.setString(2, dto.getNickname());
		                pstmt.setString(3, dto.getPhone_number());
		                pstmt.setString(4, dto.getAddress());
		                pstmt.setString(5, dto.getUser_image() == null ? rs.getString("user_image") : dto.getUser_image());
		                pstmt.setString(6, dto.getAddress2());
		                pstmt.setInt(7, dto.getPost_number());
		                pstmt.setString(8, dto.getBirth_date());
		                pstmt.setString(9, dto.getId());
		                
		                // 4. 회원 정보 업데이트 실행
		                result = pstmt.executeUpdate();
		                
		                System.out.println("DAO: 회원 정보 수정 완료 (" + result + ")");
		            }
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		    } finally {
		        closeDB();
		    }
		    
		    return result;
		}
		
		// 회원정보 삭제 - deleteMember(id,pw)
		public int deleteMember(String id,String password) {
			int result = -1;
			
			try {
				// 1.2. 디비연결
				con = getCon();
				// 3. sql 작성 & pstmt객체 
				sql = "select password from user where id=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, id);
				
				// 4. sql 실행
				rs = pstmt.executeQuery();
				// 5. 데이터 처리
				if(rs.next()) {
					if(password.equals(rs.getString("password"))) {
						// 삭제 처리 
						// 3. sql 작성
						sql = "UPDATE user SET withdrawal = true WHERE id = ?";
						pstmt = con.prepareStatement(sql);
						pstmt.setString(1, id);
						// 4. sql 실행
						result = pstmt.executeUpdate(); //1					
					}else {
						result = 0;
					}				
				}else {
					result = -1;
				}
				System.out.println(" DAO : 삭제 완료 "+result);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				closeDB();
			}
			
			return result;
		}
		// 회원정보 삭제 - deleteMember(id,pw)
		
		public List<MemberDTO> getMemberList(int startRow, int pageSize){
		    List<MemberDTO> memberList = new ArrayList<MemberDTO>();

		    try {
		        // 1.2. 디비연결
		        con = getCon();
		        // 3. sql & pstmt
		        sql = "select * from user order by user_id desc limit ?, ?";
		        pstmt = con.prepareStatement(sql);
		        pstmt.setInt(1, startRow);
		        pstmt.setInt(2, pageSize);
		        // 4. sql 실행
		        rs = pstmt.executeQuery();
		        // 5. 데이터 처리
		        //  DB정보(rs) -> DTO -> list
		        while(rs.next()) {
		            MemberDTO dto = new MemberDTO();

		            dto.setId(rs.getString("id"));
		            dto.setPassword(rs.getString("password"));
		            dto.setName(rs.getString("name"));
		            dto.setNickname(rs.getString("nickname"));
		            dto.setPhone_number(rs.getString("phone_number"));
		            dto.setAddress(rs.getString("address"));
		            dto.setUser_image(rs.getString("user_image"));
		            dto.setRegdate(rs.getDate("regdate"));
		            dto.setEmail(rs.getString("email"));
		            dto.setBirth_date(rs.getString("birth_date"));
		            dto.setBlocked(rs.getBoolean("blocked"));
		            dto.setWithdrawal(rs.getBoolean("withdrawal"));

		            memberList.add(dto);                
		        }// while

		        System.out.println(" DAO : 회원목록 조회성공! ");
		        System.out.println(" DAO : 목록 수 "+memberList.size());

		    } catch (Exception e) {
		        e.printStackTrace();
		    } finally {
		        closeDB();
		    }

		    return memberList;
		}
		
		
		//검색기능을 추가한 getAdminList()메소드 시작
		public List<MemberDTO> getMemberList(int startRow, int pageSize, String search, String category) {
		
			List<MemberDTO> memberList = new ArrayList<MemberDTO>();
		try {
		con = getCon();
		
		if(category.equals("title")) {
		//제목으로 검색할 때
		sql = "select * from user where id like ? order by user_id desc limit ?,?";
		
		}else {
		//내용으로 검색할 때
		sql = "select * from user where phone_number like ? order by user_id desc limit ?,?";
		
		}
		pstmt = con.prepareStatement(sql);
		
		pstmt.setString(1, "%"+search+"%");
		pstmt.setInt(2, startRow-1);
		pstmt.setInt(3, pageSize);
		
		rs = pstmt.executeQuery();
		
		// DB정보(rs) -> DTO -> list
		while (rs.next()) {
		MemberDTO dto = new MemberDTO();
		
        dto.setId(rs.getString("id"));
        dto.setPassword(rs.getString("password"));
        dto.setName(rs.getString("name"));
        dto.setNickname(rs.getString("nickname"));
        dto.setPhone_number(rs.getString("phone_number"));
        dto.setPost_number(rs.getInt("post_number"));
        dto.setAddress(rs.getString("address"));
        dto.setUser_image(rs.getString("user_image"));
        dto.setEmail(rs.getString("email"));
        dto.setRegdate(rs.getDate("regdate"));
        dto.setBirth_date(rs.getString("birth_date"));
        dto.setBlocked(rs.getBoolean("blocked"));
        dto.setWithdrawal(rs.getBoolean("withdrawal"));
		
		memberList.add(dto);
		}
		
		System.out.println(" DAO : 상품관리 조회성공! ");
		System.out.println(" DAO : 목록 수 " + memberList.size());
		} catch (Exception e) {
		e.printStackTrace();
		} finally {
		closeDB();
		}
		
		return memberList;		
		}//검색처리 -공지리스트를 불러오는 getNoticeList()메소드 시작
		

		
		
		//닉네임 중복확인 메서드
		public String nickNameCheck(String nickname) {
			String check = "";
			try {
				con = getCon();
				sql = "select nickname from user where nickname=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, nickname);
				
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					check = "1";//중복됨
				}else {
					check = "0";
				}
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				closeDB();
			}
			return check;
		}//닉네임 중복
	
		public int passwordUpdate(String id, String password,String newpassword) {
			int result= 0;
			
			try {
				con = getCon();
				String sql="UPDATE user SET password=? WHERE id=? AND password=?";
				pstmt = con.prepareStatement(sql);
				pstmt=con.prepareStatement(sql);
				pstmt.setString(1, newpassword);
				pstmt.setString(2, id);
				pstmt.setString(3, password);
				
				result = pstmt.executeUpdate();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				closeDB();
			}
			return result;
		
		}
		
		
		//getMemberCount()

		public int getMemberCount(){
			int cnt = 0;
			try{
				getCon();
				sql="select count(*) from user";
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
		}		//getMemberCount()
		
		
		//검색처리 오버로딩- getMemberCount(String search)메소드 시작
		public int getMemberCount(String search, String category) {
		int result=0;
		try {
		con = getCon();
		System.out.println("디비연결, 드라이버 로드 성공");
		
		if(category.equals("title")) {
		//제목으로 검색할 때 
		sql = "select count(*) from user where id like ?";
		}else {
		//내용으로 검색할 때
		sql = "select count(*) from user where phone_number like ?";
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
		

		
		// userBlocked(id,blocked) 

		public int userBlocked(String id, boolean blocked) {
		    int result = 0;
		    try {
		        getCon();
		        sql = "UPDATE user SET blocked=? WHERE id=?";
		        pstmt = con.prepareStatement(sql);
		        pstmt.setBoolean(1, blocked);
		        pstmt.setString(2, id);
		        result = pstmt.executeUpdate();
		    } catch (Exception e) {
		        e.printStackTrace();
		    } finally {
		        closeDB();
		    }
		    return result;
		}
		// userBlocked(id,blocked) 
		// 회원가입 - memberJoin()
		public void memberJoin(MemberDTO dto) {
			try {
				// 1.2. 디비연결
				con = getCon();
				// 3. SQL작성 & pstmt 객체
				sql = "insert into user(id,password,name,nickname,phone_number,address,user_image,regdate,birth_date,post_number,address2,email) "
						+ "values(?,?,?,?,?,?,?,?,?,?,?,?)";
				pstmt = con.prepareStatement(sql);
				
				pstmt.setString(1, dto.getId());
				pstmt.setString(2, dto.getPassword());
				pstmt.setString(3, dto.getName());
				pstmt.setString(4, dto.getNickname());
				pstmt.setString(5, dto.getPhone_number());
				pstmt.setString(6, dto.getAddress());
				pstmt.setString(7, dto.getUser_image());
				pstmt.setDate(8, dto.getRegdate());
				pstmt.setString(9, dto.getBirth_date());
				pstmt.setInt(10, dto.getPost_number());
				pstmt.setString(11, dto.getAddress2());
				pstmt.setString(12, dto.getEmail());
				
				// 4. SQL 실행
				pstmt.executeUpdate();
				System.out.println(" DAO : 회원가입 성공! ");			
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				closeDB();
			}		
		}
		// 회원가입 - memberJoin()
		
		public int memberLogin(MemberDTO dto) {
			int result = -1; 
			try {
				con = getCon();
				sql = "select password from user where id=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, dto.getId());
				
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					if(dto.getPassword().equals(rs.getString("password"))) {
						result = 1;
					}else {
						result = 0;
					}
				}else {
					result = -1;
				}
				System.out.println(" DAO : 로그인 처리결과 "+result);
			
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				closeDB();
			}
			return result;
		}
		// 로그인 - memberLogin()	
		
		public String idFind(MemberDTO dto) {
			String result = "";
			try {
				con = getCon();
				sql = "select id from user where phone_number=? and name=?";
				pstmt = con.prepareStatement(sql);
				
				pstmt.setString(1, dto.getPhone_number());
				pstmt.setString(2, dto.getName());
				rs = pstmt.executeQuery();
				if(rs.next()) {
					result = rs.getString("id");
				}else {
					result = "none";
				}
				System.out.println(" DAO : 아디찾기 처리결과 "+result);
						
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				closeDB();
			}
			return result;
			
		}//아이디찾기
		
		public int pwFind(MemberDTO dto,String randomPw) {
			int result = -1;
			try {
				con = getCon();
				sql = "select phone_number from user where id=?";
				pstmt = con.prepareStatement(sql);
				
				pstmt.setString(1, dto.getId());
				rs = pstmt.executeQuery();
				if(rs.next()) {
					if(dto.getPhone_number().equals(rs.getString("phone_number"))) {
						sql="update user set password=? where id=?";
						pstmt = con.prepareStatement(sql);
						pstmt.setString(1, randomPw);
						pstmt.setString(2, dto.getId());
						pstmt.executeUpdate();
						result = 1;
					}else {
						result = 0;
					}
				}else {
					result = -1;
				}
				System.out.println(" DAO : 비번찾기 처리결과 "+result);
						
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				closeDB();
			}
			return result;
			
		}//비번찾기
		
		public String idCheck(String id) {
			String check = "";
			try {
				con = getCon();
				sql = "select id from user where id=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, id);
				
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					check = "1";//중복됨
				}else {
					check = "0";
				}
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				closeDB();
			}
			return check;
		}//아이디 중복
		
		
		public String emailCheck(String email) {
			String check = "";
			try {
				con = getCon();
				sql = "select email from user where email=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, email);
				
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					check = "1";//중복됨
				}else {
					check = "0";
				}
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				closeDB();
			}
			return check;
		}//아이디 중복

}//MemberDAO