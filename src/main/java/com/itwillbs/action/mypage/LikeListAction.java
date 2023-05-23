package com.itwillbs.action.mypage;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itwillbs.commons.Action;
import com.itwillbs.commons.ActionForward;
import com.itwillbs.commons.JSForward;
import com.itwillbs.db.LikeDAO;
import com.itwillbs.db.ListDAO;
import com.itwillbs.db.ListDTO;
import com.itwillbs.db.MemberDAO;
import com.itwillbs.db.MemberDTO;
import com.itwillbs.db.MypageDAO;
import com.itwillbs.db.ProductDTO;

public class LikeListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println(" BuyListAction_execute()");
		// 한글처리
		request.setCharacterEncoding("UTF-8");
		
		ActionForward forward = new ActionForward();
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		
		String sessionId = session.getId();
		//쿠키에 세션아이디 저장
		Cookie cookie = new Cookie("JSESSIONID", sessionId);
		cookie.setMaxAge(-1); // 브라우저 종료 시 세션 ID 쿠키 삭제
		response.addCookie(cookie);
		
		
		if(id == null) {
			JSForward.alertAndMove(response, "잘못된 접근입니다!", "./MemberLogin.me");
			return forward;
		}
		
		
		/*
		 *  차단 사용자 세션제어 시작
		 */
		MemberDAO mdao = new MemberDAO();
		MemberDTO mdto = mdao.getMember(id);
		if(mdto == null) {
			JSForward.alertAndMove(response, "잘못된 접근입니다!", "./MemberLogin.me");
		}
		boolean blocked = mdto.getBlocked();
		if(blocked == true) {
			JSForward.alertAndBack(response, "잘못된 접근입니다!");
		}
		
		boolean withdrawal = mdto.getWithdrawal();
		if(withdrawal == true) {
			JSForward.alertAndBack(response, "잘못된 접근입니다!");
			return null;
		}

		/*
		 *  차단 사용자 세션제어 끝
		 */
		
				

		System.out.println("모델: ListAction안의 execute() 실행됨");
		
		// 페이징 처리*****************************
		// 한 페이지에서 보여줄 글의 개수 설정
		int pageSize = 6;
		// 현 페이지의 페이지값을 확인


		//LikeDAO 객체 생성
		LikeDAO dao = new LikeDAO();
		// 글 개수 체크하는 메서드, 정보 가져오는 메서드
		// getBoardCount(), getBoardList()
		int count = dao.getLikeCount(id);

		String pageNum = request.getParameter("pageNum");
		if(pageNum == null)	pageNum = "1";
		int currentPage = Integer.parseInt(pageNum);
		int startRow = (currentPage-1)*pageSize;
		int endRow = currentPage*pageSize+1;

		List<ProductDTO> LikeList = dao.getLikeList(startRow, pageSize,id);
		
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
		
		System.out.println("M : 전체 글수 : "+LikeList.size());
		//회원정보를 request 영역에 저장
		request.setAttribute("LikeList", LikeList);
		request.setAttribute("pageNum", pageNum);
		
		
		//////////////////////////////////////////////////////////
		
//		
		// 연결된 view에 출력
		forward.setPath("./mypage/likeList.jsp");
		forward.setRedirect(false);
		System.out.println(forward);
		
		return forward;
	}

}