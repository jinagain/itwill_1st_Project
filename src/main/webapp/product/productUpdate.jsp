<%@page import="com.itwillbs.db.MemberDTO"%>
<%@page import="com.itwillbs.db.MemberDAO"%>
<%@page import="com.itwillbs.commons.JSForward"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>CODELESS</title>
<script src="https://cdn.jsdelivr.net/npm/jquery@3.6.4/dist/jquery.min.js"></script>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script> <!-- 우편api -->
<%@include file="../head.jsp" %>
<link rel="stylesheet" href="path/to/bootstrap.min.css">
<!-- Bootstrap 5 JavaScript -->
<script src="path/to/bootstrap.min.js"></script>
</head>
<body>

<%@include file="../nav.jsp" %>

<%
//세션에서 아이디 받아옴
String id = (String)session.getAttribute("id");
MemberDAO mdao = new MemberDAO();
MemberDTO mdto = mdao.getMember(id);
if(mdto == null) {
	JSForward.alertAndMove(response, "잘못된 접근입니다!", "./MemberLogin.me");
}
boolean blocked = mdto.getBlocked();
//차단사용자 세션제어
if(blocked == true) {
	JSForward.alertAndMove(response, "잘못된 접근입니다!", "./MemberInfo.my");
}
%>

<script type="text/javascript">

function addr() {
	new daum.Postcode({
		    oncomplete : function(data) {
			document.getElementById("address1").value = data.address; // 주소 넣기
		}
	}).open();
}; //우편주소창부르기

$('document').ready(function() {
	var modelList;
	var colorList;
	$.ajax({
		type: "GET",
		url: "./GetModelList.pr",
		contentType : "application/json;charset=UTF-8",
		success: function(data) {
			modelList = data[0];
			colorList = data[1];
			console.log(data);
			
			$('#brand').val(${product.brand}); //저장된 브랜드로 설정
			console.log($('#brand').val);
			inputModelOption(modelList); // 모델 옵션 삽입
			$('#model').val(${product.model}); // 저장된 모델로 설정
			console.log($('#model').val);
			inputColorOption(modelList, colorList); // 색상 옵션 삽입
			$('#color').val(${product.color}); // 저장된 색상으로 설정
			console.log($('#color').val);
		},
		error: function(data) {
			alert("정보를 받아올 수 없습니다.");
		}
	});
	 $('#brand').change(function () {
		 inputModelOption(modelList);
	});//특정 브랜드 선택시 모델옵션 변경
	
	 $('#model').change(function() {
		 inputColorOption(modelList, colorList);
	 });

});

