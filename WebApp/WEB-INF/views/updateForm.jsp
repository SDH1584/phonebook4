<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>[Phonebook4]</h1>

	<h2>전화번호 수정화면</h2>

	<p>수정 화면 입니다. 아래 항목을 수정하고 "수정" 버튼을 클릭하세요.</p>
	<form action="/phonebook4/phone/update" method="get">
		이름(name) : <input type="text" name="name" value="${requestScope.personVo.name}">
		<br>
		핸드폰(hp) : <input type="text" name="hp" value="${requestScope.personVo.hp}">
		<br>
		회사(company) : <input type="text" name="company" value="${requestScope.personVo.company}">
		<br>
		 <input type="hidden" name="personId" value="${requestScope.personVo.personId}">
		<br>
		<button type="submit">수정</button>
	</form>
</body>
</html>