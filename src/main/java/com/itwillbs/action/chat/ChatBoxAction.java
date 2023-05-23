package com.itwillbs.action.chat;

import java.net.URLDecoder;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itwillbs.commons.Action;
import com.itwillbs.commons.ActionForward;
import com.itwillbs.commons.JSForward;
import com.itwillbs.db.ChatDAO;
import com.itwillbs.db.ChatDTO;
import com.itwillbs.db.MemberDAO;
import com.itwillbs.db.MemberDTO;

//import user.UserDAO;

public class ChatBoxAction implements Action {
	private static final long serialVersionUID = 1L;
  
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println(" M : ChatBoxAction execute");
		
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		HttpSession session = request.getSession();

		
		String userID = null;
		if(session.getAttribute("id") != null) {
			userID = (String) session.getAttribute("id");
		}
		if(userID == null) {
			session.setAttribute("messageType", "오류 메시지");
			session.setAttribute("messageContent", "현재 로그인이 되어 있지 않습니다.");
			response.sendRedirect("./Main.me");
		}
		
		/*
		 *  차단 사용자 세션제어 시작
		 */
		MemberDAO dao = new MemberDAO();
		MemberDTO dto = dao.getMember(userID);
		if(dto == null){
			JSForward.alertAndMove(response, "잘못된 접근입니다!", "./MemberLogin.me");
		}
		boolean blocked = dto.getBlocked();
		ActionForward forward = new ActionForward();
		if(blocked == true) {
			JSForward.alertAndBack(response, "잘못된 접근입니다!");

		}
		
		//탈퇴회원 세션제어
		boolean withdrawal = dto.getWithdrawal();
		if(withdrawal == true) {
			JSForward.alertAndBack(response, "잘못된 접근입니다!");
			return null;
		}
		/*
		 *  차단 사용자 세션제어 끝
		 */
		
	   
//		String userID = request.getParameter("id");
		if(userID == null || userID.equals("")) {
			response.getWriter().write("");
			response.sendRedirect("./Main.me");
		} else {
			try {
//				HttpSession session = request.getSession();
				if(!URLDecoder.decode(userID, "UTF-8").equals((String) session.getAttribute("id"))) {
					response.getWriter().write("");
					return null;
				}
				userID = URLDecoder.decode(userID, "UTF-8");
				response.getWriter().write(getBox(userID));
			} catch (Exception e) {
				response.getWriter().write("");
			}
		}
		return null;
	}
	
	public String getBox(String userID) {
		System.out.println("get box 호출");
		StringBuffer result = new StringBuffer("");
		result.append("{\"result\":[");
		ChatDAO chatDAO = new ChatDAO();
		ArrayList<ChatDTO> chatList = chatDAO.getBox(userID);
		if(chatList.size() == 0) return "";
		for(int i=chatList.size()-1; i>=0; i--) {
			String unread = "";
			String userProfile = "";
			if(userID.equals(chatList.get(i).getToID())) {
				unread = chatDAO.getUnreadChat(chatList.get(i).getFromID(), userID) + "";
				if(unread.equals("0")) unread = "";
			}
			if(userID.equals(chatList.get(i).getToID())) {
				userProfile = new ChatDAO().getProfile(chatList.get(i).getFromID());
			} else {
				userProfile = new ChatDAO().getProfile(chatList.get(i).getToID());
			}
			result.append("[{\"value\": \"" + chatList.get(i).getFromID() + "\"},");
			result.append("{\"value\": \"" + chatList.get(i).getToID() + "\"},");
			result.append("{\"value\": \"" + chatList.get(i).getChatContent() + "\"},");
			result.append("{\"value\": \"" + chatList.get(i).getChatTime() + "\"},");
			result.append("{\"value\": \"" + unread + "\"},");
			result.append("{\"value\": \"" + userProfile + "\"}]");
			if(i != 0) result.append(",");
		}
		result.append("], \"last\":\"" + chatList.get(chatList.size() - 1).getChatID() + "\"}");
		return result.toString();
	}


}
