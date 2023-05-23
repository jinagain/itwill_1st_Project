<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	
<!DOCTYPE html>
<html>
<head>
<%@ include file="../head.jsp"%>
<script src="https://cdn.jsdelivr.net/npm/jquery@3.6.4/dist/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/gh/hung1001/font-awesome-pro@4cac1a6/css/all.css" />
<meta charset="UTF-8"> 

	<style type="text/css">

.round-btn {
  border: none;
  border-radius: 50%;
  background-color: transparent;
  padding: 0px;
  transition: transform 0.2s ease-in-out;
  font-size: 1.5rem !important; 
  cursor: pointer;
  
  display: flex; /* 버튼을 flex container로 설정 */
  justify-content: center; /* 수평 가운데 정렬 */
  align-items: center; /* 수직 가운데 정렬 */
}

.round-btn.yellow {
  color: #ff6347;
}

.round-btn.green {
  color: #00cc44;
}

.round-btn:hover {
  transform: scale(1.2);
}

.round-btn:focus {
  outline: none;
}

#nttable * {
  font-size: 95%;
  text-align: left;
font-weight: bold !important;
  style="font-family: 'Font Awesome 5 Free';"
  
   padding-left: 100px;important;
}
#nttable th {
  width: 30%;
  font-weight: bold !important;
  
}



#nttable table td {
  width: 400px;
  height: 100px;
}

	</style>
		<script type="text/javascript">
		
		
		$(document).ready(function() {
		    $('.block-btn').change(function() {
		        var id = $(this).data('user-id');
		        var blocked = $(this).prop('checked');
		        $.ajax({
		            url: './AjaxBlockedAction.aj',
		            data: {id: id, blocked: blocked},
		            success: function(data) {
		                console.log(data);
		                location.reload();
		                alert('회원정보 변경 완료!');
		                
		            }
		        });
		    });
		    
		    

		    $('button[name="adminsaleList"]').click(function() {
		    	  var id = $(this).val();
		    	  window.open('./AdminsaleListAction.my?id=' + id, '_blank', 'width=800,height=600');
		    });

		    	$('button[name="adminbuyList"]').click(function() {
		    	  var id = $(this).val();
		    	  window.open('./AdminbuyListAction.my?id=' + id, '_blank', 'width=800,height=600');
		    });
		    	
		    	
		   // /AdminMemberInfo.my 	
		    	
		    
		});
		


		function openWindow(url, name, width, height) {
			  var left = (window.innerWidth - width) / 2;
			  var top = (window.innerHeight - height) / 2;
			  var options = 'width=' + width + ',height=' + height + ',top=' + top + ',left=' + left;
			  window.open(url, name, options);
			}

		</script>

<title>Insert title here</title>
</head>
<body>
	<%@ include file="../nav.jsp"%><!-- nav 삽입 -->
	<div class="row" style="margin-left: 100px;">
	<%@ include file="../mySide.jsp"%>
	
		 <!--   사이드바 -->
	 

  
 	 <div class="col-md-10">
 <div id="right" style="width: 80%">
 <h1 style="font-family: 'TheJamsil5Bold';">회 원 목 록</h1>
<hr style="border: 0;height: 3px; background-color: black;">

<!-- 검색창기능 -->
<br>
<div id="admin_search" class="button" style="margin-bottom:10px;">
	<form action="./MemberList.me" method="post">
		<select name="category" class="form-control" style="height:40px; width:120px; display:inline;" >
			<option value="title">아이디</option>
			<option value="content">전화번호</option>
		</select>
		<input type="text" name="search" class="form-control" style="height:40px; width:30%; display:inline;">
		<input type="image" src="./images/magnifying-glass-g1d4816a37_640.png" width="25px" style="vertical-align:middle; display:inline;">
	</form>
</div>
<br>
<!-- 검색창기능	 -->


	  <table class="table" id="nttable" style= "width: 100%"; align="center"; >
   <thead style="background-color: #F6F6F6;">
	   <tr>
	      <td>아이디</td>
