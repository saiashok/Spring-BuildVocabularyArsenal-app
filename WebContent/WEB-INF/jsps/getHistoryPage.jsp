<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link type="text/css" rel="stylesheet"	href="${pageContext.request.contextPath}/static/script/userpage.css">
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/static/script/navigationbar.css">
<script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Search History</title>

</head>
<body>

<div id="page"  align="center" ></div>
		
		<div class="topnav" id="myTopnav">
  <a href="${pageContext.request.contextPath}/userpage">go back to User page</a>
  <a id="epub" href="${pageContext.request.contextPath}/epubWordExtractorPage">Epub-Vocabulory</a>
  <a href="${pageContext.request.contextPath}/manageflashcardspage">Manage flashcards</a>
  <a href="#contact">Contact</a>
  <a href="#about">About</a>
  <a href="${pageContext.request.contextPath}/logout">Logout</a>
 <%-- <img src="${pageContext.request.contextPath}/static/images/background.jpg" alt="background" width="990" height="800"> --%> 
</div>

Your search history...

 <div id="History">
	  
	  <table border="1">
			<c:forEach var="temQuote" items="${SearchHis}">
			<tr>
			<td>${temQuote}</td>
			</tr>
			</c:forEach>
	</table>	
	</div>
	<div class="footer"><strong>@Copyright</strong> Sai Ashok Kumar Reddy</div>
</body>
</html>