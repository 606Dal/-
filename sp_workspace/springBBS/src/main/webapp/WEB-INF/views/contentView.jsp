<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>글 내용 보기</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
	<div align="center" class="container col-13 mt-4">
		<%-- 제목으로 보이게 --%>
		<h2>${contentView.bSubject}</h2>
		<hr class="border border-danger border-2 opacity-50">
	</div>
	<div class="mx-auto col-8">
		<%-- 수정 누르면 이동 --%>
		<form action="modify" method="post">
			<%-- 중요! 수정은 안 되는데 sql 처리할 때 필요해서 값을 숨겨서 넘겨줘야 함 --%>
			<input type="hidden" name="bNo" value="${contentView.bNo}">
			<table class="table" style="text-align: center;">
				<%-- 헤드 부분만 검은색 --%>
				<thead class="table-dark">
					<tr>
						<td>번호</td>
						<td>${contentView.bNo }</td>
						<td>조회 수</td>
						<td>${contentView.bHit }</td>
				<%-- 작성자, 제목, 내용은 수정 가능 --%>
						<td style="text-align: right;">작성자</td>
						<td><input type="text" name="bName" style="width: 80%" class="col-sm-offset-3" value="${contentView.bName}"></td>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td colspan="2">제목</td>
						<td colspan="4"><input type="text" name="bSubject" style="width: 80%" value="${contentView.bSubject}"></td>
					</tr>
					<tr>
						<td colspan="2">내용</td>
						<td colspan="4"><textarea rows="10" name="bContent" style="width: 80%">${contentView.bContent}</textarea></td>
					</tr>
					<tr>
						<td colspan="6"><input type="submit" class="btn btn-outline-success col-2" value="수정하기">
								<a class="btn btn-outline-primary ms-4 col-2" href="list" role="button">목록</a>
								<a class="btn btn-outline-danger ms-4 col-2" href="delete?bNo=${contentView.bNo}" role="button">삭제</a>
								<a class="btn btn-outline-primary ms-4 col-2" href="replyForm?bNo=${contentView.bNo}" role="button">답변</a>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>

</body>
</html>