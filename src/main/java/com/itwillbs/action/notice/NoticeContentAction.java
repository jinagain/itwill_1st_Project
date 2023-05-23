package com.itwillbs.action.notice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itwillbs.commons.Action;
import com.itwillbs.commons.ActionForward;
import com.itwillbs.commons.JSForward;
import com.itwillbs.db.MemberDAO;
import com.itwillbs.db.MemberDTO;
import com.itwillbs.db.NoticeDAO;
import com.itwillbs.db.NoticeDTO;

public class NoticeContentAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("M: NoticeContentAction_execute()메소드 호출!");
		request.setCharacterEncoding("UTF-8");

		String notice_id = request.getParameter("notice_id");
		String pageNum = request.getParameter("pageNum");
		String realpath = request.getRealPath("/upload");
		
		ActionForward forward = new ActionForward();
		
		NoticeDAO dao = new NoticeDAO();
		//글정보 불러오기 메소드 호출
		NoticeDTO dto = dao.getNoticeContent(notice_id);
		
		//글정보를 불러왔으니 조회수를 증가시켜야겠지 메소드 호출
		//그런데 같은 사람이면 조회증가하지 않아야겠지 로직
		HttpSession session = request.getSession();
		if (session.getAttribute(notice_id) == null) {
			    dao.updateReadCount(notice_id);
		}
		session.setAttribute(notice_id, true);
		
		request.setAttribute("dto", dto);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("realpath", realpath);
		
		forward.setPath("./notice/noticeContent.jsp");
		forward.setRedirect(false);
		
		
		
		
		
		
		return forward;
	}

}
