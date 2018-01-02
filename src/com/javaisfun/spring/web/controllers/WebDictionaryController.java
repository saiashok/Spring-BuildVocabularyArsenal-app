package com.javaisfun.spring.web.controllers;



import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.javaisfun.spring.web.model.Book_Data;
import com.javaisfun.spring.web.model.Quote;
import com.javaisfun.spring.web.model.User;
import com.javaisfun.spring.web.model.WordDef;
import com.javaisfun.spring.web.service.DictionaryApplicationService;

@Controller
public class WebDictionaryController {
	
	
	private DictionaryApplicationService dicService;
	

	
	@Autowired
	public void setDicService(DictionaryApplicationService dicService) {
		this.dicService = dicService;
	}

	@RequestMapping("/")
	public String showHome(HttpSession session) {
			
		session.setAttribute("message", "I am session..I am here to tell you GOD is dead!");
		
		return "redirect:userpage";
	}
	
	@RequestMapping("/admin")
	public String showAdmin(HttpSession session, Model model,@RequestParam("startP")String start,@RequestParam("endP")String end, Principal principal) {
		List<WordDef> wordDB = dicService.getListByLimt(1,10);
		String username = principal.getName();
		System.out.println("UserName from AdminPage is "+ username);
		model.addAttribute("username", username);
		session.setAttribute("words", wordDB);

		return "Admin_Home_Page";
		
	}
	
	@RequestMapping("/user")
	public String showUser(Model model, Principal principal){
		String username = principal.getName();
		model.addAttribute("username", username);
		return "word_search_engine";
	}
	
	@RequestMapping(value= "/listusers", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public Map<String, Object> getUserList(){
		
		List<User> users = dicService.getListOfUsers();
		Map<String, Object> data = new HashMap<String, Object>();
		
		data.put("users", users);
		data.put("number",	users.size());
		
		return data;
		
	}
	
	
	@RequestMapping("/managefcpage")
	public String showManageFCPage(Model model, Principal principal){
		return "ManageFCPage";
	}
	
	
	@RequestMapping("/epubWordExtractorPage")
	public String getWordsFromEpubPage(Model model,Principal principal) throws FileNotFoundException, IOException{
		
		if(principal!=null){
			String username = principal.getName();
		
		model.addAttribute("username", username);
		}
		return "epubBookToWords";
	}
	
	
	@RequestMapping("/getbooktext")
	public ModelAndView  getBook(Model model, @RequestParam("fileupload")String fileupload, Principal principal, RedirectAttributes redir) throws FileNotFoundException, IOException{
		String filepath = fileupload;
		
		if(principal!= null){
			String username = principal.getName();
		redir.addFlashAttribute("username", username);}
		File file = new File(filepath);
		System.out.println("File exists "+file.exists());
		if (file.isDirectory() || file.exists()){	
		System.out.println(fileupload);
		List<String> list = dicService.epubRdforWordList(filepath);
		ModelAndView modelAndView =  new ModelAndView("redirect:epubWordExtractorPage");
		return modelAndView;
		
		}else{
			ModelAndView modelAndView =  new ModelAndView("redirect:epubWordExtractorPage");
			redir.addFlashAttribute("Errormessage", "File not found or invalid directory...!");
			return modelAndView;
		}
	}
	
	
	
	
	@RequestMapping(value = "/getVocab", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
public Map<String, Object> getJsonofBookWords(Model model,@RequestParam("book_id")int book_id,@RequestParam("frequency")int frequency) throws IOException {
		
			List<String> list = dicService.vocabularyOfBook(book_id, frequency);		
			Map<String, Object> dataQ = new HashMap<String, Object>();
			dataQ.put("quoteJson", list);
			return dataQ;
	}
	
	
	@RequestMapping(value = "/getBookList", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
public Map<String, Object> getBooksListfromDB(Model model) throws IOException {	
			List<Book_Data> booksL = dicService.listOfBooks();
			Map<String, Object> dataQ = new HashMap<String, Object>();
			dataQ.put("booksList", booksL);
			return dataQ;
	}
	
	
	
}
