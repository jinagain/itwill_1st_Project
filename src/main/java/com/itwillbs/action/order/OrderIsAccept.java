package com.itwillbs.action.order;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itwillbs.commons.Action;
import com.itwillbs.commons.ActionForward;
import com.itwillbs.commons.JSForward;
import com.itwillbs.db.OrderDAO;
import com.itwillbs.db.OrderDTO;

public class OrderIsAccept implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println(" M : OrderIsAccept_execute() 호출");
		
		
		int is_accept = Integer.parseInt(request.getParameter("is_accept"));
		int order_id = Integer.parseInt(request.getParameter("order_id"));
		int product_id = Integer.parseInt(request.getParameter("product_id"));
		System.out.println("is_accept : "+is_accept);
		System.out.println("order_id: "+order_id +" / product_id: " +product_id);
			
			OrderDTO dto = new OrderDTO();
			dto.setIs_accept(is_accept);
			dto.setOrder_id(order_id);
			dto.setProduct_id(product_id);
			OrderDAO dao = new OrderDAO();
			dto = dao.getOrderStatus(dto);
			dto = dao.getOrderContent(dto);
			
			JSForward jsf = new JSForward();
			if(is_accept == 0) {
				jsf.alertAndMove(response, "주문 취소 완료", "./OrderContent.or?product_id="+product_id+"&order_id="+order_id);
			}
			else if(is_accept == 1) {
				jsf.alertAndMove(response, "주문이 수락되었습니다. 택배 발송 후 운송장정보를 등록해주세요 ! ", "./OrderContent.or?product_id="+product_id+"&order_id="+order_id);
			}
			else if(is_accept == 3) {
				jsf.alertAndMove(response, "주문 확정 완료", "./OrderContent.or?product_id="+product_id+"&order_id="+order_id);
			}
			
		return null;
	}

}
