package com.itwillbs.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itwillbs.action.member.IdFindAction;
import com.itwillbs.action.member.KakaoLoginAction;
import com.itwillbs.action.member.MemberDeleteAction;
import com.itwillbs.action.member.MemberJoinAction;
import com.itwillbs.action.member.MemberListAction;
import com.itwillbs.action.member.MemberLoginAction;
import com.itwillbs.action.member.MemberLogoutAction;
import com.itwillbs.action.member.MemberUpdateAction;
import com.itwillbs.action.member.MemberUpdateProAction;
import com.itwillbs.action.member.PasswordUpdateAction;
import com.itwillbs.action.member.PwFindAction;
import com.itwillbs.commons.Action;
import com.itwillbs.commons.ActionForward;



@WebServlet("*.me")
public class MemberController extends HttpServlet{
	
	// http://localhost:8088/CodeLess/Main.me
	
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

		// 메인페이지
		if(command.equals("/Main.me")) {
		System.out.println(" C : Main.me 호출");
		System.out.println(" C : DB사용 X, 페이지 이동O (패턴1)");
		
		forward = new ActionForward();
		forward.setPath("./main.jsp");
		forward.setRedirect(false);
		}
		else if(command.equals("/KakaoLogin.me")) {
			System.out.println(" C : /KakaoLogin.me 호출");
			System.out.println(" C : DB사용O, view 페이지 출력 (패턴3)");
			
			action = new KakaoLoginAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}//카카오로그인
		
		// 로그인처리
		else if(command.equals("/MemberLogin.me")) { 
			System.out.println(" C : /MemberLogin.me 호출");
			System.out.println(" C : DB사용 X, view페이지 이동(패턴1)");
			
			forward = new ActionForward();
			forward.setPath("./member/loginForm.jsp");
			forward.setRedirect(false);
		}else if(command.equals("/MemberLoginAction.me")) {
			System.out.println(" C : MemberLoginAction.me 호출");
			System.out.println(" C : DB사용 O, 페이지 이동O (패턴2)");
			
			action = new MemberLoginAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/MemberLogout.me")) {
			System.out.println(" C : Logout.me 호출");
			System.out.println(" C : 처리작업, 페이지 이동O (패턴2)");
			
			action = new MemberLogoutAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/IdFind.me")) {
			//임시 이동 코드
			forward = new ActionForward();
			forward.setPath("./member/idFindForm.jsp");
			forward.setRedirect(false);
		}else if(command.equals("/PwFind.me")) {
			//임시 이동 코드
			forward = new ActionForward();
			forward.setPath("./member/pwFindForm.jsp");
			forward.setRedirect(false);
		}
		//아이디찾기 처리
		  else if(command.equals("/IdFindAction.me")) {
		  System.out.println(" C : /IdFindAction.me 호출 ");
		  System.out.println(" C : DB사용o, 페이지 이동(패턴2)");
		  
		    action = new IdFindAction(); 
		    try { 
		    	forward  = action.execute(request, response); 
		    } catch (Exception e) {
		    	 e.printStackTrace(); 
		    }
		    }
		//비밀번호찾기 처리
		  else if(command.equals("/PwFindAction.me")) {
		  System.out.println(" C : /PwFindAction.me 호출 ");
		  System.out.println(" C : DB사용o, 페이지 이동(패턴2)");
		  
		    action = new PwFindAction(); 
		    try { 
		    	forward  = action.execute(request, response); 
		    } catch (Exception e) {
		    	 e.printStackTrace(); 
		    }
		    }
		  else if(command.equals("/PwFindProAction.me")) {
			  System.out.println(" C : /PwFindProAction.me 호출 ");
			  System.out.println(" C : DB사용x, 페이지 이동");
				forward = new ActionForward();
				forward.setPath("./member/pwFindAction.jsp");
				forward.setRedirect(false);
		  }
		
		// 회원가입
		else if(command.equals("/MemberJoin.me")) {
				System.out.println(" C : /MemberJoin.me 실행");
				System.out.println(" C : DB사용 X, view페이지로 이동O (패턴1)");
				
	
				forward = new ActionForward();
				forward.setPath("./member/insertForm.jsp");
				forward.setRedirect(false);
				
		}else if(command.equals("/MemberJoinAction.me")) {
			System.out.println(" C : MemberJoinAction.me 호출");
			System.out.println(" C : DB사용 O, 페이지 이동O (패턴2)");
			
			action = new MemberJoinAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}else if(command.equals("/MemberUpdate.me")) {
			System.out.println(" C : MemberUpdate.me 호출");
			System.out.println(" C : DB사용 O, view이동&출력 (패턴3)");
			
			action = new MemberUpdateAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		}else if(command.equals("/MemberUpdateProAction.me")) {
			System.out.println(" C : MemberUpdateProAction.me 호출");
			System.out.println(" C : DB사용 O, 페이지 이동 (패턴2)");
			
			action = new MemberUpdateProAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}else if(command.equals("/MemberDelete.me")) {
			System.out.println(" C : /MemberDelete.me 호출");
			System.out.println(" C : DB사용X, view페이지 이동 (패턴1)");
			
			forward = new ActionForward();
			forward.setPath("./member/memberDelete.jsp");
			forward.setRedirect(false);
		}else if(command.equals("/MemberDeleteAction.me")) {
			System.out.println(" C : /MemberDeleteAction.me 호출");
			System.out.println(" C : DB사용O, 페이지 이동 (패턴2)");

			action = new MemberDeleteAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if (command.equals("/PasswordUpdate.me")) {
			//임시 이동 코드
			forward = new ActionForward();
			forward.setPath("./member/passwordUpdate.jsp");
			forward.setRedirect(false);
			
		}else if (command.equals("/PasswordUpdateAction.me")) {
			//임시 이동 코드
			action = new PasswordUpdateAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}else if (command.equals("/MemberList.me")) {
			System.out.println(" C : /MemberList.me 호출");
			System.out.println(" C : DB사용O, view 페이지 출력 (패턴3)");
			
			action = new MemberListAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
//		}else if (command.equals("/MypageQNAList.me")) {
//			//임시 이동 코드
//			forward = new ActionForward();
//			forward.setPath("./qna/qnaList.jsp");
//			forward.setRedirect(false);
//		}else if (command.equals("/MypageQNAList.me")) {
//			//임시 이동 코드
//			forward = new ActionForward();
//			forward.setPath("./qna/qnaList.jsp");
//			forward.setRedirect(false);
		
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
