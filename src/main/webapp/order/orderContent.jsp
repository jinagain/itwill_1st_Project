<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>

 <!-- jQuery -->
<script src="https://cdn.jsdelivr.net/npm/jquery@3.6.4/dist/jquery.min.js"></script>
<jsp:include page="../head.jsp"/>
 <!-- iamport.payment.js -->
 <script type="text/javascript" src="https://cdn.iamport.kr/js/iamport.payment-1.2.0.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
  	<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
   <link rel="stylesheet" href="https://cdn.jsdelivr.net/gh/hung1001/font-awesome-pro@4cac1a6/css/all.css" /> 
	
	<script>
	$(function(){
		var id = $('#id').val();
		var receiver_id = $('#receiver_id').val();
		var seller_id = $('#seller_id').val();
		var order_status = $('#order_status').val();
		console.log(id);
		console.log(seller_id);
		console.log(order_status);
		
		// 구매자가 '주문 확인 상태'일 때만 보이는 보이는 '주문 취소' 버튼
		// 구매자가 아니거나, 주문 확인 상태가 아닐때에는 버튼을 감춤
		if(order_status != "주문 확인" || id != receiver_id ){
			$("#accept_0").hide();
		}
		// 구매자에게만 보이는 주문확정 버튼
		// 구매자가 아니거나, 주문확인, 주문확정, 주문 취소 상태일때에는 버튼을 감춤 
		if(id != receiver_id || order_status == "주문 확인" || order_status == "주문 확정" || order_status == "주문 취소" ){
			$("#accept_3").hide();
		}
		// 구매자에게만 보이는 운송장 정보
		// 구매자가 아니거나, 발송, 구매확정 아니면 감춤
		if(order_status == "주문 확인" || order_status == "주문 취소" ||  id != receiver_id){
			$(".receiverTraking").hide();
		}
		// 판매자에게만 보이는 운송장번호 등록 정보
		// 판매자가 아니거나, 주문확인, 주문취소 상태에서는 정보를 감춤
		if(id != seller_id || order_status == "주문 확인" || order_status == "주문 취소"){
			$(".sellerTraking").hide();
		}
		// 판매자가 운송장정보를 등록하기 이전에는 등록하기, 등록한 후에는 수정하기 버튼
		// 발송, 주문확정 상태에서는 등록하기 버튼을 감춤
		if(id != seller_id || order_status == "발송" || order_status == "주문 확정"){
			$("#tracking1").hide();
		}else{
			$("#tracking2").hide();
		}
		// 판매자에게만 보이는 주문수락 버튼
		// 판매자가 아니거나 주문확인 상태가 아닐때에는 버튼을 감춤
		if(id != seller_id || order_status != "주문 확인"){
		$("#accept_1").hide();
		}
// 		// 수정용
// 		if(id != seller_id ){
// 		$("#accept_1").hide();
// 		}
		
	});
		
	 </script>

<meta charset="UTF-8">
<title>주문서 보기</title>
</head>
<body>
<jsp:include page="../nav.jsp"/>
<div class=row style="margin-left: 100px;"> 
<jsp:include page="../mySide.jsp"/>
	 <div class="col-md-10">
 <div id="right" style="width: 70%">
 <h1 style="font-family: 'TheJamsil5Bold';">주 문 내 역</h1>
