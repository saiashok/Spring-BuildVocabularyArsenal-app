<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<html>
<head>
<style type="text/css">
.error {
    color: rgb(255,0,0);
}
#message{
font: bold;
color: rgb(0,255,0);
}

h2{
font-family: fantasy;
}

</style>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/static/script/navigationbar.css">
<title>Login Page</title>
</head>
<body onload='document.f.username.focus();'>

<div class="topnav" id="myTopnav">
  <a href="${pageContext.request.contextPath}/userpage">Continue without login</a>
   <a href="${pageContext.request.contextPath}/newaccountpage">Create an Account</a>
    <a href="#contact">Contact</a>
  <a href="#about">About</a>
</div>

<br><br><br>

<div align="center">
	<h2>Login with Username and Password</h2>
	
	<div id="image" align="center">
			<img
				src="${pageContext.request.contextPath}/static/images/ninja.png"
				alt="background" width="170" height="150">
		</div>
	<c:if test="${param.error != null}">
	
	<p class="error"> Login Failed. Please check your user id or password.</p>
	
	</c:if>
	
	<p id="message">${message}</p>
	
	<form name='f' action='${pageContext.request.contextPath}/login' method='POST'>
		<table>
			<tr>
				<td>User ID:</td>
				<td><input type='text' name='username' value='' placeholder='User ID'></td>
			</tr>
			<tr>
				<td>Password:</td>
				<td><input type='password' name='password' placeholder='Password'/></td>
			</tr>
			<tr>
				<td colspan='2'><button name="submit" type="submit"
					value="Login">Login</button></td>
			</tr>
			
			
		</table>
	</form>
	<p>
			<a href="${pageContext.request.contextPath}/newaccountpage">Create New Account</a>
		</p>
</div>
</body>
</html>