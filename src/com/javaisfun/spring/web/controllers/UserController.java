package com.javaisfun.spring.web.controllers;

import java.io.IOException;
import java.security.Principal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javaisfun.spring.web.dao.WordDefDao;
import com.javaisfun.spring.web.model.FlashCard;
import com.javaisfun.spring.web.model.Quote;
import com.javaisfun.spring.web.model.UserSearchRecords;
import com.javaisfun.spring.web.model.WordDef;
import com.javaisfun.spring.web.service.DictionaryApplicationService;
import com.javaisfun.spring.web.service.UserService;

@Controller("userconroller")
public class UserController {

	private DictionaryApplicationService dicService;
	private UserService userServ;

	@Autowired
	public void setDicService(DictionaryApplicationService dicService) {
		this.dicService = dicService;
	}
	@Autowired
	private MailSender mailSender;

	@Autowired
	public void setUserServ(UserService userServ) {
		this.userServ = userServ;
	}

	@RequestMapping("/userpage")
	public String userpage(Model model, Principal principal, HttpSession session) throws IOException {

		List<WordDef> wL = dicService.getList();
		
		Random gen = new Random();
		
		int indxList = gen.nextInt(wL.size());
		
		WordDef randomWord = wL.get(indxList);
		String word = randomWord.getWord();
		List<Quote> qList = dicService.getQuotes(word);
		ObjectMapper mapper = new ObjectMapper();
		String json = "";
		try {
			json = mapper.writeValueAsString(qList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("Quote list is "+qList);
		
		model.addAttribute("QuoteList", qList);
		model.addAttribute("jSONQ", json);	
		model.addAttribute("word_Searched", randomWord);
		model.addAttribute("wordSearched", word);
		if (principal == null) {
			
			return "word_search_engine";
		} else {
			String username = principal.getName();
			System.out.println("UserName from userpage is " + username);
			model.addAttribute("username", username);
			String userid = userServ.getUserId(username);
			int wordNum = randomWord.getWordNum();
			Date date = new Date();	
			Timestamp timestamp = new Timestamp(new Date().getTime());
			UserSearchRecords newSearch = new UserSearchRecords(userid,wordNum,timestamp);	
			System.out.println(newSearch);
			dicService.insertSearchTable(newSearch);
			return "word_search_engine";
		}

	}

	@RequestMapping(value = "/searchdefinition", method = RequestMethod.GET)
	public String getDefintion(Model model, @RequestParam("word") String word,
			@RequestParam("ButtonAction") String[] action, RedirectAttributes redir, Principal principal, HttpSession session) throws IOException {

		WordDef wordD = dicService.getWordDetails(word);	
		
		if(wordD != null){
			
		model.addAttribute("word_Searched", wordD);
		model.addAttribute("wordSearched", word);
		
			if(principal != null){
				String username = principal.getName();
				String userid = userServ.getUserId(username);
				int wordNum = wordD.getWordNum();
				Timestamp timestamp = new Timestamp(new Date().getTime());
				UserSearchRecords newSearch = new UserSearchRecords(userid,wordNum,timestamp);	
				dicService.insertSearchTable(newSearch);
				
			}
			if (action[0].equals("ShowQuotes")) {
				List<Quote> qList = dicService.getQuotes(word);			
				ObjectMapper mapper = new ObjectMapper();
				String json = "";
				try {
					json = mapper.writeValueAsString(qList);
				} catch (Exception e) {
					e.printStackTrace();
				}
				model.addAttribute("QuoteList", qList);
				model.addAttribute("jSONQ", json);		
		
			}
		
		}else{
			model.addAttribute("System_message","Word defintion not found..!");
			Quote q = new Quote();
			q.setQuote("Word defintion not found..!");
			q.setAuthor("None");
			if(word == ""){
				q.setWord("No word Entered");
			}
			else{
				q.setWord(word);
			}
			List<Quote> qList = new ArrayList<Quote>();
			qList.add(q);
			ObjectMapper mapper = new ObjectMapper();
			String json = "";
			try {
				json = mapper.writeValueAsString(qList);
			} catch (Exception e) {
				e.printStackTrace();
			}
			model.addAttribute("jSONQ", json);
			model.addAttribute("QuoteList", qList);
			
		}
		if(principal != null){
			String username = principal.getName();
			System.out.println("UserName from userpage is " + username);
			model.addAttribute("username", username);
		}
			return "word_search_engine";
	
	}

	@RequestMapping("/listflashcards")
	public String getFlashCardList(Model model, Principal principal) {
		String username = principal.getName();
		String userid = userServ.getUserId(username);
		System.out.println("UserId is " + userid);
		List<FlashCard> fList = dicService.getFlashCardList(userid);
		model.addAttribute("flashword_list", fList);
		model.addAttribute("username", username);
		return "word_search_engine";
	}

	@RequestMapping(value = "/addtoflashcards", method = RequestMethod.GET)
	public String addToFlashCards(Model model, @RequestParam("word") String word, Principal principal) {
		
		System.out.println("I am in add to flashcards..");
		String username = principal.getName();
		String userid = userServ.getUserId(username);
		int FNum = dicService.getMaxFlashCards(userid);
		WordDef wordS = dicService.getWordDetails(word);
		FlashCard wordF = new FlashCard();
		wordF.setFlashword(wordS.getWord());
		wordF.setFwordNUm(wordS.getWordNum());
		wordF.setFDefinition(wordS.getWordDef());
		wordF.setUserId(userid);
		wordF.setFlashCardId(FNum + 1);
		System.out.println(wordF);
		
		if(dicService.insertFlashWord(wordF)){
				model.addAttribute("System_message", word+" is now added to your flashcards");
		}else{
			model.addAttribute("System_message", word+" is already added to your flashcards");
		}

		return "word_search_engine";
	}

	
	@RequestMapping(value = "/userflashcards", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String, Object> getUserFlashCards(Principal principal) {
		if (principal != null) {
			String username = principal.getName();
			String userid = userServ.getUserId(username);
			List<FlashCard> flashCards = dicService.getFlashCardList(userid);
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("flashCards", flashCards);
			data.put("number", flashCards.size());

			return data;

		} else {
			return null;
		}

	}
	
	@RequestMapping(value = "/masteredflashcards", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String, Object> getmasteredFlashcards(Principal principal) {
		if (principal != null) {
			String username = principal.getName();
			String userid = userServ.getUserId(username);
			List<FlashCard> flashCards = dicService.getMasteredFlashCardList(userid);
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("flashCards", flashCards);
			data.put("number", flashCards.size());

			return data;

		} else {
			return null;
		}

	}
	
	
	@RequestMapping(value = "/getQuotesJson", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String, Object> getJsonQuotes(Model model, @RequestParam("word") String word) throws IOException {
			
			List<Quote> qList = dicService.getQuotes(word);
			Map<String, Object> dataQ = new HashMap<String, Object>();
			dataQ.put("quoteJson", qList);
			return dataQ;

		

	}
	
	
	@RequestMapping(value = "/postcheckboxvalues", method = RequestMethod.GET)
	public ModelAndView getArrayFlashCards(Model model, @RequestParam("flashcard") String[] words,
			@RequestParam("emailid") String emailId, RedirectAttributes redir, Principal principal, HttpSession	session) throws IOException {
		
		
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setFrom("reddysaiashok29@gmail.com");
		
		String x = "";
		for(String s : words){
			WordDef wordS = dicService.getWordDetails(s);
			x += s+"-"+wordS.getWordDef();
		}
		if(principal != null){
			
		String userid = userServ.searchEmail(emailId);
		if(userid == null){
			
			mail.setTo(emailId);
			mail.setSubject("Non member email sent");
			mail.setText(x);
		} else{
			
			
			for(String s : words){
				int FNum = dicService.getMaxFlashCards(userid);
				WordDef wordS = dicService.getWordDetails(s);
				FlashCard wordF = new FlashCard();
				wordF.setFlashword(wordS.getWord());
				wordF.setFwordNUm(wordS.getWordNum());
				wordF.setFDefinition(wordS.getWordDef());
				wordF.setUserId(userid);
				wordF.setFlashCardId(FNum + 1);
				
				if(dicService.insertFlashWord(wordF)){
					System.out.println(s+" word added to user flashcards");
			}else{
				System.out.println(s+" word already in user flashcards");
			}
			}
			
			mail.setTo(emailId);
			mail.setSubject("You recived flashacards from "+ principal.getName());
			mail.setText(x);
		}
	
		
		
		try {
			mailSender.send(mail);
			redir.addFlashAttribute("MessageFromMailService", "Flashcards shared successfully...!");
			
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Can't send message");
		}

		

		
	}
		ModelAndView modelAndView =  new ModelAndView("redirect:manageflashcardspage");
		
		return modelAndView;
	}


	
	@RequestMapping("/manageflashcardspage")
	public String manageFlashcardpage(Model model, Principal principal) {

		if (principal == null) {

			return "word_search_engine";
		} else {
			String username = principal.getName();
			model.addAttribute("username", username);
			return "ManageFCPage";
		}

	}
	
	@RequestMapping("/getHistory")
	public String getHistoryPage(Model model, Principal principal) {

		if (principal == null) {

			return "word_search_engine";
		} else {
			String username = principal.getName();
			String userid = userServ.getUserId(username);
			List<UserSearchRecords> userRec= dicService.getSearchRecords(userid);
			List<Integer> wordNums = dicService.getWSearchRecords(userid);
			List<String> listOfWordsSearched = new ArrayList<String>();
			
			for(int i : wordNums){
				WordDef w = dicService.getWordDefByNum(i);
				listOfWordsSearched.add(w.getWord());
			}
			
						
			model.addAttribute("username", username);
			model.addAttribute("SearchHis",listOfWordsSearched);
			return "getHistoryPage";
		}

	}
	
	
	
	@RequestMapping(value = "/getHistorydata", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String, Object> getHistoryPage(Principal principal) {
		if (principal != null) {
			String username = principal.getName();
			String userid = userServ.getUserId(username);
			List<UserSearchRecords> userRec= dicService.getSearchRecords(userid);
			System.out.println("UserName from HistoryPage is " + username);
			System.out.println(userRec);
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("searchRecords", userRec);
			data.put("number", userRec.size());

			return data;

		} else {
			return null;
		}

	}
	
	@RequestMapping(value = "/addtoflashcardsJson", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String, Object> addToFlashCardsJson(Model model, @RequestParam("word") String word, Principal principal) {
		
		String username = principal.getName();
		String userid = userServ.getUserId(username);
		int FNum = dicService.getMaxFlashCards(userid);
		WordDef wordS = dicService.getWordDetails(word);
		FlashCard wordF = new FlashCard();
		wordF.setFlashword(wordS.getWord());
		wordF.setFwordNUm(wordS.getWordNum());
		wordF.setFDefinition(wordS.getWordDef());
		wordF.setUserId(userid);
		wordF.setFlashCardId(FNum + 1);
		List<FlashCard> flashCards = dicService.getFlashCardList(userid);
		Map<String, Object> data = new HashMap<String, Object>();
		
		if(dicService.insertFlashWord(wordF)){
			data.put("System_message", word+" is now added to your flashcards");
		}else{
			data.put("System_message", word+" is already added to your flashcards");
		}
		
		data.put("flashCards", flashCards);
		data.put("number", flashCards.size());

		return data;
	}
	
	
	@RequestMapping(value = "/deleteflashcardJ", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String, Object> deletefromFlashCardsJson(Model model, @RequestParam("wordD") String word,Principal principal) {
		System.out.println("I am in delete Json flash cards to delete" + word);
		if (principal != null) {
			String username = principal.getName();
			String userid = userServ.getUserId(username);
			
			Map<String, Object> data = new HashMap<String, Object>();
			dicService.deleteFlashWord(word, userid);
			List<FlashCard> flashCards = dicService.getFlashCardList(userid);			
			data.put("flashCards", flashCards);
			data.put("number", flashCards.size());
			data.put("System_message", word+" - is now removed from your flashcards..!");
			return data;
		}
		else{
			return null;
		}
	}
	
	
	@RequestMapping(value = "/archiveflashcardJ", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String, Object> archiveFlashCardsJ(Model model, @RequestParam("wordA") String word,Principal principal) {
		System.out.println("I am in archive flash cards");
		if (principal != null) {
			String username = principal.getName();
			String userid = userServ.getUserId(username);
			Map<String, Object> data = new HashMap<String, Object>();
			dicService.archiveFlashWord(word, userid);
			List<FlashCard> flashCards = dicService.getFlashCardList(userid);			
			data.put("flashCards", flashCards);
			data.put("number", flashCards.size());
			data.put("System_message", word+" - is now archived from your MasteredList..!");
			return data;
		}
		else{
			return null;
		}
	}
	
	@RequestMapping(value = "/mostSearchWord", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String, Object> mostSearchWord(Model model, Principal principal) {
			if (principal != null) {
			String username = principal.getName();
			String userid = userServ.getUserId(username);
			
			Map<String, Object> data = new HashMap<String, Object>();
			List<HashMap<Integer, Integer>> map= dicService.mostSearchedWord();
			List<String> topTen = new ArrayList<String>();
			int count=0;
			for(HashMap<Integer, Integer> val : map){
				
				if(count<10){
					
					String s = val.toString();
					int y = s.indexOf("=");
					String z = s.substring(1, y);
					topTen.add(z);
				
				
				}
				count++;
			}
			
			List<WordDef> mostList = new ArrayList<WordDef>();
			
			for(String num: topTen){
				int temPNum = Integer.parseInt(num);
				mostList.add(dicService.getWordDefByNum(temPNum));
			}
			
			data.put("MostSearchedWord", mostList);
			return data;
		}
		else{
			return null;
		}
	}
		
}


