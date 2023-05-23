package com.itwillbs.action.faq;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itwillbs.commons.Action;
import com.itwillbs.commons.ActionForward;
import com.itwillbs.commons.JSForward;
import com.itwillbs.db.FaqDAO;
import com.itwillbs.db.MemberDAO;
import com.itwillbs.db.MemberDTO;
import com.itwillbs.db.FaqDAO;
import com.itwillbs.db.FaqDTO;

public class FaqUpdateAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("M : FaqUpdateAction_execute()메소드 호출!");
			
		request.setCharacterEncoding("UTF-8");
		ActionForward forward = new ActionForward();

		//관리자 계정인지 확인 
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		System.out.println("현재 계정 : "+id);
		
		
		
		if(id == null || !id.equals("admin")) {
			forward.setPath("./FaqList.fa");
			forward.setRedirect(true);
			return forward;
		}
		
		String pageNum = request.getParameter("pageNum");
		
		FaqDTO dto = new FaqDTO();
		dto.setFaq_id(Integer.parseInt(request.getParameter("faq_id")));
		
		dto.setTitle(request.getParameter("title"));
		dto.setContent(request.getParameter("content"));
		dto.setCategory(request.getParameter("category"));
		
		//공지사항 업데이트 메소드 호출 
		FaqDAO dao = new FaqDAO();
		dao.updateFaq(dto);
		
		//디비처리를 완료하고 페이지 이동 -> 티켓 가지고서
		forward = new ActionForward();
		forward.setPath("./FaqContent.fa?pageNum="+request.getParameter("pageNum")+"&faq_id="+request.getParameter("faq_id"));  
		forward.setRedirect(true);
		System.out.println("M : FAQ 수정 완료=> FAQ 보기 페이지로 이동합니다.");
		
		
		
		
		return forward;
	}

}
