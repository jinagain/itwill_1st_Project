package com.itwillbs.action.chat;

import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itwillbs.commons.Action;
import com.itwillbs.commons.ActionForward;
import com.itwillbs.commons.JSForward;
import com.itwillbs.db.ChatDAO;
import com.itwillbs.db.MemberDAO;
import com.itwillbs.db.MemberDTO;

public class ChatSubmitAction implements Action {
	private static final long serialVersionUID = 1L;
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println(" M : ChatSubmitAction_execute() 실행");
		
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
		}
		/*
		 *  차단 사용자 세션제어 끝
		 */
		
        // 전달된 정보 저장
        String fromID = request.getParameter("fromID");
        String toID = request.getParameter("toID");
        String chatContent = request.getParameter("chatContent");
        if(fromID == null || fromID.equals("") || toID == null || toID.equals("")
        		|| chatContent == null || chatContent.equals("")) {
        	response.getWriter().write("0");
        } else if(fromID.equals(toID)) {
        	response.getWriter().write("-1");
        } else {
        	fromID = URLDecoder.decode(fromID, "UTF-8");
        	toID = URLDecoder.decode(toID, "UTF-8");
			if(!URLDecoder.decode(fromID, "UTF-8").equals(id)) {
				response.getWriter().write("");
				return forward;
			}
        	chatContent = URLDecoder.decode(chatContent, "UTF-8");
        	response.getWriter().write(new ChatDAO().submit(fromID, toID, chatContent) + "");
        }
		return null;
	}

}
