<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<script src="https://cdn.jsdelivr.net/npm/jquery@3.6.4/dist/jquery.min.js"></script>
<!-- Bootstrap 5 CSS -->
<link rel="stylesheet" href="path/to/bootstrap.min.css">

<!-- Bootstrap 5 JavaScript -->
<script src="path/to/bootstrap.min.js"></script>
<head>
	<meta charset="UTF-8">
	<title>CODELESS</title>

<%@include file="../head.jsp" %>
<script type="text/javascript">
		$(document).ready(function() {
			
			const dotsContainer = document.querySelector('.dots-container');
			const imageContainer = document.querySelector('.image-container');
			const images = imageContainer.querySelectorAll('img');
			const dots = [];

			// 이미지마다 점 생성
			for (let i = 0; i < images.length; i++) {
			  const dot = document.createElement('div');
			  dot.classList.add('dot');
			  dotsContainer.appendChild(dot);
			  dots.push(dot);

			  if (images[i].src.includes("null")) {
				  dot.style.display = 'none'; // 이미지가 없는 경우 점 숨김
				  images[i].remove(); // 이미지 태그 제거
				  dot.remove(); // 점 요소 제거
			  }
			}

			let currentImageIndex = 0;

			function showImage(index) {
			  // 모든 이미지를 숨기고 모든 점을 비활성화
			  for (let i = 0; i < images.length; i++) {
			    images[i].style.display = 'none';
			    dots[i].classList.remove('active');
			  }
			  // 선택한 이미지 보이기 및 해당하는 점 활성화
			  images[index].style.display = 'block';
			  dots[index].classList.add('active');
			  currentImageIndex = index;
			}

			// 초기에 첫 번째 이미지와 점 활성화
			showImage(0);
			dots[0].classList.add('active');

			// 점 클릭 시 해당하는 이미지 보이기
			for (let i = 0; i < dots.length; i++) {
			  dots[i].addEventListener('click', function () {
			    showImage(i);
			  });
			}

			//이전/다음 버튼 클릭 시 이전/다음 이미지 보이기
			const prevBtn = document.querySelector('.prev-btn');
			const nextBtn = document.querySelector('.next-btn');

			prevBtn.addEventListener('click', function() {
				  let prevIndex = (currentImageIndex - 1 + images.length) % images.length;

				  while (images[prevIndex].src.includes("null")) {
				    prevIndex = (prevIndex - 1 + images.length) % images.length;
				  }

				  currentImageIndex = prevIndex;
				  showImage(currentImageIndex);
				});

			nextBtn.addEventListener('click', function() {
				  let nextIndex = (currentImageIndex + 1) % images.length;

				  while (images[nextIndex].src.includes("null")) {
				    nextIndex = (nextIndex + 1) % images.length;
				  }

				  currentImageIndex = nextIndex;
				  showImage(currentImageIndex);
				});
			
			  // 사용자 식별자를 얻어오는 로직이 필요합니다. 예시로 'userId' 변수에 사용자 식별자를 할당합니다.

			  var userId = '<%= session.getAttribute("id") %>';
			  // 찜한 상품 정보를 로컬 스토리지에서 가져옵니다.
			  var likedProducts = JSON.parse(localStorage.getItem('likedProducts')) || {};
			  // 사용자의 찜한 상품 정보를 가져옵니다.
			  var userLikedProducts = likedProducts[userId] || {}; 
		  
			  $('.like-btn').each(function() {
			        var $btn = $(this);
			        var product_id = $btn.data('product-id');
			        
			        // 서버로 찜 상태 확인 요청
			        $.ajax({
			            url: './getLikedStatus.aj',
			            data: { id: userId, product_id: product_id },
			            success: function(response) {
			                if (response.isLiked) {
			                    $btn.addClass('liked');
			                } else {
			                    $btn.removeClass('liked');
			                }
			            }
			        });
			    });

			  // 찜하기 버튼을 클릭할 때 찜한 상품 정보를 로컬 스토리지에 저장합니다.
			  $('.like-btn').on('click', function() {
			    var $btn = $(this);
			    var product_id = $btn.data('product-id');
			    var key = product_id.toString(); // 찜 상품 키로 사용할 문자열로 변환합니다.
			    var isLiked = $btn.hasClass('liked');

			    if (!isLiked) {
			      $.ajax({
			        url: './AjaxLikedAction.aj',
			        data: { product_id: product_id, id: userId },
			        success: function(response) {
			          if (response.success) {
			            $btn.addClass('liked');
			            // 사용자의 찜한 상품 정보를 업데이트합니다.
			            userLikedProducts[key] = true;
			            likedProducts[userId] = userLikedProducts;
			            localStorage.setItem('likedProducts', JSON.stringify(likedProducts));
			            location.reload();
			            alert('찜 추가완료!');
			          } else {
			            alert('추가 실패!');
			          }
			        }
			      });
			    } else { 
			      $.ajax({
			        url: './AjaxUnLikedAction.aj',
			        data: { product_id: product_id, id: userId },
			        success: function(response) {
			          if (response.success) {
			            $btn.removeClass('liked');
			            // 사용자의 찜한 상품 정보에서 해당 상품을 삭제합니다.
			            delete userLikedProducts[key];
			            likedProducts[userId] = userLikedProducts;
			            localStorage.setItem('likedProducts', JSON.stringify(likedProducts));
			            location.reload();
			            alert('찜 해제완료!');
			          } else {
			            alert('삭제 실패!');
			          }
			        }
			      });
			    }
			  });
			});
		
		// 판매완료 처리 함수
	    function completeSale() {
	      // 판매완료 처리된 상품을 나타내는 요소들을 숨깁니다.
	      $('.sale-elements').hide();
	      // 판매완료 메시지를 보여줍니다.
	      $('.sale-message').show();
	    }
		
        window.addEventListener('DOMContentLoaded', adjustHeight);

        function adjustHeight() {
            var outerDiv = document.getElementById('right');
            var innerDiv = document.getElementById('innerDiv');
            outerDiv.style.height = innerDiv.offsetHeight + 'px';
        }

        window.addEventListener('resize', adjustHeight);
		
