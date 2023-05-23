package com.itwillbs.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itwillbs.action.notice.NoticeContentAction;
import com.itwillbs.action.notice.NoticeDeleteAction;
import com.itwillbs.action.notice.NoticeListAction;
import com.itwillbs.action.notice.NoticeProUpdateAction;
import com.itwillbs.action.notice.NoticeUpdateAction;
import com.itwillbs.action.notice.NoticeWriteAction;
import com.itwillbs.commons.Action;
import com.itwillbs.commons.ActionForward;

@WebServlet("*.no")
public class NoticeController extends HttpServlet{
	
	
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
		
		
		// 공지사항
		if (command.equals("/NoticeList.no")) {
			/*
			 * 공지리스트 보기로 이동
			 * noticeListAcion으로 가서 먼저 처리한 다음에 페이지 이동처리를 해야 한다.
			 * DB사용 O, view 페이지 출력 O(패턴3) 
			 */
			action = new NoticeListAction();
			
			try {
				forward=action.execute(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if (command.equals("/NoticeWrite.no")) {
			//임시 이동 코드
			/*
			 * 공지사항 글쓰기 메소드 
			 * DB사용 X, 페이지 이동O(패턴1)
			 */
			forward = new ActionForward();
			forward.setPath("./notice/noticeWrite.jsp");
			forward.setRedirect(false);
			
		}else if(command.equals("/NoticeWriteAction.no")) {
			/*
			 * 공지사항 글쓰고 업데이트 하는 모델로 이동
			 * DB사용 O, 페이지 이동(패턴2)
			 */
			action = new NoticeWriteAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if (command.equals("/NoticeContent.no")) {
			//임시 이동 코드
			/*
			 * 게시판 글을 눌렀을 때 해당 글 페이지로 이동하는 동작
			 * DB사용 O, 페이지출력(패턴3)
			 */
			
			action = new NoticeContentAction();
			
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if (command.equals("/NoticeProUpdateAction.no")) {
			//임시 이동 코드
			/*
			 * 공지글 불러오기 액션(model)으로 이동하기 전 액션
			 * 공지글보기(view) -> 공지글불러오기(model) -> 공지글수정양식(view) -> 공지글업데이트(model) -> 공지글보기(view) 
			 * DB사용 O, 페지이 이동O, view페이지 출력 O(패턴3) 
			 */
			action= new NoticeProUpdateAction();
			
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if (command.equals("/NoticeUpdateAction.no")) {
			//임시 이동 코드
			/*
			 * noticeUpdate.jsp페이지에서 공지사항 수정을 완료하면 정보를 전달받아 
			 * 수정하는 액션으로 이동. 해당 페이지에서 수정을 업데이트하고 noticeList로 이동한다. 
			 * DB사용 O, 페이지 이동 O (패턴2)
			 */
			action = new NoticeUpdateAction();
			
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if (command.equals("/NoticeDelete.no")) {
			//임시 이동 코드 
			/*
			 * 공지사항 게시글 삭제 주소로 이동
			 * 삭제 액션페이지로 이동, 해당 페이지에서 삭제를 완료하고 noticeList페이지로 이동한다.  
			 * DB사용 O, 페이지이동 O (패턴2)
			 */
			action = new NoticeDeleteAction();
			
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(command.equals("/CompanyInfo.no")) {
			System.out.println("/CompanyInfo.no 호출!");
			forward = new ActionForward();
			forward.setPath("./notice/companyInfo.jsp");
			forward.setRedirect(false);
			
		}else if(command.equals("/PrivacyPolicy.no")) {
			
			forward = new ActionForward();
			forward.setPath("./notice/privacyPolicy.jsp");
			forward.setRedirect(false);
			
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
