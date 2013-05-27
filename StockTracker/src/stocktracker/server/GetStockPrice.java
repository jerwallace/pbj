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

/**
 *
 * @author MrAtheist
 */
public class GetStockPrice implements Runnable{
	
	private String uri = "(";
	private String tickerName;
	private double price;
	
	/*	EXAMPLE JSON OBJECT
	 {
 "query": {
  "count": 4,
  "created": "2013-05-27T03:34:37Z",
  "lang": "en-US",
  "diagnostics": {
   "publiclyCallable": "true",
   "redirect": {
    "from": "http://datatables.org/alltables.env",
    "status": "301",
    "content": "http://www.datatables.org/alltables.env"
   },
   "url": [
    {
     "execution-start-time": "1",
     "execution-stop-time": "64",
     "execution-time": "63",
     "proxy": "DEFAULT",
     "content": "http://datatables.org/alltables.env"
    },
    {
     "execution-start-time": "85",
     "execution-stop-time": "109",
     "execution-time": "24",
     "proxy": "DEFAULT",
     "content": "http://www.datatables.org/yahoo/finance/yahoo.finance.quotes.xml"
    },
    {
     "execution-start-time": "114",
     "execution-stop-time": "173",
     "execution-time": "59",
     "proxy": "DEFAULT",
     "content": "http://download.finance.yahoo.com/d/quotes.csv?f=aa2bb2b3b4cc1c3c6c8dd1d2ee1e7e8e9ghjkg1g3g4g5g6ii5j1j3j4j5j6k1k2k4k5ll1l2l3mm2m3m4m5m6m7m8nn4opp1p2p5p6qrr1r2r5r6r7ss1s7t1t7t8vv1v7ww1w4xy&s=YHOO,AAPL,GOOG,MSFT"
    }
   ],
   "cache": [
    {
     "execution-start-time": "111",
     "execution-stop-time": "112",
     "execution-time": "1",
     "method": "GET",
     "type": "MEMCACHED",
     "content": "13c4f8eac77ad886bade5a711c8c1ef5"
    },
    {
     "execution-start-time": "112",
     "execution-stop-time": "112",
     "execution-time": "0",
     "method": "GET",
     "type": "MEMCACHED",
     "content": "70.79.141.87"
    }
   ],
   "query": {
    "execution-start-time": "114",
    "execution-stop-time": "173",
    "execution-time": "59",
    "params": "{url=[http://download.finance.yahoo.com/d/quotes.csv?f=aa2bb2b3b4cc1c3c6c8dd1d2ee1e7e8e9ghjkg1g3g4g5g6ii5j1j3j4j5j6k1k2k4k5ll1l2l3mm2m3m4m5m6m7m8nn4opp1p2p5p6qrr1r2r5r6r7ss1s7t1t7t8vv1v7ww1w4xy&s=YHOO,AAPL,GOOG,MSFT]}",
    "content": "select * from csv where url=@url and columns='Ask,AverageDailyVolume,Bid,AskRealtime,BidRealtime,BookValue,Change&PercentChange,Change,Commission,ChangeRealtime,AfterHoursChangeRealtime,DividendShare,LastTradeDate,TradeDate,EarningsShare,ErrorIndicationreturnedforsymbolchangedinvalid,EPSEstimateCurrentYear,EPSEstimateNextYear,EPSEstimateNextQuarter,DaysLow,DaysHigh,YearLow,YearHigh,HoldingsGainPercent,AnnualizedGain,HoldingsGain,HoldingsGainPercentRealtime,HoldingsGainRealtime,MoreInfo,OrderBookRealtime,MarketCapitalization,MarketCapRealtime,EBITDA,ChangeFromYearLow,PercentChangeFromYearLow,LastTradeRealtimeWithTime,ChangePercentRealtime,ChangeFromYearHigh,PercebtChangeFromYearHigh,LastTradeWithTime,LastTradePriceOnly,HighLimit,LowLimit,DaysRange,DaysRangeRealtime,FiftydayMovingAverage,TwoHundreddayMovingAverage,ChangeFromTwoHundreddayMovingAverage,PercentChangeFromTwoHundreddayMovingAverage,ChangeFromFiftydayMovingAverage,PercentChangeFromFiftydayMovingAverage,Name,Notes,Open,PreviousClose,PricePaid,ChangeinPercent,PriceSales,PriceBook,ExDividendDate,PERatio,DividendPayDate,PERatioRealtime,PEGRatio,PriceEPSEstimateCurrentYear,PriceEPSEstimateNextYear,Symbol,SharesOwned,ShortRatio,LastTradeTime,TickerTrend,OneyrTargetPrice,Volume,HoldingsValue,HoldingsValueRealtime,YearRange,DaysValueChange,DaysValueChangeRealtime,StockExchange,DividendYield'"
   },
   "javascript": {
    "execution-time": "87",
    "instructions-used": "273821",
    "table-name": "yahoo.finance.quotes"
   },
   "user-time": "200",
   "service-time": "147",
   "build-version": "37079"
  },
  "results": {
   "quote": [
    {
     "symbol": "YHOO",
     "Ask": "27.00",
     "AverageDailyVolume": "19962600",
     "Bid": "26.34",
     "AskRealtime": "27.00",
     "BidRealtime": "26.34",
     "BookValue": "13.035",
     "Change_PercentChange": "+0.31 - +1.19%",
     "Change": "+0.31",
     "Commission": null,
     "ChangeRealtime": "+0.31",
     "AfterHoursChangeRealtime": "N/A - N/A",
     "DividendShare": "0.00",
     "LastTradeDate": "5/24/2013",
     "TradeDate": null,
     "EarningsShare": "3.446",
     "ErrorIndicationreturnedforsymbolchangedinvalid": null,
     "EPSEstimateCurrentYear": "1.41",
     "EPSEstimateNextYear": "1.54",
     "EPSEstimateNextQuarter": "0.34",
     "DaysLow": "25.65",
     "DaysHigh": "26.48",
     "YearLow": "14.59",
     "YearHigh": "24.99",
     "HoldingsGainPercent": "- - -",
     "AnnualizedGain": null,
     "HoldingsGain": null,
     "HoldingsGainPercentRealtime": "N/A - N/A",
     "HoldingsGainRealtime": null,
     "MoreInfo": "cn",
     "OrderBookRealtime": null,
     "MarketCapitalization": "28.506B",
     "MarketCapRealtime": null,
     "EBITDA": "1.327B",
     "ChangeFromYearLow": "+11.74",
     "PercentChangeFromYearLow": "+80.47%",
     "LastTradeRealtimeWithTime": "N/A - <b>26.33</b>",
     "ChangePercentRealtime": "N/A - +1.19%",
     "ChangeFromYearHigh": "+1.34",
     "PercebtChangeFromYearHigh": "+5.36%",
     "LastTradeWithTime": "May 24 - <b>26.33</b>",
     "LastTradePriceOnly": "26.33",
     "HighLimit": null,
     "LowLimit": null,
     "DaysRange": "25.65 - 26.48",
     "DaysRangeRealtime": "N/A - N/A",
     "FiftydayMovingAverage": "23.268",
     "TwoHundreddayMovingAverage": "20.0919",
     "ChangeFromTwoHundreddayMovingAverage": "+6.2381",
     "PercentChangeFromTwoHundreddayMovingAverage": "+31.05%",
     "ChangeFromFiftydayMovingAverage": "+3.062",
     "PercentChangeFromFiftydayMovingAverage": "+13.16%",
     "Name": "Yahoo! Inc.",
     "Notes": null,
     "Open": "25.90",
     "PreviousClose": "26.02",
     "PricePaid": null,
     "ChangeinPercent": "+1.19%",
     "PriceSales": "5.74",
     "PriceBook": "2.00",
     "ExDividendDate": null,
     "PERatio": "7.55",
     "DividendPayDate": null,
     "PERatioRealtime": null,
     "PEGRatio": "1.36",
     "PriceEPSEstimateCurrentYear": "18.45",
     "PriceEPSEstimateNextYear": "16.90",
     "Symbol": "YHOO",
     "SharesOwned": null,
     "ShortRatio": "1.50",
     "LastTradeTime": "4:00pm",
     "TickerTrend": " ====== ",
     "OneyrTargetPrice": "27.48",
     "Volume": "14974345",
     "HoldingsValue": null,
     "HoldingsValueRealtime": null,
     "YearRange": "14.59 - 24.99",
     "DaysValueChange": "- - +1.19%",
     "DaysValueChangeRealtime": "N/A - N/A",
     "StockExchange": "NasdaqNM",
     "DividendYield": null,
     "PercentChange": "+1.19%"
    },
    {
     "symbol": "AAPL",
     "Ask": "445.14",
     "AverageDailyVolume": "17939600",
     "Bid": "445.00",
     "AskRealtime": "445.14",
     "BidRealtime": "445.00",
     "BookValue": "144.124",
     "Change_PercentChange": "+3.01 - +0.68%",
     "Change": "+3.01",
     "Commission": null,
     "ChangeRealtime": "+3.01",
     "AfterHoursChangeRealtime": "N/A - N/A",
     "DividendShare": "7.95",
     "LastTradeDate": "5/24/2013",
     "TradeDate": null,
     "EarningsShare": "41.896",
     "ErrorIndicationreturnedforsymbolchangedinvalid": null,
     "EPSEstimateCurrentYear": "39.58",
     "EPSEstimateNextYear": "43.70",
     "EPSEstimateNextQuarter": "8.25",
     "DaysLow": "440.36",
     "DaysHigh": "445.66",
     "YearLow": "385.10",
     "YearHigh": "705.07",
     "HoldingsGainPercent": "- - -",
     "AnnualizedGain": null,
     "HoldingsGain": null,
     "HoldingsGainPercentRealtime": "N/A - N/A",
     "HoldingsGainRealtime": null,
     "MoreInfo": "cnsprmiIed",
     "OrderBookRealtime": null,
     "MarketCapitalization": "417.8B",
     "MarketCapRealtime": null,
     "EBITDA": "57.381B",
     "ChangeFromYearLow": "+60.05",
     "PercentChangeFromYearLow": "+15.59%",
     "LastTradeRealtimeWithTime": "N/A - <b>445.15</b>",
     "ChangePercentRealtime": "N/A - +0.68%",
     "ChangeFromYearHigh": "-259.92",
     "PercebtChangeFromYearHigh": "-36.86%",
     "LastTradeWithTime": "May 24 - <b>445.15</b>",
     "LastTradePriceOnly": "445.15",
     "HighLimit": null,
     "LowLimit": null,
     "DaysRange": "440.36 - 445.66",
     "DaysRangeRealtime": "N/A - N/A",
     "FiftydayMovingAverage": "431.59",
     "TwoHundreddayMovingAverage": "505.657",
     "ChangeFromTwoHundreddayMovingAverage": "-60.507",
     "PercentChangeFromTwoHundreddayMovingAverage": "-11.97%",
     "ChangeFromFiftydayMovingAverage": "+13.56",
     "PercentChangeFromFiftydayMovingAverage": "+3.14%",
     "Name": "Apple Inc.",
     "Notes": null,
     "Open": "440.52",
     "PreviousClose": "442.14",
     "PricePaid": null,
     "ChangeinPercent": "+0.68%",
     "PriceSales": "2.45",
     "PriceBook": "3.07",
     "ExDividendDate": "Feb  7",
     "PERatio": "10.55",
     "DividendPayDate": "May 16",
     "PERatioRealtime": null,
     "PEGRatio": "0.53",
     "PriceEPSEstimateCurrentYear": "11.17",
     "PriceEPSEstimateNextYear": "10.12",
     "Symbol": "AAPL",
     "SharesOwned": null,
     "ShortRatio": "2.30",
     "LastTradeTime": "4:00pm",
     "TickerTrend": " +===== ",
     "OneyrTargetPrice": "540.67",
     "Volume": "9872377",
     "HoldingsValue": null,
     "HoldingsValueRealtime": null,
     "YearRange": "385.10 - 705.07",
     "DaysValueChange": "- - +0.68%",
     "DaysValueChangeRealtime": "N/A - N/A",
     "StockExchange": "NasdaqNM",
     "DividendYield": "1.80",
     "PercentChange": "+0.68%"
    },
    {
     "symbol": "GOOG",
     "Ask": "874.40",
     "AverageDailyVolume": "2320560",
     "Bid": "873.40",
     "AskRealtime": "874.40",
     "BidRealtime": "873.40",
     "BookValue": "228.01",
     "Change_PercentChange": "-9.47 - -1.07%",
     "Change": "-9.47",
     "Commission": null,
     "ChangeRealtime": "-9.47",
     "AfterHoursChangeRealtime": "N/A - N/A",
     "DividendShare": "0.00",
     "LastTradeDate": "5/24/2013",
     "TradeDate": null,
     "EarningsShare": "33.422",
     "ErrorIndicationreturnedforsymbolchangedinvalid": null,
     "EPSEstimateCurrentYear": "45.81",
     "EPSEstimateNextYear": "53.16",
     "EPSEstimateNextQuarter": "11.17",
     "DaysLow": "871.01",
     "DaysHigh": "878.82",
     "YearLow": "556.52",
     "YearHigh": "844.00",
     "HoldingsGainPercent": "- - -",
     "AnnualizedGain": null,
     "HoldingsGain": null,
     "HoldingsGainPercentRealtime": "N/A - N/A",
     "HoldingsGainRealtime": null,
     "MoreInfo": "cnprmiIed",
     "OrderBookRealtime": null,
     "MarketCapitalization": "289.7B",
     "MarketCapRealtime": null,
     "EBITDA": "16.813B",
     "ChangeFromYearLow": "+316.80",
     "PercentChangeFromYearLow": "+56.93%",
     "LastTradeRealtimeWithTime": "N/A - <b>873.32</b>",
     "ChangePercentRealtime": "N/A - -1.07%",
     "ChangeFromYearHigh": "+29.32",
     "PercebtChangeFromYearHigh": "+3.47%",
     "LastTradeWithTime": "May 24 - <b>873.32</b>",
     "LastTradePriceOnly": "873.32",
     "HighLimit": null,
     "LowLimit": null,
     "DaysRange": "871.01 - 878.82",
     "DaysRangeRealtime": "N/A - N/A",
     "FiftydayMovingAverage": "805.294",
     "TwoHundreddayMovingAverage": "744.272",
     "ChangeFromTwoHundreddayMovingAverage": "+129.048",
     "PercentChangeFromTwoHundreddayMovingAverage": "+17.34%",
     "ChangeFromFiftydayMovingAverage": "+68.026",
     "PercentChangeFromFiftydayMovingAverage": "+8.45%",
     "Name": "Google Inc.",
     "Notes": null,
     "Open": "875.26",
     "PreviousClose": "882.79",
     "PricePaid": null,
     "ChangeinPercent": "-1.07%",
     "PriceSales": "5.47",
     "PriceBook": "3.87",
     "ExDividendDate": null,
     "PERatio": "26.41",
     "DividendPayDate": null,
     "PERatioRealtime": null,
     "PEGRatio": "1.29",
     "PriceEPSEstimateCurrentYear": "19.27",
     "PriceEPSEstimateNextYear": "16.61",
     "Symbol": "GOOG",
     "SharesOwned": null,
     "ShortRatio": "1.70",
     "LastTradeTime": "4:00pm",
     "TickerTrend": " ====== ",
     "OneyrTargetPrice": "922.82",
     "Volume": "2296760",
     "HoldingsValue": null,
     "HoldingsValueRealtime": null,
     "YearRange": "556.52 - 844.00",
     "DaysValueChange": "- - -1.07%",
     "DaysValueChangeRealtime": "N/A - N/A",
     "StockExchange": "NasdaqNM",
     "DividendYield": null,
     "PercentChange": "-1.07%"
    },
    {
     "symbol": "MSFT",
     "Ask": "34.22",
     "AverageDailyVolume": "49855600",
     "Bid": "34.02",
     "AskRealtime": "34.22",
     "BidRealtime": "34.02",
     "BookValue": "9.185",
     "Change_PercentChange": "+0.119 - +0.35%",
     "Change": "+0.119",
     "Commission": null,
     "ChangeRealtime": "+0.119",
     "AfterHoursChangeRealtime": "N/A - N/A",
     "DividendShare": "0.86",
     "LastTradeDate": "5/24/2013",
     "TradeDate": null,
     "EarningsShare": "1.938",
     "ErrorIndicationreturnedforsymbolchangedinvalid": null,
     "EPSEstimateCurrentYear": "2.76",
     "EPSEstimateNextYear": "3.06",
     "EPSEstimateNextQuarter": "0.68",
     "DaysLow": "33.90",
     "DaysHigh": "34.28",
     "YearLow": "26.26",
     "YearHigh": "32.52",
     "HoldingsGainPercent": "- - -",
     "AnnualizedGain": null,
     "HoldingsGain": null,
     "HoldingsGainPercentRealtime": "N/A - N/A",
     "HoldingsGainRealtime": null,
     "MoreInfo": "cn",
     "OrderBookRealtime": null,
     "MarketCapitalization": "286.2B",
     "MarketCapRealtime": null,
     "EBITDA": "30.493B",
     "ChangeFromYearLow": "+8.009",
     "PercentChangeFromYearLow": "+30.50%",
     "LastTradeRealtimeWithTime": "N/A - <b>34.269</b>",
     "ChangePercentRealtime": "N/A - +0.35%",
     "ChangeFromYearHigh": "+1.749",
     "PercebtChangeFromYearHigh": "+5.38%",
     "LastTradeWithTime": "May 24 - <b>34.269</b>",
     "LastTradePriceOnly": "34.269",
     "HighLimit": null,
     "LowLimit": null,
     "DaysRange": "33.90 - 34.28",
     "DaysRangeRealtime": "N/A - N/A",
     "FiftydayMovingAverage": "28.6342",
     "TwoHundreddayMovingAverage": "27.9396",
     "ChangeFromTwoHundreddayMovingAverage": "+6.3294",
     "PercentChangeFromTwoHundreddayMovingAverage": "+22.65%",
     "ChangeFromFiftydayMovingAverage": "+5.6348",
     "PercentChangeFromFiftydayMovingAverage": "+19.68%",
     "Name": "Microsoft Corpora",
     "Notes": null,
     "Open": "33.92",
     "PreviousClose": "34.15",
     "PricePaid": null,
     "ChangeinPercent": "+0.35%",
     "PriceSales": "3.75",
     "PriceBook": "3.72",
     "ExDividendDate": "Feb 19",
     "PERatio": "17.62",
     "DividendPayDate": "Jun 13",
     "PERatioRealtime": null,
     "PEGRatio": "1.45",
     "PriceEPSEstimateCurrentYear": "12.37",
     "PriceEPSEstimateNextYear": "11.16",
     "Symbol": "MSFT",
     "SharesOwned": null,
     "ShortRatio": "1.50",
     "LastTradeTime": "4:00pm",
     "TickerTrend": " ====== ",
     "OneyrTargetPrice": "33.64",
     "Volume": "33175804",
     "HoldingsValue": null,
     "HoldingsValueRealtime": null,
     "YearRange": "26.26 - 32.52",
     "DaysValueChange": "- - +0.35%",
     "DaysValueChangeRealtime": "N/A - N/A",
     "StockExchange": "NasdaqNM",
     "DividendYield": "2.52",
     "PercentChange": "+0.35%"
    }
   ]
  }
 }
} 
	   
	 */
	public GetStockPrice(String[] StockArr){
		
		// StockArr holds a string array of tickerNames
		// sample uri = (\"GOOG\",\"YHOO\",\"AMZN\")
		
		for (String ticker : StockArr) 
			uri = uri.concat("\"" + ticker + "\"");
		
		uri = uri.concat(")"); 		// closeing bracket
		
		try {
			run();
		} catch (JSONException ex) {
			Logger.getLogger(Stock.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	public GetStockPrice() {
		try {
			run();
		} catch (JSONException ex) {
			Logger.getLogger(Stock.class.getName()).log(Level.SEVERE, null, ex);
		}
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
	
	@Override
	public void run() throws JSONException {
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