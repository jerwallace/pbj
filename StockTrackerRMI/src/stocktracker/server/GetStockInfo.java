/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stocktracker.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;

import org.json.JSONException;
import org.json.JSONObject;
import stocktracker.api.Stock;

/**
 *
 * @author MrAtheist
 */
public class GetStockInfo {
	
    
	private static String uri = "(";
	private String tickerName;
	private double price;
        private static GetStockInfo stockInfoConnector = null;
        
        protected GetStockInfo() {

        }
        
	public static GetStockInfo getInstance(String[] StockArr) {
		
		// StockArr holds a string array of tickerNames
		// sample uri = (\"GOOG\",\"YHOO\",\"AMZN\")
                
                stockInfoConnector = new GetStockInfo();
                
                for (String ticker : StockArr)
		uri = uri.concat("\"" + ticker + "\"");
		
		uri = uri.concat(")"); 		// closeing bracket
		
		try {
			run();
		} catch (JSONException ex) {
			Logger.getLogger(Stock.class.getName()).log(Level.SEVERE, null, ex);
		}
                return stockInfoConnector;
	}
	
	
	private String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}
	
	public JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
		InputStream is = new URL(url).openStream();
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			JSONObject json = new JSONObject(jsonText);
			return json;
		} finally {
			is.close();
		}
	}
	
	public static void run() throws JSONException {
		try {
			String yql = new StringBuilder("http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.quotes%20where%20symbol%20in%20")
							.append(uri).append("%0A%09%09&format=json&diagnostics=true&env=http%3A%2F%2Fdatatables.org%2Falltables.env&callback=").toString();
		
			System.out.println(yql);
			JSONObject json = readJsonFromUrl(yql);
			System.out.println(json);
			JSONArray results = json.getJSONObject("query").getJSONObject("results").getJSONArray("quote");

//			JSONObject duration = elements.getJSONObject("duration");
//			JSONObject distance = elements.getJSONObject("duration");
	//		this.price = distance.get("text").toString();
	//		this.price = distance.get("text").toString();
                        
                        
		} catch (IOException ex) {
			Logger.getLogger(Stock.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	/**
	 * @return the tickerName
	 */
	public String getTickerName() {
		return tickerName;
	}
	
	/**
	 * @param tickerName the tickerName to set
	 */
	public void setTickerName(String tickerName) {
		this.tickerName = tickerName;
	}
	
	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}
	
	/**
	 * @param price the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}
	
}