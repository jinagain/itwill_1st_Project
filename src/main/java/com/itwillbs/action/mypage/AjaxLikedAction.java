package com.itwillbs.action.mypage;

import java.io.PrintWriter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itwillbs.commons.Action;
import com.itwillbs.commons.ActionForward;
import com.itwillbs.commons.JSForward;
import com.itwillbs.db.LikeDAO;
import com.itwillbs.db.MemberDAO;

public class AjaxLikedAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		
		String id = (String) request.getParameter("id");
		int product_id = Integer.parseInt(request.getParameter("product_id"));
		
		String sessionId = session.getId();
		//쿠키에 세션아이디 저장
		Cookie cookie = new Cookie("JSESSIONID", sessionId);
		cookie.setMaxAge(-1); // 브라우저 종료 시 세션 ID 쿠키 삭제
		response.addCookie(cookie);
		
		System.out.println(id);
		System.out.println(product_id);
		
		LikeDAO ldao = new LikeDAO();
		ldao.Likeadd(id,product_id);
		ldao.Likecountadd(product_id);
		
        
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.print("{\"success\": true}");
        out.close();
		return null;
	}

}