</script>

<style>

.image-slider {
  width: 300px;
  height: 350px;
  position: relative;
  overflow: hidden;
}

.image-container {
  display: flex;
  justify-content: center;
  align-items: center;
  position: relative;
  width: 100%;
  height: 100%;
}

.slider-image {
  max-width: 100%;
  max-height: 100%;
  object-fit: contain;
  object-position: center;
}

.prev-btn,
.next-btn {
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  font-size: 24px;
  color: #fff;
  background-color: rgba(0, 0, 0, 0.5);
  padding: 8px;
  cursor: pointer;
}

.prev-btn {
  left: 0;
}

.next-btn {
  right: 0;
}

.product-info {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
  align-items: center;
  padding: 20px;
/*   border: 1px solid #ddd; */
}

.dots-container {
  position: absolute;
  bottom: 20px; /* 중앙 하단으로 조절 */
  left: 50%; /* 왼쪽으로 50% 이동 */
  transform: translateX(-50%); /* 자기 자신의 가로폭만큼 왼쪽으로 이동 */
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 10px;
}

.dot {
  width: 10px;
  height: 10px;
  border-radius: 50%;
  margin: 0 5px;
  background-color: #bbb;
}

.dot.active {
  background-color: #555;
}
.product-info {
  display: flex;
  flex-direction: column;
}


.product-container {
  height: 500px; /* 원하는 높이 값으로 설정 */
  overflow: auto;
}

.prev-btn,
.next-btn {
  margin: 0 5px;
  cursor: pointer;
}

.dots-container {
  margin-left: 5px;
}

  .product-table {
    display: flex;
    align-items: flex-start;
    justify-content: flex-start;
  }
  
  .product-image {
    flex: 2;
  }
  
  .product-info {
    flex: 1;
    margin-right: 20px;
  }
  
  .product-info table {
    width: 100%;
  }
  
  .product-info table td:first-child {
    text-align: right;
    font-weight: bold;
  }
		
	.like-btn i, .eye-btn i, .comment-btn i {
		  font-size: 1.5em;
		  color: #999;
		  transition: color 0.3s ease-in-out;
		}
		
		.like-btn.liked i {
		 color: #ff6969;
		}
		
		.like-btn:hover {
		  transform: scale(1.1);
		}
		
		.like-btn:hover i {
		  color: #ff6969;
		}
	
		.eye-btn:hover i {
		  color: lightblue;
		}
	
		.comment-btn:hover i {
		  color: orange;
		}
	
	
	.sale-message {
      display: none;
      font-weight: bold;
      color: red;
    }
	
	form { 
	display : inline; margin:0px
	}
	
