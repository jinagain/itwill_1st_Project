package com.itwillbs.action.faq;

import java.util.Date;

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

public class FaqWriteAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("M : FaqWriteAciton_execute() 호출!");
		
		//한글처리 
		request.setCharacterEncoding("UTF-8");
		
		//관리자 계정인지 확인 
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		System.out.println("현재 계정 : "+id);
		
		ActionForward forward = new ActionForward();
		
		if(id == null || !id.equals("admin")) {
			forward.setPath("./FaqList.fa");
			forward.setRedirect(true);
			return forward;
		}	
		
		
		//FaqDTO객체에 전달받은 FAQ 내용을 전달받아 초기화
		FaqDTO dto = new FaqDTO();
		
//		dto.setFaq_id(Integer.parseInt(request.getParameter("Faq_id")));
		dto.setTitle(request.getParameter("title"));
		//글쓰기 날짜는 현재 날짜와 시간으로 지정
		dto.setContent(request.getParameter("content"));
		dto.setCategory(request.getParameter("category"));
		
		//MemberDAO의 FAQ 쓰기 메소드 호출
		FaqDAO dao = new FaqDAO();
		dao.faqWrite(dto);
		
		//디비처리를 완료하고 페이지 이동 -> 티켓 가지고서
		forward = new ActionForward();
		forward.setPath("./FaqList.fa"); //FAQ 쓴 뒤에는 공지 보기 페이지로 이동한다. 
		forward.setRedirect(true);
		System.out.println("M : FAQ 쓰기 완료=> FAQ 보기 페이지로 이동합니다.");
		
		return forward;
	}

}