<hr style="border: 0;height: 3px; background-color: black;">
<!-- 	<h1>주문서 보기</h1> -->
	<div class="orderContent" style="margin-left: 20px;">
		<fieldset>
			<input type="hidden" id="id" name="id" value="${dto.id }">
			<input type="hidden" id="receiver_id" name="receiver_id" value="${dto.receiver_id }">
			<input type="hidden" id="seller_id" name="seller_id" value="${dto.seller_id }">
			<input type="hidden" id="order_status" name="order_status" value="${dto.order_status }">
		<table>
			<tr>
				<td style="text-align: left;"><span style="color: gray; margin-right: 5px; font-size: 15px;">주문번호:</span></td>
				<td style="text-align: right;"><span style="color: gray; margin-right: 25px; font-size: 15px;">${dto.order_id }</span></td>
				<td style="text-align: left;"><span style="color: gray; margin-right: 5px; font-size: 15px;">주문일자:</span></td>
				<td style="text-align: right;"><span style="color: gray; margin-right: 25px; font-size: 15px;"><fmt:formatDate value="${dto.order_date }" pattern="yyyy-MM-dd HH:mm:ss"/></span></td>
				<td style="text-align: left;"><span style="color: gray; margin-right: 5px; font-size: 15px;">주문상태:</span></td>
				<td style="text-align: right;"><span style="color: gray; margin-right: 25px; font-size: 15px;">${dto.order_status }</span></td>
			</tr>
		</table>
		</fieldset>
		<fieldset>
		<legend style="font-size: 1.4rem;">상품 정보</legend>
		<input type="hidden" id="title" name="title" value="${dto.title}">
		<input type="hidden" name="price" value="${dto.price}">
		<input type="hidden" name="fee" value="${dto.fee}">
		<div style="display: flex; align-items: center;" >
			<img src="./upload/product/${dto.product_image.split(',')[0]}" alt="이미지 없음"  width="150px" style="display: block; margin-right: 10px; max-height: 150px; max-width: 150px;">
			<table >
				<tr>
					<td style="text-align: left;"><span style="color: black; margin-right: 20px; margin-left: 35px;"></span></td>
					<td style="text-align: right;"><span style="color: black; margin-left: 25px; font-size: 20px;">${dto.title}</span></td>
				</tr>
				<tr>
					<td style="text-align: left;"><span style="color: black; margin-right: 20px; margin-left: 35px;">가격</span></td>
					<td style="text-align: right;"><span style="color: black; margin-left: 20px; padding-left: 240px;"><fmt:formatNumber value="${dto.price}" pattern="#,###"/>원</span></td>
				</tr>
				<tr>
					<td style="text-align: left;"><span style="color: black; margin-right: 20px; margin-left: 35px;">배송비</span></td>
					<td style="text-align: right;"><span style="color: black; margin-left: 20px; "><fmt:formatNumber value="${dto.fee}" pattern="#,###"/>원</span></td>
				</tr>
			</table>
		</div>
	</fieldset>
</div>
	<hr>
	<div class="trackingInfo" style="margin-left: 20px;">
	<fieldset>
		<legend style="font-size: 1.4rem;">배송 정보</legend>
		<input type="hidden" id="order_id" name="order_id" value="${dto.order_id }">
		<input type="hidden" id = "id" name="id" value="${dto.id }">
			<table>
				<tr>
					<td style="text-align: left;"><span style="color: black; margin-right: 160px;">이름</span></td>
					<td style="text-align: right;"><span style="color: black;">${dto.receiver_name }</span></td>
				</tr>
				<tr>
					<td style="text-align: left;"><span style="color: black; margin-right: 160px;">전화번호</span></td>
					<td style="text-align: right;"><span style="color: black;">${dto.receiver_phone }</span></td>
				</tr>
				<tr>
					<td style="text-align: left;"><span style="color: black; margin-right: 160px;">우편번호</span></td>
					<td style="text-align: right;"><span style="color: black; padding-left: 320px;">${dto.receiver_post }</span></td>
				</tr>
				<tr>
					<td style="text-align: left;"><span style="color: black; margin-right: 160px;">주소</span></td>
					<td style="text-align: right;"><span style="color: black;"> ${dto.receiver_addr1 } , ${dto.receiver_addr2 } </span></td>
				</tr>
			</table>
			<div class="sellerTraking">
				<form name="trackingForm" action="./TrackingNumberAction.or" method="post" accept-charset="utf-8">
				  <input type="hidden" id="order_id" name="order_id" value="${dto.order_id}">
				  <input type="hidden" id="product_id" name="product_id" value="${dto.product_id}">
				  <span style="padding-right: 160px; color: black;">운송장정보</span>
				  <select id="deliveryCompany" name="deliveryCompany" style="width: 120px; height: 35px;">
				    <option value="CJ대한통운">CJ대한통운</option>
				    <option value="롯데택배">롯데택배</option>
				    <option value="한진택배">한진택배</option>
				    <option value="우체국택배">우체국택배</option>
				    <option value="로젠택배">로젠택배</option>
				    <option value="경동택배">경동택배</option>
				    <option value="CU편의점택배">CU편의점택배</option>
				    <option value="GS편의점택배">GS편의점택배</option>
				  </select>
				  <input type="hidden" id="delivery_company" name="delivery_company">
				  <span style="display: inline-block; width: 150px;"><input type="text" oninput="checkInputLength(this, 15)" id="tracking_number" name="tracking_number" pattern="[0-9]*" value="${dto.tracking_number}" placeholder="운송장번호를 숫자로만 입력해주세요"></span>
				  <input type="submit" id="tracking1" value="등록하기">
				  <input type="submit" id="tracking2" value="수정하기">
				</form>
				</div>
				<script>
					// 숫자 입력수 제한 함수
					function checkInputLength(inputElement, maxLength) {
					  if (inputElement.value.length > maxLength) {
					    inputElement.value = inputElement.value.slice(0, maxLength);
					  }
					}
				</script>
				<div class="receiverTraking">
				<table>
				<tr>
					<td style="text-align: left;"><span style="color: black; margin-right: 280px;">운송장정보</span></td>
					<c:choose>
					<c:when test="${dto.order_status eq '주문 수락'}">
					<td style="text-align: cemter; padding-left: 70px; padding-right:15px;"><span style="color: black;">
						</span></td>
					</c:when>
					<c:otherwise>
					<td style="text-align: cemter; padding-right:10px;"><span style="color: black;">
						[ ${dto.delivery_company } ]
						</span></td>
					</c:otherwise>
					</c:choose>
					
					<td style="text-align: right; padding-right:15px;"><span style="color: black;">
					<c:choose>
					<c:when test="${dto.order_status eq '주문 수락'}">
						(운송장번호 미등록)
					</c:when>
					<c:otherwise>
						${dto.tracking_number}
					</c:otherwise>
					</c:choose>
					</span></td>
				</tr>
				</table>
				</div>
				</fieldset>
				</div>
				<script>
				document.getElementsByName("trackingForm")[0].addEventListener("submit", function(event) {
				  var select = document.getElementById("deliveryCompany");
				  var selectedValue = select.options[select.selectedIndex].value;
				  
				  // 선택된 값을 hidden input에 설정
				  document.getElementById("delivery_company").value = selectedValue;
				});
