<%@page import="com.itwillbs.db.ListDTO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.itwillbs.db.ListDAO"%>
<%@page import="com.itwillbs.action.mypage.BuyListAction"%>
<%@page import="com.itwillbs.db.MemberDAO"%>
<%@page import="com.itwillbs.db.MemberDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
    
<!DOCTYPE html>
<html>
<head>
<%@ include file="../head.jsp"%>
<meta charset="UTF-8">

<script src="https://cdn.jsdelivr.net/npm/jquery@3.6.4/dist/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/gh/hung1001/font-awesome-pro@4cac1a6/css/all.css" />

	<!-- 목록없음 문구에 적용되는 스타일 -->
	<style>

	.like-btn {
	  border: none;
	  background: none;
	  font-size: 1.2em;
	  transition: transform 0.3s ease-in-out;
	  border: none; /* 버튼 주변의 테두리 제거 */
 		  outline: none; /* 포커스 상자 제거 */
	}
	
	.like-btn i {
	  font-size: 1.5em;
	  color: #ff6347;
	  transition: color 0.3s ease-in-out;
	}
	
	.like-btn:hover {
	  transform: scale(1.1);
	}
			
    .fade-in {
        opacity: 0;
        margin-top: 250px;
        animation: fadeInAnimation 1s ease-in forwards;
    }

    @keyframes fadeInAnimation {
        0% {
            opacity: 0;
            transform: translateY(20px);
        }
        100% {
            opacity: 1;
            transform: translateY(0);
        }
    }
    
    
    .fade-in2 {
    opacity: 0;
    margin-top: 10px;
    animation: fadeInAnimation 1s ease-in forwards;
    }

    @keyframes fadeInAnimation {
        0% {
            opacity: 0;
            transform: translateY(20px);
        }
        100% {
            opacity: 1;
            transform: translateY(0);
        }
    }
    
    .fade-in2 td {
    padding: 10px 20px; /* 가로 간격을 조정할 수 있는 padding 값 설정 */
    }
    			
	</style>
	
</head>

<body>
<%@ include file="../nav.jsp"%><!-- nav 삽입 -->

<div class=row style="margin-left: 100px;"> 
<%@ include file="../mySide.jsp"%> <!-- 사이드바 -->

	<%
	ListDAO li = new ListDAO();
	String id = (String)session.getAttribute("id"); 
	ArrayList buyList = (ArrayList) li.getBuyList(id);
	%>

	 <div class="col-md-10">
 <div id="right" style="width: 80%">
 <h1 style="font-family: 'TheJamsil5Bold';">구 매 목 록</h1>
<hr style="border: 0;height: 3px; background-color: black;">

<!-- 목록없음 문구 기능 추가 -->
<div style="text-align:center; overflow-y: auto; height: 520px; ">
    <c:choose>
        <c:when test="${empty requestScope.buyList}">
            <p class="fade-in">
	            <button class="like-btn">
	              <i class="fas fa-shopping-cart"></i>   구매목록이 없습니다!<br>
	            </button>
            </p>
        </c:when>
        <c:otherwise>
          <div style="overflow-y: auto;">
		  <table class="table" id="nttable">
		   <thead style="background-color: #F6F6F6;">
		   <tr>
		     <th style="text-align: center;">제목</th>
		     <th style="text-align: center;">가격</th>
		     <th style="text-align: center;">판매자</th>
		     <th style="text-align: center;">주문서번호</th>
		     <th style="text-align: center;">결제일자</th>
		     <th style="text-align: center;">주문진행상태</th>
		   </tr> 
			</thead>
			<tbody>
		   <c:forEach var="dto" items="${requestScope.buyList }">
		<!-- rs <- DTO <- List -->
		   <tr>
		     <td width="600px;" style="text-align: center;">
		     <a href="./ProductContent.pr?product_id=${dto.product_id }">${dto.title }</a></td>
		     <td width="120px;" style="text-align: center;"><fmt:formatNumber value="${dto.price}" pattern="#,###"/></td>
		     <td width="140px;" style="text-align: center;">${dto.seller_id}</td>
		     <td width="220px;" style="text-align: center;"><a href="./OrderContent.or?product_id=${dto.product_id }&order_id=${dto.order_id }">${dto.order_id }</a></td>
		     <td width="220px;" style="text-align: center;">${dto.order_date}</td>
		     <td width="220px;" style="text-align: center;">${dto.order_status}</td>
		   </tr>
			</c:forEach>
			</tbody>
		 </table>
		</div>
		
       </c:otherwise>
    </c:choose>
   
</div>
</div>
</div>
 </div>
<%@ include file="../footer.jsp"%> <!-- footer 삽입 -->
</body>
</html>