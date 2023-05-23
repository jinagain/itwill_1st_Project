<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<!DOCTYPE HTML>
<html>
  <head>
  <%@ include file="../head.jsp" %>

  </head>    
<body>
 <%@ include file="../nav.jsp" %><!-- nav 삽입 -->
	
 <div class="container" id="login-con">

    <div class="form-group" id="log-form">
     <div style="text-align: center;"><a class="navbar-brand" style="font-size: 40px;"><span>비밀번호 찾기</span></a></div>
     	<br>
		<h4 style="font-family: 'TheJamsil5';">&lt;임시 비밀번호가 발급되었습니다.&gt;</h4>
		<h3 style="font-family: 'TheJamsil5';">${randomPw }</h3>
        <br>      
      
      <span onclick="location.href='./MemberLogin.me'" style="cursor:pointer;" >로그인 페이지로 이동</span>  
      

    
   </div>

</div><!-- 로그인 컨테이너박스 끝 -->
 <%@ include file="../footer.jsp" %> <!-- footer 삽입 -->
</body>
</html>