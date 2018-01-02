<!DOCTYPE html>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
<style>
.error{
font-size: small;
color: red;

}

</style>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/static/script/navigationbar.css">
<title>Update word</title>
</head>
<body>
<div class="topnav" id="myTopnav">
  <a href="${pageContext.request.contextPath}/adminpage">Go back to Admin Page</a>
  <a href="${pageContext.request.contextPath}/userpage">Navigate to User page</a>
  <a href="#contact">Contact</a>
  <a href="#about">About</a>
  <a href="${pageContext.request.contextPath}/logout">Logout</a>
</div>
<br><br><br>
	<div id="wrapper">
		<div id="header">

			<h2>Admin Dictionary</h2>
		</div>
	</div>

<div id="image" align="center">
			<img
				src="${pageContext.request.contextPath}/static/images/updatepagebg.png"
				alt="background" width="170" height="150">
		</div>
<div class= "error" align="center">${sysmessage}</div>
	<div id="container" align="center">
		<h3>Update Word in Database</h3>
		<sf:form action="${pageContext.request.contextPath}/updatewordDef"	method="get" commandName="wordUpdate">

			<table class= "updatetable">
			<tr>
						<td><sf:input type="text" path= "word"  value="${wordUpdate.word}" disabled="true"/></td>
					</tr>
					<tr>
						<td><sf:input type="hidden" path= "wordNum" name="wordNum" placeholder="Word" value="${wordUpdate.wordNum}"/></td>
					</tr>
					<tr>
						<td><sf:input type="hidden" path= "word" name="word" placeholder="Word" value="${wordUpdate.word}"/></td>
					</tr>
					<tr><td><sf:errors cssClass= "error" path= "word" ></sf:errors></td></tr>
					<tr>
						<td><sf:input type="text" path= "wordType"  name="wordType" value="${wordUpdate.wordType}"	 placeholder="Word Type"  /></td>
					</tr>
					<tr><td><sf:errors cssClass= "error" path= "wordType" > </sf:errors></td></tr>
					<tr>
						<td><sf:textarea type="text" path= "wordDef"  name="wordDef" placeholder="Word Definition" value="${wordUpdate.wordDef}" rows="4" cols="80" /></td>
					</tr>
					<tr><td><sf:errors cssClass= "error" path= "wordDef" ></sf:errors></td></tr>

					<tr>
						<td><input type="submit" value="Update to Database" /></td>
					</tr>

			</table>

		</sf:form>

	</div>
	
	<br><br><br>	
	
	<div class="footer"><strong>@Copyright</strong> Sai Ashok Kumar Reddy</div>
</body>
</html>