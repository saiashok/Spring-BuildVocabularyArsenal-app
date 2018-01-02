<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<style>
.error{
font-size: small;
color: red;
}

</style>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/static/script/navigationbar.css">

<title>Add new word to Database</title>
</head>
<body>

<div class="topnav" id="myTopnav">
  <a href="${pageContext.request.contextPath}/adminpage">Go back to Admin Page</a>
  <a href="${pageContext.request.contextPath}/userpage">Navigate to User page</a>
  <a href="#contact">Contact</a>
  <a href="#about">About</a>
  <a href="${pageContext.request.contextPath}/loggedout">Logout</a>
</div>
	<div id="wrapper">
		<div id="header">

			<h2>Admin Dictionary</h2>
		</div>
	</div>
<div id="image" align="center">
			<img
				src="${pageContext.request.contextPath}/static/images/insertpagebg.png"
				alt="background" width="170" height="150">
		</div>
		<div class= "error" align="center">${sysmessage}</div>
	<div id="container" align="center">
		<h3>Add Word to Database</h3>
		<sf:form action="${pageContext.request.contextPath}/addNewWord"	method="post" commandName="insertWord">

			<input type="hidden" name="FormAction" value="InsertWord" />
			<table class= "insertable">
					<tr>
						<td><sf:input type="text" path= "word" name="word" placeholder="Word" /></td>
					</tr>
					<tr><td><sf:errors cssClass= "error" path= "word" ></sf:errors></td></tr>
					<tr>
						<td><sf:input type="text" path= "wordType"  name="wordType" placeholder="Word Type" /></td>
					</tr>
					<tr><td><sf:errors cssClass= "error" path= "wordType" > </sf:errors></td></tr>
					<tr>
						<td><sf:textarea type="text" path= "wordDef"  name="wordDef" placeholder="Word Definition" rows="4" cols="80" /></td>
					</tr>
					<tr><td><sf:errors cssClass= "error" path= "wordDef" ></sf:errors></td></tr>

					<tr>
						<td><input type="submit" value="Insert to Database" /></td>
					</tr>

			</table>

		</sf:form>

	</div>
	
	<div class="footer"><strong>@Copyright</strong> Sai Ashok Kumar Reddy</div>
</body>
</html>