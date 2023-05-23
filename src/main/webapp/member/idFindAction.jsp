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
  <form action="./IdFindAction.me" name="fr"> 
    <div class="form-group" id="log-form">
     <div style="text-align: center;"><a class="navbar-brand" style="font-size: 40px;"><span>아이디 찾기</span></a></div>
     	<br>
		<h3 style="font-family: 'TheJamsil5';">${requestScope.name }님의 아이디는</h3>
		<h3 style="font-family: 'TheJamsil5';">${requestScope.result }입니다.</h3>
        <br>      
      
      <span onclick="location.href='./MemberLogin.me'" style="cursor:pointer;" >로그인 페이지로 이동</span>  
      

    
   </div>
  </form>
</div><!-- 로그인 컨테이너박스 끝 -->
 <%@ include file="../footer.jsp" %> <!-- footer 삽입 -->
</body>
</html>