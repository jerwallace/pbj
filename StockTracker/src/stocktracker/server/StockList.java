package stocktracker.server;

import java.sql.Time;
import java.util.Map;

public class StockList
{

    private Map<String, Integer> stocksTable;
    private Time timeStamp;

    public Time getTimeStamp()
    {
        return timeStamp;
    }

    public void updateTimeStamp(Time timeStamp)
    {
        this.timeStamp = timeStamp;
    }

    public void addNewStock(String name, Integer price)
    {
        this.stocksTable.put(name, price);
    }

    public void updateStockPrice(String name, Integer price)
    {
        this.stocksTable.put(name, price);
    }

    public Integer getStockPrice(String stockName)
    {
        return this.stocksTable.get(stockName);
    }

    @Override
    public String toString()
    {
        String mapString = "";
        mapString = "Stock Name" + "\t" + "Stock Value\n";
        for (Map.Entry<String, Integer> entry : this.stocksTable.entrySet())
        {
            mapString += entry.getKey() + "\t\t"
                    + Integer.toString(entry.getValue());
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
