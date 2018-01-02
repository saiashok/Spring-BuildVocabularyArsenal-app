package com.javaisfun.spring.web.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

public class User {

@NotBlank(message = "Username cannot be blank")
@Size(min=3, max=15)
/*@Pattern(regexp= "^\\w{8,}$" , message="Username can only consist of numbers, letters and the underscore character.")*/
private String username;

@NotBlank(message = "Password cannot be blank")
@Pattern(regexp="^\\S+$",  message="Password cannot contain spaces.")
@Size(min= 2, max= 15, message = "Password must be between 8 and 15 character long.")
private String password;

private String UserID;
private boolean enabled = false;

@NotBlank(message="Email cannot be blank.")
@Email
private String emailid;
private String authority;

/**
 * @param username
 * @param password
 * @param enabled
 * @param emailid
 */
/*public User(String username, String password, boolean enabled, String emailid) {
	this.username = username;
	this.password = password;
	this.enabled = enabled;
	this.emailid = emailid;
}*/
/**
 * 
 */
public User() {
}
public String getAuthority() {
	return authority;
}
public String getUsername() {
	return username;
}
public void setUsername(String username) {
	this.username = username;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public String getUserID() {
	return UserID;
}
public void setUserID(String string) {
	UserID = string;
}
public boolean isEnabled() {
	return enabled;
}
public void setEnabled(boolean enabled) {
	this.enabled = enabled;
}
public String getEmailid() {
	return emailid;
}
public void setEmailid(String emailid) {
	this.emailid = emailid;
}

public void setAuthority(String authority) {
	this.authority = authority;
}
@Override
public String toString() {
	return "User [username=" + username + ", password=" + password + ", UserID=" + UserID + ", enabled=" + enabled
			+ ", emailid=" + emailid + ", authority=" + authority + "]";
}


	
	
	
	
}
