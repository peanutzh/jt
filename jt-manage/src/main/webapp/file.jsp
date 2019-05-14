<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
	<h1>文件上传</h1>
	
	<!--enctype 表示该form表单提交是多媒体的提交方式  -->
	<form action="http://localhost:8091/file" method="post" 
	enctype="multipart/form-data">
		
		文件:<input type="file" name="pic"/><br/>
		<input type="submit" value="提交"/>
	</form>
</html>