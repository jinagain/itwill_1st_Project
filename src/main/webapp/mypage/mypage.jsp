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
</head>
<body>
<%@ include file="../nav.jsp"%><!-- nav 삽입 -->

	 <!--   사이드바 -->
<div class="col-sm-4">
 <div class="container" id="left">
  <br>
  <h4 onclick="location.href='./MemberMypage.my'"> 마이페이지 </h4>
  <hr style="border: 0;height: 1px; background-color: black;">
   <h5 onclick="location.href='./MemberInfo.my'"> 내정보 조회  </h5>
  <c:if test="${id eq 'admin@gmail.com'}">
	<h5 onclick="location.href='./MemberList.me'"> 회원관리  </h5>
 	<h5 onclick="location.href='./ProductList.me'"> 상품관리  </h5>
  	<h5 onclick="location.href='./AdminQNAList.qn'"> QnA 게시판  </h5>
  	</c:if>
	<c:if test="${!id.equals('admin@gmail.com')}">
		  <h5 onclick="location.href='./UserQNAList.qn'"> QnA 게시판  </h5>
		  <h5 onclick="location.href='./MypagePurchaseList.my'"> 구매목록  </h5>
		  <h5 onclick="location.href='./MypageSalesList.my'"> 판매목록  </h5> 
		  <h5 onclick="location.href='./LikeList.me'"> 찜목록  </h5>
		  <h5 onclick="location.href='./ChatBox.me'"> 채팅목록  </h5>
	</c:if>
  </div>
	</div>	
	
	 <!--   사이드바 -->
<div class="container" style="margin: auto; margin-bottom: 300px;">	 
<div class="col-sm-8" style="margin:auto;">
<div id="right" style="margin-left: 50px; width: 100%;">
 <h1 onclick="location.href='./MemberMypage.my'" style="font-family: 'TheJamsil5Bold';">마이페이지</h1>
<hr style="border: 0;height: 3px; background-color: black;">
 </div>
	</div>	</div>

	
<%@ include file="../footer.jsp"%> <!-- footer 삽입 -->
</body>
</html>