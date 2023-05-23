package com.itwillbs.action.product;

import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itwillbs.commons.Action;
import com.itwillbs.commons.ActionForward;
import com.itwillbs.commons.JSForward;
import com.itwillbs.db.MemberDAO;
import com.itwillbs.db.MemberDTO;
import com.itwillbs.db.ProductDAO;
import com.itwillbs.db.ProductDTO;

public class ProductUpdateAction implements Action {
	
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println(" P : ProductUpdateAction_execute() 호출");
		
		// 로그인 세션제어
		HttpSession session =  request.getSession();
		String id = (String)session.getAttribute("id");
		ActionForward forward = new ActionForward();
		if(id == null ) {
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
		/*
		 *  차단 사용자 세션제어 끝
		 */
		
		// 한글처리(인코딩)
		request.setCharacterEncoding("UTF-8");
		// 1. JSP 페이지에서 수정할 상품의 id를 전달
		int productId = Integer.parseInt(request.getParameter("product_id"));
		System.out.println("상품번호 : "+productId);
		
		// 기존의 회원정보를 가져오기 (DB)
		ProductDAO dao = new ProductDAO();
		ProductDTO product = dao.productContent(productId);

		//	System.out.println(dto);
		
		// 상세 정보 request에 저장
		request.setAttribute("product", product);
		System.out.println(product.toString());
		
		// 페이지 이동
		forward = new ActionForward();
		// ./member/updateForm.jsp 출력
		forward.setPath("./product/productUpdate.jsp");
		forward.setRedirect(false);
		
		System.out.println("P : 정보 조회 저장, 처리 끝");
		return forward;
	}
}