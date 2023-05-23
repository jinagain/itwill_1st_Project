package com.itwillbs.action.chat;

import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itwillbs.commons.Action;
import com.itwillbs.commons.ActionForward;
import com.itwillbs.commons.JSForward;
import com.itwillbs.db.ChatDAO;
import com.itwillbs.db.MemberDAO;
import com.itwillbs.db.MemberDTO;

public class ChatUnreadAction implements Action {
//	private static final long serialVersionUID = 1L;
   
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println(" M : ChatListAction_execute() 실행");
		
		request.setCharacterEncoding("UTF-8");
		
		String userID = (String)request.getSession().getAttribute("id");
		if(userID == null || userID.equals("")) {
			response.getWriter().write("0");
		} else {
			if(!URLDecoder.decode(userID, "UTF-8").equals(userID)) {
				response.getWriter().write("");
//				return null;
			}
			response.getWriter().write(new ChatDAO().getAllUnreadChat(userID) + "");
		}
		
		
		
//		String userID = request.getParameter("id");
//		if(userID == null || userID.equals("")) {
//			response.getWriter().write("0");
//		} else {
//			HttpSession session = request.getSession();
//			if(!URLDecoder.decode(userID, "UTF-8").equals((String) session.getAttribute("id"))) {
//				response.getWriter().write("");
//				return null;
//			}
//			response.getWriter().write(new ChatDAO().getAllUnreadChat(userID) + "");
//		}
//		return null;
		return null;
	}
	
	
}