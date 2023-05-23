package com.itwillbs.action.member;

import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itwillbs.commons.Action;
import com.itwillbs.commons.ActionForward;
import com.itwillbs.commons.JSForward;
import com.itwillbs.db.MemberDAO;
import com.itwillbs.db.MemberDTO;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class MemberUpdateProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println(" M : MemberUpdateProAction_execute() 호출 ");

		// 세션정보 제어
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		
		ActionForward forward = new ActionForward();
		if(id == null ) {
			JSForward.alertAndMove(response, "잘못된 접근입니다!", "./MemberLogin.me");
			return forward;
		}
		
		// 전달된 정보 저장
		MemberDAO qdao = new MemberDAO();
		MemberDTO qdto = qdao.getMember(id);
		if(qdto == null) {
			JSForward.alertAndMove(response, "잘못된 접근입니다!", "./MemberLogin.me");
		}
		boolean blocked = qdto.getBlocked();
		if(blocked == true) {
			JSForward.alertAndBack(response, "잘못된 접근입니다!");
			return forward;
		}
		
		boolean withdrawal = qdto.getWithdrawal();
		if(withdrawal == true) {
			JSForward.alertAndBack(response, "잘못된 접근입니다!");
			return null;
		}

		// 한글처리
		request.setCharacterEncoding("UTF-8");
		
		//notice_image 업로드
				String realpath = request.getRealPath("/upload"); //deprecated -> 실무에선 context에 있는 realpath를 사용함
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
		
		
		// 전달정보(파라메터) 저장(DTO)
		MemberDTO dto = new MemberDTO();

		
		dto.setId(multupartRequest.getParameter("id"));
		dto.setAddress(multupartRequest.getParameter("address"));
		dto.setName(multupartRequest.getParameter("name"));
		dto.setNickname(multupartRequest.getParameter("nickname"));
		dto.setPhone_number(multupartRequest.getParameter("phone_number"));
		dto.setPassword(multupartRequest.getParameter("password"));
		dto.setRegdate(new Date(System.currentTimeMillis()));
		dto.setUser_image(multupartRequest.getFilesystemName("user_image"));
		dto.setBirth_date(multupartRequest.getParameter("birth_date"));
		dto.setAddress2(multupartRequest.getParameter("address2"));
		dto.setPost_number(Integer.parseInt(multupartRequest.getParameter("post_number")));
		
		System.out.println(dto);
		// DAO - 정보수정 메서드 호출 - memberUpdate(dto)
		MemberDAO dao = new MemberDAO();
		

		
		int result = dao.memberUpdate(dto);
		
		System.out.println(" M : 정보 수정결과 "+result);

		// 수정 처리 결과에 따른 페이지 이동 (JS)
		if(result == -1) { //아이디 없음
//			response.setContentType("text/html; charset=UTF-8");
//			PrintWriter out = response.getWriter();
//			out.print("<script>");
//			out.print(" alert('아이디 없음!'); ");
//			out.print(" history.back(); ");
//			out.print("</script>");
//			out.close();
			JSForward.alertAndBack(response, "아이디 없음!");
			return null;
		}
		if(result == 0) { // 비밀번호 오류
			// JS이동 처리 객체 동작 호출
			JSForward.alertAndBack(response, "수정 오류!");
			return null;
		}

		JSForward.alertAndMove(response, "수정완료!", "./MemberInfo.my");
		return null;
	}

}