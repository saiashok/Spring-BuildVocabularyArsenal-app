package com.javaisfun.spring.web.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.javaisfun.spring.web.model.User;
import com.javaisfun.spring.web.model.WordDef;
import com.javaisfun.spring.web.service.UserService;

@Controller("logincontroller")
public class LoginController {
	
	private UserService userServ;
	
	
	@Autowired
	private MailSender mailSender;
	
	@Autowired
	public void setUserServ(UserService userServ) {
		this.userServ = userServ;
	}

	@RequestMapping("/login")
	public String doLoginAuth(){
		
		return "login";
		
	}
	
	
	
	@RequestMapping("/loggedout")
	public String doLogout(){
		System.out.println("User Loggedout");
		return "login";
		
	}
	
	@RequestMapping("/newaccountpage")
	public String showNewAccount(Model model){
		
		model.addAttribute("user", new User());
		return "newaccount";
		
	}
	
	@RequestMapping(value = "/createaccount", method = RequestMethod.POST)
	public String createNewAccount(Model model, @Valid User user, BindingResult result){
		
		
		
		if (result.hasErrors()) {

			System.out.println("Form not validated...!");
			return "newaccount";
		} else {
			System.out.println("Form validated...!");
			
			/*String s = userServ.getUserId(user.getEmailid());*/
			
			int newUserId = userServ.getMaxUserID() + 1;
			user.setUserID(""+newUserId);
			user.setEnabled(true);
			user.setAuthority("ROLE_USER");
			try{
			if(userServ.createUser(user)){
				
				System.out.println("Account created..!");
				String emailid = user.getEmailid();
				
				SimpleMailMessage mail = new SimpleMailMessage();
				mail.setFrom("reddysaiashok29@gmail.com");
				mail.setTo(emailid);
				mail.setSubject("Account created..!");
				mail.setText("Registration successful: "
						+ " UserName:"+ user.getUsername());
				try {
					mailSender.send(mail);
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("Can't send message");
				}
			}
			
			}catch(DuplicateKeyException e){
				result.rejectValue("username", "DuplicateKey.user.username", "Username already exists!");
				result.rejectValue("emailid", "DuplicateKey.user.emailid", "Username already exists!");
				return "newaccount";
			}
			model.addAttribute("message", "Account created. Please login to continue");
			
			return "login";
		}
		
		
	}

}
