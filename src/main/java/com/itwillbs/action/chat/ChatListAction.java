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

public class ChatListAction implements Action {
//	private static final long serialVersionUID = 1L;

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println(" M : ChatListAction_execute() 실행");
		
		// 한글처리
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
		// 세션정보 제어
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		ActionForward forward = new ActionForward();
		if(id == null ) {
			forward.setPath("./MemberLogin.me");
			forward.setRedirect(true);
			return forward;
		}
		
		/*
		 *  차단 사용자 세션제어 시작
		 */
		MemberDAO dao = new MemberDAO();
		MemberDTO dto = dao.getMember(id);
		if(dto == null) {
			JSForward.alertAndMove(response, "잘못된 접근입니다!", "./MemberLogin.me");
		}
		boolean blocked = dto.getBlocked();
		if(blocked == true) {
			JSForward.alertAndBack(response, "잘못된 접근입니다!");
			return forward;
		}
		/*
		 *  차단 사용자 세션제어 끝
		 */
		
        // 전달된 정보 저장
        String fromID = id;
        String toID = request.getParameter("toID");
        System.out.println("fromID : " + fromID +  "/ toID : " + toID);
        String listType = request.getParameter("listType");
        if(fromID == null || fromID.equals("") || toID == null || toID.equals("")
        		|| listType == null || listType.equals("")) 
        	response.getWriter().write("");
        else if(listType.equals("ten")) response.getWriter().write(getTen(URLDecoder.decode(fromID, "UTF-8"), URLDecoder.decode(toID, "UTF-8")));
        else {
        	try {
				if(!URLDecoder.decode(fromID, "UTF-8").equals(id)) {
					response.getWriter().write("");
					return null;
				}
        		response.getWriter().write(getID(URLDecoder.decode(fromID, "UTF-8"), URLDecoder.decode(toID, "UTF-8"), listType));
        	} catch (Exception e) {
        		response.getWriter().write("");
        	}
        }
		return null;
	}
	
	public String getTen(String fromID, String toID) {
		StringBuffer result = new StringBuffer("");
		result.append("{\"result\":[");
		ChatDAO chatDAO = new ChatDAO();
		ArrayList<ChatDTO> chatList = chatDAO.getChatListByRecent(fromID, toID, 100);
		if(chatList.size() == 0) return "";
		for(int i=0; i<chatList.size(); i++) {
			result.append("[{\"value\": \"" + chatList.get(i).getFromID() + "\"},");
			result.append("{\"value\": \"" + chatList.get(i).getToID() + "\"},");
			result.append("{\"value\": \"" + chatList.get(i).getChatContent() + "\"},");
			result.append("{\"value\": \"" + chatList.get(i).getChatTime() + "\"}]");
			if(i != chatList.size() -1) result.append(",");
		}
		result.append("], \"last\":\"" + chatList.get(chatList.size() - 1).getChatID() + "\"}");
		chatDAO.readChat(fromID, toID);
		return result.toString();
	}
	
	public String getID(String fromID, String toID, String chatID) {
		StringBuffer result = new StringBuffer("");
		result.append("{\"result\":[");
		ChatDAO chatDAO = new ChatDAO();
		ArrayList<ChatDTO> chatList = chatDAO.getChatListByID(fromID, toID, chatID);
		if(chatList.size() == 0) return "";
		for(int i=0; i<chatList.size(); i++) {
			result.append("[{\"value\": \"" + chatList.get(i).getFromID() + "\"},");
			result.append("{\"value\": \"" + chatList.get(i).getToID() + "\"},");
			result.append("{\"value\": \"" + chatList.get(i).getChatContent() + "\"},");
			result.append("{\"value\": \"" + chatList.get(i).getChatTime() + "\"}]");
			if(i != chatList.size() -1) result.append(",");
		}
		result.append("], \"last\":\"" + chatList.get(chatList.size() - 1).getChatID() + "\"}");
		chatDAO.readChat(fromID, toID);
		return result.toString();
	}

}