</style>

</head>
<body style="overflow-x: hidden;">
<%@include file="../nav.jsp" %>

 <!-- 사이드바 -->
 <div class="row" style="margin-left: 100px;">
 <!-- 사이드바 -->
<%@include file="../mySide.jsp" %>
 <!--   사이드바 -->
 
<div class="col-md-10" style="width: 80%;">
<div id="right" style="border: 3px solid #909090; border-color: #FFBA5A; padding: 20px;  box-shadow: 0 20px 20px rgba(0, 0, 0, 0.4); border-radius: 5px; max-height: 1000px; ">
    <div id="innerDiv" style="text-align: center; overflow-y: auto; max-height: 1000px;">
<h1 style="font-family:'TheJamsil5Bold';" align=left  >${product.title}</h1>
<hr style="border: 0;height: 3px; background-color: black;">
<div class="product-table">
 <div class="product-info" >
  <div>
          <table>
	<tr>
		<td  style= " margin-left:0px padding-right: 35px;">
			<a href="./MypageQNAInsert.qn?product_id=${product.product_id}"> <i class="fa fa-exclamation-triangle"></i> 신고하기 </a>
		</td>
	</tr>
</table>
<table style=" border-collapse: collapse; ">
	<tr>   
	   <td style="text-align: center;"> No. ${product.product_id}</td>
	</tr>
	<tr>
  <th style="display: flex; justify-content: center; align-items: center;">
    <div class="image-slider">
      <div class="image-container">
        <img src="./upload/product/${product.product_image.split(',')[0]}"  class="slider-image" align="middle">
        <img src="./upload/product/${product.product_image.split(',')[1]}"  class="slider-image" align="middle">
        <img src="./upload/product/${product.product_image.split(',')[2]}"  class="slider-image" align="middle">
        <img src="./upload/product/${product.product_image.split(',')[3]}"  class="slider-image" align="middle">
        <img src="./upload/product/${product.product_image.split(',')[4]}"  class="slider-image" align="middle">
      </div>
      <div class="prev-btn">&lt;</div>
      <div class="next-btn">&gt;</div>
      <div class="dots-container"></div>
    </div>
  </th>
		<th rowspan="2" >
		   	<table >
		     
				<tr style="text-align: center; font-size: 20px; ">        
					<td style="text-align: left; font-size: 30px;  padding-left: 40px;color: black;">
						# ${brandName} # ${modelName} # ${colorName} # ${product.parts}
					</td>
				</tr> 
				<tr>
					<td style="text-align: left; font-size: 30px;  padding-left: 40px;color: black;" ><fmt:formatNumber> ${product.price} </fmt:formatNumber>원</td>
				</tr>
				<tr>
				    <td style="text-align: left;  padding-left: 40px;">상품 등급 : ${product.grade == 1 ? 'A' : product.grade == 2 ? 'B' : product.grade == 3 ? 'C' : ''} 급 </td>
				</tr>
				<tr>
				 	 <td style="text-align: left; padding-left: 40px;">거래 지역 : ${product.city eq '   ' ? '전국' : product.city}</td>
				</tr>
		<tr>
	    <th style="text-align: left;padding-left: 40px;">

			 <span style="font-size: 1.0em; color: gray; margin-right: 14px;"> 찜수 <i class="fa fa-heart" style="color:#ff6969"></i>  ${product.like_count }</span>
			<span style="font-size: 1.0em; color: gray; margin-right: 14px;"> 조회수 <i class="fa fa-eye" style="color:lightblue"></i>  ${product.read_count}</span>
			<span style="font-size: 1.0em; color: gray; margin-right: 14px;"> 채팅수 <i class="fa fa-comment" style="color:orange"></i>  ${product.chat_count}</span>
			
	    </th>
	</tr>
				<tr>
					<td colspan="2" style="text-align: center;">
						<div style="text-align: center; margin-top: 10px; ">
							<c:if test="${ sessionScope.id != product.user_id }">
							<button class="like-btn btn" data-product-id="${product.product_id}" data-user-id="${sessionScope.id }">
				 			 <i class="fa fa-heart"></i><span > 찜 </span>
							</button>
							<a href="./OrderWrite.or?product_id=${product.product_id}" class="btn btn-primary sale-elements">구매하기</a>
								<a href="./ChatToSeller.ch?toID=${product.user_id}" class="btn btn-secondary sale-elements">채팅하기</a>
								<hr style="border: 0;height: 3px; background-color: lightgray;">
							</c:if>
					    </div>
					</td>
				</tr>
			</table>
		</th>
	</tr>
	<tr>
	</tr>

	<c:if test="${ sessionScope.id eq 'admin@gmail.com' or product.user_id eq sessionScope.id }">
