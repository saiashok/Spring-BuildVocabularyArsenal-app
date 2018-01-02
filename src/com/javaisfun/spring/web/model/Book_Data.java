package com.javaisfun.spring.web.model;

public class Book_Data {

	private int book_id;
	private String book_title;
	private String book_author;
	
	public Book_Data() {
		
	}
	
	
	public Book_Data(int book_id, String book_title, String book_author) {
		super();
		this.book_id = book_id;
		this.book_title = book_title;
		this.book_author = book_author;
	}
	public int getBook_id() {
		return book_id;
	}
	public void setBook_id(int book_id) {
		this.book_id = book_id;
	}
	public String getBook_title() {
		return book_title;
	}
	public void setBook_title(String book_title) {
		this.book_title = book_title;
	}
	public String getBook_author() {
		return book_author;
	}
	public void setBook_author(String book_author) {
		this.book_author = book_author;
	}


	@Override
	public String toString() {
		return "Book_Data [book_id=" + book_id + ", book_title=" + book_title + ", book_author=" + book_author + "]";
	}
	
	
	
}
