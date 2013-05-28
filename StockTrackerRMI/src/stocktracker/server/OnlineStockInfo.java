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
public class OnlineStockInfo {
	
	private static String uri = "(";
	private static OnlineStockInfo stockInfoInstance = null;
        private Stock thisStock;
        
	protected OnlineStockInfo() {

	}
        
	public static OnlineStockInfo getInstance() {
		
		if (stockInfoInstance == null) {

			synchronized(OnlineStockInfo.class) {

				OnlineStockInfo inst = stockInfoInstance;

				if (inst == null) {

					synchronized(OnlineStockInfo.class) {
						stockInfoInstance = new OnlineStockInfo();
					}
				}
			}
		}
                
		return stockInfoInstance;
	}
	
	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}
	
	private static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
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

    public static Stock getLatestStockInfo(Stock thisStock) throws JSONException {

        try {
            String yql = new StringBuilder(""
                    + "http://query.yahooapis.com/v1/public/yql?q="
                    + "select%20*%20from%20yahoo.finance.quotes%20where%20symbol%20%3D%20%22"+thisStock.getTickerName()+"%22%0A%09%09"
                    + "&format=json"
                    + "&diagnostics=true"
                    + "&env=http%3A%2F%2Fdatatables.org%2Falltables.env"
                    + "&callback=").toString();

            System.out.println(yql);
            JSONObject json = readJsonFromUrl(yql);
            System.out.println(json);
            JSONObject results = json.getJSONObject("query").getJSONObject("results").getJSONObject("quote");
            thisStock.setPrice(Double.parseDouble(results.get("Bid").toString())+Double.parseDouble(results.get("Ask").toString()) / 2);
        } 
        catch (IOException ex) {
            Logger.getLogger(Stock.class.getName()).log(Level.SEVERE, null, ex);
        }

        return thisStock;
    }
}