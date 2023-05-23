package com.itwillbs.action.product;

import java.sql.Date;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itwillbs.commons.Action;
import com.itwillbs.commons.ActionForward;
import com.itwillbs.commons.JSForward;
import com.itwillbs.db.MemberDAO;
import com.itwillbs.db.MemberDTO;
import com.itwillbs.db.ProductDAO;
import com.itwillbs.db.ProductDTO;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class ProductUpdateProAction  implements Action {
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println(" M : MemberUpdateProAction_execute() 호출");
		// 한글처리(인코딩)
		request.setCharacterEncoding("UTF-8");
		// 로그인 세션제어
		HttpSession session =  request.getSession();
		String id = (String)session.getAttribute("id");
		ActionForward forward = new ActionForward();
		if(id == null ) {
			forward.setPath("./MemberLogin.me");
			forward.setRedirect(true);
			return forward;
		}
		
		
		/*
		 *  차단 사용자 세션제어 시작
		 */
		MemberDAO mdao = new MemberDAO();
		MemberDTO mdto = mdao.getMember(id);
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
		// 파일업로드 + 상품정보(파라메터)

		// 업로드 폴더(가상경로)
		ServletContext ctx = request.getServletContext();
		String realPath = ctx.getRealPath("/upload/product");// 서버의 주소
		int maxSize = 10 * 1024 * 1024; // 10MB
		System.out.println(" M : "+realPath);

		// 파일업로드
		MultipartRequest multi 
		= new MultipartRequest(
				request,
				realPath,
				maxSize,
				"UTF-8",
				new DefaultFileRenamePolicy());

		System.out.println(" M : 상품 파일 업로드 성공! ");
		
		// 전달정보(파라메터) 저장(DTO)
		ProductDTO dto = new ProductDTO();
		System.out.println(multi.getParameter("title"));
		dto.setProduct_id(Integer.parseInt(multi.getParameter("product_id")));
		dto.setTitle(multi.getParameter("title"));
		dto.setModel(Integer.parseInt(multi.getParameter("model")));
		dto.setParts(multi.getParameter("parts"));
		dto.setContent(multi.getParameter("content"));
		dto.setPrice(Integer.parseInt(multi.getParameter("price")));
		dto.setGrade(Integer.parseInt(multi.getParameter("grade")));
		String address1 = multi.getParameter("address1");
		String address2	= multi.getParameter("address2");
		String city = address1 + " " + address2;
		dto.setCity(city);
		dto.setMethod(Integer.parseInt(multi.getParameter("method")));
		dto.setCharge(Integer.parseInt(multi.getParameter("charge")));
		dto.setFee(Integer.parseInt(multi.getParameter("fee")));
		dto.setUser_id(multi.getParameter("user_id"));
		dto.setReg_date(new Date(System.currentTimeMillis()));
		dto.setBrand(Integer.parseInt(multi.getParameter("brand")));
		dto.setColor(Integer.parseInt(multi.getParameter("color")));
		System.out.println(" P : " + dto);
		
		String product_image = multi.getFilesystemName("product_image1");
		product_image += ","+multi.getFilesystemName("product_image2");
		product_image += ","+multi.getFilesystemName("product_image3");
		product_image += ","+multi.getFilesystemName("product_image4");
		product_image += ","+multi.getFilesystemName("product_image5");
		
		System.out.println(" M : product_image : "+product_image);
		dto.setProduct_image(product_image);
		
		// DAO - 정보수정 메서드 호출 memberUpdate()
		ProductDAO dao = new ProductDAO();
		int result = dao.productUpdate(dto);
		
		System.out.println(" P : 정보 수정결과 " + result);
		// 수정 처리 결과에 따른 페이지 이동 (JS)
		// 삭제 결과에 따른 페이지 이동(JS)
		if(result == 1) {
			JSForward.alertAndMove(response, "상품 수정 성공!", "MypageSalesList.my");
			return null;
		} else {
			JSForward.alertAndBack(response, "상품 수정 실패");
			return null;
		}
	}
}