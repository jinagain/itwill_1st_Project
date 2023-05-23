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


public class MypageQNABoardInsertAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1. 한글처리
				request.setCharacterEncoding("UTF-8");
				ActionForward forward = new ActionForward();
				HttpSession session = request.getSession();
				String id = (String)session.getAttribute("id");
				
				
				
				System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
				String uploadPath = request.getServletContext().getRealPath("/upload");
				File uploadDir = new File(uploadPath);
				if (!uploadDir.exists()) {
				    uploadDir.mkdirs(); // 디렉토리 생성
				}
				
				System.out.println(uploadPath);
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
				
				 System.out.println("안녕하세요!");
				 
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
				
				if(id == null ) {
					JSForward.alertAndMove(response, "잘못된 접근입니다!", "./MemberLogin.me");
					return forward;
				}
				


				//2.전달되는 파라미터 정보저장 ->MemberDTO생성
				QnADTO qdto = new QnADTO();
				qdto.setTitle(multi.getParameter("title"));
				qdto.setContent(multi.getParameter("content"));
				qdto.setImage(multi.getFilesystemName("image"));
				qdto.setId(multi.getParameter("id"));
				qdto.setNickname(multi.getParameter("nickname"));
				qdto.setQnaCategory(multi.getParameter("qna_category"));
				qdto.setIs_answered(Boolean.parseBoolean(multi.getParameter("isanswered")));
				String productIdParameter = multi.getParameter("product_id");
				if (productIdParameter != null) {
				    qdto.setProductId(Integer.parseInt(productIdParameter));
				} else {
				    // product_id가 null인 경우에 대한 처리
				    qdto.setProductId(0);
				}
//				mdto.setUserID(Integer.parseInt(request.getParameter("userID")));
//				mdto.setRe_Ref(Integer.parseInt(request.getParameter("re_ref")));
//				mdto.setRe_Lev(Integer.parseInt(request.getParameter("re_lev")));
//				mdto.setRe_Seq(Integer.parseInt(request.getParameter("re_seq")));
				
				

				//3.BoardDAO객체생성
				MypageDAO mdao = new MypageDAO();
				int result = mdao.insertQNABoard(qdto);
				request.setAttribute("mdto", mdto);
				request.setAttribute("qdto", qdto);
//				System.out.println(qdto);
				
				
				//4페이지 이동
				forward = new ActionForward();
				
		        if(result == 1){ // 글하나 작성완료
		        	JSForward.alertAndMove(response, "글작성 성공", "./UserQNAList.qn");
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