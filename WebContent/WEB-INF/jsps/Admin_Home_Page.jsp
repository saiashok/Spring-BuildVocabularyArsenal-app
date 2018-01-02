<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/static/script/navigationbar.css">
<style>


tr:hover{background-color:#3dc6c6}
th {
    background-color: #000000;
    color: white;
}
tr:nth-child(even) {background-color: #f2f2f2}



</style>
<title>Welcome to Admin Page</title>
</head>
<body>


<div class="topnav" id="myTopnav">
  <a href="${pageContext.request.contextPath}/insertwordpage">Insert New Word</a>
  <a href="${pageContext.request.contextPath}/userpage">Navigate to User page</a>
  <a href="#contact">Contact</a>
  <a href="#about">About</a>
  <a href="${pageContext.request.contextPath}/logout">Logout</a>
 <%-- <img src="${pageContext.request.contextPath}/static/images/background.jpg" alt="background" width="990" height="800"> --%> 
</div>
<br><br><br>
	<%-- <p> Hi there...! ${username}</p> --%>
	<div id="image" align="center">
			<img
				src="${pageContext.request.contextPath}/static/images/dictionary.png"
				alt="background" width="170" height="150">
		</div>
		<div>${deleteMessage}</div>
<div class="admin_words">
<h2>Manage Words</h2>
	<table class="admin_table" bgcolor="f7f9f9">
		<tr>
			<th class="dataColumn">Word</th>
			<th class="dataColumn">WordType</th>
			<th class="dataColumn">Definition</th>
			<th class="adminColumn">Update</th>
			<th class="adminColumn">Delete</th>
			</tr>

		<c:forEach var="tempWord" items="${words}">
			<tr class="dataRow">
				<td>${tempWord.word}</td>
				<td><input type="text" value="${tempWord.wordType}" disabled></td>
				<td><textarea  id="${tempWord.wordNum}" name="wordDef" rows="3" cols="80" disabled>${tempWord.wordDef}</textarea></td>
				<td class="updatecell"><div class="update"><form action="${pageContext.request.contextPath}/updatewordpage">
						<input type="hidden" name="word"  value="${tempWord.word}">
						<input type="image" name="submit" src="${pageContext.request.contextPath}/static/images/update.png" border="0" alt="Submit" />
					</form></div>
					</td>				
				<td class="deletecell">
				<div class="delete">
				<form action="${pageContext.request.contextPath}/deleteword">
						<input type="hidden" name="word"  value="${tempWord.word}">
						<input type="image" name="submit" src="${pageContext.request.contextPath}/static/images/delete.png" border="0" alt="Submit" />
					</form>
					</div></td>
			</tr>
		</c:forEach>

	</table>
	</div>
	<div class="forward" align="right"><form action="${pageContext.request.contextPath}/listwordsbyPage">
						<input type="hidden" name="startP"  value="${startP}">
					<div class="pagelimit"><label>Limit per page</label><input type= "text" name="endP" value="${endP}"></div>
						<input type="image" name="submit" src="${pageContext.request.contextPath}/static/images/next.png" border="0" style="width:100px;" alt="Submit" />
					</form></div>
					
	<div class="previous" align="left"><form action="${pageContext.request.contextPath}/listwordsbyPreviousPage">
						<input type="hidden" name="startP"  value="${startP}">
					<input type= "hidden" name="endP" value="${endP}">
						<input type="image" name="submit" src="${pageContext.request.contextPath}/static/images/previous.png" border="0"  style="width:100px;" alt="Submit" />
					</form></div>
				<br><br><br><br><br><br>
<div class="footer"><strong>@Copyright</strong> Sai Ashok Kumar Reddy</div> 
</body>

</html>