<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file = "./include/set.jsp" %>
<%@ include file = "./include/header.jsp" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글수정</title>
</head>
<body>
	
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="<%=request.getContextPath()%>/js/board.js"></script>

<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>관리자 게시글 수정폼</title>
   
    
    <!-- 삭제 코드  -->
    <script src="http://code.jquery.com/jquery-latest.js"></script>
    
    <script>
   	function del(num, page){
   		var result = confirm("삭제 하시겠습니까?");
   		if(result){
   			location.href="boarddelete?ann_Num="+num+"&page="+page
   		}   		
   	} 
   </script>


</head>

    <!-- Page Wrapper -->
    <div id="wrapper">

       
			<form method=post action="boardupdate" enctype="multipart/form-data">
		<input type="hidden" name="ann_Num" value="${notice.ann_Num }">
		<input type="hidden" name="page" value="${page }">

		
		<div class="outbox" style="margin-top : 30px;">
		<div class="container">
		<table border=0 width=400 align=center>
			<caption>글수정</caption>

			<tr>
				<th>제목</th>
				<td><input type=text name="subject" required="required"
					value="${notice.subject }"></td>
			</tr>

			<tr>
				<th>내용</th>
				<td><textarea rows="5" cols="40" name="content"
						required="required">${notice.content }</textarea></td>
			</tr>

			<tr>
					<td>첨부파일</td>
					<td>
						<c:if test="${!empty notice.aFile }">
							<img src="./upload/${notice.aFile }" width="200" height="100">
						</c:if>
					</td>
				</tr>


			<td colspan="2" align="center">
			<input type="submit" value="글수정">
				<input type="reset" value="취소"></td>
		<!-- 	</tr> -->

		</table>
	</form>
                     </div>
                </div>
  
            </div>

   
     <!-- 하단 스크롤 -->
   <a class="scroll-to-top rounded" href="#page-top">
        <i class="fas fa-angle-up"></i>
    </a> 


    <!-- 로그아웃 Modal -->
    <div class="modal fade" id="logoutModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
        aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Ready to Leave?</h5>
                    <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">×</span>
                    </button>
                </div>
                <div class="modal-body">Select "Logout" below if you are ready to end your current session.</div>
                <div class="modal-footer">
                    <button class="btn btn-secondary" type="button" data-dismiss="modal">Cancel</button>
                    <a class="btn btn-primary" href="login.html">Logout</a>
                </div>
            </div>
        </div>
    </div>

    <!-- Bootstrap core JavaScript-->
    <script src="resources/vendor/jquery/jquery.min.js"></script>
    <script src="resources/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

    <!-- Core plugin JavaScript-->
    <script src="resources/vendor/jquery-easing/jquery.easing.min.js"></script>

    <!-- Custom scripts for all pages-->
    <script src="resources/js/sb-admin-2.min.js"></script>

    <!-- Page level plugins -->
    <script src="resources/vendor/datatables/jquery.dataTables.min.js"></script>
    <script src="resources/vendor/datatables/dataTables.bootstrap4.min.js"></script>

    <!-- Page level custom scripts -->
    <script src="resources/js/demo/datatables-demo.js"></script>




</body>
</html>

	

















