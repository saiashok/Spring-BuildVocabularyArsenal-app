<%@page import="com.javaisfun.spring.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>

<link type="text/css" rel="stylesheet"	href="${pageContext.request.contextPath}/static/script/userpage.css">
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/static/script/navigationbar.css">
<title>Welcome ${username}</title>
<script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.min.js"></script>
<style>
body{
background-size:auto;
background-size: 100% 100%;
background-color: white;
}
tr:hover{background-color:#e2e522}
</style>

</head>

<body>

<div id="page"  align="center" ></div>
		
<div class="topnav" id="myTopnav">
  <a href="${pageContext.request.contextPath}/userpage">go back to User page</a>
  <a id="mflashcardtag" href="${pageContext.request.contextPath}/manageflashcardspage">Manage flashcards</a>
  <a id= "loginpagetag" href="${pageContext.request.contextPath}/login">Login as User</a>
  <a id="historytag"  href="${pageContext.request.contextPath}/getHistory">View History</a>
  <a href="#contact">Contact</a>
  <a href="#about">About</a>
  <a id ="logouttag" href="${pageContext.request.contextPath}/logout">Logout</a>
 </div>	
 <p align="center">Epub page - user is ${username}</p>
 <div align="center" style="color:red" >${Errormessage}</div>
	<div id="filepath" align="center" >	
	<h3>Please upload a book to get the list of words</h3>
	<form action="${pageContext.request.contextPath}/getbooktext"> 
	<input type="text" name="fileupload" id="fileupload" required>
	 <label for="fileupload"> Enter the local path</label> 
	 <input type="submit" value="submit">
	 </form> 	 
	 </div>
	<div align="center"> <input id="frequency" type="text" pattern="[0-9]{10}" placeholder="Please enter the frequency for search" required></div>
	 <div id="booksList">
	 </div>
	 
<script>
 $(document).ready(onReady);

function getListOfBooksToDisplay(data2){		
	console.log(data2);	
	$("#booksList").html('<table class="cardlist"><th align="center">List of Books available in Database</th>');
	for (var i = 0; i < data2.booksList.length; i++) {
		var book = data2.booksList[i];
	$("#booksList").append('<tr><td><p class="tooltip">'+book.book_title+'</p></td><td><p class="tooltip">'+book.book_author+'</p></td><td><button type="button" class="flashcardA" value="'+book.book_id+'" onclick="showListofWords(this.value)">ListWords</button></td></tr>');
				
	}
	$("#booksList").append('</table>');
	
}
function onReady() {
	getListOfBooks();
	var name = '${username}';
	console.log(name);
	if('${username}'!=""){
		$('#loginpagetag').hide();
	
	}else{
		 $('#loginpagetag').show();
		 $("#jsonFlash").attr("disabled", "disabled");
		 $('#logouttag').hide();
		 $('#historytag').hide();
		 $('#mflashcardtag').hide();
	}
}

function getListOfBooks() {

	$.getJSON("<c:url value="/getBookList/"/>", getListOfBooksToDisplay);
} 


function showListofWords(bookid){
	console.log(bookid);
	var frequency = document.getElementById("frequency").value;
	console.log(frequency);
	$.getJSON("<c:url value="/getVocab"/>", 
			{book_id: bookid, frequency:frequency},getListOfWordsfromBook);
}


function getListOfWordsfromBook(data){		

	var j=0;
	var x=15;

	console.log(data)
	if(document.getElementsByClassName("myclass").length >0){
		$('.myclass').remove();
	}
	for(var i=0; i<data.quoteJson.length; i+=15){
		
		var container = document.createElement("div");
		container.style.color = 'black';
		container.setAttribute('class', 'myclass');
		container.setAttribute('id', x);
		$('body').append(container);	
		var html = '<div><ul>';	
		
		
		for(j=i; j<x; j++){
			
			var tempWord = data.quoteJson[j];
			
			if(typeof tempWord != "undefined"){
			
			html+= "<li>"+tempWord+"</li>"
			}else{
				html+= "";
			}
		}
		
		html+= '</ul></div>';
		
		$('#'+x).append(html);
		x += 15	;
	}
	
	
	
}



</script>

<div class="footer"><strong>@Copyright</strong> Sai Ashok Kumar Reddy</div>
</body>
</html>