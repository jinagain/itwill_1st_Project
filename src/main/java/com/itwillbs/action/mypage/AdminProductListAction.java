package com.itwillbs.action.mypage;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itwillbs.commons.Action;
import com.itwillbs.commons.ActionForward;
import com.itwillbs.commons.JSForward;
import com.itwillbs.db.ListDTO;
import com.itwillbs.db.MemberDAO;
import com.itwillbs.db.MemberDTO;
import com.itwillbs.db.MypageDAO;
import com.itwillbs.db.ProductDTO;

public class AdminProductListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 한글처리
		request.setCharacterEncoding("UTF-8");
		System.out.println("M : AdminProductListAction_execute() 호출!");
		
		ActionForward forward = new ActionForward();
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		System.out.println("M: AdminProductListAciton_execute();");
		
		if(id == null) {
			JSForward.alertAndMove(response, "잘못된 접근입니다!", "./MemberLogin.me");
			return forward;
		}
		
		//  관리자 세션제어
		if(!id.equals("admin@gmail.com") && !id.equals("admin")) {
			JSForward.alertAndBack(response, "잘못된 접근입니다!");
			return forward;
		}
		
		/*
		 *  차단 사용자 세션제어 시작
		 */
		MemberDAO dao = new MemberDAO();
		MemberDTO dto = dao.getMember(id);
		boolean blocked = dto.getBlocked();
		if(blocked == true) {
			JSForward.alertAndBack(response, "잘못된 접근입니다!");
		}
		/*
		 *  차단 사용자 세션제어 끝
		 */
		
		//////////////////////////////////////////////////////////
		//검색로직 
		String category= request.getParameter("category");
		String search = request.getParameter("search");
		System.out.println("검색어 : "+search+" 카테고리 : "+category);
		int count; 
		
		// DAO 객체 생성
		MypageDAO mdao = new MypageDAO();
		
		if(search != null) {
		//검색어와 일치하는 글의 갯수
		search = search.trim();
		count= mdao.getBoardCount(search, category);
		}else {
		//검색어가 없을 때
		//전체 글 갯수 
		count = mdao.getAdminProductCount();
		}
		//////////////////////////////////////////////////////////

		
		// 페이징 처리*****************************
		// 한 페이지에서 보여줄 글의 개수 설정
		int pageSize = 8;
		// 현 페이지의 페이지값을 확인

		String pageNum = request.getParameter("pageNum");
		int startRow;
		int endRow;
		
		if(pageNum == null)	pageNum = "1";
		int currentPage = Integer.parseInt(pageNum);
		
		if(search != null) {
			startRow = (currentPage-1)*pageSize+1;
			endRow = currentPage*pageSize+1;
		}else {
			startRow = (currentPage-1)*pageSize;
			endRow = currentPage*pageSize+1;
		}
//		List<ListDTO> adminsCProductList = mdao.getAdminList(startRow, pageSize);
		
		//////////////////////////////////////////////////////////

		
		//////////////////////////////////////////////////////////
		//페이징처리2/
		
		List<ListDTO> adminList = null;
		if(search != null) {
			
			//검색어가 있는 경우
			adminList = mdao.getAdminList(startRow, pageSize, search, category); 
		}else {
			
			//검색어가 없는 경우
			adminList = mdao.getAdminList(startRow, pageSize);
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
		
		System.out.println("M : 전체 글수 : "+adminList.size());
		//회원정보를 request 영역에 저장
		request.setAttribute("adminProductList", adminList);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("category", category);
		request.setAttribute("search", search);
		
		
		//////////////////////////////////////////////////////////
		
		
		
		// 페이지 이동 (./board/boardList.jsp)	
		forward = new ActionForward();
		forward.setPath("./mypage/adminProductList.jsp");
		forward.setRedirect(false);		
		return forward;
	}


}
