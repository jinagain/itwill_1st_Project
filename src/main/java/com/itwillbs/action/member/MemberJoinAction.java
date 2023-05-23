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

// 회원가입 처리(인코딩,정보저장,디비연결,페이지이동)
public class MemberJoinAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println(" M : MemberJoinAction_execute() 실행");
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
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
		
		
		ActionForward forward = new ActionForward();
		// MemberDTO 객체 생성
		MemberDTO dto = new MemberDTO();
		// 전달된 정보 저장
		/*
		 * MemberDAO qdao = new MemberDAO(); MemberDTO qdto = qdao.getMember(id);
		 * boolean blocked = qdto.getBlocked(); if(blocked == true) {
		 * JSForward.alertAndBack(response, "잘못된 접근입니다!"); return forward; }
		 */
		
		
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
		dto.setEmail(multupartRequest.getParameter("email"));
		dto.setPost_number(Integer.parseInt(multupartRequest.getParameter("post_number")));
		dto.setEmail(multupartRequest.getParameter("email"));
		
		
		
		System.out.println(" M : "+dto);
		
		// MemberDAO 객체 생성
		MemberDAO dao = new MemberDAO();
		// 회원가입 메서드
		dao.memberJoin(dto);
		
		// 페이지 이동
		JSForward.alertAndMove(response, "회원가입이 완료되었습니다.", "./MemberLogin.me");
		
		System.out.println(" M : 데이터 처리완료! 티켓 가지고 이동");
		
		return null;
	}
	
	
}