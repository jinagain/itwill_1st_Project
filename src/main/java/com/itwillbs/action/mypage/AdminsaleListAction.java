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

public class AdminsaleListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		System.out.println(id);
		HttpSession session = request.getSession();
		String admin = (String)session.getAttribute("id");
		ActionForward forward = new ActionForward();
		if(admin == null) {
			JSForward.alertAndMove(response, "잘못된 접근입니다!", "./MemberLogin.me");
			return forward;
		}
		
		//  관리자 세션제어
		if(!admin.equals("admin@gmail.com") && !admin.equals("admin")) {
			JSForward.alertAndBack(response, "잘못된 접근입니다!");
			return forward;
		}
		
		/*
		 *  차단 사용자 세션제어 시작
		 */
		MemberDAO mdao = new MemberDAO();
		MemberDTO mdto = mdao.getMember(admin);
		boolean blocked = mdto.getBlocked();
		if(blocked == true) {
			JSForward.alertAndBack(response, "잘못된 접근입니다!");
		}
		/*
		 *  차단 사용자 세션제어 끝
		 */
		
		// 목록 - DAO : getBuyList()
		ListDAO dao = new ListDAO();
		List<ListDTO>saleList = dao.getSaleList(id);
		
		//request 영역에 저장
		request.setAttribute("saleList", saleList);
		request.setAttribute("id", id);
		System.out.println(saleList);
		
		// 연결된 view에 출력
		forward.setPath("./mypage/adminsaleList.jsp");
		forward.setRedirect(false);
		System.out.println(forward);
		
		return forward;
		

	}

}
