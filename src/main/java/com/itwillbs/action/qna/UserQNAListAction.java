package com.itwillbs.action.qna;

import java.util.ArrayList;
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

public class UserQNAListAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 한글처리
		request.setCharacterEncoding("UTF-8");
		ActionForward forward = new ActionForward();
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		
		if(id == null) {
			JSForward.alertAndMove(response, "로그인이 필요합니다.", "./MemberLogin.me");
			return forward;
		}
		
		/*
		 *  차단 사용자 세션제어 시작
		 */
		MemberDAO dao = new MemberDAO();
		MemberDTO mdto = dao.getMember(id);
		if(mdto == null) {
			JSForward.alertAndMove(response, "잘못된 접근입니다!", "./MemberLogin.me");
		}
		boolean blocked = mdto.getBlocked();
		if(blocked == true) {
			JSForward.alertAndBack(response, "잘못된 접근입니다!");
		}
		/*
		 *  차단 사용자 세션제어 끝
		 */
		
		
		System.out.println("모델: MUserQNAListAction 안의 execute() 실행됨");

		QnADTO qdto = new QnADTO();		
		// 페이징 처리*****************************
		// 한 페이지에서 보여줄 글의 개수 설정
		int pageSize = 10;
		// 현 페이지의 페이지값을 확인


		// BoardDAO 객체 생성
		MypageDAO mdao = new MypageDAO();
		// 글 개수 체크하는 메서드, 정보 가져오는 메서드
		// getBoardCount(), getBoardList()
		int count = mdao.UsergetBoardCount(id);

		String pageNum = request.getParameter("pageNum");
		if(pageNum == null)	pageNum = "1";
		int currentPage = Integer.parseInt(pageNum);
		int startRow = (currentPage-1)*pageSize;
		int endRow = currentPage*pageSize+1;

		List<QnADTO> boardList = mdao.UsergetBoardList(id,startRow, pageSize);
		
		//////////////////////////////////////////////////////////

		
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
		
		System.out.println("M : 전체 글수 : "+boardList.size());
		//회원정보를 request 영역에 저장
		request.setAttribute("boardList", boardList);
		request.setAttribute("pageNum", pageNum);
		
		
		//////////////////////////////////////////////////////////

		
		
		
		// 페이지 이동 (./board/boardList.jsp)	
		forward = new ActionForward();
		forward.setPath("./qna/userqnaList.jsp");
		forward.setRedirect(false);		
		return forward;
		}

	
		

}