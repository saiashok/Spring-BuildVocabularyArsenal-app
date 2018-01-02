package com.javaisfun.spring.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaisfun.spring.web.dao.UserDao;
import com.javaisfun.spring.web.model.User;

@Service("userservice")
public class UserService {

	private UserDao userdao;
	
	
	@Autowired
	public void setUserdao(UserDao userdao) {
		this.userdao = userdao;
	}
	
	

	public boolean createUser(User user) {
		return userdao.addNewUser(user);
	} 
	
	public int getMaxUserID(){
		return userdao.maxUserID();
	}
	
	public String getUserId(String username){
		return userdao.getUserId(username);
	}
	
	
	public String searchEmail(String email){
		return userdao.searchEmailId(email);
	}
	
	
	
	
}
