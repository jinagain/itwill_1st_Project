<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<script src="https://cdn.jsdelivr.net/npm/jquery@3.6.4/dist/jquery.min.js"></script>
<meta charset="UTF-8">
<title>CODELESS - 자주묻는질문 작성</title>
<%@include file="../head.jsp" %>
<script type="text/javascript">
$(document).ready(function () {
	$('#fr').submit(function() {
		if($('#title').val() == ""){
			alert('제목을 입력하세요.');
			$('#title').focus();
			return false;
		}//제목 입력 제어
		
		if($('#content').val() == ""){
			alert('내용를 입력하세요.');
			$('#content').focus();
			return false;
		}//내용 입력 제어
		
	
	});//정보 입력안하면 submit기능 제어 끝
});
</script>
</head>
<body>
<%@include file="../nav.jsp" %>

  <div class="row" style="margin-left: 100px;">
 <!-- 사이드바 -->

 <%@ include file="../noSide.jsp"%>	
  
 <!--   사이드바 -->
 <div class="col-md-10">
 <div id="right" style="width: 80%">
 <h1 style="font-family: 'TheJamsil5Bold';">자주묻는질문 작성</h1>
<hr style="border: 0;height: 3px; background-color: black;">

  <form action="./FaqWriteAction.fa" method="post">
   	<label>카테고리<br>
 	<select name="category" class="form-control" style="width:120x; display:inline;">
  		<option value="이용정책">이용정책</option>
  		<option value="구매">구매</option>
  		<option value="판매">판매</option>
  		<option value="신고">신고</option>
  		<option value="기타">기타</option>
  	</select>
	</label>
    <label style="width:100%;">글제목<br>
    <input type="text" name="title" placeholder="글제목을 입력하세요" class="form-control" maxlength="40">
	</label>
	<br>
    <label style="width:100%; display:inline;">글 내용<br>
    <textarea name="content" placeholder="글 내용을 작성하세요" cols="110px;" rows="10px;" class="form-control" maxlength="400"></textarea>
	</label>
	<br>
	
	<div style= "float: right; margin-top: 60px;">
    <input type="submit" value="작성완료" class="btn btn-primary">
	<a href="./FaqList.fa" class="btn btn-secondary">목록으로</a>
	</div>
  </form>
</div>
</div>
</div>
<%@include file="../footer.jsp" %>
</body>
</html>