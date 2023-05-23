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

public class OrderAddAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println(" M : OrderAddAction_execute()");

		// 세션정보 가져오기
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
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
		
		// dto 생성, 결제정보 저장
		OrderDTO dto = new OrderDTO();
		dto.setId(id);
		dto.setProduct_id(Integer.parseInt(request.getParameter("product_id")));
		System.out.println(Integer.parseInt(request.getParameter("product_id")));
		dto.setReceiver_name(request.getParameter("name"));
		System.out.println("name : "+request.getParameter("name"));
		dto.setReceiver_phone(request.getParameter("receiver_phone"));
		dto.setReceiver_addr1(request.getParameter("receiver_addr1"));
		dto.setReceiver_addr2(request.getParameter("receiver_addr2"));
		dto.setReceiver_post(Integer.parseInt(request.getParameter("receiver_post")));
		System.out.println(Integer.parseInt(request.getParameter("receiver_post")));
		dto.setPayment(request.getParameter("payment"));
		dto.setPaid_amount(request.getParameter("paid_amount"));
		System.out.println("dto : "+dto);
		
		// MemberDAO 객체 생성 
		OrderDAO dao = new OrderDAO();
		// 주문 내용 저장
		dao.addOrder(dto);
		// 주문서 출력 메서드 getOrderContent()
		dto = dao.getOrderContent(dto);
		int order_id = dto.getOrder_id();
		
		// 회원정보 저장(request 영역)
		request.setAttribute("dto", dto);
		System.out.println("id: "+id +" / product_id: " +product_id);
		
		ActionForward forward = new ActionForward();
		forward.setPath("./OrderContent.or?product_id=" + product_id + "&order_id=" + order_id);
		forward.setRedirect(true);

		System.out.println("M : 결제정보 저장, 주문정보 가져오기 끝");
		return forward;
	}
	

}

