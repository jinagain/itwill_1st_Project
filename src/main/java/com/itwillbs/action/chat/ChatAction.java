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

public class ChatAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println(" M : ChatAction_execute() 실행");
		
		// 한글처리
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
		// 세션정보 제어
        HttpSession session = request.getSession();
        String userID = null;
        if(session.getAttribute("id") != null) {
     	  userID = (String) session.getAttribute("id");
        }
        String toID = null;
        if(request.getParameter("toID") != null){
     	  toID = (String) request.getParameter("toID");
        }

		/*
		 *  차단 사용자 세션제어 시작
		 */
		MemberDAO dao = new MemberDAO();
		MemberDTO dto = dao.getMember(userID);
		if(dto == null) {
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
		
//        System.out.println("id : " + userID +  "/ toID : " + request.getParameter("toID"));
        if(userID == null) {
     	  session.setAttribute("messageType", "오류 메시지");
     	  session.setAttribute("messageContent", "현재 로그인이 되어 있지 않습니다.");
     	  response.sendRedirect("./MemberLoginAction.me");
//     	  return;
        }
//        if(toID == null) {
//      	  session.setAttribute("messageType", "오류 메시지");
//      	  session.setAttribute("messageContent", "대화 상대가 지정되지 않습니다.");
//      	  response.sendRedirect("index.jsp");
////      	  return;
//         }
         if(userID.equals(URLDecoder.decode(toID, "UTF-8"))) {
         	session.setAttribute("messageType", "오류 메시지");
 	       	session.setAttribute("messageContent", "자기 자신에게는 쪽지를 보낼 수 없습니다.");
 	       	response.sendRedirect("./");
// 	       	return;
         }
         String fromProfile = new ChatDAO().getProfile(userID);
         String toProfile = new ChatDAO().getProfile(toID);
         System.out.println(fromProfile);
         request.setAttribute("fromProfile", fromProfile);
         request.setAttribute("toProfile", toProfile);
         request.setAttribute("toID", toID);
//		HttpSession session = request.getSession();
//		String id = (String)session.getAttribute("id");
//		ActionForward forward = new ActionForward();
//		if(id == null ) {
//			forward.setPath("./MemberLogin.me");
//			forward.setRedirect(true);
//			return forward;
//		}

         forward = new ActionForward();
         forward.setPath("./chat/chat.jsp");
         forward.setRedirect(false);
         return forward;
	}

}
