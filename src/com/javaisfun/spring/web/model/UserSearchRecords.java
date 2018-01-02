package com.javaisfun.spring.web.model;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.stereotype.Component;


public class UserSearchRecords {

	private String userid;
	private int word_searched;
	private Timestamp timestamp;
	
	public UserSearchRecords() {
		
	}
	
	
	
	public UserSearchRecords(String userid, int word_searched, Timestamp timestamp) {
		super();
		this.userid = userid;
		this.word_searched = word_searched;
		this.timestamp = timestamp;
	}
	
	public String getUserid() {
		return userid;
	}
	
	
	public Timestamp getTimestamp() {
		return timestamp;
	}



	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}



	public void setUserid(String userid) {
		this.userid = userid;
	}
	public int getWord_searched() {
		return word_searched;
	}
	public void setWord_searched(int word_searched) {
		this.word_searched = word_searched;
	}
	@Override
	public String toString() {
		return "UserSearchRecords [userid=" + userid + ", word_searched=" + word_searched + ", timestamp=" + timestamp + "]";
	}
	
	
}
