package com.itwillbs.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itwillbs.action.chat.ChatAction;
import com.itwillbs.action.chat.ChatBoxAction;
import com.itwillbs.action.chat.ChatListAction;
import com.itwillbs.action.chat.ChatSubmitAction;
import com.itwillbs.action.chat.ChatUnreadAction;
import com.itwillbs.commons.Action;
import com.itwillbs.commons.ActionForward;

@WebServlet("*.ch")
public class ChatController extends HttpServlet{
	
	
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
		

		if (command.equals("/ChatToSeller.ch")) {
			System.out.println(" C : /ChatToSeller.ch 실행");
			System.out.println(" C : DB사용 O, view페이지로 이동O (패턴2)");
			
			action = new ChatAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/ChatSubmitAction.ch")) {
			System.out.println(" C : ChatSubmitAction.ch 호출");
			System.out.println(" C : DB사용 O, 페이지 이동O (패턴3)");

			action = new ChatSubmitAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/ChatListAction.ch")) {
			System.out.println(" C : ChatListAction.ch 호출");
			System.out.println(" C : DB사용 O, 페이지 이동O (패턴3)");

			action = new ChatListAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}else if(command.equals("/ChatUnreadAction.ch")) {
			System.out.println(" C : ChatUnreadAction.ch 호출");
			System.out.println(" C : DB사용 O, 페이지 이동O (패턴3)");

			action = new ChatUnreadAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if (command.equals("/ChatBox.ch")) {
			System.out.println(" C : /ChatBox.ch 실행");
			System.out.println(" C : DB사용 X, view페이지로 이동O (패턴1)");
			
			forward = new ActionForward();
			forward.setPath("./chat/box.jsp");
			forward.setRedirect(false);
		}else if(command.equals("/ChatBoxAction.ch")) {
			System.out.println(" C : ChatBoxAction.ch 호출");
			System.out.println(" C : DB사용 O, 페이지 이동O (패턴3)");

			action = new ChatBoxAction();
			try {
				forward = action.execute(request, response);
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
