package com.itwillbs.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itwillbs.action.faq.FaqContentAction;
import com.itwillbs.action.faq.FaqDeleteAction;
import com.itwillbs.action.faq.FaqListAction;
import com.itwillbs.action.faq.FaqProUpdateAction;
import com.itwillbs.action.faq.FaqUpdateAction;
import com.itwillbs.action.faq.FaqWriteAction;
import com.itwillbs.commons.Action;
import com.itwillbs.commons.ActionForward;
@WebServlet("*.fa")
public class FaqController extends HttpServlet{
	
	
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
		
		// FAQ
		if (command.equals("/FaqList.fa")) {
			/*
			 * 자주묻는질문 리스트 보기로 이동
			 * faqListAcion으로 가서 먼저 처리한 다음에 페이지 이동처리를 해야 한다.
			 * DB사용 O, view 페이지 출력 O(패턴3) 
			 */
			action = new FaqListAction();
			
			try {
				forward=action.execute(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if (command.equals("/FaqWrite.fa")) {
			/*
			 * 자주묻는 질문 글쓰기 페이지 
			 * DB사용 X, 페이지 이동O(패턴1)
			 */
			forward = new ActionForward();
			forward.setPath("./faq/faqWrite.jsp");
			forward.setRedirect(false);
		}else if(command.equals("/FaqWriteAction.fa")) {
			/*
			 * 자주묻는질문 글쓰고 업데이트 하는 모델로 이동
			 * DB사용 O, 페이지 이동(패턴2)
			 */
			action = new FaqWriteAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if (command.equals("/FaqContent.fa")) {
			
			action = new FaqContentAction();
			
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			forward.setPath("./faq/faqContent.jsp");
			forward.setRedirect(false);
		}else if (command.equals("/FaqProUpdateAction.fa")) {
			//임시 이동 코드
			/*
			 * 자주묻는질문 글 불러오기 액션(model)으로 이동하기 전 액션
			 * 자주묻는질문 글보기(view) -> 자주묻는질문 글불러오기(model) -> 자주묻는질문 글수정양식(view) -> 자주묻는질문 글업데이트(model) -> 자주묻는질문 글보기(view) 
			 * DB사용 O, 페지이 이동O, view페이지 출력 O(패턴3) 
			 */
			action= new FaqProUpdateAction();
			
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if (command.equals("/FaqUpdateAction.fa")) {
			//임시 이동 코드
			/*
			 * faqUpdate.jsp페이지에서 자주묻는질문 사항 수정을 완료하면 정보를 전달받아 
			 * 수정하는 액션으로 이동. 해당 페이지에서 수정을 업데이트하고 faqList로 이동한다. 
			 * DB사용 O, 페이지 이동 O (패턴2)
			 */
			action = new FaqUpdateAction();
			
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if (command.equals("/FaqDelete.fa")) {
			//임시 이동 코드
			/*
			 * 자주묻는질문 사항 게시글 삭제 주소로 이동
			 * 삭제 액션페이지로 이동, 해당 페이지에서 삭제를 완료하고 faqList페이지로 이동한다.  
			 * DB사용 O, 페이지이동 O (패턴2)
			 */
			action = new FaqDeleteAction();
			
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
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
