package com.itwillbs.action.notice;

import java.net.URLEncoder;
import java.util.Date;

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

public class NoticeWriteAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("M : NoticeWriteAciton_execute() 호출!");
		
		//한글처리 
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
		
		
		//noticeDTO객체에 전달받은 공지사항 내용을 전달받아 초기화
		NoticeDTO dto = new NoticeDTO();
		
		dto.setTitle(multupartRequest.getParameter("title"));
		//글쓰기 날짜는 현재 날짜와 시간으로 지정
		dto.setDate(new Date(System.currentTimeMillis()));
		dto.setCount(0);
		dto.setNotice_image(multupartRequest.getFilesystemName("notice_image"));
		dto.setContent(multupartRequest.getParameter("content"));
		System.out.println(dto);
		//MemberDAO의 공지사항 쓰기 메소드 호출
		NoticeDAO dao = new NoticeDAO();
		dao.noticeWrite(dto);
		
		//디비처리를 완료하고 페이지 이동 -> 티켓 가지고서
		forward = new ActionForward();
		forward.setPath("./NoticeList.no"); //공지사항 쓴 뒤에는 공지 보기 페이지로 이동한다. 
		forward.setRedirect(true);
		System.out.println("M : 공지사항 쓰기 완료=> 공지사항 보기 페이지로 이동합니다.");
		
		return forward;
	}

}