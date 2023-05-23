package com.itwillbs.action.mypage;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itwillbs.commons.Action;
import com.itwillbs.commons.ActionForward;
import com.itwillbs.commons.JSForward;
import com.itwillbs.db.ListDAO;
import com.itwillbs.db.ListDTO;
import com.itwillbs.db.MemberDAO;
import com.itwillbs.db.MemberDTO;

public class BuyListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println(" BuyListAction_execute()");
		
		// 세션정보 제어(로그인)
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		
		ActionForward forward = new ActionForward();
		if(id == null) {
			forward.setPath("./MemberLogin.me");
			forward.setRedirect(true);
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
		
		// 목록 - DAO : getBuyList()
		ListDAO dao = new ListDAO();
		List<ListDTO>buyList = dao.getBuyList(id);
		
		//request 영역에 저장
		request.setAttribute("buyList", buyList);
		System.out.println(buyList);
		
		// 연결된 view에 출력
		forward.setPath("./mypage/buyList.jsp");
		forward.setRedirect(false);
		System.out.println(forward);
		
		return forward;
	}

}