<%@page import="com.itwillbs.db.MemberDAO"%>
<%@page import="com.itwillbs.db.QnADTO"%>
<%@page import="com.itwillbs.db.MemberDTO"%>
<%@page import="com.itwillbs.db.MypageDAO"%>
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
<!-- <h1>qnaReWrite</h1> -->
<%@ include file="../nav.jsp"%><!-- 4444nav 삽입 -->

	<%   
	
	String id = (String)session.getAttribute("id");
	int bno = Integer.parseInt(request.getParameter("bno"));
	int re_ref = Integer.parseInt(request.getParameter("re_ref"));
	int re_lev = Integer.parseInt(request.getParameter("re_lev"));
	int re_seq = Integer.parseInt(request.getParameter("re_seq"));
	
	
	MemberDTO member = new MemberDAO().getMember(id);
	String nickname = member.getNickname();
	MypageDAO mdao = new MypageDAO();
	QnADTO qna = mdao.getBoard(bno);
 	String qna_category = qna.getQnaCategory();
	%>
	


<!-- 		로그인 세션제어 -->
		<c:if test="${ empty sessionScope.id && !id.equals('admin@gmail.com')}">
		   <c:redirect url="./MemberLogin.me"/>
		</c:if>

	<div class="row" style="margin-left: 100px;">
 <!-- 사이드바 -->
 <%@ include file="../mySide.jsp"%>		
  
 <!--   사이드바 -->
  
 <div class="col-md-10">
 <div id="right">
 <h1 style="font-family: 'TheJamsil5Bold';">답 변 하 기</h1>
<hr style="border: 0;height: 3px; background-color: black;">
	
	
	
	
		
		<fieldset style="margin:auto;">
		   <form action="./QNAReWriteAction.qn" method="post" enctype="multipart/form-data">
		<input type="hidden" name="bno" value="<%=bno %>">
			<input type="hidden" name="re_ref" value="<%=re_ref %>">
			<input type="hidden" name="re_lev" value="<%=re_lev %>">
			<input type="hidden" name="re_seq" value="<%=re_seq %>">
		    <input type="hidden" name="id" value="<%=id%>">
		    <input type="hidden" name="qna_category" value="<%=qna_category%>">
			  작성자(닉네임) : <b><%=nickname %></b> <input type="hidden" name="nickname" value="<%=nickname %>" readonly><br>
		          <label>글제목
				    <input type="text" name="title" value="답변 입니다." readonly class="form-control" size="110"  maxlength="100">
					</label>
					<br>
				    <label>글 내용<br>
				    <textarea name="content"  placeholder="답변글을 작성해주세요." cols="110px;" rows="10px;" class="form-control" maxlength="700"></textarea>
					</label>
					<br>
					<label>첨부 이미지
				    <input type="file" name="image" class="form-control">
					</label>
				 <div style= "float: right; margin-top: 30px;">	
				<input type="submit" value="글쓰기" class="btn btn-primary">
				 </div>
		   </form>		
		</fieldset>
		
	</div>	</div>
	
	</div>
	


	
	
	

<%@ include file="../footer.jsp"%> <!-- footer 삽입 -->
</body>
</html>