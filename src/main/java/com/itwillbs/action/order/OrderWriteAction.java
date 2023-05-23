package com.itwillbs.action.order;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itwillbs.commons.Action;
import com.itwillbs.commons.ActionForward;
import com.itwillbs.commons.JSForward;
import com.itwillbs.db.MemberDAO;
import com.itwillbs.db.MemberDTO;
import com.itwillbs.db.OrderDAO;
import com.itwillbs.db.OrderDTO;

public class OrderWriteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println(" M : OrderWriteAction_execute()");
		
		// 세션정보 가져오기
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		System.out.println("id: "+id);
		int product_id = Integer.parseInt(request.getParameter("product_id"));
		System.out.println("id: "+id +" / product_id: " +product_id);
		
		if(id == null) {
			JSForward jsf = new JSForward();
			jsf.alertAndMove(response, "로그인이 필요합니다", "./MemberLogin.me");
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
		/*
		 *  차단 사용자 세션제어 끝
		 */
		
		// MemberDAO 객체 생성 - 회원정보 조회 메서드 getMember()
		OrderDAO dao = new OrderDAO();
		OrderDTO dto = new OrderDTO();
		dto.setId(id);
		dto.setProduct_id(product_id);
		dto = dao.orderWrite(dto);
		System.out.println("OrderWriteAction : DAO 메서드 완료");
		System.out.println("dto : "+dto);
		
		// 회원정보 저장(request 영역)
		request.setAttribute("dto", dto);
		// 페이지 forward 이동(jsp) + 정보 출력
		ActionForward forward = new ActionForward();
		forward.setPath("./order/orderWrite.jsp");
		forward.setRedirect(false);
		
		System.out.println("M : 주문정보 가져오기 완료");
		return forward;
	}
	

}