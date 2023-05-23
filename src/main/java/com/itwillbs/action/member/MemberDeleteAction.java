package com.itwillbs.action.member;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itwillbs.commons.Action;
import com.itwillbs.commons.ActionForward;
import com.itwillbs.commons.JSForward;
import com.itwillbs.db.MemberDAO;
import com.itwillbs.db.MemberDTO;


public class MemberDeleteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		System.out.println(" M : MemberDeleteAction_execute()");

		
		
		
		
		HttpSession session =  request.getSession();
		// 세션정보 제어(로그인)
		String id = (String)session.getAttribute("id");
		ActionForward forward = new ActionForward();
		if(id == null ) {
			forward.setPath("./MemberLogin.me");
			forward.setRedirect(true);
			return forward;
		}
		
		//탈퇴회원 세션제어
		MemberDAO mdao = new MemberDAO();
		MemberDTO mdto = mdao.getMember(id);
		if(mdto == null) {
			JSForward.alertAndMove(response, "잘못된 접근입니다!", "./MemberLogin.me");
		}
		
		boolean withdrawal = mdto.getWithdrawal();
		if(withdrawal == true) {
			JSForward.alertAndBack(response, "잘못된 접근입니다!");
			return null;
		}

		
		
		// 정보 저장(id, password)
		String password = (String)request.getParameter("password");
		
		// DAO - 정보수정 메서드 호출 memberUpdate(dto)
		MemberDAO dao = new MemberDAO();
		
		// 회원탈퇴 - DAO 객체 - deleteMember()
		int result = dao.deleteMember(id, password);
		
		System.out.println(" M : 정보 삭제결과 " + result);

		// 삭제 결과에 따른 페이지 이동(JS)
		if(result == -1) {
			JSForward.alertAndBack(response, "회원 정보 없음(삭제불가)");
			return null;
		}
		if(result == 0) {
			JSForward.alertAndBack(response, "비밀번호 오류(삭제불가)");
			return null;
		}
		session.invalidate();
		JSForward.alertAndMove(response, "회원정보 탈퇴 성공!", "./MemberLogin.me");
		return null;
	}

}
