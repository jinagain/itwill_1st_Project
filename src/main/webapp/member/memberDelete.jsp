<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<script src="https://cdn.jsdelivr.net/npm/jquery@3.6.4/dist/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>

<%@ include file="../head.jsp" %>
<meta charset="UTF-8">
<script type="text/javascript">

$(document).ready(function () {
	

			
	
	$('#alert-success').hide();
	$('#alert-danger').hide();
	
	
	$('input').keyup(function() {
	  var pas1 = $('#password').val();
	  var pas2 = $('#password2').val();
	  if(pas1!="" && pas2!=""){
	  if(pas1 == pas2){
		$('#alert-success').show();
	    $('#alert-danger').hide();
	    $('#submit').removeAttr('disabled');
	  }else{
		$('#alert-success').hide();  
		$('#alert-danger').show();
		$('#submit').attr('disabled','disabled');
	  }}
	   
	});//비밀번호 일치불일치 체크
	
	
	$('#fr').submit(function() {

		if($('#password').val() == ""){
			alert('비밀번호를 입력하세요.');
			$('#password').focus();
			return false;
		}//비밀번호 입력 제어
		
		if($('#password2').val() == ""){
			alert('확인 비밀번호를 입력하세요.');
			$('#password2').focus();
			return false;
		}
		
		if($('#password').val() != $('#password2').val()){
			alert('확인 비밀번호가 다릅니다.');
			$('#password2').focus();
			return false;
		}
		
		 if(confirm('정말로 회원탈퇴를 하시겠습니까?')) {
		        return true;
		 } else {
		        return false;
		 }//회원 탈퇴전 예, 아니오 확인
		
	});//정보 입력안하면 submit기능 제어 끝
	
	  
});//총function끝

</script>
	


<title>Insert title here</title>
</head>
<body>
<!-- ./MemberDeleteAction.me -->
<%@ include file="../nav.jsp"%><!-- nav 삽입 -->

			     <!-- id == null -->
		<c:if test="${ empty id }">
			<c:redirect url="./MemberLogin.me" />
		</c:if> 
		



<div class="container" id="login-con">
  <form action="./MemberDeleteAction.me" name="fr" method="post" id="fr"> 
    <div class="form-group" id="log-form">
     <div style="text-align: center;"><a class="navbar-brand" style="font-size: 40px;"><span>코드리스와 이별하기</span></a></div>
     
	 
				<br>	 				
				비밀번호 <input type="password" class="form-control" id="password"
					placeholder="비밀번호를 입력해주세요." name="password" >
					 <br> 
				
				비밀번호 확인 <input type="password" class="form-control"
					placeholder="비밀번호를 한번 더 입력해주세요." id="password2" >
					 <br>
					 <div class="alert alert-success" id="alert-success">비밀번호가 일치합니다.</div>
					 <div class="alert alert-danger" id="alert-danger">비밀번호가 일치하지 않습니다.</div>
					 <br>
      <button type="submit" class="btn btn-primary btn-block"
										style="margin-top: 40px;" id="submit" >회원탈퇴</button><hr>
      <hr>
         </div>      
 	 </form>
  
     	</div>




		
		
		
 <%@ include file="../footer.jsp"%> <!-- footer 삽입 -->
</body>
</html>