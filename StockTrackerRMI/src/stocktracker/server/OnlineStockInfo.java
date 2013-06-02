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
import stocktracker.api.StockList;

/**
 *
 * @author MrAtheist
 */
public class OnlineStockInfo {

    private static OnlineStockInfo stockInfoInstance = null;
    private Stock thisStock;

    /**
     *
     */
    protected OnlineStockInfo() {
		// Exists only to defeat instantiation.
    }

    /**
     *
     * @return the only OnlineStockInfo instance object
     */
    public static OnlineStockInfo getInstance() {

        if (stockInfoInstance == null) {

            synchronized (OnlineStockInfo.class) {

                OnlineStockInfo inst = stockInfoInstance;

                if (inst == null) {

                    synchronized (OnlineStockInfo.class) {
                        stockInfoInstance = new OnlineStockInfo();
                    }
                }
            }
        }

        return stockInfoInstance;
    }

	/**
	 * Function used to parse a Reader object into string
	 * @param rd	Reader object
	 * @return		string form of the reader object
	 * @throws IOException 
	 */
    private static String readAll(Reader rd) throws IOException {

        StringBuilder sb = new StringBuilder();
        int cp;

        while ((cp = rd.read()) != -1)
            sb.append((char) cp);

        return sb.toString();
    }

	/**
	 * Returns a JSONObject upon queried a YQL string
	 * @param url	YQL api query string
	 * @return		a JSONObject returned by YQL
	 * @throws IOException
	 * @throws JSONException 
	 */
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

    /** Return a stock object with up-to-date information queried from Yahoo Finance API with the given thisStock
     * @param thisStock		a Stock object
     * <p/>
     * @return				a Stock object with up-to-date information
     * <p/>
     * @throws JSONException
     */
    public static Stock getLatestStockInfo(Stock thisStock) throws JSONException {

        try {
            String yql = new StringBuilder(""
                    + "http://query.yahooapis.com/v1/public/yql?q="
                    + "select%20*%20from%20csv%20where%20url%3D%27http%3A%2F%2Fdownload.finance.yahoo.com%2Fd%2Fquotes.csv%3Fs%3D" 
					+ thisStock.getTickerName() 
					+ "%26f%3Dsl1d1t1c1ohgv%26e%3D.csv%27%20and%20columns%3D%27symbol%2Cprice%2Cdate%2Ctime%2Cchange%2Ccol1%2Chigh%2Clow%2Ccol2%27"
                    + "&format=json").toString();

            System.out.println(yql);
            JSONObject json = readJsonFromUrl(yql);
            System.out.println(json);
            JSONObject results = json.getJSONObject("query").getJSONObject("results").getJSONObject("row");

            if ((results.get("price") == null)) 
                thisStock = null;
            else
                thisStock.setPrice(Double.parseDouble(results.get("price").toString()));
			
        } catch (IOException ex) {
            Logger.getLogger(Stock.class.getName()).log(Level.SEVERE, null, ex);
        }

        return thisStock;
    }

    /**
     *	Update the StockList with up-to-date information from Yahoo Finance API
     */
    public static void updateStocks() {

        JSONArray arrResults;

        if (StockList.getInstance().getNumAllStocksTracked() != 0) {

            try {
                String yql = new StringBuilder(""
                        + "http://query.yahooapis.com/v1/public/yql?q="
                        + "select%20*%20from%20csv%20where%20url%3D%27http%3A%2F%2Fdownload.finance.yahoo.com%2Fd%2Fquotes.csv%3Fs%3D" + StockList.getInstance().getTickerNameAllStocksTracked() + "%26f%3Dsl1d1t1c1ohgv%26e%3D.csv%27%20and%20columns%3D%27symbol%2Cprice%2Cdate%2Ctime%2Cchange%2Ccol1%2Chigh%2Clow%2Ccol2%27"
                        + "&format=json").toString();

                System.out.println(yql);
                JSONObject json = readJsonFromUrl(yql);
                System.out.println(json);

                Object results = json.getJSONObject("query").getJSONObject("results").get("row");

                if (results instanceof JSONObject) {
                    arrResults = new JSONArray();
                    arrResults.put(0, results);
                } else {
                    arrResults = (JSONArray) results;
                }

                for (int i = 0; i < arrResults.length(); i++) {
                    JSONObject item = arrResults.getJSONObject(i);
                    StockList.getInstance().updateStock(new Stock(item.getString("symbol"), item.getDouble("price")));
                }

            } catch (IOException ex) {
                Logger.getLogger(Stock.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
