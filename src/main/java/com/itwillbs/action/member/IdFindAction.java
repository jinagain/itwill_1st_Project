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

public class IdFindAction implements Action {

	

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		System.out.println(" M : IdFindAction_execute() 호출 ");
		

		
		request.setCharacterEncoding("UTF-8");
		ActionForward forward = new ActionForward();
		

		
		//전달정보(name, phone_number) - dto에 저장
		MemberDTO dto = new MemberDTO();
		dto.setName(request.getParameter("name"));
		dto.setPhone_number(request.getParameter("phone_number"));
		
		//MemberDAO 객체 생성해서 dto전달
		MemberDAO dao = new MemberDAO();
		String result = dao.idFind(dto);
		
		if(result=="none") {
			JSForward.alertAndBack(response, "정보를 잘못 입력하셨습니다.");
			return null;
		}else
		 System.out.println("회원아이디 : "+result);
		 request.setAttribute("result", result);
		 request.setAttribute("name", dto.getName());
		 forward.setPath("./member/idFindAction.jsp");
		 forward.setRedirect(false);
		 
		 return forward;
	}
	


}