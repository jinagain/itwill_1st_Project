package com.itwillbs.action.member;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itwillbs.commons.Action;
import com.itwillbs.commons.ActionForward;
import com.itwillbs.commons.JSForward;
import com.itwillbs.db.MemberDAO;
import com.itwillbs.db.MemberDTO;

public class PwFindAction implements Action {


	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println(" M : IdFindAction_execute() 호출 ");
		
		request.setCharacterEncoding("UTF-8");
		ActionForward forward = new ActionForward();
		
		
		//전달정보(name, phone_number) - dto에 저장
		MemberDTO dto = new MemberDTO();
		dto.setId(request.getParameter("id"));
		dto.setPhone_number(request.getParameter("phone_number"));
		
		// 전달된 정보 저장
		
		
		//임시비밀번호 생성
		String randomPw = "";
		for (int i = 0; i < 12; i++) {
			randomPw += (char) ((Math.random() * 26) + 97);
		}
		
		request.setAttribute("randomPw",randomPw);
		
		
		
		//MemberDAO 객체 생성해서 dto전달하여 비밀번호 업데이트
		MemberDAO dao = new MemberDAO();
		int result = dao.pwFind(dto,randomPw);
		

		
		
		if(result==0 || result==-1) {
			JSForward.alertAndBack(response, "정보를 잘못 입력하셨습니다.");
			return null;
		}
		else
			
			 request.setAttribute("randomPw", randomPw);
			
			 forward.setPath("./member/pwFindAction.jsp");
			 forward.setRedirect(false);
			 
			 return forward;
			
	
	}

}