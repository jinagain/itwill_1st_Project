package com.itwillbs.action.mypage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itwillbs.commons.Action;
import com.itwillbs.commons.ActionForward;
import com.itwillbs.commons.JSForward;
import com.itwillbs.db.MemberDAO;
import com.itwillbs.db.MemberDTO;

public class AdminMemberInfoAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 세션정보 가져오기
		String id = request.getParameter("id");
		HttpSession session = request.getSession();
		String admin = (String)session.getAttribute("id");
		System.out.println(id);
		ActionForward forward = new ActionForward();
		if(admin == null) {
			JSForward.alertAndMove(response, "잘못된 접근입니다!", "./MemberLogin.me");
			return forward;
		}
		
		/*
		 *  차단 사용자 세션제어 시작
		 */
		MemberDAO mdao = new MemberDAO();
		MemberDTO mdto = mdao.getMember(id);
		boolean blocked = mdto.getBlocked();
		if(blocked == true) {
			JSForward.alertAndBack(response, "잘못된 접근입니다!");
		}
		/*
		 *  차단 사용자 세션제어 끝
		 */
		
		
		// MemberDAO 객체 생성 - 회원정보 조회 메서드 getMember()
		MemberDAO dao = new MemberDAO();
		MemberDTO dto = dao.getMember(id);
		
		// 회원정보 저장(request 영역)
		request.setAttribute("dto", dto);
		
		// 페이지 forward 이동(jsp) + 정보 출력
		// ./member/memberInfo.jsp

		forward.setPath("./mypage/adminMemberInfo.jsp");
		forward.setRedirect(false);
		
		System.out.println("M : 정보조회 저장, 처리 끝");
		return forward;
	}

}
