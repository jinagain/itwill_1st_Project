<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://cdn.jsdelivr.net/npm/jquery@3.6.4/dist/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
<%@ include file="../head.jsp" %>
<style type="text/css">

span {
  color: #FFB609;
  font-weight: bold;
}

#nttable * {
  font-size: 95%;
  text-align: left;
  font-family: 'TheJamsil5Bold';
}
</style>


</head>

<body>

	<%@ include file="../nav.jsp"%><!-- nav 삽입 -->
	<br>
	<br>
	<br>
<h4 style="font-family: 'TheJamsil5Bold';" align="center">회원 :<span> ${requestScope.id}</span> 의 판매목록</h4>
<hr style="border: 0;height: 3px; background-color: gray;" width="95%";>


	<table class="table" style= "width: 95%"; align="center"; id="nttable">
	<thead style="background-color: #F6F6F6;">
			<tr>
		      <th>상품명</th>
		      <th>가격</th>
		      <th>판매자</th>
		      <th>구매자</th>
		      <th>주문서번호</th>
		      <th>결제일자</th>
		      <th>주문진행상태</th>
		   </tr>
		</thead>
	<tbody> 	     
		   
	   <!-- rs(데이터) <- DTO <- List -->
	   <c:forEach var="dto" items="${requestScope.saleList }">
		   <tr>
		      <td><a href="./ProductContent.pr?product_id=${dto.product_id }">${dto.title }</a></td>
		      <td>${dto.price }</td>
		      <td>${requestScope.id}</td>
		      <td>${dto.buyer_id }</td>
		      <td style="text-align: center;"><c:choose>
			    <c:when test="${dto.order_id ne 0}">
			        <!-- 값이 0이 아닌 경우에만 표시 -->
			        <p><a href="./OrderContent.or?product_id=${dto.product_id }&order_id=${dto.order_id }">${dto.order_id}</a></p>
			    </c:when>
			    <c:otherwise>
			        <p>-</p>
			    </c:otherwise>
			    </c:choose></td>
		      <td>${dto.order_date }</td>
		      <td>${dto.order_status }</td>

		   </tr>
	   	</c:forEach>
		</tbody>
</table>

<%-- <%@ include file="../footer.jsp"%> <!-- footer 삽입 --> --%>
</body>
</html>