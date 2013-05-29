package stocktracker.api;

import java.sql.Time;
import java.util.HashMap;
import java.util.Map;
import stocktracker.server.OnlineStockInfo;

public class StockList
{

    private Map<String, Stock> stocksTable;
    private Time timeStamp;

    public StockList()
    {
        stocksTable = new HashMap<>();
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
            Stock newStock = OnlineStockInfo.getLatestStockInfo(new Stock(tickerName,0,0));
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
        mapString = "Stock Name" + "\t" + "Stock Value";
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

    public int getNumDiffStocks()
    {
        return this.stocksTable.size();
    }
}
