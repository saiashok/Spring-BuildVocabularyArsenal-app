package com.javaisfun.spring.web.controllers;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.javaisfun.spring.web.model.User;
import com.javaisfun.spring.web.model.WordDef;
import com.javaisfun.spring.web.service.DictionaryApplicationService;

@Controller("/admincontroller")
public class AdminController {

	private DictionaryApplicationService dicService;

	@Autowired
	public void setDicService(DictionaryApplicationService dicService) {
		this.dicService = dicService;
	}

	@RequestMapping("/listwordsbyPage")
	public String showListOfWords(HttpSession session, Model model,@RequestParam("startP")String start,@RequestParam("endP")String end, Principal principal) {

		if(start== null && end==null){
		List<WordDef> wordDB = dicService.getListByLimt(1,10);
		String username = principal.getName();
		model.addAttribute("username", username);
		session.setAttribute("words", wordDB);

		return "Admin_Home_Page";
		}else{
			int istart = Integer.valueOf(start);
			int iend = Integer.valueOf(end);
			List<WordDef> wordDB = dicService.getListByLimt(istart,iend);
			String username = principal.getName();
			model.addAttribute("username", username);
			session.setAttribute("words", wordDB);
			session.setAttribute("startP", istart+10);
			session.setAttribute("endP", iend);
			return "Admin_Home_Page";
		}
	}
	
	
	@RequestMapping("/listwordsbyPreviousPage")
	public String showListOfPWords(HttpSession session, Model model,@RequestParam("startP")String start,@RequestParam("endP")String end, Principal principal) {

		int istart = Integer.valueOf(start);
		if(istart < 10){
		List<WordDef> wordDB = dicService.getListByLimt(1,10);
		String username = principal.getName();
		model.addAttribute("username", username);
		session.setAttribute("words", wordDB);

		return "Admin_Home_Page";
		}else{
			int iend = Integer.valueOf(end);
			List<WordDef> wordDB = dicService.getListByLimt(istart,iend);
			String username = principal.getName();
			model.addAttribute("username", username);
			session.setAttribute("words", wordDB);
			session.setAttribute("startP", istart-10);
			session.setAttribute("endP", iend);
			return "Admin_Home_Page";
		}
	}
	
	
	@RequestMapping("/adminpage")
	public String showListOfWords(HttpSession session, Model model, Principal principal) {

		/*List<WordDef> wordDB = dicService.getList();*/
		List<WordDef> wordDB = dicService.getListByLimt(1,10);
		String username = principal.getName();
		model.addAttribute("username", username);
		session.setAttribute("words", wordDB);
		session.setAttribute("startP", "11");
		session.setAttribute("endP", "10");

		return "Admin_Home_Page";
	}

	@RequestMapping(value = "/addNewWord", method = RequestMethod.POST)
	public String insertNewWord(Model model, @ModelAttribute("insertWord")@Valid WordDef insertWord, RedirectAttributes redir, BindingResult result, HttpSession session,Principal principal) {
		if (result.hasErrors()) {	
			System.out.println("Form not validated...!");
			List<ObjectError> errors = result.getAllErrors();
			
			for(ObjectError error:errors){
				System.out.println(error.getDefaultMessage());
			}
			
			return "InsertWord";
		} else {
		
				if(dicService.getWordDetails(insertWord.getWord()) != null){
						
						String username = principal.getName();
						model.addAttribute("username", username);
						redir.addFlashAttribute("sysmessage", "Duplicated word cannot be inserted...!");
						return "redirect:/insertwordpage";
					}else{
						int wordN = dicService.getWordNum() + 1;
						insertWord.setWordNum(wordN);
						System.out.println("Word being inserted is - " + insertWord);
						dicService.insertWord(insertWord);
						System.out.println(insertWord+" Word Created...!");
						
						List<WordDef> wordDB = dicService.getListByLimt(1,10);
						String username = principal.getName();
						model.addAttribute("username", username);
						session.setAttribute("words", wordDB);
						session.setAttribute("startP", "11");
						session.setAttribute("endP", "10");
			
						return "Admin_Home_Page";
					}
	}}

	@RequestMapping("/updatewordDef")
	public String UpdateWordDef(Model model, HttpSession session,  @ModelAttribute("wordUpdate")@Valid WordDef wordUpdate, BindingResult result, Principal principal) {

		if (result.hasErrors()) {
			System.out.println("Form not validated...!");
			List<ObjectError> errors = result.getAllErrors();
			
			for(ObjectError error:errors){
				System.out.println(error.getDefaultMessage());
			}
			
			model.addAttribute("sysmessage", "Invaid details provided");
			return "Update_word_Details";
		} else {
			System.out.println("Word being updated is - " + wordUpdate);
			dicService.updateWord(wordUpdate);
			System.out.println(wordUpdate+" Word updated...!");
			
			List<WordDef> wordDB = dicService.getListByLimt(1,10);
			String username = principal.getName();
			model.addAttribute("username", username);
			session.setAttribute("words", wordDB);
			session.setAttribute("startP", "11");
			session.setAttribute("endP", "10");
			
			return "Admin_Home_Page";
		}
	}
	

	@RequestMapping("/insertwordpage")
	public String insertNewWordPage(Model model, HttpSession session) {

		// WordDef here is the CommandName in jsp file
		model.addAttribute("insertWord", new WordDef());
		return "InsertWord";
	}

	@RequestMapping(value= "/updatewordpage", method=RequestMethod.GET)
	public String updateWordPage(Model model, @RequestParam("word")String wordS) {
		
		WordDef wordL = dicService.getWordDetails(wordS);
		model.addAttribute("wordUpdate", wordL);
		
		return "Update_word_Details";
	}

	
	
	
	@RequestMapping("/deleteword")
	
	public String deleteWord(Model model, @RequestParam("word")String wordS){
		System.out.println(wordS);
		if(dicService.deleteWord(wordS)){
		model.addAttribute("name", "Word deleted from Database");
		List<WordDef> wordDB = dicService.getList();
		model.addAttribute("words", wordDB);
		return "Admin_Home_Page";
		}
		
		else{
			return null;
		}
	}
	

}
