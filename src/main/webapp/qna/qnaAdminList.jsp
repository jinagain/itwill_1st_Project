
<%@page import="com.itwillbs.db.MypageDAO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.itwillbs.db.MemberDAO"%>
<%@page import="com.itwillbs.db.MemberDTO"%>
<%@page import="com.itwillbs.db.QnADTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    
<!DOCTYPE html>
<html>
<head>
<%@ include file="../head.jsp"%>
<script src="https://cdn.jsdelivr.net/npm/jquery@3.6.4/dist/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>
<body>
<%@ include file="../nav.jsp"%><!-- nav 삽입 -->


<%-- 	페이지번호 : ${pageNum }<br> --%>
<%-- 	아이디 : ${id }<br> --%>
	


<div class=row style="margin-left: 100px;">	




<%@ include file="../mySide.jsp"%>	 <!--   사이드바 -->

	
 <div class="col-md-10">
 <div id="right" style="width: 80%">
 <h1 style="font-family: 'TheJamsil5Bold';">1:1문의 질문목록</h1>
<hr style="border: 0;height: 3px; background-color: black;">






	  <table class="table" id="nttable">
   <thead style="background-color: #F6F6F6;">
   <tr>
     <th>작성일</th>
     <th style="text-align: center;">제목</th>
     <th>작성자</th>
     <th>처리상태</th>
   </tr> 
	</thead>
	<tbody>
   <c:forEach var="qdto" items="${requestScope.boardList }">

   <tr>
     <td width="200px;">${qdto.regdate}<input type="hidden" name="regdate" value="${qdto.regdate}"></td>
     <td width="500px;" style="text-align: center;">
    <c:if test="${qdto.re_Lev > 0}">
    <img src="./qna/re.png" width="30" height="30"> 
	</c:if>
     <a href="./AdminQNAContent.qn?bno=${qdto.bno }&pageNum=${pageNum}">${qdto.title }</a></td>
     <td width="100px;">${qdto.nickname}</td> 
      
        <td>
        <c:if test="${qdto.re_Lev <1 }">
		  <c:choose>
		    <c:when test="${qdto.is_answered}">
		      <span style="color: green;">답변완료</span>
		    </c:when>
		    <c:otherwise>
		      <span style="color: red;">답변대기</span>
		    </c:otherwise>
		  </c:choose>
		  </c:if>
	</td>
	
   </tr>
	</c:forEach>
	</tbody>
</table>
	 </div>
	 </div>
		<div class="container" style="margin: auto;">
			  <ul class="pagination justify-content-center" id="pagination" style="margin-top: 20px;">
		  	<c:if test="${startPage > pageBlock }"> 
			<li class="page-item"><a class="page-link" href="./AdminQNAList.qn?pageNum=${startPage-pageBlock} "><sapn>이전</sapn></a></li>
			</c:if>
		   <c:forEach var="i" begin="${startPage }" end="${endPage }" step="1">
			<li class="page-item"><a class="page-link" href="./AdminQNAList.qn?pageNum=${i }"><span>${i }</span></a></li>
			</c:forEach>
		    <c:if test="${endPage<pageCount }">
			<li class="page-item"><a class="page-link" href="./AdminQNAList.qn?pageNum=${startPage+pageBlock} "><span>다음</span></a></li>
			</c:if>
		  </ul>
		 
  		</div>
    </div>
	<br>

  <%@ include file="../footer.jsp"%> <!-- footer 삽입 -->
</body>
</html>