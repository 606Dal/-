<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>게시판 리스트</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<style type="text/css">
	a {
		text-decoration: none;
	}
</style>
</head>
<body>
	<div align="center" class="container col-13">
		<h2>게시판 리스트</h2>
		<%-- 붉은 선 투명도 50, mx : 마진 레프트랑 라이트 --%>
		<hr class="border border-danger border-2 opacity-50">
		<div class="mx-auto col-10">
			<table class="table table-hover" style="text-align: center;">
				<thead class="table-dark">
					<tr>
						<td>번호</td>
						<td>제목</td>
						<td>작성자</td>
						<td>날짜</td>
						<td>조회수</td>
					</tr>
				</thead>
				<tbody>
					<%-- 게시글 목록 가져오기. list(모델 객체 이름) --%>
					<c:forEach items="${list }" var="vo">
					<tr>
						<td>${vo.bNo }</td>
						<%-- 게시글의 번호를 받아서 상세보기로 이동 --%>
						<td style="text-align: left;">
							<c:forEach begin="1" end="${vo.bIndent }">&nbsp;&nbsp;</c:forEach>
							<a href="contentView?bNo=${vo.bNo }">${vo.bSubject}</a>
						</td>
						<td>${vo.bName }</td>
						<td>${vo.bDate }</td>
						<td>${vo.bHit }</td>
					</tr>
					</c:forEach>
					<tr>
						<td colspan="5"><a class="btn btn-outline-primary" href="writeForm" role="button">글쓰기</a></td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>

</body>
</html>