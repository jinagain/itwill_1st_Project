<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<script src="https://cdn.jsdelivr.net/npm/jquery@3.6.4/dist/jquery.min.js"></script>
<style type="text/css">

#category_search>button{
 width:33%; display:inline;"
 margin-bottom:10px;
 border:2px solid #ced4da;
 background-color:white;
 }
#category_search>button:active{
 width:33%; display:inline;"
 margin-bottom:10px;
 border:2px solid #ced4da;
 background-color:#F6F6F6;
 }
</style>

<link rel="stylesheet" href="../css/_custom.css" type="text/css"/>
<meta charset="UTF-8">
<title>CODE LESS - 자주묻는 질문</title>
<%@ include file="../head.jsp"%>
</head>
<body>
<%@ include file="../nav.jsp"%>



  <div class="row" style="margin-left: 100px;">
 <!-- 사이드바 -->
 <%@ include file="../noSide.jsp"%>		
  
 <!--   사이드바 -->
  
 <div class="col-md-10">
 <div id="right" style="width: 80%">
 <h1 style="font-family: 'TheJamsil5Bold';">자 주 묻 는 질 문</h1>
<hr style="border: 0;height: 3px; background-color: black;">

페이지번호 : ${pageNum }<br>
<!-- 검색창기능 -->
<div id="table_search" class="button" style="margin-bottom:10px;">
	<form action="./FaqList.fa" method="post">
		<select name="selecter" class="form-control" style="height:40px; width:120px; display:inline;" >
			<option value="title">제목</option>
			<option value="content">내용</option>
		</select>
		<input type="text" name="search" class="form-control" style="height:40px; width:30%; display:inline;"  placeholder="검색어를 입력해주세요">
		<input type="image" src="./images/magnifying-glass-g1d4816a37_640.png" width="25px" style="vertical-align:middle; display:inline;">
	</form>
</div>
<!-- 검색창기능 -->

<!-- 카테고리 기능 -->
<form action="./FaqList.fa" method="post">
<p id="category_search">
<button onclick="./FaqList.fa" class="btn btn-secondary" style="color:black;"  >전체</button>
<button name="category" value="이용정책" class="btn btn-secondary" style="color:black;">이용정책</button>
<button name="category" value="구매" class="btn btn-secondary" style="color:black;">구매</button>
<button name="category" value="판매" class="btn btn-secondary" style="color:black;">판매</button>
<button name="category" value="신고" class="btn btn-secondary" style="color:black;">신고</button>
<button name="category" value="기타" class="btn btn-secondary" style="color:black;">기타</button>
</p>

</form>


<!-- 카테고리 기능 -->

  <table class="table" id="nttable">
   <thead style="background-color: #F6F6F6;">
    <tr>
     <th style="text-align: center;">NO.</th>
     <th style="text-align: center;">카테고리</th>
     <th style="text-align: center;">제목</th>
   </tr> 
	</thead>
	<tbody>
   <c:forEach var="dto" items="${requestScope.faqList }">
   <tr>
     <td width="30px;"  style="text-align: center;">${dto.faq_id }<input type="hidden" name="faq_id" value="${dto.faq_id }"></td>
     <td width="100px;"  style="text-align: center;">${dto.category}</td>
      <td width="500px;" style="text-align: center;"><a href="./FaqContent.fa?pageNum=${pageNum}&faq_id=${dto.faq_id }">${dto.title }</a></td>
   </tr>
	</c:forEach>
</table>
<hr>
	<div class="container">
	  <ul class="pagination justify-content-center" id="pagination" style="margin-top: 70px;">
	<c:if test="${startPage > pageBlock }"> 
	<li class="page-item"><a class="page-link" a href="./FaqList.fa?pageNum=${startPage-pageBlock}&category=${category }&selecter=${selecter}&search=${search}"><sapn>이전</sapn></a></li>
	</c:if>

	<c:forEach var="i" begin="${startPage }" end="${endPage }" step="1">
	<li class="page-item"><a class="page-link" href="./FaqList.fa?pageNum=${i }&category=${category }&selecter=${selecter}&search=${search}"><span>${i }</span></a></li>
	</c:forEach>
	
	<c:if test="${endPage<pageCount }">
	<li class="page-item"><a class="page-link" href="./FaqList.fa?pageNum=${startPage+pageBlock}&category=${category }&selecter=${selecter}&search=${search}"><span>다음</span></a></li>
	</c:if>
  </ul>
  </div>	
<br>
<c:set var="category" value="${category }"/>
<c:set var="selecter" value="${selecter }"/>
<c:set var="search" value="${search }"/>
<c:set var="pageNum" value="${pageNum }"/>
<div style= "float: right;">

<!-- Faq 글쓰기는 관리자만 가능  -->
<c:if test="${id.equals('admin') }">
<button type="button" class="btn btn-primary" onclick="location.href='./FaqWrite.fa'">글쓰기</button>
</c:if>
<button type="button" class="btn btn-secondary" onclick="location.href='./Main.me'">메인으로</button>
</div>
</div>
</div>
</div>
<%@ include file="../footer.jsp"%>
</body>
</html>