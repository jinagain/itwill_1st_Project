<%@page import="com.itwillbs.db.MemberDTO"%>
<%@page import="com.itwillbs.db.MemberDAO"%>
<%@page import="com.itwillbs.commons.JSForward"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.DriverManager"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
     pageEncoding="UTF-8"%> 
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
 <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>CODELESS</title>
<%@ include file="../head.jsp"%>
<style>

  h2, p, button {
    font-family: 'TheJamsil5Bold';
  }

  .product-wrapper {
    display: flex;
    width: 400px;
    margin-bottom: 10px;
    border: 1px solid #ccc;
    border-radius: 5px;
    background-color: #fff;
    box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
  }

  .product-wrapper:hover {
    box-shadow: 0px 0px 20px rgba(0, 0, 0, 0.2);
  }

  .product-image {
    flex: 1;
    text-align: center;
  }

  .product-image img {
    width: 250px;
    height: 250px;
    object-fit: contain;
  }

  .product-details {
    flex: 1;
    padding: 10px;
    text-align: left;
  }

  .product-details p {
    margin: 0;
    font-size: 16px;
    line-height: 1.5;
  }
  /* 로딩 이미지 오버레이 */
  .loading-overlay {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(255, 255, 255, 0.8);
    display: flex;
    align-items: center;
    justify-content: center;
    z-index: 9999;
  }

  .loading-overlay img {
    width: 240px;
    height: 180px;
  }

  /* 숨김 클래스 */
  .hidden {
    display: none;
  }
 
</style>
</head>
<body style="margin: 10px; padding: 10px;background-color: transparent !important;box-shadow: none !important;">



  <div class="loading-overlay hidden">
    <img src="./upload/product/loading.gif" width="1000" height="800" >
  </div>

  
   
  <div style="text-align: center;">
    <h2 style="text-shadow: -2px 0px white, 0px 2px white, 2px 0px white, 0px -2px white;color: #5A564F;">매칭 리스트</h2>
    <div class="product-list-wrapper" style="justify-content: center;display: flex; flex-direction: row;">
      <c:forEach var="product" items="${requestScope.productList}" varStatus="status">
        <div class="product-wrapper" style="margin: 10px; padding: 10px; border: 1px solid #ccc; border-radius: 5px; width: 420px;display: flex; flex-direction: column;justify-content: center;">
          <div class="product-image">
            <a href="./ProductContent.pr?product_id=${product.product_id}" onclick="window.parent.location.href = this.href; return false;">
            <img src="./upload/product/${product.product_image.split(',')[0] }" onerror="this.src='https://via.placeholder.com/250x250?text=No Image :('" alt="${product.title}" style="max-width: 250px;max-height: 250px;">
            </a>
          </div>
          <div class="product-details" style="margin-top: 10px;">

            <a href="./ProductContent.pr?product_id=${product.product_id}" onclick="window.parent.location.href = this.href; return false;">${product.title}</a>
			<br>
            <b>가격:</b><fmt:formatNumber> ${product.price}</fmt:formatNumber> 원<br>
    
          <b><i class="fa fa-heart" style="color:#ff6969;"></i></b> ${product.like_count}
       
          </div>
        </div>
      </c:forEach>
    </div>
    <div style="margin-top: 20px;">
      <button onclick="location.reload();"type="button" class="btn btn-secondary">새로운 매칭 보기</button>
    </div>
  </div>
  
  <script>
  window.addEventListener('DOMContentLoaded', function() {
      const loadingOverlay = document.querySelector('.loading-overlay');
      loadingOverlay.classList.remove('hidden');
      setTimeout(function() {
          loadingOverlay.classList.add('hidden');
      }, 1000); // 1초 후에 로딩 이미지를 숨김
    });
  </script>
</body>

</head>
</html>