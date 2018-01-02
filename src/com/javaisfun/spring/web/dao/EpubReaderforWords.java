package com.javaisfun.spring.web.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.javaisfun.spring.web.model.Book_Data;
import com.javaisfun.spring.web.model.WordDef;

import nl.siegmann.epublib.domain.Author;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.domain.Spine;
import nl.siegmann.epublib.domain.SpineReference;
import nl.siegmann.epublib.epub.EpubReader;

@Component("readepub")
public class EpubReaderforWords {
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

	}	
	public EpubReaderforWords(){
		
	}
	
	private List<String> insertedWords = new ArrayList<String>();

	public  List<String> getTextOfBook(String path) throws FileNotFoundException, IOException{
		insertedWords.clear();	
		EpubReader epubReader = new EpubReader();
		Book book = epubReader.readEpub(new FileInputStream(path));
		Spine s = book.getSpine();
		List<SpineReference> spineList = s.getSpineReferences();
		int count = spineList.size();
		StringBuilder str = new StringBuilder();
		String line = "";
		String linez ="";
		for (int i = 0; count > i; i++) {
	        Resource res = s.getResource(i);

	        try {
	            InputStream is = res.getInputStream();
	            BufferedReader reader = new BufferedReader(
	                    new InputStreamReader(is));
	            try {
	                while ((line = reader.readLine()) != null) {
	                    linez = str.append(line + "\n").toString();
	    
	                }

	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

		}	
		Document doc = Jsoup.parse(linez);
		 String title = book.getMetadata().getFirstTitle();
         String body = doc.body().text();
         List<Author> listauthors = book.getMetadata().getAuthors();
         String author ="";
         
         if(listauthors.size()>1)
	      for(Author auth: listauthors ){
	    	  author += auth.toString()+" &";
	      }  else{
	    	  for(Author auth: listauthors ){
		    	  author += auth.toString();
		      }
	      }
	      System.out.printf("Title: %s%n", title);
    
         if(title != null && body != null){
        	 MapSqlParameterSource params = new MapSqlParameterSource();
        	 params.addValue("book_title", title);
        	 params.addValue("book_author", author);
        	 String sql =   "Insert into dictionary.books_list (book_title, book_author) values (:book_title, :book_author )";
        	 jdbcTemplate.update(sql, params);
         }
         
		return getWordsNowToInsert(body);
			
	}
	
	public List<String> getWordsNowToInsert(String bookT) throws FileNotFoundException, IOException{
		String value = new String(bookT.getBytes("UTF-8"));
		String newS = value.toLowerCase().replaceAll("[\"\'!?,:;.-]", "");
		String[] x = newS.split("\\s+");
		/*Set<String> hash_Set = new HashSet<String>();*/
		List<String> list = new ArrayList<String>();
		for(String s : x){			
			if(s.chars().allMatch(Character::isLetter)){
				list.add(s);
				/*hash_Set.add(s);*/
			}
		}
		int book_id= maxBookId();
		Set<String> unique = new HashSet<String>(list);
		
		for (String key : unique) {
			int freq = Collections.frequency(list, key);
			 if(key.length()>3 && key.length()<30 && freq < 50){
			    insertedWords.add(key);
			    MapSqlParameterSource params = new MapSqlParameterSource();
			    params.addValue("word", key);
			    params.addValue("book_id", book_id);
			    params.addValue("frequency", freq);
			    String sql =   "Insert into dictionary.wordsfrombooks (words, book_id, frequency) values (:word, :book_id, :frequency)";
				jdbcTemplate.update(sql, params);
			    }
		}
		
		
		return insertedWords;
	}
	
public int maxBookId(){
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		String sql = "select max(book_id) from dictionary.books_list"; 
		
		int maxNum = jdbcTemplate.queryForObject(sql, params, new RowMapper<Integer>(){
			
			@Override
			public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
			int wordNum = rs.getInt("max(book_id)") ;
				return wordNum;
			}
			
		}
		);
		
		return maxNum;
	}


public List<Book_Data> getListOfBooks(){
	

	String sql = "SELECT * FROM dictionary.books_list";
	List<Book_Data> bookList = jdbcTemplate.query(sql, new RowMapper<Book_Data>(){

		@Override
		public Book_Data mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			
			Book_Data bookDetails = new Book_Data();

			bookDetails.setBook_id(rs.getInt("book_id"));
			bookDetails.setBook_title(rs.getString("book_title"));
			bookDetails.setBook_author(rs.getString("book_author"));
			

			return bookDetails;
		}
		
		
	});

	System.out.println("List of Books in database.."+bookList);
	return bookList;
}

public List<String> getVocabList(int book_id, int frequency){
	MapSqlParameterSource params = new MapSqlParameterSource();
	params.addValue("book_id", book_id);
	params.addValue("frequency", frequency);
	String sql = "SELECT words FROM dictionary.wordsfrombooks where book_id= :book_id and frequency =:frequency;"; 
	
	List<String> listWordsOfBook = jdbcTemplate.query(sql, params, new RowMapper<String>(){
		
		@Override
		public String mapRow(ResultSet rs, int rowNum) throws SQLException {
		String wrd = rs.getString("words") ;
			return wrd;
		}
		
	}
	);
	return listWordsOfBook;
	
}	

}
