package com.itwillbs.action.qna;

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

public class QNAUpdateAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		
		ActionForward forward = new ActionForward();
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		
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
	
		
		
		//2.전달되는 파라미터 정보저장 
		int bno = Integer.parseInt(request.getParameter("bno"));
		String pageNum = (String) request.getAttribute("pageNum");
		
		//3.DAO객체생성 -> bno 해당되는 정보 가져오기
		MypageDAO mdao = new MypageDAO();
		QnADTO qdto = mdao.getBoard(bno);
		
		//5.리퀘스트영역에 정보저장
		request.setAttribute("qdto", qdto);
		
		//6. 화면출력하는 뷰페이지(.jsp)로 전달 (get방식)
		forward = new ActionForward();
		forward.setPath("./qna/qnaUpdate.jsp?pageNum="+pageNum);
		forward.setRedirect(false);
		
		return forward;
	}

}

