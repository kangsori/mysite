<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.servletContext.contextPath }/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp"/>
		<div id="content">
			<div id="board">
				<form id="search_form" action="${pageContext.request.contextPath }/board" method="post">
					<input type="text" id="kwd" name="kwd" value="${kwd }">
					<input type="submit" value="찾기">
				</form>
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>		
					<c:forEach items="${list }" var="vo" >
						<tr>
							<td>${vo.no }</td>
							<td style="text-align:left; padding-left: ${vo.depth*10}px; ">
								<c:if test="${vo.depth != 0 }">
									<img src="${pageContext.servletContext.contextPath }/assets/images/reply.png">
								</c:if>
								<a href="${pageContext.request.contextPath }/board?a=view&no=${vo.no }">${vo.title }</a>
							</td>
							<td>${vo.userName }</td>
							<td>${vo.hit }</td>
							<td>${vo.regDate }</td>
							<td>
								<c:if test="${authUser.no == vo.userNo}">
									<a href="${pageContext.request.contextPath }/board?a=delete&no=${vo.no }" class="del">
										<img src="${pageContext.servletContext.contextPath }/assets/images/recycle.png">
									</a>
								</c:if>
							</td>
						</tr>
					</c:forEach>		
				</table>
				
				<!-- pager 추가 -->
				<div class="pager">
					<select name="rows" onchange="location.href='${pageContext.request.contextPath }/board?page=${page}&kwd=${kwd}&rows='+this.value">
						<option value=10 <c:if test="${rows==10}">selected</c:if>>10</option>
						<option value=20 <c:if test="${rows==20}">selected</c:if>>20</option>
						<option value=30 <c:if test="${rows==30}">selected</c:if>>30</option>
					</select>
					<ul>
						<c:if test="${page > beginNo }"><li><a href="${pageContext.request.contextPath }/board?page=${page-1}&rows=${rows}&kwd=${kwd}">◀</a></li></c:if>
						<c:forEach var="i" begin="${beginNo }" end="${(beginNo+pageNo)-1 }" step="1" varStatus="status">
							<li <c:if test="${page==i}">class="selected"</c:if>><a href="${pageContext.request.contextPath }/board?page=${i}&rows=${rows}&kwd=${kwd}">${i}</a></li>
						</c:forEach>
						<c:if test="${page < beginNo+pageNo-1}"><li><a href="${pageContext.request.contextPath }/board?page=${page+1}&rows=${rows}&kwd=${kwd}">▶</a></li></c:if>
					</ul>
				</div>					
				<!-- pager 추가 -->
				
				<c:if test="${not empty authUser }">
					<div class="bottom">
						<a href="${pageContext.request.contextPath }/board?a=writeform" id="new-book">글쓰기</a>
					</div>	
				</c:if>
							
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp"/>
		<c:import url="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
</html>