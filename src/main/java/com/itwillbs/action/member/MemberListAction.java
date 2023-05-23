package com.itwillbs.action.member;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itwillbs.commons.Action;
import com.itwillbs.commons.ActionForward;
import com.itwillbs.commons.JSForward;
import com.itwillbs.db.MemberDAO;
import com.itwillbs.db.MemberDTO;
import com.itwillbs.db.MypageDAO;
import com.itwillbs.db.QnADTO;

public class MemberListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		System.out.println(" M : MemberListAction_execute() ");
		
		// 세션정보 제어(로그인 + 관리자일때만 사용)
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");

		ActionForward forward = new ActionForward();
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
		MemberDAO mdao = new MemberDAO();
		MemberDTO mdto = mdao.getMember(id);
		boolean blocked = mdto.getBlocked();
		if(blocked == true) {
			JSForward.alertAndBack(response, "잘못된 접근입니다!");
			return forward;
		}
		
		//탈퇴회원 세션제어
		if(mdto == null) {
			JSForward.alertAndMove(response, "잘못된 접근입니다!", "./MemberLogin.me");
		}
		
		boolean withdrawal = mdto.getWithdrawal();
		if(withdrawal == true) {
			JSForward.alertAndBack(response, "잘못된 접근입니다!");
			return null;
		}
		/*
		 *  차단 사용자 세션제어 끝
		 */
		
		
		// 회원정보 목록 - MemberDAO : getMemberList()
		MemberDAO dao = new MemberDAO();
		//검색로직 
		String category= request.getParameter("category");
		String search = request.getParameter("search");
		System.out.println("검색어 : "+search+" 카테고리 : "+category);
		int count; 
		
		// DAO 객체 생성
		
		if(search != null) {
		//검색어와 일치하는 글의 갯수
		search = search.trim();
		count= dao.getMemberCount(search, category);
		}else {
		//검색어가 없을 때
		//전체 글 갯수 
		count = dao.getMemberCount();
		}
		
		
		
		

		System.out.println("모델: MypageQNAListAction안의 execute() 실행됨");
		
		// 페이징 처리*****************************
		// 한 페이지에서 보여줄 글의 개수 설정
		int pageSize = 7;
		// 현 페이지의 페이지값을 확인

		
		
		// 글 개수 체크하는 메서드, 정보 가져오는 메서드
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
		
		

		
		
		
		List<MemberDTO> memberList = null;
		
		
		//////////////////////////////////////////////////////////
		if(search != null) {
			//검색어가 있는 경우
			memberList = dao.getMemberList(startRow, pageSize, search, category); 
		}else {
			//검색어가 없는 경우
			memberList = dao.getMemberList(startRow, pageSize);
		}
		System.out.println(" M : 회원 수 " + memberList.size() + "명");
		request.setAttribute("memberList", memberList);
		//////////////////////////////////////////////////////////
		//페이징처리2/
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
		
		//회원정보를 request 영역에 저장
		request.setAttribute("pageNum", pageNum);
		

		
		// 연결된 view에 출력(./member/memberList.jsp)
		forward.setPath("./member/memberList.jsp");
		forward.setRedirect(false);
		System.out.println(forward);
		return forward;
	}

}