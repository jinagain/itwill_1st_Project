package com.itwillbs.action.member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itwillbs.commons.Action;
import com.itwillbs.commons.ActionForward;
import com.itwillbs.commons.JSForward;
import com.itwillbs.db.MemberDAO;
import com.itwillbs.db.MypageDAO;

public class AjaxBlockedAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		 String id = request.getParameter("id");
		 boolean blocked = Boolean.parseBoolean(request.getParameter("blocked"));
		
		 
		 
		System.out.println(id);
		System.out.println(blocked);
		
		MemberDAO dao = new MemberDAO();
		int result = dao.userBlocked(id,blocked);
		System.out.println( "차단값 :" +result);
		if(result == -1) { //아이디 없음

			JSForward.alertAndBack(response, "차단 오류!");
			return null;
		}
		if(result == 0) { // 비밀번호 오류

			JSForward.alertAndBack(response, "차단 오류!");
			return null;
		}
		if(result == 1) {
		JSForward.alertAndMove(response, "회원정보 변경완료!", "./MemberList.me");
		}
		
		return null;
	}

}