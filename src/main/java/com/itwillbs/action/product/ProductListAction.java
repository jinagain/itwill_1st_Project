package com.itwillbs.action.product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

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

public class ProductListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
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
		
		
		System.out.println("P : ProductListAction_execute() 호출");
		int brand = Integer.parseInt(request.getParameter("brand"));
		int model = Integer.parseInt(request.getParameter("model"));
		int color = Integer.parseInt(request.getParameter("color"));
		String parts = request.getParameter("parts");
		
		int method = Integer.parseInt(request.getParameter("trade"));
		String sido1 = request.getParameter("sido1");
		String gugun1 = request.getParameter("gugun1");
		
		String sql = "select * from product where " +
				" brand= "+brand +
				" and model= "+model +
				" and color= "+color +
				" and parts= '"+parts + "'" +
				" and method= "+ method +
				" and product_status= 0";
		if(method == 1) {
			sql += " and city like '"+sido1+ " " + gugun1 + "%'";
		}
		System.out.println(sql);
		// ProductDAO 객체 생성
		ProductDAO dao = new ProductDAO();

 		// 상품 정보 가져오기
		List<ProductDTO> productList = dao.getProductList(sql);
		 Collections.shuffle(productList);
		
		 // 상품 정보 가져오기
		 // productList에서 랜덤으로 3개의 상품을 선택하여 새 리스트에 저장
		 //List<ProductDTO> randomProductList = new ArrayList<>();

		while (productList.size() > 3) {
			productList.remove(productList.size()-1);
		}

		// request 객체에 저장
		request.setAttribute("productList", productList);

		System.out.println(productList.size());
		System.out.println("");
		// 연결된 view에 출력(./product/productList.jsp)
		forward = new ActionForward();
		forward.setPath("./product/productList.jsp");
		forward.setRedirect(false);
		System.out.println(forward);
		return forward;
	}

}