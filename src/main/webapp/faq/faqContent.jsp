<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<script src="https://cdn.jsdelivr.net/npm/jquery@3.6.4/dist/jquery.min.js"></script>
<meta charset="UTF-8">
<title>CODELESS - 자주묻는질문 내용</title>
<%@include file="../head.jsp" %>
</head>
<body>
<%@include file="../nav.jsp" %>

  <div class="row" style="margin-left: 100px;">
 <!-- 사이드바 -->

 <%@ include file="../noSide.jsp"%>	
 <!--   사이드바 -->
 
 <div class="col-md-10">
 <div id="right" style="width: 80%">
 <h1 style="font-family: 'TheJamsil5Bold'; font-size:25px ;">${dto.title }</h1>
<hr style="border: 0;height: 3px; background-color: black;">

페이지번호 ${pageNum }
	<table class="table" id="nttable">
		   <tr>
		     <th style="width:94px; text-align: center;">FAQ번호</th>
		     <td>${dto.faq_id }</td>
		   </tr> 
		   <tr>
		     <td style="text-align: center;">카테고리</td>
		     <td>${dto.category }</td>
		   </tr> 
		   <tr>
		     <td style="text-align: center;">제목</td>
		     <td>${dto.title }</td>
		   </tr> 
		   <tr>
		     <td style="text-align: center;">내용</td>
		     <td style="white-space:pre-line; word-break:break-all;">${dto.content }</td>
		   </tr> 
		</table>
		
<div style= "float: right; margin-top: 80px;">

<c:if test="${id.equals('admin') }">
<a href="./FaqProUpdateAction.fa?faq_id=${dto.faq_id }&pageNum=${pageNum}" class="btn btn-primary">게시글 수정</a>
<a href="./FaqDelete.fa?faq_id=${dto.faq_id }&pageNum=${pageNum}" class="btn btn-primary">게시글 삭제</a>
</c:if>
<a href="./FaqList.fa?pageNum=${pageNum }" class="btn btn-secondary">목록으로</a>
</div>
</div>
</div>
</div>
<%@include file="../footer.jsp" %>
</body>
</html>
</body>
</html>