<table>
	<tr>
	<c:choose>
	  <c:when test="${product.product_status == 1}">
	    <!-- 상품이 판매 완료된 경우 -->
	    <p>판매완료된 상품입니다</p>
	  </c:when>
		  <c:otherwise>
		    <!-- 판매 중인 경우 -->
			<td style="text-align: left;"> 
			</td>
			<td colspan="2" style="text-align: center;">
			<div style="text-align: center; margin-top: 10px; ">
				<a href="./ProductUpdate.pr?product_id=${product.product_id}" class="btn btn-secondary btn-sm sale-elements">상품글 수정</a>
					<form method="post" action="./ProductDeleteAction.pr" onsubmit="if(!confirm('상품을 삭제하시겠습니까?')) return false;">
						<input type="hidden" name="product_id" value="${product.product_id}" class="btn btn-primary">
						<button type="submit" class="btn btn-secondary btn-sm sale-elements" >상품글 삭제</button>
					</form>
					<form method="post" action="ProductSoldAction.pr" class="sale-elements" onsubmit="if(!confirm('상품을 판매 완료 처리하시겠습니까?')) return false;" >
						<input type="hidden" name="product_id" value="${product.product_id}" class="btn btn-primary">
						<button type="submit" class="btn btn-secondary btn-sm sale-elements">판매완료</button>
						<hr style="border: 0;height: 3px; background-color: lightgray;">
					</form>
				</div>
			</td >
			</c:otherwise>
		</c:choose>
	</tr>
</table>	
        </c:if>
</table>
<!-- 		<tr> -->
<table style="margin-top: 40px;">
	<tr style="text-align: left;">
		<td style="text-align: left; padding-left: 1.7%; font-size: 1.3em;" colspan="4">${product.content}
	</tr>
	<tr><td colspan="4"></td></tr>
	<tr>
		<td style="text-align: left; padding-left: 1.5%; font-size: 0.9em;">
			# 등록일 : ${product.reg_date}
		</td>
		<td style="text-align: left; padding-left: 1.5%; font-size:0.9em;">
			# 판매자 : ${product.user_id}
		</td>
		<td style="text-align: left; padding-left: 1.5%; font-size:0.9em;">
			# 거래 방식 : ${product.charge == 0 ? '계좌 거래' : product.charge == 1 ? '안전 결제' : ''}
		</td>
		<td style="text-align: left; padding-left: 1.5%; font-size:0.9em;">
			# 배송비 : ${product.fee}원
		</td>
	</tr>
	<tr>
		<td style="text-align: left; padding-left: 1.5%; font-size:0.9em;">
			# 배송방법 : ${product.method == 1 ? '직거래' : product.method == 2 ? '택배' : ''}
		</td>
	</tr>
</table>

<table>
	<tr>
	<c:choose>
	  <c:when test="${product.product_status == 1}">
	    <!-- 상품이 판매 완료된 경우 -->
	    <p>판매완료된 상품입니다</p>
	  </c:when>
	  <c:otherwise>
	    <!-- 판매 중인 경우 -->
	
	  </c:otherwise>
	</c:choose>
</table>

				</div>
			</div> 
		</div>
		 <div style="margin-bottom: 10px;"></div> 
	</div>
<div>
</div>
</div>
</div>
</div>	

<%@include file="../footer.jsp" %>
</body>
</html>