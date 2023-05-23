package com.itwillbs.action.member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itwillbs.commons.Action;
import com.itwillbs.commons.ActionForward;
import com.itwillbs.commons.JSForward;
import com.itwillbs.db.MemberDAO;
import com.itwillbs.db.MemberDTO;

public class PasswordUpdateAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println(" M : MemberUpdateAction_execute() 호출");
		
		// 세션정보 제어
		
		request.setCharacterEncoding("UTF-8");
		
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		String password = request.getParameter("password");
		String newpassword = request.getParameter("newpassword");
		ActionForward forward = new ActionForward();
		
		System.out.println(id);
		if(id == null ) {
			JSForward.alertAndMove(response, "잘못된 접근입니다!", "./MemberLogin.me");
			return forward;
		}
		
		/*
		 *  차단 사용자 세션제어 시작
		 */
		MemberDAO mdao = new MemberDAO();
		MemberDTO mdto = mdao.getMember(id);
		if(mdto == null) {
			JSForward.alertAndMove(response, "잘못된 접근입니다!", "./MemberLogin.me");
		}
		
		boolean blocked = mdto.getBlocked();
		if(blocked == true) {
			JSForward.alertAndBack(response, "잘못된 접근입니다!");
			return forward;
		}
		boolean withdrawal = mdto.getWithdrawal();
		if(withdrawal == true) {
			JSForward.alertAndBack(response, "잘못된 접근입니다!");
			return null;
		}
		/*
		 *  차단 사용자 세션제어 끝
		 */

		// 기존의 회원정보를 가져오기 (DB)
		MemberDAO dao = new MemberDAO();
		
		int result = dao.passwordUpdate(id,password,newpassword);
		
		MemberDTO dto =  dao.getMember(id);
//		System.out.println(dto);
		
		// 정보저장 (request영역)
		request.setAttribute("dto", dto);
		
		System.out.println(" M : 정보 수정결과 "+result);

		// 수정 처리 결과에 따른 페이지 이동 (JS)
		if(result == -1) { //아이디 없음

			JSForward.alertAndBack(response, "비밀번호 오류!");
			return null;
		}
		if(result == 0) { // 비밀번호 오류

			JSForward.alertAndBack(response, "비밀번호 오류!");
			return null;
		}

		JSForward.alertAndMove(response, "비밀번호 수정완료!", "./MemberInfo.my");

		

		return null;
		
		
	}

		
	
}