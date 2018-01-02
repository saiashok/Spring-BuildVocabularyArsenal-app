package com.javaisfun.spring.web.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.javaisfun.spring.web.model.Quote;


@Component("/brianyquotes")
public class QuotesFromBriany {
	URL brianyQuote;
	BufferedReader Buffreader;
	InputStream inputS;
	HttpURLConnection con;
	List<Quote> quoteList = new ArrayList<>();
	List<String> quoteJList = new ArrayList<>();

	public QuotesFromBriany(){

	}
	
	/*To get list of quotes from BrianyQuotes website
	 * 
	 */
	public List<Quote> getQuotefor(String word) throws IOException{
		
		//Remove all the existing quotes before we get the new list
		quoteList.removeAll(quoteList);
		
		String Qword = word;
		String currentLine;
		
		//Create String value for search word
		String search = "q="+Qword; 
		String url = "https://www.brainyquote.com/search_results.html?"+search;
		
		//Instanstiate URL object with url string
		brianyQuote	= new URL(url);
		
		//downcasting  url connection to Http Url Connection		
		HttpURLConnection con = (HttpURLConnection) brianyQuote.openConnection();
		
		/*System.out.println(url+" is Connected! & "+"query is "+ brianyQuote.getQuery());*/
			
		// if we dont set the "User-Agent" property we get 403 Forbidden error
		con.addRequestProperty("User-Agent", "Mozilla/4.0");
		
	
		/*System.out.println("Response code for briany connection "+con.getResponseCode());*/
		if (con.getResponseCode() == 200){  // this must be called before 'getErrorStream()' works
	
			//getting input conection from the established http url connection
			inputS = con.getInputStream();
			
		
		Buffreader = new BufferedReader(new InputStreamReader(inputS));	
String quoteLine ="";
		while ((currentLine = Buffreader.readLine()) != null){

			//serching for the quote in the html file (input stream)
			int i= currentLine.lastIndexOf("title=\"view quote\">");
			quoteLine += currentLine;
			if(i != -1){
				int j = currentLine.indexOf("</a>");

				String	QuoteString = currentLine.substring(i+19, j);

				//Read next line for Author
				currentLine = Buffreader.readLine();
				//searching for the Author in html input stream
				int k = currentLine.indexOf("title=\"view author\">");
				int m = currentLine.indexOf("</a>");

				String	authorString = currentLine.substring(k+20, m);

				Quote tempQuote = new Quote(QuoteString,authorString,Qword);
				quoteList.add(tempQuote);	

			}


		}

		if(quoteList.isEmpty()){
			Quote q = new Quote();
			q.setQuote("No quotes found for \""+ word+"\"");
			q.setAuthor("None");
			q.setWord(Qword);
			quoteList.add(q);
		}
		System.out.println(quoteList);
		return quoteList;
		}
		else {
			inputS = con.getErrorStream();
			
			return null;
		}
	}

}


