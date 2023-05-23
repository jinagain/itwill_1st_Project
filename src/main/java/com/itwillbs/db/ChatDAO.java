package com.itwillbs.db;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
public class ChatDAO {
	
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
	
//    DataSource dataSource;
//	
//	public ChatDAO() {
//		try {
//			InitialContext initContext = new InitialContext();
//			Context envContext = (Context) initContext.lookup("java:/comp/env");
//			dataSource = (DataSource) envContext.lookup("jdbc/class7_230118_team1");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
	public ArrayList<ChatDTO> getChatListByID(String fromID, String toID, String chatID) {
		ArrayList<ChatDTO> chatList = null;
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
		try {
			// 1.2. 디비연결
			con = getCon();
			// 3. SQL작성 & pstmt 객체
			sql = "SELECT * FROM chat WHERE ((from_id = ? AND to_id =?) OR (from_id = ? AND to_id = ?)) AND chat_id > ? ORDER BY chat_time";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, fromID);
			pstmt.setString(2, toID);
			pstmt.setString(3, toID);
			pstmt.setString(4, fromID);
			pstmt.setInt(5, Integer.parseInt(chatID));
			rs = pstmt.executeQuery();
			chatList = new ArrayList<ChatDTO>();
			while (rs.next()) {
				ChatDTO chat = new ChatDTO();
				chat.setChatID(rs.getInt("chat_id"));
				chat.setFromID(rs.getString("from_id").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
				chat.setToID(rs.getString("to_id").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
				chat.setChatContent(rs.getString("chat_content").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
				int chatTime = Integer.parseInt(rs.getString("chat_time").substring(11, 13));
				String timeType = "오전";
				if(chatTime >= 12) {
					timeType = "오후";
					chatTime -= 12;
				}
				if(chatTime == 12) {
					timeType = "오후";
				}
				chat.setChatTime(rs.getString("chat_time").substring(0, 11) + " " + timeType + " " + chatTime + ":" + rs.getString("chat_time").substring(14, 16) + "");
				chatList.add(chat);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				closeDB();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return chatList; // 리스트 반환
	}
	
	public ArrayList<ChatDTO> getChatListByRecent(String fromID, String toID, int number) {
		ArrayList<ChatDTO> chatList = null;
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
		try {
			// 1.2. 디비연결
			con = getCon();
			// 3. SQL작성 & pstmt 객체
			sql = "SELECT * FROM chat WHERE ((from_id = ? AND to_id = ?) OR (from_id = ? AND to_id = ?)) AND chat_id > (SELECT MAX(chat_id) - ? FROM chat WHERE (from_id = ? AND to_id = ?) OR (from_id = ? AND to_id = ?)) ORDER BY chat_time";                     
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, fromID);
			pstmt.setString(2, toID);
			pstmt.setString(3, toID);
			pstmt.setString(4, fromID);
			pstmt.setInt(5, number);
			pstmt.setString(6, fromID);
			pstmt.setString(7, toID);
			pstmt.setString(8, toID);
			pstmt.setString(9, fromID);
			rs = pstmt.executeQuery();
			chatList = new ArrayList<ChatDTO>();
			while (rs.next()) {
				ChatDTO chat = new ChatDTO();
				chat.setChatID(rs.getInt("chat_id"));
				chat.setFromID(rs.getString("from_id").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
				chat.setToID(rs.getString("to_id").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
				chat.setChatContent(rs.getString("chat_content").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
				int chatTime = Integer.parseInt(rs.getString("chat_time").substring(11, 13));
				String timeType = "오전";
				if(chatTime >= 12) {
					timeType = "오후";
					chatTime -= 12;
				}
				chat.setChatTime(rs.getString("chat_time").substring(0, 11) + " " + timeType + " " + chatTime + ":" + rs.getString("chat_time").substring(14, 16) + "");
				chatList.add(chat);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				closeDB();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return chatList; // 리스트 반환
	}
	
	public ArrayList<ChatDTO> getBox(String userID) {
		ArrayList<ChatDTO> chatList = null;
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
		try {
			// 1.2. 디비연결
			con = getCon();
			// 3. SQL작성 & pstmt 객체
			sql = "SELECT * FROM chat WHERE chat_id IN (SELECT MAX(chat_id) FROM chat WHERE to_id = ? OR from_id = ? GROUP BY from_id, to_id)";                     
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userID);
			pstmt.setString(2, userID);
			rs = pstmt.executeQuery();
			chatList = new ArrayList<ChatDTO>();
			while (rs.next()) {
				ChatDTO chat = new ChatDTO();
				chat.setChatID(rs.getInt("chat_id"));
				chat.setFromID(rs.getString("from_id").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
				chat.setToID(rs.getString("to_id").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
				chat.setChatContent(rs.getString("chat_content").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
				int chatTime = Integer.parseInt(rs.getString("chat_time").substring(11, 13));
				String timeType = "오전";
				if(chatTime >= 12) {
					timeType = "오후";
					chatTime -= 12;
				}
				chat.setChatTime(rs.getString("chat_time").substring(0, 11) + " " + timeType + " " + chatTime + ":" + rs.getString("chat_time").substring(14, 16) + "");
				chatList.add(chat);
			} 
			for(int i=0; i<chatList.size(); i++) {
				ChatDTO x = chatList.get(i);
				for(int j=0; j<chatList.size(); j++) {
					ChatDTO y = chatList.get(j);
					if(x.getFromID().equals(y.getToID()) && x.getToID().equals(y.getFromID())) {
						if(x.getChatID() < y.getChatID()) {
							chatList.remove(x);
							i--;
							break;
						} else {
							chatList.remove(y);
							j--;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				closeDB();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return chatList; // 리스트 반환
	}
	
	public int submit(String fromID, String toID, String chatContent) {
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
		System.out.println("fromID : " + fromID);
		System.out.println("toID : " + toID);
		System.out.println("chatContent : " + chatContent);
		try {
			// 1.2. 디비연결
			con = getCon();
			// 3. SQL작성 & pstmt 객체
			sql = "INSERT INTO chat VALUES (NULL, ?, ?, ?, NOW(), 0)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, fromID);
			pstmt.setString(2, toID);
			pstmt.setString(3, chatContent);
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				closeDB();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return -1; // 데이터베이스 오류
	}
	
	public int readChat(String fromID, String toID) {
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
		try {
			// 1.2. 디비연결
			con = getCon();
			// 3. SQL작성 & pstmt 객체
			sql = "UPDATE chat SET chat_read = 1 WHERE (from_id = ? AND to_id = ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, toID);
			pstmt.setString(2, fromID);
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				closeDB();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return -1; // 데이터베이스 오류
	}
	
	public int getAllUnreadChat(String userID) {
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
		try {
			// 1.2. 디비연결
			con = getCon();
			// 3. SQL작성 & pstmt 객체
			sql = "SELECT COUNT(chat_id) FROM chat WHERE to_id = ? AND chat_read = 0";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userID);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getInt("COUNT(chat_id)");
			}
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				closeDB();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return -1; // 데이터베이스 오류
	}
	
	public int getUnreadChat(String fromID, String toID) {
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
		try {
			// 1.2. 디비연결
			con = getCon();
			// 3. SQL작성 & pstmt 객체
			sql = "SELECT COUNT(chat_id) FROM chat WHERE from_id = ? AND to_id = ? AND chat_read = 0";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, fromID);
			pstmt.setString(2, toID);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getInt("COUNT(chat_id)");
			}
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				closeDB();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return -1; // 데이터베이스 오류
	}
	
	public String getProfile(String userID) {
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
		try {
			// 1.2. 디비연결
			con = getCon();
			// 3. SQL작성 & pstmt 객체
			sql = "SELECT user_image FROM user WHERE id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userID);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				if(rs.getString("user_image") == null) {
					return "./images/icon.png";
				}
				return "./upload/" + rs.getString("user_image");
			} 
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				closeDB();
			} catch (Exception e) {
			    e.printStackTrace();
			}
		}
		return "./images/icon.png";
	}
	
}
