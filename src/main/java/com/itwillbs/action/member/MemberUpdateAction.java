package com.itwillbs.action.member;

import java.io.PrintWriter;
import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itwillbs.commons.Action;
import com.itwillbs.commons.ActionForward;
import com.itwillbs.commons.JSForward;
import com.itwillbs.db.MemberDAO;
import com.itwillbs.db.MemberDTO;

public class MemberUpdateAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		System.out.println(" M : MemberUpdateAction_execute() 호출");
		
		// 세션정보 제어
		
		
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		ActionForward forward = new ActionForward();
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
		
		MemberDTO dto =  dao.getMember(id);
//		System.out.println(dto);
		
		// 정보저장 (request영역)
		request.setAttribute("dto", dto);
		
		// ./member/updateForm.jsp 출력
		forward.setPath("./member/updateForm.jsp");
		forward.setRedirect(false);
		
		return forward;
	}


}