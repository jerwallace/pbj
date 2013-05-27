package stocktracker.server;

import java.sql.Time;
import java.util.HashMap;
import java.util.Map;

public class StockList
{

    private Map<String, Stock> stocksTable;
    private Time timeStamp;

    public StockList()
    {
        this.stocksTable = new HashMap<String, Stock>();
    }

    public Time getTimeStamp()
    {
        return timeStamp;
    }

    public void updateTimeStamp(Time timeStamp)
    {
        this.timeStamp = timeStamp;
    }

    public void addNewStock(String name, Stock stock)
    {
        this.stocksTable.put(name, stock);
    }

    public void updateStockPrice(String name, Stock stock)
    {
        this.stocksTable.put(name, stock);
    }

    public Double getStockPrice(String stockName)
    {
//        if (stocksTable.containsKey(stockName))
//        {
        return this.stocksTable.get(stockName).getPrice();
//        }
        //else check and see if stock is valid, get price and add to stocktable
    }

    @Override
    public String toString()
    {
        String mapString = "";
        mapString = "Stock Name" + "\t" + "Stock Value\n";
        for (Map.Entry<String, Stock> entry : stocksTable.entrySet())
        {
            mapString += entry.getKey() + "\t\t" + entry.getValue();
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
