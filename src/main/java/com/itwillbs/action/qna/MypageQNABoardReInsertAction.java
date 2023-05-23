package com.itwillbs.action.qna;


import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itwillbs.commons.Action;
import com.itwillbs.commons.ActionForward;
import com.itwillbs.commons.JSForward;
import com.itwillbs.db.MemberDAO;
import com.itwillbs.db.MemberDTO;
import com.itwillbs.db.MypageDAO;
import com.itwillbs.db.QnADTO;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class MypageQNABoardReInsertAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1. 한글처리
				request.setCharacterEncoding("UTF-8");
				ActionForward forward = new ActionForward();
				HttpSession session = request.getSession();
				String id = (String)session.getAttribute("id");
				
				if(id == null) {
					JSForward.alertAndMove(response, "잘못된 접근입니다!", "./MemberLogin.me");
					return forward;
				}
				
				//  관리자 세션제어
				if(!id.equals("admin@gmail.com") && !id.equals("admin")) {
					JSForward.alertAndBack(response, "잘못된 접근입니다!");
					return forward;
				}
				
				/*
				 *  차단 사용자 세션제어 시작
				 */
				MemberDAO dao = new MemberDAO();
				MemberDTO mdto = dao.getMember(id);
				if(mdto == null) {
					JSForward.alertAndMove(response, "잘못된 접근입니다!", "./MemberLogin.me");
				}
				boolean blocked = mdto.getBlocked();
				if(blocked == true) {
					JSForward.alertAndBack(response, "잘못된 접근입니다!");
				}
				/*
				 *  차단 사용자 세션제어 끝
				 */
			
				String uploadPath = request.getServletContext().getRealPath("/upload");
				File uploadDir = new File(uploadPath);
				if (!uploadDir.exists()) {
				    uploadDir.mkdirs(); // 디렉토리 생성
				}
				 // => 톰켓에 저장되는 경로
				
				//  업로드 할 파일의 크기 10mb
				int maxSize = 10 * 1024 * 1024;

				// 파일업로드 
				MultipartRequest multi 
				           = new MultipartRequest(
				        		   request,
				        		   uploadPath,
				        		   maxSize,
				        		   "UTF-8",
				        		   new DefaultFileRenamePolicy()		        		   
				        		   );
				
				
				System.out.println("아아아ㅏ아아아아앙아ㅏ아아아앙아아ㅏ아아ㅏ");
				//2.전달되는 파라미터 정보저장 ->MemberDTO생성
				QnADTO qdto = new QnADTO();
				qdto.setTitle(multi.getParameter("title"));
				qdto.setContent(multi.getParameter("content"));
				qdto.setImage(multi.getFilesystemName("image"));
				qdto.setId(multi.getParameter("id"));
				qdto.setNickname(multi.getParameter("nickname"));
//				mdto.setUserID(Integer.parseInt(request.getParameter("userID")));
				//hidden 에서 가져온것 저장
				qdto.setBno(Integer.parseInt(multi.getParameter("bno")));
				qdto.setRe_Ref(Integer.parseInt(multi.getParameter("re_ref")));
				qdto.setRe_Lev(Integer.parseInt(multi.getParameter("re_lev")));
				qdto.setRe_Seq(Integer.parseInt(multi.getParameter("re_seq")));
				qdto.setQnaCategory(multi.getParameter("qna_category"));
				qdto.setIs_answered(Boolean.parseBoolean(multi.getParameter("isanswered")));
				System.out.println(qdto);
				//3.BoardDAO객체생성
				request.setAttribute("qdto", qdto);
				MypageDAO mdao = new MypageDAO();
				int result = mdao.reInsertBoard(qdto);
				
				
				//4페이지 이동
				forward = new ActionForward();
				
		        if(result == 1){ // 글하나 작성완료
		        	JSForward.alertAndMove(response, "글작성 성공", "./AdminQNAList.qn");
//		        	forward.setPath("./MypageQNAList.me");
//		        	forward.setRedirect(true);
		        }else{
		        	//에러 상황( 자바스크립트 / 페이지 이동 )
		        	JSForward.alertAndBack(response, "글 작성 실패!");
//		        	forward.setPath("./Main.me");
//		        	forward.setRedirect(true);
//		        	
		        }
				return forward;

	}
	

}