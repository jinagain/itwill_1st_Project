package com.itwillbs.commons;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

public class JSForward {
	// 자바스크립트 사용해서 페이지 이동
	
	// 1. alert 메세지 출력, 페이지 뒤로가기
	public static void alertAndBack(HttpServletResponse response, String msg) {
		try {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.print("<script>");
			out.print("alert('" + msg + "');");
			out.print("history.back();");
			out.print("</script>");
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	// 2. alert 메세지 출력, 원하는 위치로 이동
	public static void alertAndMove(HttpServletResponse response, String msg, String location) {
		try {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.print("<script>");
			out.print("alert('" + msg + "');");
			out.print("location.href='"+ location +"'");
			out.print("</script>");
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
