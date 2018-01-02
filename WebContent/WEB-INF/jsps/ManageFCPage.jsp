<%@page import="com.javaisfun.spring.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/static/script/navigationbar.css">
<title>Manage Flashcards</title>
<script type="text/javascript"	src="${pageContext.request.contextPath}/static/script/jquery.js"></script>
<script type="text/javascript"	src="${pageContext.request.contextPath}/static/script/jsondata.js"></script>

</head>

<body>

<div id="page"  align="center" ></div>
		
<div class="topnav" id="myTopnav">
  <a href="${pageContext.request.contextPath}/userpage">go back to User page</a>
  <a href="${pageContext.request.contextPath}/getHistory">View History</a>
  <a id="epub" href="${pageContext.request.contextPath}/epubWordExtractorPage">Epub-Vocabulory</a>
  <a href="#contact">Contact</a>
  <a href="#about">About</a>
  <a href="${pageContext.request.contextPath}/logout">Logout</a>
 <%-- <img src="${pageContext.request.contextPath}/static/images/background.jpg" alt="background" width="990" height="800"> --%> 
</div>


<div align="center"  class="info" id="AddtoFlashMessagebox" style="color:red">${MessageFromMailService}</div>
<form action="${pageContext.request.contextPath}/postcheckboxvalues" id="email" method="get">
<div id="MasteredFlashcards"></div>
<!-- <button type="button"> Get Values </button> -->

<input type="email" placeholder="Enter email id" name= "emailid" required>
<input type="submit" value="Send">
</form>
<script type="text/javascript">

$(document).ready(function() {
		function getListOfFlashCards(data) {
			
			console.log(data);
			$("#MasteredFlashcards").html('<h3 align="center">Flashcards</h3>');
			for (var i = 0; i < data.flashCards.length; i++) {
				var flashcard = data.flashCards[i];
				console.log(flashcard.flashword);
			$("#MasteredFlashcards").append('<tr><td><p class="checkbox">'+flashcard.flashword+'</p></td><td><input type="checkbox" name="flashcard" class="flashword" value='+flashcard.flashword+'></td></tr>');
				var FlashDef = flashcard.fdefinition;			
			}
		}
		
		function getMasteredList(data1){
			console.log(data1);
			for (var i = 0; i < data1.flashCards.length; i++) {
				
				var flashcard = data1.flashCards[i];
				console.log(flashcard.flashword);
			$("#MasteredFlashcards").append('<tr><td><p class="checkbox">'+flashcard.flashword+'</p></td><td><input type="checkbox" name="flashcard" class="flashword" value='+flashcard.flashword+'></td></tr>');
				var FlashDef = flashcard.fdefinition;
		}
			
		}
		
		

		//to refresh 
		function onReady() {
			updateFlashCardList();
			window.setInterval(updateFlashCardList, 1550000);
			updateMasteredList();
			window.setInterval(updateMasteredList, 1550000);
		}

		function updateFlashCardList() {

			$.getJSON("<c:url value="/userflashcards/"/>", getListOfFlashCards);
		}
		
		
		function updateMasteredList() {

			$.getJSON("<c:url value="/masteredflashcards/"/>", getMasteredList);
		}
		
		$(document).ready(onReady);
		
});
		
			
			
		
	</script>
	
	
<script>
jQuery(function ($) {
    //form submit handler
    $('#email').submit(function (e) {
        //check atleat 1 checkbox is checked
        if (!$('.flashword').is(':checked')) {
            //prevent the default form submit if it is not checked
            alert("Select atleast one flashcard...");	
            e.preventDefault();
        }
    })
})
</script>

<div class="footer"><strong>@Copyright</strong> Sai Ashok Kumar Reddy</div>

</body>
</html>