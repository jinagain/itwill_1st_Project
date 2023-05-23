package com.itwillbs.action.qna;

import java.io.File;
import java.io.PrintWriter;

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

public class QNAUpdateActionPro implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

			request.setCharacterEncoding("UTF-8");
			System.out.println("M : BoardUpdateProAction_execute 호출");
			ActionForward forward = new ActionForward();
			HttpSession session = request.getSession();
			String id = (String)session.getAttribute("id");
			String pageNum = request.getParameter("pageNum");
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
			
			
			
		
			// 전달되는값들 저장  (이름,비밀번호,제목, 내용, bno,pageNum)
			int bno = Integer.parseInt(multi.getParameter("bno"));
	
			// BoardDTO 객체 생성
			QnADTO qdto = new QnADTO();
			qdto.setBno(Integer.parseInt(multi.getParameter("bno")));
			qdto.setNickname(multi.getParameter("nickname"));
			qdto.setContent(multi.getParameter("content"));
			qdto.setTitle(multi.getParameter("title"));
			qdto.setImage(multi.getFilesystemName("image"));

			
			// BoardDAO 객체 생성 -> 정보 수정 메서드 updateBoard(dto)
			MypageDAO mdao = new MypageDAO();
			
			int result = mdao.updateQNA(qdto);
			request.setAttribute("qdto", qdto);
			System.out.println("M : DB 처리 완료후 결과  -> "+result);

			// 결과를 리턴받아서 처리 (1, 0 ,-1)
			// 페이지이동 (js)	
			
			// 페이지 출력준비 
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			
	        if(result == 1){ // 글하나 작성완료
	        	JSForward.alertAndMove(response, "글수정 성공", "./AdminQNAContent.qn?bno="+bno+"&pageNum="+pageNum);

	        }else{
	        	//에러 상황( 자바스크립트 / 페이지 이동 )
	        	JSForward.alertAndBack(response, "글 수정 실패!");

	        }
			
			return null;
		}

}