function inputModelOption(modelList) {
	if($('#brand').val() == "1"){
		$('#model option').remove();
		$('#color option').remove();
		$('#model').append("<option value=''>모델</option>");
		$('#color').append("<option value=''>색상</option>");
		$.each(modelList,function(idx, obj){
//				console.log(obj.model_id);
//				console.log(obj.model);
			if(obj.model_id>5) return false;
			$('#model').append("<option value='"+obj.model_id+"'>"+ obj.model + "</option>");
		}); // selected apple
	}else if($('#brand').val() == "2"){
		// Init selectBox
		$('#model option').remove();
		$('#color option').remove();
		$('#model').append("<option value=''>모델</option>");
		$('#color').append("<option value=''>색상</option>");
		// add values
		$.each(modelList,function(idx, obj){
			if(obj.model_id<6) return true;
			$('#model').append("<option value='"+obj.model_id+"'>"+ obj.model + "</option>");
		}); // selected samsung
	}else{
		$('#model option').remove();
		$('#model').append("<option value=''>모델</option>");
	}//미선택시
}
function inputColorOption(modelList, colorList) {
	$('#color option').remove();
	$('#color').append("<option value=''>색상</option>");
	 var color = "";
	$.each(modelList,function(idx, obj){
//			alert("color : " + obj.color+ "/ model id : " + obj.model_id + "/ model val : " + $('#model').val());
		if(obj.model_id == $('#model').val()) {
			color = obj.color;
			return false;
		}
	}); // get Colors by Model
	var colorArr = color.split("/");
	$.each(colorList,function(idx, obj){
		$.each(colorArr, function(idx, colorEle){
			if(obj.color_id == colorEle) {
				$('#color').append("<option value='"+obj.color_id+"'>"+obj.color+"</option>");
			}
		});
	});
}
  function comma(str) {
      str = String(str);
      return str.replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,');
  }
  function uncomma(str) {
      str = String(str);
      return str.replace(/[^\d]+/g, '');
  }
  function inputNumberFormat(obj) {
      obj.value = comma(uncomma(obj.value));
  }
  function inputOnlyNumberFormat(obj) {
      obj.value = onlynumber(uncomma(obj.value));
  }
  function onlynumber(str) {
	    str = String(str);
	    return str.replace(/(\d)(?=(?:\d{3})+(?!\d))/g,'$1');
	}
  function changeNumber() {
	  var price = document.getElementById('price');
	  inputOnlyNumberFormat(price);
  }


  function toggleAddressField() {
	  var methodSelect = document.getElementById("method");
	  var addressFields = document.getElementById("addressFields");
	  var deliveryMessage = document.getElementById("deliveryMessage");
	  var address1Input = document.querySelector('input[name="address1"]');
	  var address2Input = document.querySelector('input[name="address2"]');

	  if ( methodSelect.value === "") {
		  //기본값인 경우
		    addressFields.style.display = "none";
		    deliveryMessage.style.display = "block";
		    payDelFields.style.display = "none";
		    address1Input.value = "";
		    address2Input.value = "";
	  }
	  else if (methodSelect.value === "2") {
	    // 택배를 선택한 경우 
	    addressFields.style.display = "none";
	    payDelFields.style.display = "block";
// 	    address1, address2 값을 공백으로 설정
	    address1Input.value = " ";
	    address2Input.value = " ";
	  } else {
	    addressFields.style.display = "block";
	    deliveryMessage.style.display = "none";
	    payDelFields.style.display = "none";
	    address1Input.value = "";
	    address2Input.value = "";
	  }
	}

  //페이지 로드시 주소창 안 보이게 하는 함수
  window.addEventListener("DOMContentLoaded", function() {
	    toggleAddressField();
	  });
  
</script>

 <!-- 사이드바 -->

<div class="row" style="margin-left: 100px; ">
<%@include file="../mySide.jsp" %>
  
 <!--   사이드바 -->
 
 <div class="col-md-10">
 <div id="right">
  <div style="text-align:left; overflow-y: auto; height: 800px; ">
 <h1 style="font-family: 'TheJamsil5Bold';">상 품 수 정</h1>