<!-- 	      <td>비밀번호</td> -->
	      <td>이름</td>
<!-- 	      <td>닉네임</td> -->
	      <td>전화번호</td>
<!-- 	      <td>주소</td> -->
<!-- 	      <td>사진</td> -->
	      <td>가입일</td>
<!-- 	      <td>생년월일</td> -->
	      <td>회원상태</td>
	      <td>구매목록</td>
	      <td>판매목록</td>
	   </tr>
	</thead>
	<tbody> 
	   
	   <!-- rs(데이터) <- DTO <- List -->
	   <c:forEach var="dto" items="${requestScope.memberList }">
		   <tr>
		      <td>
<a href="#" onclick="openWindow('./AdminMemberInfo.my?id=${dto.id}', 'AdminMemberInfo', 500, 700); return false;"> <span style="color: #FFB609;">${dto.id}</span>
				</a>
				
				</td>
<%-- 		      <td>${dto.password }</td> --%>
		      <td>${dto.name }</td>
<%-- 		      <td>${dto.nickname }</td> --%>
		      <td>${dto.phone_number }</td>
<%-- 		      <td>${dto.address }</td> --%>
<%-- 		      <td>${dto.user_image }</td> --%>
		      <td>${dto.regdate }</td>
<%-- 		      <td>${dto.birth_date }</td> --%>
<td>
<c:if test="${!dto.withdrawal}">
    <label class="checkbox-container">
        <input type="checkbox" name="blocked" id="blocked" class="block-btn" value="false" style="transform: scale(1.5); margin-right: 5px" data-user-id="${dto.id}"
        <c:if test="${dto.blocked}">checked</c:if>
        >
        <span class="checkmark"></span>
    </label>
</c:if>
<c:if test="${dto.withdrawal}" >
    <span  style="margin: 0 20px; font-size: 13px" >탈퇴</span>
</c:if>
<c:if test="${!dto.withdrawal}">
    <c:if test="${dto.blocked}">
        <span style="color:red"> 차단 <i class="fas fa-ban"></i> </span>
    </c:if>
    <c:if test="${!dto.blocked}">
        <span style="color:green">정상 <i class="far fa-check-circle"></i></span>
    </c:if>
</c:if>
</td>
<td><button type="button" name="adminbuyList" value="${dto.id}" class="round-btn yellow"><i class="fas fa-shopping-cart"></i></button></td>
<td><button type="button" name="adminsaleList" value="${dto.id}" class="round-btn green"><i class="fas fa-shopping-bag"></i></button></td>
		   </tr>
	   	</c:forEach>
	</tbody>
</table>

		<div class="container" style="margin: auto;">
				  <ul class="pagination justify-content-center" id="pagination" style="margin-top: 0px;">
			  	<c:if test="${startPage > pageBlock }"> 
				<li class="page-item"><a class="page-link" href="./MemberList.me?pageNum=${startPage-pageBlock}&category=${category }&search=${search } "><sapn>이전</sapn></a></li>
				</c:if>
			   <c:forEach var="i" begin="${startPage }" end="${endPage }" step="1">
				<li class="page-item"><a class="page-link" href="./MemberList.me?pageNum=${i }&category=${category }&search=${search }"><span>${i }</span></a></li>
				</c:forEach>
			    <c:if test="${endPage<pageCount }">
				<li class="page-item"><a class="page-link" href="./MemberList.me?pageNum=${startPage+pageBlock}&category=${category }&search=${search } "><span>다음</span></a></li>
				</c:if>
			  </ul>
			 <br>
	  		</div>
			</div>
	</div>
<br>
<!--     <h2><a href="./Main.me">메인 페이지로</a></h2> -->

</div>

<%@ include file="../footer.jsp"%> <!-- footer 삽입 -->
</body>
</html>