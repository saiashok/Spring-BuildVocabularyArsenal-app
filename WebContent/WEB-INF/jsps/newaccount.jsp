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
<script type="text/javascript" src="${pageContext.request.contextPath}/static/script/jquery.js"></script>
<script type="text/javascript">

function onLoad(){
	$("#password").keyup(checkPasswordMatch);
	$("#confirmpass").keyup(checkPasswordMatch);
	$("#details").submit(canSubmit);
}

function canSubmit(){
	var password = $("#password").val();
	var confirmpass = $("#confirmpass").val();
	if(password == confirmpass){
		return true;
	}
	else{
		alert("Password donot match!")
		return false;
	}
	
}

function checkPasswordMatch(){
	var password = $("#password").val();
	var confirmpass = $("#confirmpass").val();
	
	
	if(password.length < 3 || confirmpass.length <3){
		return;
	}
	
	if(password == confirmpass){
		$("#matchpass").text("Passwords match");
		$("#matchpass").addClass("valid");
		$("#matchpass").remove("error");
	}
	else{
		$("#matchpass").text("Passwords donot match");
		$("#matchpass").addClass("error");
		$("#matchpass").remove("valid");
	}
}


$(document).ready(onLoad);

function changeColor(txtFeild){
	txtFeild.style.background = "yellow";
}

</script>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/static/script/navigationbar.css">

<title>Add new word to Database</title>
</head>
<body>

<div class="topnav" id="myTopnav">
  <a href="${pageContext.request.contextPath}/userpage">Continue without login</a>
  <a href="${pageContext.request.contextPath}/login">Go back to Login Page</a>
  <a href="#contact">Contact</a>
  <a href="#about">About</a>
</div>

<br><br><br>	
	<div id="wrapper" align="center">
		<div id="header">

			<h2>Create New Account</h2>
		</div>
	</div>
	
	<div align="center">
	<img src="${pageContext.request.contextPath}/static/images/newaccounticon.png" alt="background" width="170" height="150">
	</div>


	<div id="container" align="center">
		<!-- <h3>Create New Account</h3> -->
		<sf:form id="details" action="${pageContext.request.contextPath}/createaccount"
			method="post" commandName="user">

			
			<table>
				<tbody>
					<tr>
						<td><label>Username</label></td><td><input type="text"  name="username" onfocus="changeColor(this)" placeholder="Enter username"/></td>
					</tr>
					<tr><td></td><td><sf:errors cssClass= "error" path= "username" ></sf:errors></td></tr>
					<tr>
						<td><label>Email</label><br><td><input type="text"  name="emailid" onfocus="changeColor(this)"  placeholder="Enter emailid"/></td>
					</tr>
					<tr><td></td><td><sf:errors cssClass= "error" path= "emailid" > </sf:errors></td></tr>
					<tr>
						<td><label>Password</label><br><td><input id="password" type="password" name="password"  placeholder="password" /></td>
					</tr>
					<tr><td></td><td><sf:errors cssClass= "error" path= "password" > </sf:errors></td></tr>
					
					<tr>
						<td><label>ConfirmPassword</label><br><td><input id="confirmpass"  type="password" name="confirmpassword"  placeholder="confirmpassword"/><div id="matchpass"> </div></td>
						
					</tr>
					
					
					<tr><td></td><td><sf:errors cssClass= "error" path= "password" > </sf:errors></td></tr>
					

					<tr>
						<td>
						<%-- <sf:form action="${pageContext.request.contextPath}/login">
						<input type="submit" value="Cancel" />
						</sf:form> --%>
						</td>		
						<td><input type="submit" value="Create new account" /></td>
					</tr>

				</tbody>

			</table>

		</sf:form>


	</div>
	<br><br><br><br><br><br>
	
	<div class="footer"><strong>@Copyright</strong> Sai Ashok Kumar Reddy</div> 
</body>
</html>