<hr style="border: 0;height: 3px; background-color: black;">
	
	<form action="./ProductUpdateProAction.pr" method="post" enctype="multipart/form-data" onsubmit="return changeNumber()">
		<input type="hidden" name="user_id" value="${product.user_id}"> <!-- user_id를 hidden으로 전송 -->
  <!-- user_id를 hidden으로 전송 -->
  
	<label for="seller"></label>
    <span id="seller">${sessionScope.id} 님</span>
	<br>

	
	<div style="display: flex;">
	<label style="margin-top: 10px; margin-right: 10px;">No. </label>
	<input type="text" value="${product.product_id }" name="product_id" class="form-control" style="width: 70px;" readonly>
	<label for="title"></label>
    <input type="text" value="${product.title }" name="title" class="form-control" style="width:384px" maxlength="40" required>
	</div>
	<i class="fa fa-exclamation-triangle"></i> 브랜드, 모델, 색상을 선택해주세요
	<br>
	
	<div style="display: inline-block;">
		<div style="display: flex;">
		<select name="brand" id="brand" style="width: 122px;" class="form-control" required>			
			<option value="">브랜드</option>
			<option value="1">애플</option>
		    <option value="2">삼성</option>
			
		</select>
		<select name="model" id="model" style="width: 122px;" class="form-control" required>	
			<option value="">모델</option>
		</select>
		
		<select name="color" id="color" style="width: 122px;" class="form-control" required>
			<option value="">색상</option>
		</select>
		<select name="parts" id="parts" style="width: 122px;" class="form-control" required>
			<option value="">부품</option>
				<option value="left" ${product.parts == "left" ? "selected" : ""}>좌</option>
				<option value="right"${product.parts == "right" ? "selected" : ""}>우</option>
				<option value="body" ${product.parts == "body" ? "selected" : ""}>본체</option>
			</select>
		</div>
	</div>
   <br>
   <label for="content"></label>
   <textarea name="content" placeholder="글 내용을 작성하세요"  rows="4" cols="50"  style="width: 488px;"  maxlength="400" class="form-control"  required>${product.content}</textarea>
   <br>
	<table>
	  <tr>
	    <td class="file-input">
	    <hr>
	   <i class="fa fa-exclamation-triangle"></i> 상품 이미지 3개 이상 첨부해주세요
	    <br>
	    <img src="./upload/product/${product.product_image.split(',')[0]}" alt="이미지 없음" width="40px">
	    <img src="./upload/product/${product.product_image.split(',')[1]}" alt="이미지 없음" width="40px">
	    <img src="./upload/product/${product.product_image.split(',')[2]}" alt="이미지 없음" width="40px">
	    <img src="./upload/product/${product.product_image.split(',')[3]}" alt="이미지 없음" width="40px">
	    <img src="./upload/product/${product.product_image.split(',')[4]}" alt="이미지 없음" width="40px">
	    <br>
	      <input type="file" name="product_image1" id="product_image1" required>
	      <input type="file" name="product_image2" id="product_image2" required>
	      <input type="file" name="product_image3" id="product_image3" required>
	      <input type="file" name="product_image4" id="product_image4">
	      <input type="file" name="product_image5" id="product_image5">
	      </td>
	  </tr>
	</table>
	      <hr>

      <label for="price"></label>
       <input type="text" name="price" placeholder="가격을 입력해주세요" class="form-control" style="width: 400px" value="${product.price}" id="price" onkeyup="inputNumberFormat(this);" required>

    <label for="grade" style="padding-left: 5px; padding-right:10px;">등급 </label>
      <select name="grade" required class="form-control" style="width: 400px">
						<option value="1">상</option>
						<option value="2">중</option>
						<option value="3">하</option>
				</select>
		
  <label for="method" style="padding-left: 5px; padding-right:10px;"> 거래방식 </label>
		<select name="method" id="method" onchange="toggleAddressField()" class="form-control" required style="width: 400px">
		  <option value="">거래방식을 선택하세요</option>
		  <option value="1" ${product.method == 1 ? "selected" : ""}>직거래</option>
		  <option value="2" ${product.method == 2 ? "selected" : ""}>택배</option>
		</select>

<div id="addressFields"  style="display: inline-block; width:400px; ">
	<hr>
	<i class="fa fa-exclamation-triangle"></i> 주소를 입력해주세요
	<br>
  <label for="address" style="text-align: left;"></label>
    현재 주소 : ${product.city eq '   ' ? '전국' : product.city}<br>
	<div style="display: flex; align-items: center; width: 400px;">
		<input type="text" name="address1" placeholder="주소를 입력해주세요"  id="address1" class="form-control" size="45" onclick="addr();" required>
		<input type="button" value="우편번호 찾기" onclick="addr();" id="postalCodeBtn" >
	</div>
	<input type="text" name="address2" id="address2" size="45" placeholder="수정하실 상세주소를 입력해주세요."  class="form-control" required>
  </div>
  <div id="payDelFields"  style="display: inline-block; width:400px; " >
    <label style="padding-right: 10px;">결제방식</label>
    <input type="radio" name="charge" id="account" value="${product.charge}"checked="${product.charge==0}"  required>
    <label for="account" style="padding-left: 5px; padding-right:10px;"> 계좌거래 </label>
    <input type="radio" name="charge" id="payment" value="1" required>
    <label for="payment" style="padding: 5px;"> 안전결제 </label>
	<br>
    <label style="padding-right: 24.2px;">배송비</label>
    <input type="radio" name="fee" id="free" value="${product.fee}" checked="${product.fee == 0}"  required>
    <label for="free" style="padding-left: 5px; padding-right:10px;"> 배송비 포함 </label>
    <input type="radio" name="fee" id="not_free" value="3000" required>
    <label for="not_free" style="padding-left: 5px; " > 배송비 미포함</label>
</div>
<p id="deliveryMessage" style="display: none; color: red;">(직거래 시에는 주소 입력이 필요합니다.)</p>
<hr>
  <button type="submit" class="btn btn-primary" style="display: inline-block;">상품 수정</button>
	</form>
	</div>
			</div>
		</div>
	</div>
</div>
<script src="js/main.js"></script>
<%@include file="../footer.jsp" %>
</body>
</html>