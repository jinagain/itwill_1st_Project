package com.itwillbs.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itwillbs.action.order.AddrChangeAction;
import com.itwillbs.action.product.ProductContentAction;
import com.itwillbs.action.product.ProductDeleteAction;
import com.itwillbs.action.product.ProductListAction;
import com.itwillbs.action.product.ProductSoldAction;
import com.itwillbs.action.product.ProductUpdateAction;
import com.itwillbs.action.product.ProductUpdateProAction;
import com.itwillbs.action.product.ProductWriteAction;
import com.itwillbs.action.product.SearchBoxAction;
import com.itwillbs.commons.Action;
import com.itwillbs.commons.ActionForward;

@WebServlet("*.pr")
public class ProductController extends HttpServlet{
	
	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 페이지 정보 전달방식에 상관없이 한번에 처리하는 메서드
		System.out.println("doProcess() 호출!");
		
		/*********1. 가상주소 계산*************/
		System.out.println("1. 가상주소 계산 - 시작");
		
		String requestURI = request.getRequestURI();
		System.out.println("requestURI : " + requestURI);
		String ctxPath = request.getContextPath();
		System.out.println("ctxPath :" + ctxPath);
		String command = requestURI.substring(ctxPath.length());
		System.out.println("command : " + command);
		
		System.out.println(" 1. 가상주소 계산 - 끝");
		/*********1. 가상주소 계산*************/
		
		/*********2. 가상주소 매핑*************/
		System.out.println();
		System.out.println(" 2. 가상주소 매핑 - 시작");
		
		Action action = null;
		ActionForward forward = null;
		
		if(command.equals("/ProductList.pr")) {
			System.out.println(" C : /ProductList.pr 호출 ");
			System.out.println(" C : DB사용o, 페이지 이동(패턴2)");

			action = new ProductListAction(); 
			try { 
				forward  = action.execute(request, response); 
			} catch (Exception e) {
				e.printStackTrace(); 
			}
		}else if (command.equals("/ProductWrite.pr")) {
			System.out.println(" C : /ProductWrite.pr 실행");
			System.out.println(" C : DB사용 X, view페이지로 이동O (패턴1)");

			forward = new ActionForward();
			forward.setPath("./product/productWrite.jsp");
			forward.setRedirect(false);
		}else if(command.equals("/ProductWriteAction.pr")) {
			System.out.println(" C : ProductWriteAction.pr 호출");
			System.out.println(" C : DB사용 O, 페이지 이동O (패턴2)");

			action = new ProductWriteAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/ProductContent.pr")) {
			System.out.println(" C : ProductContent.pr 호출");
			System.out.println(" C : DB사용 O, 페이지 이동O (패턴3)");
			action = new ProductContentAction();
			try {
				forward = action.execute(request, response);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/ProductUpdate.pr")) {
			System.out.println(" C : ProductUpdate.pr 호출");
			System.out.println(" C : DB사용 O, view이동&출력 (패턴3)");
			
//			action = new SearchBoxAction();
//			try {
//				action.execute(request, response);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
			
			action = new ProductUpdateAction();
			try {
				forward = action.execute(request, response);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/ProductUpdateProAction.pr")) {
			System.out.println(" C : ProductUpdateProAction.pr 호출");
			System.out.println(" C : DB사용 O, 페이지 이동 (패턴2)");

			action = new ProductUpdateProAction();
			try {
				forward = action.execute(request, response);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/ProductDeleteAction.pr")) {
			System.out.println(" C : /ProductDeleteAction.pr 호출");
			System.out.println(" C : DB사용O, 페이지 이동 (패턴2)");
			action = new ProductDeleteAction();
			try {
				forward = action.execute(request, response);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/ProductSoldAction.pr")) {
			System.out.println(" C : /ProductSoldAction.pr 호출");
			System.out.println(" C : DB사용O, 페이지 이동 (패턴2)");
			action = new ProductSoldAction();
			try {
				forward = action.execute(request, response);
			}catch (Exception e) {
				e.printStackTrace();
			}

		}else if (command.equals("/AddrChangeAction.pr")) {
			System.out.println("C : ./AddrChangeAction.pr 호출");
			
			// AddrChangeAction() 객체 생성
			action = new AddrChangeAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if (command.equals("/GetModelList.pr")) {
			System.out.println("C : ./GetModelList.pr 호출");
				
			// AddrChangeAction() 객체 생성
			action = new SearchBoxAction();
			try {
				action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		System.out.println(" 2. 가상주소 매핑 - 끝");
		/*********2. 가상주소 매핑*************/
		
		/*********3. 가상주소 이동*************/
		System.out.println(" 3. 가상주소 이동 - 시작 ");
		if(forward != null) { // 이동정보가 있을 때
			
			// 페이지 이동방식 - true
			if(forward.isRedirect()) {
				System.out.println(" C : sendRedirect방식 - " + forward.getPath() + "이동");
				response.sendRedirect(forward.getPath());
			}else {	// 페이지 이동방식 - false
				System.out.println(" C : forward방식 - " + forward.getPath() + "이동");
				
				RequestDispatcher dis = request.getRequestDispatcher(forward.getPath());
				dis.forward(request, response);
			}
			
		}
		
		System.out.println(" 3. 가상주소 이동 - 끝 ");
		/*********3. 가상주소 이동*************/
		System.out.println(" doProcess - 끝(컨트롤러 종료) ");
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doGET() 호출!");
		doProcess(request, response);
		
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doPOST() 호출!");
		doProcess(request, response);
	}

}
