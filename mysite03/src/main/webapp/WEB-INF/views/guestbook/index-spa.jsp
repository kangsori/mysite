<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="${pageContext.request.contextPath }/assets/css/guestbook-spa.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/ejs/ejs.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
var listItemTemplate = new EJS({
	url: "${pageContext.request.contextPath }/assets/js/ejs/list-item-template.ejs"
})

// list 그리기 
var render = function(vo, mode) {
	/*
	var htmls = 
		"<li data-no='" + vo.no + "'>" +
		"	<strong>" + vo.name + "</strong>" +
		"	<p>" + vo.message + "</p>" +
		"	<strong></strong>" +
		"	<a href='' data-no='" + vo.no + "'>삭제</a>" + 
		"</li>";
	*/
	
	var htmls = listItemTemplate.render(vo);
	
	$("#list-guestbook")[mode? "prepend" : "append"](htmls);
}

// list 데이터 가져오기 
var fetch = function(startNo) {
	$.ajax({
		url: "${pageContext.request.contextPath}/guestbook/api?sno="+startNo,
		type: "get",
		dataType: "json",
		success: function(response) { 
			if(response.result === 'fail') {
				console.error(response.message);
				return;
			}
			
			response.data.forEach(function(vo){
				render(vo,false);
			});
		}
	});	
}

$(function(){
	
	//스크롤 이벤트 
	$(window).scroll(function() {
		var $window = $(this);
		var $document = $(document);
		
		var windowHeight = $window.height();
		var documentHeight = $document.height();
		var scrollTop = $window.scrollTop();
		
		// 스크롤이 마지막일 때 
		if(documentHeight < windowHeight + scrollTop + 10) {
			// li 마지막 요소의 no 값 찾아서 fetch
			var startNo = $("#list-guestbook li").last().data("no");
			fetch(startNo);
		}
	});
	
	// 최초 리스트 가져오기
	fetch(0);
	
	$("#add-form").submit(function(event){
		event.preventDefault();
		
		// 1. 이름 유효성 체크 
		if($("#input-name").val() === ''){
			messageBox("방명록","이름을 입력해 주세요.",function(){
				$("#input-name").focus();
			});
			return;
		}
		
		// 2. 비밀번호 유효성 체크 
		if($("#input-password").val() === ''){
			messageBox("방명록","비밀번호를 입력해 주세요.", function(){
				$("#input-password").focus();
			});
			return;
		}
		
		// 3. 내용 유효성 체크 
		if($("#tx-content").val() === ''){
			messageBox("방명록","비밀번호를 입력해 주세요.", function(){
				$("#tx-content").focus();
			});
			return;
		}
		
		// 4. ok
		this.submit();
	});
	
	
})

</script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="guestbook">
				<h1>방명록</h1>
				<form id="add-form" action="" method="post">
					<input type="text" id="input-name" placeholder="이름">
					<input type="password" id="input-password" placeholder="비밀번호">
					<textarea id="tx-content" placeholder="내용을 입력해 주세요."></textarea>
					<input type="submit" value="보내기" />
				</form>
				<ul id="list-guestbook">
									
				</ul>
			</div>
			<div id="dialog-delete-form" title="메세지 삭제" style="display:none">
  				<p class="validateTips normal">작성시 입력했던 비밀번호를 입력하세요.</p>
  				<p class="validateTips error" style="display:none">비밀번호가 틀립니다.</p>
  				<form>
 					<input type="password" id="password-delete" value="" class="text ui-widget-content ui-corner-all">
					<input type="hidden" id="hidden-no" value="">
					<input type="submit" tabindex="-1" style="position:absolute; top:-1000px">
  				</form>
			</div>
			<div id="dialog-message" title="" style="display:none">
  				<p></p>
			</div>						
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp"/>
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>