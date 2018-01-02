<%@page import="com.javaisfun.spring.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>

<link type="text/css" rel="stylesheet"	href="${pageContext.request.contextPath}/static/script/userpage.css">
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/static/script/navigationbar.css">
<title>Welcome ${username}</title>
<script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.min.js"></script>
 <script type="text/javascript" src = "https://platform.twitter.com/widgets.js"></script>
 <script src="http://code.responsivevoice.org/responsivevoice.js"></script>
 <script src="https://cdn.jsdelivr.net/jquery.validation/1.15.1/jquery.validate.min.js"></script>
<style>
body{
	/* background-image: url("${pageContext.request.contextPath}/static/images/userpage-bacground.png"); */
background-size:auto;
background-size: 100% 100%;
background-color: white;
}
tr:hover{background-color:#e2e522}
</style>



</head>

<body>

<script>
window.twttr = (function(d, s, id) {
	var js, fjs = d.getElementsByTagName(s)[0],
	t = window.twttr || {};
	if (d.getElementById(id)) return t;
	js = d.createElement(s);
	js.id = id;
	js.src = "https://platform.twitter.com/widgets.js";
	fjs.parentNode.insertBefore(js, fjs);

	t._e = [];
	t.ready = function(f) {
	t._e.push(f);
	
	};

	return t;
	}(document, "script", "twitter-wjs"));</script>
	
<div id="page" align="center" ></div>
		
		<div class="topnav" id="myTopnav">
  <a id="mflashcardtag" href="${pageContext.request.contextPath}/manageflashcardspage">Manage flashcards</a>
  <a id="epub" href="${pageContext.request.contextPath}/epubWordExtractorPage">Epub-Vocabulory</a>
  <a id="historytag"  href="${pageContext.request.contextPath}/getHistory">View History</a>
  <a id= "loginpagetag" href="${pageContext.request.contextPath}/login">Login as User</a>
  <a href="#contact">Contact</a>
  <a href="#about">About</a>
  <a id ="logouttag" href="${pageContext.request.contextPath}/logout">Logout</a>
 </div>
 
<br>
<br>
<h3 id="welcome" align="center">Welcome ${username}</h3>
		<div id="image" align="center">
			<img src="${pageContext.request.contextPath}/static/images/wordList.png" alt="background" width="170" height="150">
		</div>
		
		<div align="center">

		<form id="searchfrom" name="searchfrom" class="engine" action="${pageContext.request.contextPath}/searchdefinition" onsubmit="return validateForm()" method="get">
			
			<input id="wordText" type="text" name="word" title="EnterYourWordHere...only aplhabets allowed" placeholder="Search word" pattern='[A-Za-z\\s]*' required/> 
			<input type="checkbox" name="ButtonAction" value="ShowQuotes" /> Quotes 
			<input type="submit" name="ButtonAction" value="SearchDefinition"/> 
		</form>	
			<div><button type="button" id="pronounce" name="pronounce"  onclick="getPronounciation()"><img src="${pageContext.request.contextPath}/static/images/play-audio.gif" title="click here to pronounce"  alt="background" width="70" height="73"></button></div>
			<textarea placeholder="Word Searched" rows="1" cols="25" disabled> ${word_Details.word}${temQuote.word}${word_Searched.word}</textarea>
			<br />
			<textarea placeholder="Definition" rows="4" cols="80" disabled> ${word_Details.wordDef}${temQuote.wordDef}${word_Searched.wordDef} 
		${System_message}</textarea>
			<br/>

</div>
<div align="center"  class="info" id="AddtoFlashMessagebox" style="color:red"></div>
<div align="center">
<button type="button" id="jsonFlash" name="addFlash"  onclick="addFlashcardsJ()">AddToFlashcards</button>
</div>	
	<input id="JsonQotesTag" type="hidden" value= "${jSONQ}">
	  <div id="quotes">
	  
	  <ul>
			<c:forEach var="temQuote" items="${QuoteList}">
			<li><a title="Click to share on Twitter" class="twitter-share-button" href="https://twitter.com/share" data-size="large" data-text= '${temQuote.quote}' data-url="#"  data-hashtags='${temQuote.author}'  data-via="buildVocab"  data-related="twitterapi,twitter">Tweet</a> </li>
			</c:forEach>
	</ul>	
	</div>  
 	 
<div id="image" align="center">
			<img src="${pageContext.request.contextPath}/static/images/ezgif.com-crop.gif" alt="background" width="70" height="49">
		</div>
		
	<div id="quotesDy" align="center">
	<p id="QuoteArea" align="center" ></p>
	<button type="button"  class="QNavBut" id= "prev" >Prev</button>
    <button  type="button" class="QNavBut" id="next" >Next </button>
   	</div>
	
	
	
	<div id="DyFlashcards" align="center"></div>
	<div id="MasteredFlashcards" align="center"></div>
	<div id= "mostSearched" align="center"></div>

<script>
	$(document).ready(function() {
		
		function validateForm(){
		jQuery.validator.addMethod("lettersonly", function(value, element) {
			  return this.optional(element) || /^[a-z]+$/i.test(value);
			}, "Letters only please"); 
	    
		$("#searchfrom").validate({
			  rules: {
				  wordText: { lettersonly: true }
			  }
			});
		}
		var name = '${username}';
		if('${username}'!=""){
			$('#loginpagetag').hide();
		
		}else{
			 $('#loginpagetag').show();
			 $("#jsonFlash").attr("disabled", "disabled");
			 $('#logouttag').hide();
			 $('#historytag').hide();
			 $('#mflashcardtag').hide();
		}
		
		
		var list = ${jSONQ};
		var word = '${wordSearched}';
		var min= 0;
		var max = Object.keys(list).length
         var count=0;
		
		var qu = list[count].quote;
		var au = list[count].author;
		
		
		   var boldQu=  qu.replace(/${wordSearched}/i, "<b>"+'${wordSearched}'+"</b>");
		   
		   $("li").hide();		 
			var x =  $( "li" ).get(count).innerHTML; 

		  document.getElementById("QuoteArea").innerHTML= boldQu+" <br><p align='right'>-Author(<b>"+ list[count].author +"</b>)</p><br>"+x;
		
		
         
         /* $(document).on("click", 'button',function(){ */
           $('#next').click(function() { 
        	          	   
             if (count < max) {
            	 count++;
            	var quT = list[count].quote;
           		var auT = list[count].author;
           		var y =  $( "li" ).get(count).innerHTML; 
           		
           		var boldQuT=  quT.replace(/${wordSearched}/i, "<b>"+'${wordSearched}'+"</b>");
           		 document.getElementById("QuoteArea").innerHTML= boldQuT+" <br><p align='right'>-Author(<b>"+ list[count].author +"</b>)</p><br>"+y;
           		 
           		var ele = document.getElementById('Tweet-button-share');
           		           		
             }
        	  });
    
         
          
          $('#prev').click(function() {
              if (count > 0) {
             	 count--;
             	var quT = list[count].quote;
        		var auT = list[count].author;
        		var z =  $( "li" ).get(count).innerHTML; 
        		var boldQuT=  quT.replace(/${wordSearched}/i, "<b>"+'${wordSearched}'+"</b>");
             	document.getElementById("QuoteArea").innerHTML= boldQuT+" <br><p align='right'>-Author(<b>"+ list[count].author +"</b>)</p><br>"+z;
             	
                      
              }else{
            	  count=0;
            	  var quT = list[0].quote;
          		var auT = list[0].author;
          		var z =  $( "li" ).get(count); 
            	  document.getElementById("QuoteArea").innerHTML= list[count].quote+" <br><p align='right'>-Author(<b>"+ list[count].author +"</b>)</p><br>"+z;
            	 
              }
 
        });
		
	

	});	
</script>

<script type="text/javascript">

function getListFlashCards(data) {
	$("#DyFlashcards").html('<table class="cardlist"><th align="center">Flashcards</th>');
	for (var i = 0; i < data.flashCards.length; i++) {
		var flashcard = data.flashCards[i];
	$("#DyFlashcards").append('<tr><td><p class="tooltip">'+flashcard.flashword+'<span class="tooltiptext">'+flashcard.fdefinition+'</span></p></td><td><button type="button" class="flashcardA" value="'+flashcard.flashword+'"  onclick="archiveFlashcardJ(this.value)">Archive</button></td><td><button type="button" class="flashcardB" value="'+flashcard.flashword+'"  onclick="deleteFlashcardJ(this.value)">Delete</button></td></tr>');
		var FlashDef = flashcard.fdefinition;			
	}
	$("#DyFlashcards").append('</table>');
	
	var message = data.System_message;
	$("#AddtoFlashMessagebox").html('<p style="color:red;">'+message+'</p>');
}


function addFlashcardsJ(){
	/* $.getJSON("<c:url value="/addtoflashcardsJson?word=${wordSearched}"/>",getListFlashCards); */
		$.getJSON("<c:url value="/addtoflashcardsJson"/>", 
				{word:'${wordSearched}'},getListFlashCards);
}


function deleteFlashcardJ(deleteWord){
	console.log(deleteWord);
	$.getJSON("<c:url value="/deleteflashcardJ"/>", 
			{wordD: deleteWord},getListFlashCards);
}

function archiveFlashcardJ(archiveWord){
	console.log(archiveWord);
	$.getJSON("<c:url value="/archiveflashcardJ"/>", 
			{wordA: archiveWord},getListFlashCards);
}



</script>

<script type="text/javascript">

			function getPronounciation(){
				
				var inpu = document.getElementById('wordText').value;
				var searchword= '${wordSearched}';
				console.log(searchword);
				
				if(searchword != null){
				responsiveVoice.speak(searchword);
				}
				else if(inpu == ""){
						responsiveVoice.speak("Please enter the word");
				}
				else{
					responsiveVoice.speak(inpu);
				}
			}
			
			
			
			

		function getListOfFlashCards(data) {
			$("#DyFlashcards").html('<table class="cardlist"><th align="center">Flashcards</th>');
			for (var i = 0; i < data.flashCards.length; i++) {
				var flashcard = data.flashCards[i];
			$("#DyFlashcards").append('<tr><td><p class="tooltip">'+flashcard.flashword+'<span class="tooltiptext">'+flashcard.fdefinition+'</span></p></td><td><button type="button" class="flashcardA" name="archiveFlash" value="'+flashcard.flashword+'"  onclick="archiveFlashcardJ(this.value)">Archive</button></td><td><button type="button" class="flashcardB" name="deleteFlash" value="'+flashcard.flashword+'"  onclick="deleteFlashcardJ(this.value)">Delete</button></td></tr>');
				var FlashDef = flashcard.fdefinition;			
			}
			$("#DyFlashcards").append('</table>');
		}
		
		function getMasteredList(data1){
			
			$("#MasteredFlashcards").html('<table class="masteredlist"><th align="center">Mastered</th>');
			for (var i = 0; i < data1.flashCards.length; i++) {
				
				var flashcard = data1.flashCards[i];
			$("#MasteredFlashcards").append('<tr><td><p class="mtooltip">'+flashcard.flashword+'<span class="mtooltiptext">'+flashcard.fdefinition+'</span></p></td><td><button type="button" class="flashcardB" name="deleteFlash" value="'+flashcard.flashword+'"  onclick="deleteFlashcardJ(this.value)">Delete</button></td></tr>');
				var FlashDef = flashcard.fdefinition;
		}
			$("#MasteredFlashcards").append('</table>');
			
		}
		
		function getMSWord(data2){		
		
			$("#mostSearched").html('<table class="notifications"><th align="center">Most searched Words</th>');
			for (var i = 0; i < data2.MostSearchedWord.length; i++) {
				
				var tempWord = data2.MostSearchedWord[i];
			$("#mostSearched").append('<tr><td><p class="tooltip">'+tempWord.word+'<span class="tooltiptext">'+tempWord.wordDef+'</span></p></td></tr>');
					}
			$("#mostSearched").append('</table>');
			
		}
		

		//to refresh 
		function onReady() {
			updateFlashCardList();
			window.setInterval(updateFlashCardList, 2000);
			updateMasteredList();
			window.setInterval(updateMasteredList, 2000);
			getMostSearchedWord();
			window.setInterval(getMostSearchedWord, 5000);
		}

		function updateFlashCardList() {

			$.getJSON("<c:url value="/userflashcards/"/>", getListOfFlashCards);
		}
		
		
		function updateMasteredList() {

			$.getJSON("<c:url value="/masteredflashcards/"/>", getMasteredList);
		}
	
		function getMostSearchedWord(){
			$.getJSON("<c:url value="/mostSearchWord/"/>", getMSWord);
		}
			
			$(document).ready(onReady);
		
	</script>

<div class="footer"><strong>@Copyright</strong> Sai Ashok Kumar Reddy</div>

</body>
</html>