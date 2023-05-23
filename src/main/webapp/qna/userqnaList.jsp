<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.itwillbs.db.MypageDAO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.itwillbs.db.MemberDAO"%>
<%@page import="com.itwillbs.db.MemberDTO"%>
<%@page import="com.itwillbs.db.QnADTO"%>

<!DOCTYPE html>
<html>
<head>
<%@ include file="../head.jsp"%>
<script src="https://cdn.jsdelivr.net/npm/jquery@3.6.4/dist/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" />


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
	  color: #ffba5a;
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


   <c:if test="${ empty sessionScope.id }">
      <c:redirect url="./MemberLogin.me" />
   </c:if>



		
<div class=row style="margin-left: 100px;"> 
	 <!--   사이드바 -->
<%@ include file="../mySide.jsp"%> <!-- 사이드바 -->

	 <!--   사이드바 -->
	 
 <div class="col-md-10">
 <div id="right" style="width: 80%">
 <h1 style="font-family: 'TheJamsil5Bold';">1:1문의 질문목록</h1>
<hr style="border: 0;height: 3px; background-color: black;">



	
<%-- 	페이지번호 : ${pageNum }<br> --%>
<%-- 	아이디 : ${id }<br> --%>
<%-- 	${requestScope.boardList } --%>


<!-- 목록없음 문구 기능 추가 -->
<div style="text-align:center;">
    <c:choose>
        <c:when test="${empty requestScope.boardList}">
            <p class="fade-in">
	            <button class="like-btn">
	              <i class="fas fa-pen-nib"></i>   1:1문의 내역이 없습니다!<br>
	            </button>
            </p>
        </c:when>
        <c:otherwise>
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
		     <a href="./QNAContent.qn?bno=${qdto.bno }&pageNum=${pageNum}">${qdto.title }</a></td>
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
	  </c:otherwise>
	</c:choose>
		
	 </div>
	 </div>

		<div class="container" style="margin: auto;">
			  <ul class="pagination justify-content-center" id="pagination" style="margin-top: 50px;">
		  	<c:if test="${startPage > pageBlock }"> 
			<li class="page-item"><a class="page-link" href="./UserQNAList.qn?pageNum=${startPage-pageBlock} "><sapn>이전</sapn></a></li>
			</c:if>
		   <c:forEach var="i" begin="${startPage }" end="${endPage }" step="1">
			<li class="page-item"><a class="page-link" href="./UserQNAList.qn?pageNum=${i }"><span>${i }</span></a></li>
			</c:forEach>
		    <c:if test="${endPage<pageCount }">
			<li class="page-item"><a class="page-link" href="./UserQNAList.qn?pageNum=${startPage+pageBlock} "><span>다음</span></a></li>
			</c:if>
		  </ul>
		 
  		</div>
   
	<br>
</div>
 </div>
 <%@ include file="../footer.jsp"%> <!-- footer 삽입 -->
</body>
</html>