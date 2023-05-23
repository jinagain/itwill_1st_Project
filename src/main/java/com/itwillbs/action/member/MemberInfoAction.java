package com.itwillbs.action.member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itwillbs.commons.Action;
import com.itwillbs.commons.ActionForward;
import com.itwillbs.commons.JSForward;
import com.itwillbs.db.MemberDAO;
import com.itwillbs.db.MemberDTO;

public class MemberInfoAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println(" M : MemberInfoAction_execute()");
		
		// 세션정보 가져오기
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		
		ActionForward forward = new ActionForward();

		
		if(id == null) {
			System.out.println("ID 정보가 없습니다.");
//			response.sendRedirect("");
			JSForward.alertAndMove(response, "ID 정보가 없습니다!", "./MemberLogin.me");
			return forward;
		}
		
		
		
		
		// MemberDAO 객체 생성 - 회원정보 조회 메서드 getMember()
		MemberDAO dao = new MemberDAO();
		MemberDTO dto = dao.getMember(id);
		// 회원정보 저장(request 영역)
		request.setAttribute("dto", dto);
		
		// 페이지 forward 이동(jsp) + 정보 출력
		// ./member/memberInfo.jsp

		forward.setPath("./member/memberInfo.jsp");
		forward.setRedirect(false);
		
		System.out.println("M : 정보조회 저장, 처리 끝");
		return forward;
	}



	

}