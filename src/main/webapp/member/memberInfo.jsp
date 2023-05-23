
<%@page import="com.itwillbs.db.MemberDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<script src="https://cdn.jsdelivr.net/npm/jquery@3.6.4/dist/jquery.min.js"></script>
	<head>
<!-- Bootstrap 5 CSS -->
<link rel="stylesheet" href="path/to/bootstrap.min.css">

<!-- Bootstrap 5 JavaScript -->
<script src="path/to/bootstrap.min.js"></script>
	
	<script type="text/javascript">
	
	  function openModal() {
		    var modal = document.getElementById("myModal");
		    modal.style.display = "block";
		  }

		  function closeModal() {
		    var modal = document.getElementById("myModal");
		    modal.style.display = "none";
		  }

		  // When the user clicks anywhere outside of the modal, close it
		  window.onclick = function(event) {
		    var modal = document.getElementById("myModal");
		    if (event.target == modal) {
		      closeModal();
		    }
	 }

	</script>
	
	<%@ include file="../head.jsp" %>
	
		<meta charset="UTF-8">
	<style>
    .profile-img {
        display: inline-block;
        width: 150px;
        height: 150px;
        border-radius: 50%;
        overflow: hidden;
        margin-right: 10px;
    }

    .profile-img img {
        width: 100%;
        height: 100%;
        object-fit: cover;
    }

    .profile-img img:hover {
        cursor: pointer;
        transform: scale(1.1);
        transition: transform 0.2s ease-in-out;
    }
.modal {
  display: none;
  position: fixed;
  z-index: 1;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
}

.modal-content {
  display: block;
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  max-width: 30%;
  max-height: 50%;
  overflow: auto;
}

.modal-content img {
  width: 100%;
  height: auto;
  display: block;
}

.close {
  position: absolute;
  top: 10px;
  right: 10px;
  font-size: 24px;
  font-weight: bold;
  color: white;
  z-index: 9999;
  cursor: pointer;
}

.close:hover,
.close:focus {
  color: #bbb;
  text-decoration: none;
  cursor: pointer;
}
</style>
	</head>
	<body>
<!-- 		<h1>memberInfo.jsp(MVC)</h1> -->
		
<!-- 		<h2> 회원가입시 입력한 정보를 화면에 출력</h2> -->
		
		<%@ include file="../nav.jsp"%><!-- nav 삽입 -->

		<c:if test="${ empty sessionScope.id }">
		   <c:redirect url="./MemberLogin.me"/>
		</c:if>
	<div class="row" style="margin-left: 100px;">
<%@ include file="../mySide.jsp"%>


<!--  사이드바 -->
			

	 <div class="col-md-10">
	 

	<div class="container" id="login-con"
		style="width: 900px; color: black; margin: 100px 100px;">
		
		<form action="./MemberUpdateProAction.me" id="fr" method="post">
			<div class="form-group" id="log-form"style="width: 400px;">
		

		<div style="text-align: center; background-color: white;">
		 <h1 style="font-family: 'TheJamsil5Bold';">내정보 보기</h1>
		  <c:choose>
		    <c:when test="${empty dto.user_image}">
		      <img src="./member/userimg.png" alt="프로필사진 " style="max-width: 200px; max-height: 200px; border-radius: 50%; cursor: pointer;" onclick="openModal()">
		    </c:when>
		    <c:otherwise>
		      <img src="./upload/${dto.user_image}" alt="프로필사진" style="max-width: 200px; max-height: 200px; border-radius: 50%; cursor: pointer;" onclick="openModal()">
		    </c:otherwise>
		  </c:choose>
		</div>
		
		<c:choose>
		<c:when test="${empty dto.user_image}">
		<!-- The Modal -->
		<div id="myModal" class="modal">
		  <span class="close" onclick="closeModal()">&times;</span>
		  <img class="modal-content" src="./member/userimg.png" alt="프로필사진">
		</div>
		 </c:when>
		<c:otherwise>	
				<div id="myModal" class="modal">
		  <span class="close" onclick="closeModal()">&times;</span>
		  <img class="modal-content" src="./upload/${dto.user_image}" alt="프로필사진">
		</div>
		</c:otherwise>
	 </c:choose>			
		<br> <br>
				

				아이디 <input type="email" class="form-control" id="id"
					value=${requestScope.dto.id } name="id" readonly> <br> 
					
				
				이름 <input type="text" class="form-control" name="name" id="name"  value="${dto.name }" readonly>
					 <br> 
				이메일 <input type="text" class="form-control" name="email" id="name"  value="${dto.email }" readonly>
					 <br> 
				닉네임 <input type="text" class="form-control" name="nickname" id="nickname" 
						value="${dto.nickname }" readonly>	
				<span id="nickmsg" ></span>
					 <br> 
				
				연락처 <input type="text" class="form-control"
					name="phone_number" placeholder="010-0000-0000 형태로 입력해주세요." id="phone_number"
					value="${dto.phone_number }" readonly>
					 <br> 
				
				생년월일 <input type="date" class="form-control"
					name="birth_date" id="birth_date" value="${dto.birth_date }" readonly="readonly"> 
					<br>
			 	
			 	주소
				<table>
					<tr>
						<td><input type="text" name="post_number" id="zipcode" size="15" value="${dto.post_number }" readonly ></td>
					</tr>
					<tr>
						<td><input type="text" name="address" id="address" size="45" onclick="addr();" value="${dto.address}" readonly></td>
					<tr>
					<tr>
						<td><input type="text" name="address2" id="address2" size="45" readonly value="${dto.address2}"></td>
					<tr>
				</table>
				


		
		
      	<a href="./MemberUpdate.me" class="btn btn-primary btn-block"
										style="margin-top: 40px;" >
										내정보 수정 하기</a>

		<a href="./PasswordUpdate.me" class="btn btn-primary btn-block"
										style="margin-top: 40px;" >비밀번호 변경</a> 
										
		<a href="./MemberDelete.me" class="btn btn-secondary btn-block"
									style="margin-top: 40px;" >코드리스와 이별하기</a>  <br>
			</div>
			</form>
</div></div>
			
</div>

<script src="js/jquery-3.3.1.min.js"></script>
<script src="js/jquery-migrate-3.0.1.min.js"></script>
<script src="js/popper.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/owl.carousel.min.js"></script>
<script src="js/jquery.stellar.min.js"></script>
<script src="js/jquery.fancybox.min.js"></script>


<script src="js/aos.js"></script>

<script src="js/bootstrap-datepicker.js"></script>
<script src="js/jquery.timepicker.min.js"></script>

<script src="js/main.js"></script>
<%@ include file="../footer.jsp"%> <!-- footer 삽입 -->
	</body>
</html>