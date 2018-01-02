package com.javaisfun.spring.web.model;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

public class WordDef {
	
	private int wordNum;
	
	
	@NotBlank(message = "Please enter the word")
	@Size(min=1, max= 29,message= "Word field is not valid")
	@Pattern(regexp="^[A-za-z]*$")
	private String word;
	

	@NotBlank(message = "Please enter the word Type")
	@Size(min=1, max= 10,message= "Word-Type field cannot be null")
	private String wordType;
	

	@NotBlank(message = "Please enter the word Defintion")
	@Size(min=4, message= "Word-Definition field cannot be null")
	private String wordDef;
	
	public WordDef(){
		
	}
	
	public WordDef(int wordNum, String word, String wordType, String wordDef) {
		this.word = word;
		this.wordType = wordType;
		this.wordDef = wordDef;
	}
	
	public int getWordNum() {
		return wordNum;
	}

	public void setWordNum(int wordNum) {
		this.wordNum = wordNum;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getWordType() {
		return wordType;
	}

	public void setWordType(String wordType) {
		this.wordType = wordType;
	}

	public String getWordDef() {
		return wordDef;
	}

	public void setWordDef(String wordDef) {
		this.wordDef = wordDef;
	}

	@Override
	public String toString() {
		return "WordDef [wordNum=" + wordNum + ", word=" + word + ", wordType=" + wordType + ", wordDef=" + wordDef
				+ "]";
	}
	
	
	
	

}
