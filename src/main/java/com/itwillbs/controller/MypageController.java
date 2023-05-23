package com.itwillbs.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itwillbs.action.member.MemberDeleteAction;
import com.itwillbs.action.member.MemberInfoAction;
import com.itwillbs.action.member.MemberListAction;
import com.itwillbs.action.member.MemberUpdateAction;
import com.itwillbs.action.member.MemberUpdateProAction;
import com.itwillbs.action.member.PasswordUpdateAction;
import com.itwillbs.action.mypage.AdminMemberInfoAction;
import com.itwillbs.action.mypage.AdminProductListAction;
import com.itwillbs.action.mypage.AdminbuyListAction;
import com.itwillbs.action.mypage.AdminsaleListAction;
import com.itwillbs.action.mypage.BuyListAction;
import com.itwillbs.action.mypage.LikeListAction;
import com.itwillbs.action.mypage.SaleListAction;
import com.itwillbs.commons.Action;
import com.itwillbs.commons.ActionForward;

@WebServlet("*.my")
public class MypageController extends HttpServlet{
	
	
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
		
		
		// 마이페이지
		if(command.equals("/MemberMypage.my")) {
			System.out.println(" C : /MemberMypage.me 실행");
			System.out.println(" C : DB사용 X, view페이지로 이동O (패턴1)");
			
			forward = new ActionForward();
			forward.setPath("./mypage/mypage.jsp");
			forward.setRedirect(false);
		}else if(command.equals("/MemberInfo.my")) {
			System.out.println(" C : MemberInfo.me 호출");
			System.out.println(" C : DB사용 O, view이동&출력 (패턴3)");
			
			action = new MemberInfoAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
				} 
			}
		
		/*
	       *
	       * 예원쓰 코드
	       *
	       *
	       * 마이페이지 구현 시작
	       */
	      // 마이페이지
	      else if(command.equals("/MemberMypage.me")) {
	         System.out.println(" C : /MemberMypage.me 실행");
	         System.out.println(" C : DB사용 X, view페이지로 이동O (패턴1)");
	         forward = new ActionForward();
	         forward.setPath("./mypage/mypage.jsp");
	         forward.setRedirect(false);
	      }else if(command.equals("/MemberInfo.me")) {
	         System.out.println(" C : MemberInfo.me 호출");
	         System.out.println(" C : DB사용 O, view이동&출력 (패턴3)");
	         action = new MemberInfoAction();
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
	         forward = new ActionForward();
	         forward.setPath("./member/passwordUpdate.jsp");
	         forward.setRedirect(false);
	      }else if (command.equals("/PasswordUpdateAction.me")) {
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
	         
	      
	       //마이페이지 관리자 회원관리 판매목록
		    }else if (command.equals("/AdminsaleListAction.my")) {
		        System.out.println(" C : /AdminsaleListAction.my");
		        System.out.println(" C : DB사용O, view 페이지 출력 (패턴3)");
		        action = new AdminsaleListAction();
		        try {
		           forward = action.execute(request, response);
		        } catch (Exception e) {
		           e.printStackTrace();
		         }
		        
		     }else if (command.equals("/AdminbuyListAction.my")) {
		        System.out.println(" C : /AdminbuyListAction.my");
		        System.out.println(" C : DB사용O, view 페이지 출력 (패턴3)");
		        action = new AdminbuyListAction();
		        try {
		           forward = action.execute(request, response);
		        } catch (Exception e) {
		           e.printStackTrace();
		         }
		     
		
		     } else if (command.equals("/AdminProductList.my")) {
			        System.out.println(" C : /AdminProductList.my");
			        System.out.println(" C : DB사용O, view 페이지 출력 (패턴3)");
//			        action = new AdminProductListAction();
			        action = new AdminProductListAction();
			        try {
			           forward = action.execute(request, response);
			        } catch (Exception e) {
			           e.printStackTrace();
			         }
			     }
			    else if (command.equals("/AdminMemberInfo.my")) {
			        System.out.println(" C : /AdminMemberInfo.my");
			        System.out.println(" C : DB사용O, view 페이지 출력 (패턴3)");
			        action = new AdminMemberInfoAction();
			        try {
			           forward = action.execute(request, response);
			        } catch (Exception e) {
			           e.printStackTrace();
			         }
			     }
						
			
			
			
			
		//--------------------- 소연쓰 --------------------------------	
		// 구매목록
		else if (command.equals("/MypagePurchaseList.my")) {
			System.out.println(" C : /MypagePurchaseList.my 호출 ");

			action = new BuyListAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// 판매목록
		else if(command.equals("/MypageSalesList.my")) {
			System.out.println(" C : /MypageSaleslist.my 호출 ");

			action = new SaleListAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		//찜목록
		else if(command.equals("/LikeList.my")) {
			System.out.println(" C : /LikeList.my 호출 ");

			action = new LikeListAction();
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
