package com.itwillbs.action.faq;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itwillbs.commons.Action;
import com.itwillbs.commons.ActionForward;
import com.itwillbs.commons.JSForward;
import com.itwillbs.db.FaqDAO;
import com.itwillbs.db.FaqDTO;
import com.itwillbs.db.MemberDAO;
import com.itwillbs.db.MemberDTO;
import com.itwillbs.db.NoticeDAO;
import com.itwillbs.db.NoticeDTO;

public class FaqListAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("M : FaqListAction.execute() 메소드 호출");
		
		//한글처리
		request.setCharacterEncoding("UTF-8");
		
		ActionForward forward = new ActionForward();

		//////////////////////////////////////////////////////////
		//검색로직 
		String category= request.getParameter("category");
		String selecter = request.getParameter("selecter");
		String search = request.getParameter("search");
		System.out.println("검색어 : "+search+" 검색카테고리 : "+selecter+" 카테고리 : "+category);
		int count; 
		
		if(search =="") search=null;
		if(category =="") category=null;
		System.out.println("검색어 : "+search+" 검색카테고리 : "+selecter+" 카테고리 : "+category);
		
		FaqDAO dao = new FaqDAO();
		
		if(search != null) {
		//검색어가 있을 때 
		search = search.trim();
		count= dao.getFaqCount(search, selecter);
		}else if(category != null){
			//카테고리를 선택했을 때
			search = null;
			count= dao.getFaqCount(category);
		}else{
			//검색어가 없을 때
			//전체 글 갯수 
			count = dao.getFaqCount();
		}
			
		//////////////////////////////////////////////////////////
				
		
		//////////////////////////////////////////////////////////
		//페이징처리1/

		int pageSize =5;
		int pageBlcok;
		
		String pageNum = request.getParameter("pageNum");
		if(pageNum == null)	pageNum = "1";

		int currentPage = Integer.parseInt(pageNum);
		int startRow = (currentPage-1)*pageSize+1;
		int endRow = currentPage*pageSize+1;

		
		//////////////////////////////////////////////////////////

		
		//////////////////////////////////////////////////////////
		//페이징처리2/
		List<FaqDTO> faqList =null;
		if(search != null) {
			//검색어가 있는 경우
			faqList = dao.getFaqList(startRow, pageSize, search, selecter); 
		}else if(category != null) {
			//카테고리를 선택한 경우
			faqList = dao.getFaqList(startRow, pageSize, category); 
		}else {
			//검색어가 없는 경우
			faqList = dao.getFaqList(startRow, pageSize);
		}
		
		int pageCount;
		int pageBlock;
		int startPage;
		int endPage;
		
		if(count !=0) {
			pageCount = count/pageSize + (count%pageSize == 0?0:1);
			pageBlock = 5;
			
			startPage = ((currentPage-1)/pageBlock)*pageBlock+1;
			
			endPage = startPage+pageBlock-1;
			if(endPage>pageCount) endPage = pageCount;
			
			request.setAttribute("pageCount", pageCount);
			request.setAttribute("pageBlock", pageBlock);
			request.setAttribute("startPage", startPage);
			request.setAttribute("endPage", endPage);
			
		}
		
		//////////////////////////////////////////////////////////
		
		
		System.out.println("M : faq수 : "+faqList.size());
		//회원정보를 request 영역에 저장
		request.setAttribute("faqList", faqList);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("category", category);
		request.setAttribute("search", search);
		request.setAttribute("selecter", selecter);
		
		
		forward.setPath("./faq/faqList.jsp");
		forward.setRedirect(false);
		
		return forward;	
		
		
		
	}
}
