package com.javaisfun.spring.web.dao;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import nl.siegmann.epublib.domain.Author;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.domain.Spine;
import nl.siegmann.epublib.domain.SpineReference;
import nl.siegmann.epublib.epub.EpubReader;

public class SampleEpubReader {
	
	public void getListByCountTen() throws FileNotFoundException, IOException{
		EpubReader epubReader = new EpubReader();
		Book book = epubReader.readEpub(new FileInputStream("C:/Users/Sai Ashok Kumar/Documents/Calibre Library/Adam Phillips/Going Sane (237)/Going Sane - Adam Phillips.epub"));

			Spine s = book.getSpine();
			List<SpineReference> spineList = s.getSpineReferences();
			int count = spineList.size();
			StringBuilder str = new StringBuilder();
			String line = "";
			String linez ="";
			 String[] wordsA= null;
			for (int i = 0; count > i; i++) {
		        Resource res = s.getResource(i);

		        try {
		            InputStream is = res.getInputStream();
		            BufferedReader reader = new BufferedReader(
		                    new InputStreamReader(is));
		            try {
		                while ((line = reader.readLine()) != null) {
		                    linez = str.append(line + "\n").toString();
		                    wordsA = line.split("\\s+");
		    
		                }

		            } catch (IOException e) {
		                e.printStackTrace();
		            }
				
		            // do something with stream
		        } catch (IOException e) {
		            e.printStackTrace();
		        }

			}	
			Document doc = Jsoup.parse(linez);
			 String title = book.getMetadata().getFirstTitle();
	         String body = doc.body().text();
	         List<Author> listauthors = book.getMetadata().getAuthors();
	         String author ="";
	      for(Author auth: listauthors ){
	    	  author += auth.toString()+" &";
	      }
	      
	      System.out.println("Author is "+author);
	        System.out.println(book.getMetadata().getFirstTitle().length());
	        System.out.println(book.getMetadata().getAuthors());
	        /*   System.out.println(doc.data());*/
	         
	         /*   System.out.println("Text is "+ doc.select("div").text());*/
	         
	       /*  System.out.println("Text is "+ doc.getElementsByClass("crt").text());*/
	      System.out.printf("Title: %s%n", title);
	        /* System.out.printf("Body: %s", body);*/
	        
	         
	}

	public static void main(String[] args) throws FileNotFoundException, IOException {

		
		SampleEpubReader s = new SampleEpubReader();
		s.getListByCountTen();
	}

}
