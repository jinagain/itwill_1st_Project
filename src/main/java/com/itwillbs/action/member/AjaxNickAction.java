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

public class AjaxNickAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println(" M : AjaxAction_execute ");

		 response.setContentType("text/html; charset=UTF-8");
		 String nickname = request.getParameter("nickname");
		
		  System.out.println(nickname);
		
		  MemberDAO dao = new MemberDAO();
		  String result = dao.nickNameCheck(nickname);
		  
		  PrintWriter out = response.getWriter();
		  if(result =="1"){
			  out.print("no");
		  }else{
			  out.print("yes");
		  }
		
		return null;
	}

}