<%@page import="com.itwillbs.commons.JSForward"%>
<%@page import="com.itwillbs.db.MemberDTO"%>
<%@page import="com.itwillbs.db.MemberDAO"%>
<%@page import="com.itwillbs.db.QnADTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="../head.jsp"%>
<meta charset="UTF-8">
<script src="https://cdn.jsdelivr.net/npm/jquery@3.6.4/dist/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
<script>

  function toggleTextArea(selectElement) {
    var category = selectElement.value;
    var contentTextarea = document.getElementById('contentTextarea');
    var orderRelatedTemplate = `■ 상품관련 문의시 하단양식을 꼭 작성해 주세요 ■ 

    	주문자 아이디 : ${sessionScope.id}
    	상품 번호 : ${param.product_id}
    	문의 내용 :`;
	var reportRelatedTemplate = `■ 신고관련 문의시 하단양식을 꼭 작성해 주세요 ■ 

    	주문자 아이디 : ${sessionScope.id}
    	상품 번호 : ${param.product_id}
    	문의 내용 :`;
    var checkRelatedTemplate = `■ 결제관련 문의시 하단양식을 꼭 작성해 주세요 ■ 

    	주문자 아이디 : ${sessionScope.id}
    	상품 번호 : ${param.product_id}
    	문의 내용 :`;

    	
    switch (category) {
      case '회원정보':
    	  contentTextarea.value = '';
          contentTextarea.placeholder = '회원정보에 관한 문의 내용을 작성해 주세요.';
        break;
      case '상품':
        contentTextarea.value = orderRelatedTemplate;
        break;
      case '신고':
        contentTextarea.value = reportRelatedTemplate;
        break;
      case '결제':
        contentTextarea.value = checkRelatedTemplate;
        break;
      case '기타':
    	  contentTextarea.value = '';
          contentTextarea.placeholder = '기타 문의 내용을 작성해 주세요.';
        break;
      default:
        contentTextarea.value = '';
    }
  }
  
  document.addEventListener('DOMContentLoaded', function() {
	    var categorySelect = document.getElementsByName('qna_category')[0];
	    toggleTextArea(categorySelect);
	  });
  
</script>

</head>
<body>



<%
String id = (String)session.getAttribute("id");
MemberDAO mdao = new MemberDAO();
MemberDTO mdto = mdao.getMember(id);
if(mdto == null) {
	JSForward.alertAndMove(response, "잘못된 접근입니다!", "./MemberLogin.me");
}
String nickname = mdto.getNickname();
boolean blocked = mdto.getBlocked();
//차단사용자 세션제어
if(blocked == true) {
	JSForward.alertAndBack(response, "잘못된 접근입니다!");
}

%>
<%@ include file="../nav.jsp"%><!-- nav 삽입 -->
<div class="row" style="margin-left: 100px;">
<%@ include file="../mySide.jsp"%> <!-- 사이드바 -->
	
<!-- 		로그인 세션제어 -->
		<c:if test="${ empty sessionScope.id }">
		   <c:redirect url="./MemberLogin.me"/>
		</c:if>
					
	 
 	 <div class="col-md-10">
 <div id="right" style="width: 80%">
 <h1 style="font-family: 'TheJamsil5Bold';">1 : 1 문 의 글 작 성</h1>
<hr style="border: 0;height: 3px; background-color: black;">



	<fieldset>
  <form action="./MypageQNAInsertAction.qn" method="post" enctype="multipart/form-data">
    <input type="hidden" name="id" value="${sessionScope.id}"><br>
    <c:if test="${not empty param.product_id}">
      <input type="hidden" name="product_id" value="${param.product_id}">
    </c:if>
    <input type="hidden" name="isanswered" value="${qdto.is_answered}">
    <select name="qna_category" onchange="toggleTextArea(this)">
      <c:choose>
        <c:when test="${param.product_id ne null}">
          <option value="신고" selected>신고</option>
        </c:when>
        <c:otherwise>
          <option value="회원정보" selected>회원정보</option>
          <option value="상품">상품</option>
          <option value="신고">신고</option>
          <option value="결제">결제</option>
          <option value="기타">기타</option>
        </c:otherwise>
      </c:choose>
    </select>
    작성자(닉네임) : <%=nickname %><input type="hidden" name="nickname" value="<%=nickname %>" readonly><br>
    <label>글제목
      <input type="text" name="title" placeholder="제목을 입력해 주세요." class="form-control" size="110" required="required">
    </label>
    <br>
    <label>글 내용<br>
      <textarea name="content" id="contentTextarea" cols="110px;" rows="10px;" class="form-control" maxlength="700"></textarea>
    </label>
    
		<span style="font-size: 14px; color: #FFBF00;"><i class="fa fa-exclamation-triangle"></i> 사이트 이용관련(주문,취소,배송 등) 각종궁금하신 사항은 
		[고객센터] - [자주하는질문] 으로 들어가시면 자세하게 답변이 남겨져있으니 참고 바랍니다.</span><br>
    <c:if test="${param.product_id ne null}">
      <a href="./ProductContent.pr?product_id=${param.product_id}" style="color: red">신고된 상품링크로 가기 상품번호 : ${param.product_id}번</a>
    </c:if>
    <br>
    <label>첨부 이미지
      <input type="file" name="image" class="form-control">
    </label>
    <div style="float: right; margin-top: 30px;">
      <input type="submit" value="글쓰기" class="btn btn-primary">
			</div>	
		   </form>		
		</fieldset>
	</div>	

</div>	

</div>



<%@ include file="../footer.jsp"%> <!-- footer 삽입 -->
</body>
</html>