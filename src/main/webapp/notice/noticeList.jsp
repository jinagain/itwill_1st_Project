<%@page import="java.util.List"%>
<%@page import="com.itwillbs.db.NoticeDTO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.itwillbs.db.MemberDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<script src="https://cdn.jsdelivr.net/npm/jquery@3.6.4/dist/jquery.min.js"></script>
<%@ include file="../head.jsp"%>
 <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

<meta charset="UTF-8">
<title>CODE LESS - 공지사항</title>
</head>
<body>
<%@ include file="../nav.jsp"%><!-- nav삽입 -->


  <div class="row" style="margin-left: 100px;">
 <!-- 사이드바 -->
 <%@ include file="../noSide.jsp"%>	
  
 <!--   사이드바 -->
  
 <div class="col-md-8">
 <div id="right">
 <h1 style="font-family: 'TheJamsil5Bold';">공 지 사 항</h1>
<hr style="border: 0;height: 3px; background-color: black;">

페이지번호 : ${pageNum }<br>
  <table class="table" id="nttable">
   <thead style="background-color: #F6F6F6;">
   <tr>
     <th style="text-align: center;">NO.</th>
     <th style="text-align: center;">제목</th>
     <th style="text-align: center;">게시일</th>
     <th style="text-align: center;">조회수</th>
   </tr> 
	</thead>
	<tbody>
   <c:forEach var="dto" items="${requestScope.noticeList }">
<!-- rs <- DTO <- List -->
   <tr>
     <td width="30px;" style="text-align: center;">${dto.notice_id }<input type="hidden" name="notice_id" value="${dto.notice_id }"></td>
     <td width="500px;" style="text-align: center;"><a href="./NoticeContent.no?notice_id=${dto.notice_id }&pageNum=${pageNum}">${dto.title }</a></td>
     <td width="200px;" style="text-align: center;">${dto.date}</td>
     <td width="100px;" style="text-align: center;">${dto.count }</td>
   </tr>
	</c:forEach>
	</tbody>
</table>

<!-- 검색창기능 -->
<div class="table" id="nttable">
	<form action="./NoticeList.no" method="post">
		<select name="category" class="form-control" style="width:120px; display:inline;">
			<option value="title">제목</option>
			<option value="content">내용</option>
		</select>
		<input type="text" name="search" class="form-control" style="width:30%; display:inline;" placeholder="검색어를 입력해주세요">
		<input type="image" src="./images/magnifying-glass-g1d4816a37_640.png" width="35px" style="vertical-align:middle; display:inline;">
	</form>
</div>
<!-- 검색창기능 -->
	
	<div class="container">
	  <ul class="pagination justify-content-center" id="pagination" style="margin-top: 70px;">
  	<c:if test="${startPage > pageBlock }"> 
	<li class="page-item"><a class="page-link" href="./NoticeList.no?pageNum=${startPage-pageBlock}&category=${category }&search=${search } "><sapn>이전</sapn></a></li>
	</c:if>
   <c:forEach var="i" begin="${startPage }" end="${endPage }" step="1">
	<li class="page-item"><a class="page-link" href="./NoticeList.no?pageNum=${i }&category=${category }&search=${search } "><span>${i }</span></a></li>
	</c:forEach>
    <c:if test="${endPage<pageCount }">
	<li class="page-item"><a class="page-link" href="./NoticeList.no?pageNum=${startPage+pageBlock}&category=${category }&search=${search } "><span>다음</span></a></li>
	</c:if>
  </ul>
  </div>
<br>
<c:set var="category" value="${category }"/>
<c:set var="search" value="${search }"/>
<c:set var="pageNum" value="${pageNum }"/>
<div style= "float: right;">

<!-- 관리자만 글작성할 수 있게 해주세요 -->
<c:if test="${id.equals('admin') }">
<button type="button" class="btn btn-primary" onclick="location.href='./NoticeWrite.no'">글작성</button>
</c:if>
<button type="button" class="btn btn-secondary" onclick="location.href='./Main.me'">메인으로</button>
</div>
</div>
</div>
</div>
<%@ include file="../footer.jsp"%>
</body>
</html>