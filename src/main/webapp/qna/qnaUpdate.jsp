<%@page import="com.itwillbs.db.QnADTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="../head.jsp"%>
<meta charset="UTF-8">
<script src="https://cdn.jsdelivr.net/npm/jquery@3.6.4/dist/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
<%@ include file="../nav.jsp"%><!-- nav 삽입 -->
<!-- <h1>qnaUpdate</h1> -->

			
			
	<div class="row" style="margin-left: 100px;">
 <!-- 사이드바 -->
 <%@ include file="../mySide.jsp"%>		
  
 <!--   사이드바 -->
	 
<div class="col-md-10">
 <div id="right">
 <h1 style="font-family: 'TheJamsil5Bold';">QnA 질문내용</h1>
<hr style="border: 0;height: 3px; background-color: black;">
			
			<fieldset>
			<legend> 게시판 수정하기 </legend>
				<form action="./QNAUpdateAction.qn?pageNum=${pageNum }" method="post" name="fr" enctype="multipart/form-data">
				<input type="hidden" name="bno" value="${qdto.bno }">
				<input type="hidden" name="pageNum" value="${pageNum }">
				   작성자 : ${qdto.nickname} <input type="hidden" name="nickname" required value="${qdto.nickname}" readonly="readonly"><br>
		          <label>글제목
				    <input type="text" name="title" value="답변 입니다." readonly class="form-control" size="110" maxlength="100">
					</label>
					<br>
				    <label>글 내용<br>
				    <textarea name="content" cols="110px;" rows="10px;" class="form-control" maxlength="700">${qdto.content}</textarea>
					</label>
					<br>
					<label>첨부 이미지
				    <input type="file" name="image" class="form-control">
					</label> 
					 <div style= "float: right; margin-top: 30px;">	
				  <input type="submit" value="글 수정하기" class="btn btn-primary">
				  <input type="button" value="수정취소" class="btn btn-secondary" onclick="location.href='./AdminQNAList.qn'">
				  </div> 
				</form>
			</fieldset>

	</div></div>
	</div>
<br><br><br><br>
<%@ include file="../footer.jsp"%> <!-- footer 삽입 -->
</body>
</html>