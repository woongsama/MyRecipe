<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<c:if test="${result == 1}">
	<script>
		alert("리뷰가 작성되었습니다");
		location.href="r_listForm";
	</script>
</c:if>

<c:if test="${result != 1}">
	<script>
		alert("다시 작성해주세요");
		history.go(-1);
	</script>
</c:if>

</body>
</html>