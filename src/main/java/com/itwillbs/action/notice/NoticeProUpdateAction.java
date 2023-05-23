package com.itwillbs.action.notice;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itwillbs.commons.Action;
import com.itwillbs.commons.ActionForward;
import com.itwillbs.db.MemberDAO;
import com.itwillbs.db.NoticeDAO;
import com.itwillbs.db.NoticeDTO;
/**
 * 수정 전 처리 페이지 
 * 공지사항 업데이트를 하기 위해서 디비에서 정보를 불러오는 페이지 
 * 해당 페이지의 기능은 디비에서 정보를 불러와 수정페이지에 출력한다. 
 * @author YURAN
 *
 */
public class NoticeProUpdateAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("M : NoticeProUpdateAction_execute()메소드 호출!");
		
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
		
		
		String notice_id = request.getParameter("notice_id");
		System.out.println("공지글수정 페이지의 글번호 "+notice_id);
		String pageNum = request.getParameter("pageNum");
		
		NoticeDAO dao = new NoticeDAO();
		NoticeDTO dto = dao.getNoticeContent(notice_id);
		System.out.println("M : 공지사항 정보 불러오기 완료!");
		
		System.out.println("공지업데이트프로 액션의 "+dto);
		//정보 저장
		request.setAttribute("dto", dto);
		request.setAttribute("pageNum", pageNum);
				
		//저장한 정보를 ./member/updateForm.jps에서  출력
		forward.setPath("./notice/noticeUpdate.jsp");
		forward.setRedirect(false);
		
		
		
		return forward;
	}

}
