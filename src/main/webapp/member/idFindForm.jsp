<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
  <head>
  <%@ include file="../head.jsp" %>
  </head>    
<body>
 <%@ include file="../nav.jsp" %><!-- nav 삽입 -->
	
 <div class="container" id="login-con">
  <form action="./IdFindAction.me" name="fr"> 
    <div class="form-group" id="log-form" >
     <div style="text-align: center;"><a class="navbar-brand" style="font-size: 40px;"><span>아이디 찾기</span></a></div>
     
      <input type="text" class="form-control"  placeholder="이름을 입력해주세요." name="name" style="margin-bottom: 10px;">
	 
      <input type="text" class="form-control" placeholder="등록하신 연락처 정보를 '-'를 포함하여 입력해주세요." name="phone_number">

      <button type="submit" class="btn btn-primary btn-block" style="margin-top: 30px;" >아이디 찾기</button>
      <hr>
               
      
      <span onclick="location.href='./PwFind.me'" style="cursor:pointer;" >비밀번호 찾기</span>  
      

    
   </div>
  </form>
</div><!-- 로그인 컨테이너박스 끝 -->
 <%@ include file="../footer.jsp" %> <!-- footer 삽입 -->
</body>
</html>