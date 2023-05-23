package com.itwillbs.action.member;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itwillbs.commons.Action;
import com.itwillbs.commons.ActionForward;
import com.itwillbs.commons.JSForward;
import com.itwillbs.db.MemberDAO;
import com.itwillbs.db.MemberDTO;
import com.mysql.cj.Session;

// 로그인 정보 처리 
public class MemberLoginAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		System.out.println(" M : MemberLoginAction_execute() 호출 ");
		
		// 한글처리 
		request.setCharacterEncoding("UTF-8");
		// 전달정보 ( id/pw ) 저장 - MemberDTO
		MemberDTO dto = new MemberDTO();
		dto.setId(request.getParameter("id"));
		dto.setPassword(request.getParameter("password"));
		String id = (String)request.getParameter("id");
		//탈퇴회원 세션제어
		MemberDAO mdao = new MemberDAO();
		MemberDTO mdto = mdao.getMember(id);
		if(mdto == null) {
			JSForward.alertAndMove(response, "잘못된 접근입니다!", "./MemberLogin.me");
		}
		
		boolean withdrawal = mdto.getWithdrawal();
		if(withdrawal == true) {
			JSForward.alertAndBack(response, "탈퇴된 회원 입니다!");
			return null;
		}
		// MemberDAO 객체 생성
		MemberDAO dao = new MemberDAO();
		// 로그인체크 메서드 호출 => 결과확인
		int result = dao.memberLogin(dto);
		
		// 페이지 이동 
		if(result == -1) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.print("<script>");
			out.print(" alert('회원정보가 없습니다.'); ");
			out.print(" history.back(); ");
			out.print("</script>");
			out.close();
			
			return null; //컨트롤러에서는 이동 X		
		}
		
		if(result == 0) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.print("<script>");
			out.print(" alert('정보를 잘못 입력하셨습니다.'); ");
			out.print(" history.back(); ");
			out.print("</script>");
			out.close();
			
			return null; //컨트롤러에서는 이동 X	
		}
		
		// 로그인 성공! (result == 1)
		// 세션에 아이디정보 저장
		HttpSession session = request.getSession();
		session.setAttribute("id", dto.getId());
		
		// 페이지 이동-ActionForward객체 생성
		ActionForward forward = new ActionForward();
		forward.setPath("./Main.me");
		forward.setRedirect(true);
		
		return forward;
	}

}