</script>
		<hr>
		<div class="payInfo" style="margin-left: 20px;">
	<fieldset>
	<legend style="font-size: 1.4rem;">결제 정보</legend>
		<input type="hidden" id="t1" name="total" value="${dto.price+dto.fee }">
		<input type="hidden" name="payment" value="카드결제" readonly> 
		<input type="hidden" name = "fee" value="${dto.price+dto.fee }" readonly >
		<table>
			<tr>
				<td style="text-align: left;"><span style="color: black; margin-right: 135px; ">결제수단</span></td>
				<td style="text-align: right;"><span style="color: black; margin-right: 20px;">${dto.payment }</span></td>
				<td style="text-align: center;"><span style="color: black; margin-right: 20px;">|</span></td>
				<td style="text-align: left;"><span style="color: black; margin-right: 135px;">결제금액</span></td>
				<td style="text-align: right;"><span style="color: black;">${dto.price + dto.fee }원</span></td>
			</tr>
			</table>
	</fieldset>
	</div>
		<br>
		<div  style="margin-left: 20px;">
		<!-- 주문 취소 / 주문 수락 -->
		<form action="./OrderIsAccept.or" method="post" accept-charset="utf-8">
		<input type="hidden" id="id" name="id" value="${dto.id }">
		<input type="hidden" id="order_id" name="order_id" value="${dto.order_id }">
		<input type="hidden" id="product_id" name="product_id" value="${dto.product_id }">
		<button class="btn btn-secondary" style="margin-left: 165px; width: 250px;" type="submit" id="accept_0" name="is_accept" value="0" onclick="if(!confirm('주문을 취소하시겠습니까?')){return false;}">주문 취소</button>
		<button class="btn btn-secondary" style="margin-left: 165px; width: 250px;" type="submit" id="accept_1" name="is_accept" value="1" onclick="if(!confirm('주문을 수락하시겠습니까?')){return false;}">주문 수락</button>
		<button class="btn btn-secondary" style="margin-left: 165px; width: 250px;" type="submit" id="accept_3" name="is_accept" value="3" onclick="if(!confirm('주문을 확정하시겠습니까?')){return false;}">주문 확정</button>
		</form>
		</div>
	</div>
</div>
</div>
	<jsp:include page="../footer.jsp"/>
</body>
</html>