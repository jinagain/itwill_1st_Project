<%@page import="com.itwillbs.commons.JSForward"%>
<%@page import="com.itwillbs.db.MemberDTO"%>
<%@page import="com.itwillbs.db.MemberDAO"%>
<%@page import="com.itwillbs.db.ChatDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.net.URLDecoder" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>CODE LESS</title>
	<jsp:include page="../head.jsp"/>
	<style type="text/css">	
		#chatList *{
			font-family: 'TheJamsil5Bold' !important;
/* 			display: block; */
		}
		#chatList{
			width: 100%;
			height: 70%;
		}
		.chatNameM { font-size: 1em; color: #f6df66;}
		.chatContentM { font-size: 1.3em; color: #3e3d43;}
		.chatNameY { font-size: 1.3em; color: #6589f6;}
		.chatContentY { font-size: 1.7em; color: black;}
		.chatTime { font-size: 0.7em; color: gray;}
		#successMessage { color: #2d8659;}
		#dangerMessage { color: #b30000;}
		#warningMessage { color: #bfbf40;}
		
		.speech-bubbleM {
 			width: 50%;
	 		position: relative;
	 		bottom: 15px;
 			background: #f6df66; 
			border-radius: .4em;
			display: inline-block;
			padding: 7px;
		}
	
		.speech-bubbleM:after {
			content: '';
			position: absolute;
			left: 0;
			top: 50%;
			width: 0;
			height: 0;
			border: 20px solid transparent;
			border-right-color: #f6df66;
			border-left: 0;
			border-bottom: 0;
			margin-top: -10px;
			margin-left: -20px;
		}
		.speech-bubbleY {
			width: 50%;
	 		position: relative;
	 		bottom: 15px;
 			background: #6589f6; 
			border-radius: .4em;
			display: inline-block;
			margin-left: 20%;
			text-align: right;
			padding: 7px;
		}

		.speech-bubbleY:after {
			content: '';
			position: absolute;
			right: 0;
			top: 50%;
			width: 0;
			height: 0;
			border: 20px solid transparent;
			border-left-color: #6589f6;
			border-right: 0;
			border-top: 0;
			margin-top: -10px;
			margin-right: -20px;
		}
		.chatHeadM {
			width: 20%;
			display: inline-block;
			padding: 10px;
		}
		.chatHeadY {
			margin-left: 20px;
			width: 20%;
			display: inline-block;
			padding: 10px;
		}
	</style>
<!-- 	<script src="./js/chat.js"></script> -->
	<script type="text/javascript">
	function chkValue() {
        var tmp = $('#chatContent').val().replace(/\s|　/gi, '');
        if(tmp == ''){
        	$('#chatContent').val('');
           return false; // 공백일 때
        }else {
            return true; // 공백이 아닐 때
        }
    }
    function autoClosingAlert(selector, delay) {
    	var alert = $(selector)//.alert();
    	alert.show();
    	window.setTimeout(function() { alert.hide() }, delay);
    }
    function submitFunction() {
    	if(!chkValue()) {
    		return false;
    	}
    	var fromID = '${id}'
    	var toID = '${param.toID}'
    	var chatContent = $('#chatContent').val();
    	$.ajax({
    		type: "POST",
    		url: "./ChatSubmitAction.ch",
    		data: {
    			fromID: encodeURIComponent(fromID),
    			toID: encodeURIComponent(toID),
    			chatContent: encodeURIComponent(chatContent),
    		},
    		success: function(result) {
    			if(result == 1) {
    				autoClosingAlert('#successMessage', 1000);
    			} else if(result == 0) {
    				autoClosingAlert('#dangerMessage', 1000);
    			} else {
    				autoClosingAlert('#warningMessage', 1000);
    			}
    		}
    	});
    	$('#chatContent').val('');
    }
    var lastID = 0;
    function chatListFunction(type) {
    	var fromID = '${id}';
    	var toID = '${param.toID}';
    	$.ajax({
    		type: "POST",
    		url: "./ChatListAction.ch",
    		data: {
    			fromID: encodeURIComponent(fromID),
    			toID: encodeURIComponent(toID),
    			listType: type
    		},
    		success: function(data) {
    			if(data == "") return;
    			var parsed = JSON.parse(data);
    			var result = parsed.result;
    			for(var i=0; i<result.length; i++) {
    				if(result[i][0].value == fromID) {
    					result[i][0].value = '나';
    				}
    				addChat(result[i][0].value, result[i][2].value, result[i][3].value);
    			}	
    			lastID = Number(parsed.last);
    		}
    	});
    }
    function addChat(chatName, chatContent, chatTime) {
    	if(chatName == '나') {
	    	$('#chatList').append(
	    			'<div class="chatHeadM">' +
					'<span class="chatNameM">' + 
	    			chatName +
	    			'</span>' + 
	    			'<img class="media-object img-circle" style="width: 25px; height: 25px; margin: 20px;" src="${fromProfile}" alt="">' +
	    			'<br>' +
					'<span class="chatTime">' + 
	    			chatTime + 
	    			'</span>' + 
	    			'</div>' +
	    			'<div class="speech-bubbleM">' +
					'<span class="chatContentM">' + 
	    			chatContent +
	    			'</span>' + 
	    			'</div>' +
	    			'<div class="clearfix" style="height: 5px;"></div>'
	    			);	  
    	} else {
	    	$('#chatList').append(
	    			'<div style="width:140px; height 150px;"></div>' +
	    			'<div class="speech-bubbleY">' +
					'<span class="chatContentY">' + 
	    			chatContent +
	    			'</span>' + 
	    			'</div>' +
	    			'<div class="chatHeadY">' +
					'<span class="chatNameY">' + 
	    			chatName +
	    			'</span>' + 
	    			'<img class="media-object img-circle" style="width: 25px; height: 25px; margin: 20px;" src="${toProfile}" alt="">' +
	    			'<br>' +
					'<span class="chatTime">' + 
	    			chatTime + 
	    			'</span>' + 
	    			'</div>' +
	    			'<div class="clearfix" style="height: 5px;"></div>'
	    			);	
    	}
    	$('#chatList').scrollTop($('#chatList')[0].scrollHeight);
    }
    function getInfiniteChat() {
    	setInterval(function() {
    		chatListFunction(lastID);
    	}, 3000);
    }
    function getUnread() {
    	$.ajax({
    		type: "POST",
    		url: "./ChatUnreadAction.ch",
    		data: {
    			userID: encodeURIComponent('${id}'),
    		},
    		success: function(result) {
    			if(result >= 1) {
    				showUnread(result);
    			} else {
    				showUnread('');
    			}
    		}
    	});
    }
    function getInfiniteUnread() {
    	setInterval(function() {
    		getUnread();
    	}, 4000);
    }
    function showUnread(result) {
    	$('#unread').html(result);
    }
	</script>

</head>
<body>
	<jsp:include page="../nav.jsp"/>
	<div class=row style="margin-left: 100px;"> 
	<jsp:include page="../mySide.jsp"/>

<!--  	<div class="col-sm-8" style="margin:auto;"> -->
<!-- 	<div class="container bootstrap snippet"> -->
<!-- 		<div class="row"> -->
<!-- 			<div class="col-xs-12"> -->
<!-- 				<div class="portlet portlet-default"> -->
<!-- 					<div class="portlet-heading"> -->
<!-- 						<div class="portlet-title"> -->
<!--                             <h4><i class="fa fa-circle text-green"></i>실시간 채팅창</h4> -->
<!-- 						</div> -->
						<div class="clearfix"></div>
<!-- 					</div> -->
<!--                     <div id="chat" class="panel-collapse collapse in"> -->

	<div class="col-md-10">
 	<div id="right" style="width: 80%">
 	<h1 style="font-family: 'TheJamsil5Bold';">채 팅 목 록</h1>
	<hr style="border: 0;height: 3px; background-color: black;">
					<div id="chatList" class="portlet-body chat-widget" style="overflow-y: auto;">
					</div>
<!-- 					<div class="portlet-footer"> -->
<!-- 						<div class="row" style="height: 90px;"> -->
<!-- 							<div class="form-group col-xs-10"> -->
							<div class="form-group" style="display: flex; align-items: center; justify-content: center;">
							    <textarea style="width:70%; align-items: center;" id="chatContent" class="form-control" placeholder="메시지를 입력하세요." maxlength="100"></textarea>
							    <div style="padding: 20px;">
							    	<button type="button" class="btn btn-default" id="send" onclick="submitFunction();">전송</button>
						    	</div>
							</div>
							    <span id="successMessage" style="display: none;">
						        <strong>메시지 전송에 성공했습니다.</strong></span>
						<!--     </div> -->
						        <span id="dangerMessage" style="display: none;">
						        <strong>이름과 내용을 모두 입력해주세요.</strong></span>
						<!--     </div> -->
						       <span id="warningMessage" style="display: none;">
						        <strong>데이터베이스 오류가 발생했습니다.</strong>
						        </span>
<!-- 							</div> -->
<!-- 							<div class="form-group col-xs-2"> -->
								<input type="hidden" id="count" value="200">
								<!-- 버튼있던자리 -->
							<div class="clearfix"></div>
<!-- 							</div> -->
<!-- 						</div> -->
<!-- 					</div> -->
<!--                     </div> -->
<!-- 				</div> -->
<!-- 			</div> -->
<!-- 		</div> -->
<!-- 	</div> -->
</div>
</div>
<!--     <div class="alert alert-success" id="successMessage" style="display: none;"> -->
<!--         <strong>메시지 전송에 성공했습니다.</strong> -->
<!--     </div> -->
<!--         <div class="alert alert-danger" id="dangerMessage" style="display: none;"> -->
<!--         <strong>이름과 내용을 모두 입력해주세요.</strong> -->
<!--     </div> -->
<!--         <div class="alert alert-warning" id="warningMessage" style="display: none;"> -->
<!--         <strong>데이터베이스 오류가 발생했습니다.</strong> -->
<!--     </div> -->
    <%
//         String messageContent = null;
//         if (session.getAttribute("messageContent") != null) {
//         	messageContent = (String) session.getAttribute("messageContent");
//         }
//         String messageType = null;
//         if (session.getAttribute("messageType") != null) {
//         	messageType = (String) session.getAttribute("messageType");
//         }
//         if (messageContent != null) {
    %>
    <c:if test="${!empty messageContent }">
    <div class="modal fade" id="messageModal" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="vertical-alignment-helper">
            <div class="modal-dialog vertical-align-center">
<%--                 <div class="modal-content <% if(messageType.equals("오류 메시지")) out.println("panel-warning"); else out.println("panel-success"); %>"> --%>
                <c:if test="${messageType eq '오류메세지' }">
	                <div class="modal-content panel-warning">
                </c:if>
                <c:if test="${!messageType eq '오류메세지' }">
	                <div class="modal-content panel-success">
                </c:if>
                    <div class="modal-header panel-heading">
                        <button type="button" class="close" data-dismiss="modal">
                            <span aria-hidden="true">&times</span>
                            <span class="sr-only">Close</span>
                        </button>
                        <h4 class="modal-title">
                            ${messageType }
                        </h4>
                    </div>
                    <div class="modal-body">
                        ${messageContent}
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-primary" data-dismiss="modal">확인</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script>
        $('#messageModal').modal("show");
    </script>
	    <c:remove var="messageContent" scope="session"/>
	    <c:remove var="messageType" scope="session"/>
    </c:if>
    <script type="text/javascript">
	    $(document).ready(function() {
	    	getUnread();
	        chatListFunction('0');
	        getInfiniteChat();
	        getInfiniteUnread();
	        $('#chatContent').on("keyup",function(key){
	            if(key.keyCode==13) {
	            	$('#send').click();
	            }
	        });
	    });
    </script>
    </div>
    <jsp:include page="../footer.jsp"/>
</body>
</html>