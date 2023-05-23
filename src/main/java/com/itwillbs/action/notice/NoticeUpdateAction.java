package com.itwillbs.action.notice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itwillbs.commons.Action;
import com.itwillbs.commons.ActionForward;
import com.itwillbs.db.MemberDAO;
import com.itwillbs.db.NoticeDAO;
import com.itwillbs.db.NoticeDTO;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class NoticeUpdateAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("M : NoticeUpdateAction_execute()메소드 호출!");
			
		request.setCharacterEncoding("UTF-8");
		
		//관리자 계정인지 확인 
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		System.out.println("현재 계정 : "+id);
		
		ActionForward forward = new ActionForward();
		
		if(id == null || !id.equals("admin")) {
			forward.setPath("./NoticeList.no");
			forward.setRedirect(true);
			return forward;
		}
		
		
		//notice_image 업로드
				String realpath = request.getRealPath("/upload");
				System.out.println("realpath: "+realpath);
				int maxSize = 10 * 1024 * 1024; //10MB
				MultipartRequest multupartRequest 
				= new MultipartRequest(
						request,
						realpath,
						maxSize,
						"UTF-8",
						new DefaultFileRenamePolicy()
						);
				System.out.println("파일업로드성공");
		
		String pageNum = multupartRequest.getParameter("pageNum");

		NoticeDTO dto = new NoticeDTO();
		dto.setNotice_id(Integer.parseInt(multupartRequest.getParameter("notice_id")));
		
		dto.setTitle(multupartRequest.getParameter("title"));
		dto.setContent(multupartRequest.getParameter("content"));
		dto.setNotice_image(multupartRequest.getFilesystemName("notice_image"));
		System.out.println("M : update 값 : "+dto);
		//공지사항 업데이트 메소드 호출 
		NoticeDAO dao = new NoticeDAO();
		
		dao.updateNotice(dto);
		
		//디비처리를 완료하고 페이지 이동 -> 티켓 가지고서
		forward = new ActionForward();
		forward.setPath("./NoticeContent.no?pageNum="+pageNum+"&notice_id="+multupartRequest.getParameter("notice_id")); 
		forward.setRedirect(true);
		System.out.println("M : 공지사항 수정 완료=> 공지사항 보기 페이지로 이동합니다.");
		
		
		
		
		return forward;
	}

}
