<%@page import="com.itwillbs.commons.JSForward"%>
<%@page import="com.itwillbs.db.MemberDTO"%>
<%@page import="com.itwillbs.db.MemberDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>

  <script src="https://cdn.jsdelivr.net/npm/jquery@3.6.4/dist/jquery.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
<%@ include file="../head.jsp"%>
<%
//세션에서 아이디 받아옴
String id = (String)session.getAttribute("id");
MemberDAO mdao = new MemberDAO();
MemberDTO mdto = mdao.getMember(id);
boolean blocked = mdto.getBlocked();
//차단사용자 세션제어
if(blocked == true) {
	JSForward.alertAndMove(response, "잘못된 접근입니다!", "./MemberInfo.my");
}
%>
<script type="text/javascript">


	
	$(document).ready(function () {
		
		
		$('#alert-success').hide();
		$('#alert-danger').hide();
		
		$('input').keyup(function() {
		  var pas1 = $('#newpassword').val();
		  var pas2 = $('#newpassword2').val();
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

			if($('#newpassword').val() == ""){
				alert('비밀번호를 입력하세요.');
				$('#newpassword').focus();
				return false;
			}//비밀번호 입력 제어
			
			if($('#newpassword2').val() == ""){
				alert('확인 비밀번호를 입력하세요.');
				$('#newpassword2').focus();
				return false;
			}//비밀번호확인 입력 제어
			
			if($('#newpassword').val() != $('#newpassword2').val()){
				alert('확인 비밀번호가 다릅니다.');
				$('#newpassword2').focus();
				return false;
			}
		});//정보 입력안하면 submit기능 제어 끝
		
		  
	});//총function끝
	

	</script>


<title>Insert title here</title>
</head>
<body>
<!-- <h1>passwordUpdate</h1> -->
<%@ include file="../nav.jsp"%><!-- nav 삽입 -->




	<div class="container" id="login-con">
  <form action="./PasswordUpdateAction.me" name="fr" method="post" id="fr"> 
    <div class="form-group" id="log-form">
     <div style="text-align: center;"><a class="navbar-brand" style="font-size: 40px;"><span>비밀번호 변경하기</span></a></div>
     
      <input type="password" class="form-control"  placeholder="현재 비밀번호를 입력해주세요" name="password" style="margin-bottom: 10px;" maxlength="40" >
	 
      <input type="password" class="form-control" placeholder="새 비밀번호를 입력해주세요" name="newpassword" id="newpassword" style="margin-bottom: 10px;" maxlength="40" >
      <input type="password" class="form-control" placeholder="새 비밀번호를  한번더 입력해주세요" name="newpassword2" id="newpassword2" style="margin-bottom: 10px;" maxlength="40" >
	<div class="alert alert-success" id="alert-success">비밀번호가 일치합니다.</div>
	<div class="alert alert-danger" id="alert-danger">비밀번호가 일치하지 않습니다.</div>
					 <br>
       <button type="submit" class="btn btn-primary btn-block"
										style="margin-top: 40px;" id="submit">비밀번호변경</button>
      
         </div>      
  </form>
  
     	</div>





<%@ include file="../footer.jsp"%> <!-- footer 삽입 -->
</body>
</html>