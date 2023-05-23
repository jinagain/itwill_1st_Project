package com.itwillbs.action.product;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;

import com.itwillbs.commons.Action;
import com.itwillbs.commons.ActionForward;
import com.itwillbs.db.ModelDAO;

public class SearchBoxAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println(" M : SearchBoxAction_execute");
		
		ModelDAO dao = new ModelDAO();
		JSONArray modelList = dao.getModelList();
		JSONArray colorList = dao.getColorList();
		JSONArray arr = new JSONArray();
		arr.add( modelList);
		arr.add(colorList);

		String str = arr.toJSONString();
		PrintWriter out = response.getWriter();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		out.print(str);
		out.flush();
		System.out.println(str);
		
//		session.setAttribute("modelList", modelList);
//		session.setAttribute("colorList", colorList);	
		
		return null;
	}
}
