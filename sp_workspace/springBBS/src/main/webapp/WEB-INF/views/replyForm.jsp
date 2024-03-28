<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>답글 폼</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
	<div align="center" class="container col-13">
		<hr class="border border-danger border-2 opacity-50">
		<h2>답글 페이지</h2>
		<hr class="border border-danger border-2 opacity-50">
		<%-- 목록으로 이동하는 버튼 --%>
		<div class="d-grid d-md-flex justify-content-md-end">
			<a class="btn btn-outline-primary" href="list" role="button">목록</a>
		</div>
		
		<div class="mx-auto col-8">
			<form action="replyOk" method="post">
				<input type="hidden" name="bNo" value="${replyForm.bNo}">
				<input type="hidden" name="bGroup" value="${replyForm.bGroup}">
				<input type="hidden" name="bStep" value="${replyForm.bStep}">
				<input type="hidden" name="bIndent" value="${replyForm.bIndent}">
				
				<table class="table" style="text-align: center;">
					<thead class="table-dark">
						<tr>
							<td>번호</td>
							<td>${replyForm.bNo}</td>
							<td>조회수</td>
							<td>${replyForm.bHit}</td>
							<%-- 답글의 작성자 --%>
							<td style="text-align: right;">작성자</td>
							<td><input type="text" name="bName" style="width: 80%" class="col-sm-offset-3"></td>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td colspan="2">제목</td>
							<td colspan="4"><input type="text" name="bSubject" style="width: 80%" value="[re]${replyForm.bSubject}" readonly></td>
						</tr>
						<tr>
							<td colspan="2">답변</td>
							<td colspan="4"><textarea rows="10" name="bContent" style="width: 80%"></textarea></td>
						</tr>
						<tr>
							<td colspan="6"><input type="submit" class="btn btn-outline-success col-3" value="답변 등록"></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		
		
	</div>

</body>
</html>