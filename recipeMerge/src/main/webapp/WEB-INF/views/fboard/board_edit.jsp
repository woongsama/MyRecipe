<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file = "../include/set.jsp" %>
  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>자유게시판 글 수정</title>
</head>
<body>
<link href="css/r_custom.css" rel="stylesheet">

	<!-- (공통) 헤더부분 -->
	<%@ include file="../include/header.jsp"%>
		<div class="outbox" style="margin-top : 30px;">
		<div class="container">
	<form method="post" action="<%=request.getContextPath() %>/board_up_ok2"  enctype="multipart/form-data">
	 <input type="hidden" name="comm_num" value="${board.comm_num}" />
 	 <input type="hidden" name="page" value="${page}" />
  
	<table>
		<tr>
			<td>작성자 : 
			    ${sessionScope.id }
     		</td>
		</tr>
		<tr>
			
			<td>
				제목 :<br> <input name="subject" id="subject" size=20 class="input_box" value="${board.subject}">
			</td>
		</tr>
		<tr>
		
			<td>
				내용 : <br><textarea name="content" id="content" row="8" cols="50" class="input_box" >${board.content}</textarea>
			</td>
		</tr>
		<tr>
			<td>
			
				<input type="file" id="commfile2" name="commfile2" value="${board.commfile}">
				<img src="./upload/${board.commfile}" height="200px" width="200px"/><br>
			</td>
		</tr>
	</table>
	<input type="submit" value="등록" style="background-color: aquamarine;"/>
	<input type="reset" value="리셋" style="background-color: aquamarine;"/> <!-- class="input_button" onclick="$('#board_name').focus();" --> 
	</form>
	</div>
	</div>
<%@ include file="../include/footer.jsp"%>
</body>
</html>