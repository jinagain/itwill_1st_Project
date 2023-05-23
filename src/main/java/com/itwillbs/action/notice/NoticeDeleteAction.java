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

public class NoticeDeleteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("M : NoticeDeleteAction_execute()메소드 호출! ");
		
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
		
		String pageNum = request.getParameter("pageNum");
		
		NoticeDAO dao = new NoticeDAO();
		NoticeDTO dto = new NoticeDTO();
		dto.setNotice_id(Integer.parseInt(request.getParameter("notice_id")));
		int result = dao.deleteNotice(dto);
		
		if(result ==1) {
			JSForward.alertAndMove(response, "게시글 삭제 성공!", "./NoticeList.no?pageNum="+pageNum);
		}
		
		
		return null;
	}

}
