package com.itwillbs.action.product;

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

public class ProductContentAction implements Action {

@Override
public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
	System.out.println("P : ProductContentAction_execute() 호출");
	
	// 세션정보 제어(로그인)
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
	
	// 상세 페이지를 보기 위해 상품번호를 가져옴
	int productId = Integer.parseInt(request.getParameter("product_id"));
	System.out.println("상품번호 : "+productId);
	
	ProductDAO dao = new ProductDAO();	// DAO 객체 생성
	ProductDTO product = dao.productContent(productId);// 상세 정보 가져오기

	// 조회수 증가
	dao.updateReadcount(productId);
	
	String city = product.getCity();
	String sido = "";
	int idxArea = 0;
	String[] area0 = {"시/도 선택","서울","인천","대전","광주","대구","울산","부산","경기","강원","충북","충남","전북","전남","경북","경남","제주"};
	String[][] area = {	{},
							{"강남구","강동구","강북구","강서구","관악구","광진구","구로구","금천구","노원구","도봉구","동대문구","동작구","마포구","서대문구","서초구","성동구","성북구","송파구","양천구","영등포구","용산구","은평구","종로구","중구","중랑구"},
							{"계양구","남구","남동구","동구","부평구","서구","연수구","중구","강화군","옹진군"},
							{"대덕구","동구","서구","유성구","중구"},
							{"광산구","남구","동구","북구","서구"},
							{"남구","달서구","동구","북구","서구","수성구","중구","달성군"},
							{"남구","동구","북구","중구","울주군"},
							{"강서구","금정구","남구","동구","동래구","부산진구","북구","사상구","사하구","서구","수영구","연제구","영도구","중구","해운대구","기장군"},
							{"고양시","과천시","광명시","광주시","구리시","군포시","김포시","남양주시","동두천시","부천시","성남시","수원시","시흥시","안산시","안성시","안양시","양주시","오산시","용인시","의왕시","의정부시","이천시","파주시","평택시","포천시","하남시","화성시","가평군","양평군","여주군","연천군"},
							{"강릉시","동해시","삼척시","속초시","원주시","춘천시","태백시","고성군","양구군","양양군","영월군","인제군","정선군","철원군","평창군","홍천군","화천군","횡성군"},
							{"제천시","청주시","충주시","괴산군","단양군","보은군","영동군","옥천군","음성군","증평군","진천군","청원군"},
							{"계룡시","공주시","논산시","보령시","서산시","아산시","천안시","금산군","당진군","부여군","서천군","연기군","예산군","청양군","태안군","홍성군"},
							{"군산시","김제시","남원시","익산시","전주시","정읍시","고창군","무주군","부안군","순창군","완주군","임실군","장수군","진안군"},
							{"광양시","나주시","목포시","순천시","여수시","강진군","고흥군","곡성군","구례군","담양군","무안군","보성군","신안군","영광군","영암군","완도군","장성군","장흥군","진도군","함평군","해남군","화순군"},
							{"경산시","경주시","구미시","김천시","문경시","상주시","안동시","영주시","영천시","포항시","고령군","군위군","봉화군","성주군","영덕군","영양군","예천군","울릉군","울진군","의성군","청도군","청송군","칠곡군"},
							{"거제시","김해시","마산시","밀양시","사천시","양산시","진주시","진해시","창원시","통영시","거창군","고성군","남해군","산청군","의령군","창녕군","하동군","함안군","함양군","합천군"},
							{"서귀포시","제주시","남제주군","북제주군"}
						};
	if(city != null) {
		for(int i=0; i<area0.length; i++) {
			if(city.indexOf(area0[i]) > -1) {
				sido = area0[i];
				idxArea = i;
				break;
			}
		}
		if(idxArea>0) {
			for(int i=0; i<area[idxArea].length; i++) {
				if(city.indexOf(area[idxArea][i]) > -1) {
					String gugun = area[idxArea][i];
					product.setCity(sido + " " + gugun);
					break;
				}
			}
		}
	}
	// 상세 정보 request에 저장
	request.setAttribute("product", product);
	
	System.out.println(product.toString());
	
	//브랜드,모델,컬러 변환
	int brandId = product.getBrand();
	int modelId = product.getModel();
	int colorId = product.getColor();
	String brandName = dao.getBrandName(brandId); // brandId를 사용하여 브랜드 이름 조회
	String modelName = dao.getModelName(modelId); // modelId를 사용하여 브랜드 이름 조회
	String colorName = dao.getColorName(colorId); // colorId를 사용하여 브랜드 이름 조회
	System.out.println("브랜드 이름: " + brandName);
	System.out.println("모델 이름: " + modelName);
	System.out.println("컬러 이름: " + colorName);
	
	// 액션 페이지에서 리퀘스트에 데이터 저장
	request.setAttribute("brandName", brandName);
	request.setAttribute("modelName", modelName);
	request.setAttribute("colorName", colorName);
	
	// 페이지 이동
	forward = new ActionForward();
	forward.setPath("./product/productContent.jsp");
	forward.setRedirect(false);

	System.out.println("P : 정보 조회 저장, 처리 끝");
	return forward;
	}
}