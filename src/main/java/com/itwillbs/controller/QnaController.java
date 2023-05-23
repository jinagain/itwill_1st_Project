package com.itwillbs.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itwillbs.action.qna.AdminQNAListAction;
import com.itwillbs.action.qna.AdminQNAcontentAction;
import com.itwillbs.action.qna.MypageQNABoardInsertAction;
import com.itwillbs.action.qna.MypageQNABoardReInsertAction;
import com.itwillbs.action.qna.QNADeleteAction;
import com.itwillbs.action.qna.QNAUpdateAction;
import com.itwillbs.action.qna.QNAUpdateActionPro;
import com.itwillbs.action.qna.UserQNADeleteAction;
import com.itwillbs.action.qna.UserQNAListAction;
import com.itwillbs.action.qna.UserQNAcontentAction;
import com.itwillbs.commons.Action;
import com.itwillbs.commons.ActionForward;

@WebServlet("*.qn")
public class QnaController extends HttpServlet{
	
	
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
		
	      // 마이페이지 QNA - ./MypageQNAInsertAction.me(1:1문의 글쓰기 실행)
	      if(command.equals("/MypageQNAInsertAction.qn")) {
	         System.out.println(" C : /MypageQNAInsertAction.qn 호출 ");
	         System.out.println(" C : DB사용 O, 페이지 이동O (패턴2) @@@@@@@@@@@@@@22@@@@@@@@@");
	         System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
	         // MypageQNABoardInsertAction 객체 생성
	         action = new MypageQNABoardInsertAction();
	         try {
	            forward = action.execute(request, response);
	         } catch (Exception e) {
	            e.printStackTrace();
	            }
	         }
	      //마이페이지 QNA - 리스트(관리자용) /AdminQNAList.me
	      else if(command.equals("/AdminQNAList.qn")) {
	         System.out.println(" C : /AdminQNAList.qn 호출 ");
	         System.out.println(" C : DB사용O,view페이지 출력(패턴3)");
	         // 페이지 이동
	         action = new AdminQNAListAction();
	         try {
	            forward = action.execute(request, response);
	         } catch (Exception e) {
	            e.printStackTrace();
	            }
	         }
	      // 마이페이지 QNA - 리스트(유저용) /UserQNAList.me
	      else if(command.equals("/UserQNAList.qn")) {
	         System.out.println(" C : /AdminQNAList.qn 호출 ");
	         System.out.println(" C : DB사용O,view페이지 출력(패턴3)");
	         // 페이지 이동
	         action = new UserQNAListAction();
	         try {
	            forward = action.execute(request, response);
	         } catch (Exception e) {
	            e.printStackTrace();
	            }
	         }
	         // QNAContent.me 글내용보기 - 유저용
	      else if(command.equals("/QNAContent.qn")) {
	         System.out.println(" C : /QNAContent.qn 호출 ");
	         System.out.println(" C : DB사용x,view페이지 이동(패턴1)");
	         // 페이지 이동
	         action = new UserQNAcontentAction();
	         try {
	            forward = action.execute(request, response);
	         } catch (Exception e) {
	            e.printStackTrace();
	            }
	         }
	         // AdminQNAContent.me 글내용보기 - 관리자용
	      else if(command.equals("/AdminQNAContent.qn")) {
	         System.out.println(" C : /QNAContent.me 호출 ");
	         System.out.println(" C : DB사용x,view페이지 이동(패턴1)");
	         // 페이지 이동
	         action = new AdminQNAcontentAction();
	         try {
	            forward = action.execute(request, response);
	         } catch (Exception e) {
	            e.printStackTrace();
	            }
	         }
	      //마이페이지QNA -  /QNAReWrite.me (1:1문의 답글쓰기)
	      else if(command.equals("/QNAReWrite.qn")) { // 글정보 입력
	         System.out.println(" C : /QNAReWrite.me ");
	         System.out.println(" C : DB사용 x, view페이지 이동 (패턴1)");
	         // 페이지 이동
	         forward = new ActionForward();
	         forward.setPath("./qna/qnaReWrite.jsp");
	         forward.setRedirect(false);
	      }
	      // 마이페이지 QNA ./QNAReWriteAction.me (1:1문의 답글쓰기 실행)
	      else if(command.equals("/QNAReWriteAction.qn")){
	         System.out.println("C : /QNAReWriteAction.me 호출");
	         action =  new MypageQNABoardReInsertAction();
	         try{ forward = action.execute(request, response);
	            }catch(Exception e){e.printStackTrace();
	         }
	      }
	      // 마이페이지 QNA  ./QNAUpdate.me 수정하기
	      else if(command.equals("/QNAUpdate.qn")){
	            System.out.println("컨트롤러 : BoardUpdate객체 생성 execute() 호출");
	            action = new QNAUpdateAction();
	            try{
	               forward = action.execute(request, response);
	            }catch(Exception e){
	               e.printStackTrace();
	            }
	      }else if(command.equals("/QNAUpdateAction.qn")){
	            System.out.println("C : /QNAUpdateAction.qn 호출");
	            //
	            action = new QNAUpdateActionPro();
	            try {
	               forward = action.execute(request, response);
	            } catch (Exception e) {
	               e.printStackTrace();
	            }
	         //글삭제 - 관리자용
	         }else if(command.equals("/QNADeleteAction.qn")){
	            System.out.println("C : /QNADeleteAction.qn 호출");
	            action = new QNADeleteAction();
	            try{ forward = action.execute(request, response);
	            }catch(Exception e){e.printStackTrace();
	            }
	         }
	         //글삭제 - 유저용 ./UserQNADeleteAction.me
	            else if(command.equals("/UserQNADeleteAction.qn")){
	               System.out.println("C : /UserQNADeleteAction.qn 호출");
	               action = new UserQNADeleteAction();
	               try{ forward = action.execute(request, response);
	               }catch(Exception e){e.printStackTrace();
	               }
	          }
	      //마이페이지QNA -  ./MypageQNAInsert.me(1:1문의 글쓰기)
		         else if(command.equals("/MypageQNAInsert.qn")) { // 글정보 입력
		         System.out.println(" C : /MypageQNAInsert.qn ");
		         System.out.println(" C : DB사용 x, view페이지 이동 (패턴1)");
		         // 페이지 이동
		         forward = new ActionForward();
		         forward.setPath("./qna/qnaWrite.jsp");
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
