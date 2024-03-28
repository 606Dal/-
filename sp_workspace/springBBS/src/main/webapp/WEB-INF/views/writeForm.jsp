<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>글쓰기</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
	<div align="center" class="container col-13 mt-4">
		<h2>글쓰기 페이지</h2>
		<hr class="border border-danger border-2 opacity-50">
		<%-- 목록으로 이동하는 버튼 --%>
		<div class="d-grid d-md-flex justify-content-md-end">
			<a class="btn btn-outline-primary" href="list" role="button">목록</a>
		</div>
		
		<div class="mx-auto col-10" style="text-align: center;">
			<form:form modelAttribute="BVO" action="writeOk" method="post">
				<%-- 부트스트랩 - 플로팅 라벨(작성자, 제목은 input groups) --%>
				<div class="col-sm-6">
					<div class="input-group mb-3">
					  <span class="input-group-text col-sm-3">작성자</span>
					  <div class="form-floating">
					    <form:input path="bName" class="form-control p-2" id="floatingInputGroup2"/>
					  </div>
					</div>
				</div>
				
				<%-- <div class="d-flex justify-content-start mt-3">
					<label class="col-sm-8">제목
						<form:input path="bSubject"/>
					</label>
				</div> --%>
				<div class="col-sm-6">
					<div class="input-group mb-3">
					  <span class="input-group-text col-sm-3">제목</span>
					  <div class="form-floating">
					    <form:input path="bSubject" class="form-control p-2" id="floatingInputGroup2"/>
					  </div>
					</div>
				</div>
				<%-- 부트스트랩 - 플로팅 라벨(내용은 Textarea) --%>
				<div class="form-floating mb-4">
					<form:textarea path="bContent" class="form-control" cols="70" rows="8" placeholder="내용" 
					style="height: 150px"/>
					<label class="col-sm-2">내용</label>
				</div>
				
				<div class="form-group row">
					<div class="col-sm-offset-3">
						<input type="submit" class="btn btn-outline-success" value="등록">
					</div>
				</div>
			</form:form>
		</div>
		
	</div>

</body>
</html>