<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath }/assets/css/user.css" rel="stylesheet" type="text/css">
<script src="${pageContext.request.contextPath }/jquery/jquery-3.6.0.js"></script>
<script>
	$(function(){
		$("#join-form").submit(function(event){
			event.preventDefault();
			
			var name = $("#name").val();
			if(name == ''){
				alert("이름을 입력해 주세요.");
				$("#name").focus();
				return;
			}
			
			if(!$("#img-check").is(":visible")){
				alert("이메일 중복 확인을 해 주세요.");
				return;
			}
			
			this.submit();
		});
		
		$("#email").change(function(){
			$("#img-check").hide();
			$("#btn-checkemail").show();
		});
		
		$("#btn-checkemail").click(function(){
			var email = $("#email").val();
			if(email===''){
				return;
			}
			$.ajax({
				url:"${pageContext.request.contextPath }/user/api/checkemail?email="+email,
				type:"get",
				dataType:"json",
				error: function(){
					console.log(status, error);
				},
				success: function(response){
					if(response.result === 'fail'){
						console.log(response.message);
						return;
					}
					
					if(response.data){
						alert("존재하는 이메일 입니다. 다른 이메일을 선택해 주세요.");
						$("#email").val("").focus();
						return;
					}
					
					$("#img-check").show();
					$("#btn-checkemail").hide();
				}
			});
		});
	});
</script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="user">

				<form:form 
					modelAttribute="userVo"
					id="join-form" 
					name="joinForm" 
					method="post"
					action="${pageContext.request.contextPath }/user/join">
					
					<label class="block-label" for="name">이름</label> 
					<form:input path="name" />
					<spring:hasBindErrors name="userVo">
						<c:if test="${errors.hasFieldErrors('name') }">
							<P style="color: #f00; text-align: left; padding: 0;">
								<!-- 
								${errors.getFieldError("name").defaultMessage}
								-->
								<spring:message code='${errors.getFieldError("name").codes[0] }' />
							</P>
						</c:if>
					</spring:hasBindErrors>


					<label class="block-label" for="email">이메일</label> 
					<form:input path="email" /> 
					<img id="img-check" src="${pageContext.request.contextPath }/assets/images/check.png" style="width: 20px; vertical-align: bottom; display: none;" />
					<input type="button" id="btn-checkemail" value="중복체크" style="display: ;">
					<P style="color: #f00; text-align: left; padding: 0;">
						<form:errors path="email" />
					</P>


					<label class="block-label"> <spring:message code="user.join.label.password" /></label> 
					<form:password path="password" />
					<P style="color: #f00; text-align: left; padding: 0;">
						<form:errors path="password" />
					</P>

					<fieldset>
						<legend>성별</legend>
						<form:radiobutton path="gender" value="female" label="여" />
						<form:radiobutton path="gender" value="male" label="남"/>
					</fieldset>

					<fieldset>
						<legend>약관동의</legend>
						<input id="agree-prov" type="checkbox" name="agreeProv" value="y">
						<label>서비스 약관에 동의합니다.</label>
					</fieldset>

					<input type="submit" value="가입하기">

				</form:form>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp" />
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>