<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ include file = "./include/set.jsp" %>
<%@ include file = "./include/header.jsp" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글작성</title>
</head>


<link href="css/r_custom.css" rel="stylesheet">
<body>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>공지사항 글작성폼</title>

    <!-- Custom fonts for this template -->
    <!-- <link href="resources/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
    <link
        href="resources/https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
        rel="stylesheet"> -->

    <!-- Custom styles for this template -->
 <!--    <link href="resources/css/sb-admin-2.min.css" rel="stylesheet"> -->

    <!-- Custom styles for this page -->
    <!-- <link href="resources/vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet"> -->
    
    
    <script src="http://code.jquery.com/jquery-latest.js"></script>
    
    <script>
	$(function(){
		 $("#adminmain").load("n_boardlist");
	});
    </script>	
    
</head>

    
    <div class="outbox" style="margin-top : 30px;">
		<div class="container">
			 <form method=post action="boardwrite" enctype="multipart/form-data">
    <table border=0 width=400 align=center> 
	<caption>글작성</caption>
	<tr>
	    <th>제목</th>
		<td><input type=text name="subject" required="required"></td>
	</tr>
	<tr>
	    <th>내용</th>
		<td><textarea cols=40 rows=5 name="content" required="required"></textarea></td>
	</tr>
	<tr>
		<th>파일</th>
		<td><input type="file" name="aFile1"></td>
   </tr> 
	
	<tr><td colspan=2 align=center>
			<input type=submit value="글작성">
			<input type=reset value="취소">
		</td>
	</tr>
</table>
</form>
                

                </div>
                <!-- /.container-fluid -->

            </div>
            <!-- End of Main Content -->



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


