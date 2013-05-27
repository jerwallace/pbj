package stocktracker.api;

import java.sql.Time;
import java.util.HashMap;
import java.util.Map;

public class StockList
{

    private Map<String, Stock> stocksTable;
    private Map<String, Integer> numStocks;
    
    private Time timeStamp;
    
    public StockList() {
        stocksTable = new HashMap<>();
        numStocks = new HashMap<>();
    }

    public Time getTimeStamp()
    {
        return timeStamp;
    }

    public void updateTimeStamp(Time timeStamp)
    {
        this.timeStamp = timeStamp;
    }

    public void updateStock(Stock stock, int numStocks)
    {
        this.stocksTable.put(stock.getTickerName(), stock);
        this.numStocks.put(stock.getTickerName(), numStocks);
    }
    
    public void removeStock(Stock stock) {
        this.stocksTable.remove(stock.getTickerName());
        this.numStocks.remove(stock.getTickerName());
    }

    public Stock getStockByTickerName(String tickerName) 
    {
        return this.stocksTable.get(tickerName);
    }
    
    public int getNumStocks(String tickerName) 
    {
        return this.numStocks.get(tickerName);
    }

    @Override
    public String toString()
    {
        String mapString = "";
        mapString = "Stock Name" + "\t" + "Stock Value"+ "\t"+ "Num Stocks"+"\t"+ "Total\n";
        for (Map.Entry<String, Stock> entry : this.stocksTable.entrySet())
        {
            Stock thisStock = entry.getValue();
            int numStocksOwned = numStocks.get(thisStock.getTickerName());
            
            mapString += thisStock+"\t\t"+numStocksOwned+"\t\t"+(numStocksOwned*thisStock.getPrice())+"\n";
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
