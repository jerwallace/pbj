package stocktracker.api;

import java.sql.Time;
import java.util.HashMap;
import java.util.Map;
import stocktracker.server.OnlineStockInfo;

public class StockList
{

    private Map<String, Stock> stocksTable;
    private Time timeStamp;
    private static StockList currentStockList = null;

    protected StockList() 
    {
        stocksTable = new HashMap<>();
    }

    public static StockList getInstance() {
		
		if (currentStockList == null) {

			synchronized(StockList.class) {

				StockList inst = currentStockList;

				if (inst == null) {

					synchronized(StockList.class) {
						currentStockList = new StockList();
					}
				}
			}
		}
                
		return currentStockList;
    }
    
    public Time getTimeStamp()
    {
        return timeStamp;
    }

    public void updateTimeStamp(Time timeStamp)
    {
        this.timeStamp = timeStamp;
    }

    public void updateStock(Stock stock)
    {
        this.stocksTable.put(stock.getTickerName(), stock);
    }

    public void removeStock(Stock stock)
    {
        this.stocksTable.remove(stock.getTickerName());
    }

    public Stock getStockByTickerName(String tickerName)
    {
        if (stocksTable.containsKey(tickerName))
        {
            return this.stocksTable.get(tickerName);
        }
        else
        {
            //try{
            Stock newStock = OnlineStockInfo.getLatestStockInfo(new Stock(tickerName,0));
            
            if (newStock != null)
                stocksTable.put(tickerName, newStock);
            
            return newStock;
            //}
            //catch invalidStockName isn()
//            {
//                return null;
//            }
            //return null;
        }
    }

    public int getNumStocks(String tickerName)
    {
        return this.stocksTable.get(tickerName).getVolume();
    }

    @Override
    public String toString()
    {
        String mapString = "";
        mapString = "Stock Name" + "\t" + "Stock Value \n";
        for (Map.Entry<String, Stock> entry : this.stocksTable.entrySet())
        {
            mapString += entry.getValue();
        }
        return mapString;
    }

    public void resetStockList()
    {
        this.stocksTable.clear();
    }

    public int getNumStocks()
    {
        return this.stocksTable.size();
    }
    
    public String getCSVStocks() {
        String mapString = "";
        boolean firstStock = true;
        
        for (Map.Entry<String, Stock> entry : this.stocksTable.entrySet())
        {
            
            mapString += entry.getValue().getTickerName()+",";
            
        }
        return mapString.substring(0,mapString.length()-1);
    }
}
