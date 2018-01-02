package com.javaisfun.spring.web.model;

public class Quote {

	public String quote;
	public String author;
	public String word;
	
	public Quote(String quote, String author, String word) {
		super();
		this.quote = quote;
		this.author = author;
		this.word =word;
	}
	
	public Quote(){
		
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public String getQuote() {
		return quote;
	}
	public void setQuote(String quote) {
		this.quote = quote;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	@Override
	public String toString() {
		return "Quote [quote=" + quote + ", author=" + author + ", word=" + word + "]";
	}
	
	public String getJsonString(){
		return "{\"quote\":\"" + quote + "\", \"author\":\"" + author + "\", \"word\":\"" + word + "\""+"}";
	}
}
