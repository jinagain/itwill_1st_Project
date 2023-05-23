package com.itwillbs.action.mypage;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.itwillbs.commons.Action;
import com.itwillbs.commons.ActionForward;
import com.itwillbs.db.MypageDAO;

public class LikedStatusAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
	     	
		
			String id = request.getParameter("id");
	        int product_id = Integer.parseInt(request.getParameter("product_id"));
	        
	        
	        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
	        System.out.println(id);
	        System.out.println(product_id);
	        
	        MypageDAO dao = new MypageDAO();
	        // DB에서 찜 상태 확인
	        boolean isLiked = dao.isProductLiked(id, product_id);

	        // JSON 형식으로 응답
	        response.setContentType("application/json");
	        response.setCharacterEncoding("UTF-8");
	        JSONObject jsonObject = new JSONObject();
	        jsonObject.put("isLiked", isLiked);
	        PrintWriter out = response.getWriter();
	        out.print(jsonObject.toString());
	        out.flush();
	        out.close();
	        
	        
		return null;
	}

}
