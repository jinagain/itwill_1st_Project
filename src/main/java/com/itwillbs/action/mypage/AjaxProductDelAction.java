package com.itwillbs.action.mypage;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itwillbs.commons.Action;
import com.itwillbs.commons.ActionForward;
import com.itwillbs.db.MypageDAO;
import com.itwillbs.db.ProductDAO;
import com.mysql.cj.xdevapi.Result;

public class AjaxProductDelAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {


		int product_id = Integer.parseInt(request.getParameter("product_id"));
		System.out.println("AjaxProductDelAction 실행");
		System.out.println("product_id: "+product_id);
		
		ProductDAO pdao = new ProductDAO();
		int result = pdao.productDelete(product_id);
		MypageDAO mdao = new MypageDAO();
		int result2 = mdao.mypageDelete(product_id);
		System.out.println("result 값 : "+result);
		System.out.println("result2 값 : "+result);
		
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.print("{\"success\": true}");
        out.close();
		return null;
		
	}

}
