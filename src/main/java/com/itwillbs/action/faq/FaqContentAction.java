package com.itwillbs.action.faq;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itwillbs.commons.Action;
import com.itwillbs.commons.ActionForward;
import com.itwillbs.commons.JSForward;
import com.itwillbs.db.MemberDAO;
import com.itwillbs.db.MemberDTO;
import com.itwillbs.db.FaqDAO;
import com.itwillbs.db.FaqDTO;

public class FaqContentAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("M: FaqContentAction_execute()메소드 호출!");
		

		ActionForward forward = new ActionForward();

		
		String faq_id = request.getParameter("faq_id");
		String pageNum = request.getParameter("pageNum");
		
		forward = new ActionForward();
		
		FaqDAO dao = new FaqDAO();
		//글정보 불러오기 메소드 호출
		FaqDTO dto = dao.getFaqContent(faq_id);
		
		
		request.setAttribute("dto", dto);
		request.setAttribute("pageNum", pageNum);
		
		forward.setPath("./faq/faqContent.jsp");
		forward.setRedirect(false);
		
		
		
		
		
		
		return forward;
	}

}
