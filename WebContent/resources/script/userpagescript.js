/**
 * 
 */
function getListOfFlashCards(data) {
			$("#DyFlashcards").html("");
			for (var i = 0; i < data.flashCards.length; i++) {
				var flashcard = data.flashCards[i];
			$("#DyFlashcards").append('<tr><td><p class="tooltip">'+flashcard.flashword+'<span class="tooltiptext">'+flashcard.fdefinition+'</span></p></td><td><form method="get" action="${pageContext.request.contextPath}/archiveflashcard"><input type="hidden" name="wordA" value="'+flashcard.flashword+'"><button class="flashcardA" type="submit">Archive<button></td><td>'+'<form method="get" action="${pageContext.request.contextPath}/deleteflashcard"><input type="hidden" name="wordD" value="'+flashcard.flashword+'"><button class="flashcardB" type="submit">Delete</button></form></td></tr>');
			
			
				var FlashDef = flashcard.fdefinition;
				
				
			}
			
		}
		
		

		//to refresh 
		function onReady() {
			updateFlashCardList();
			window.setInterval(updateFlashCardList, 5000);

		}

		function updateFlashCardList() {

			$.getJSON("<c:url value="/userflashcards/"/>", getListOfFlashCards);
		}

		$(document).ready(onReady);