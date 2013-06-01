package stocktracker.api;

import java.sql.Time;
import java.util.HashMap;
import java.util.Map;
import stocktracker.server.OnlineStockInfo;

/**
 * Class describing the Singleton StockList
 */
public class StockList
{

    private Map<String, Stock> stocksTable;
    private static StockList currentStockList = null;

    /**
     * Singleton class constructor
     */
    protected StockList()
    {
        stocksTable = new HashMap<>();
    }

    /**
     * Returns the only userList instance object
     */
    public static StockList getInstance()
    {

        if (currentStockList == null)
        {

            synchronized (StockList.class)
            {

                StockList inst = currentStockList;

                if (inst == null)
                {

                    synchronized (StockList.class)
                    {
                        currentStockList = new StockList();
                    }
                }
            }
        }

        return currentStockList;
    }

    /**
     * Public method that updates the information of a Stock object inside the
     * StockList
     * <p/>
     * @param stock - Stock Object
     */
    public void updateStock(Stock stock)
    {
        this.stocksTable.put(stock.getTickerName(), stock);
    }

    /**
     * Public method that removes a Stock Object from the StockList
     * <p/>
     * @param stock - Stock Object
     */
    public void removeStock(Stock stock)
    {
        this.stocksTable.remove(stock.getTickerName());
    }

    /**
     * Public method that returns the Stock Object corresponding to a stock
     * using its tickerName
     * <p/>
     * @param tickerName <p/>
     * @return Stock Object
     */
    public Stock getStockByTickerName(String tickerName)
    {
        if (stocksTable.containsKey(tickerName))
        {
            return this.stocksTable.get(tickerName);
        }
        else
        {
            Stock newStock = OnlineStockInfo.getLatestStockInfo(new Stock(tickerName, 0));
            if (newStock != null)
            {
                stocksTable.put(tickerName, newStock);
            }
            return newStock;
        }
    }

    /**
     * Public method that returns the overall Volume of stocks available using
     * the tickerName of a Stock Object
     * <p/>
     * @param tickerName <p/>
     * @return - Integer Volume of Stock
     */
    public int getNumStocks(String tickerName)
    {
        return this.stocksTable.get(tickerName).getVolume();
    }

    /**
     * Overriding the toString() method so that it returns a String containing
     * both the tickerName and the stock Price when it is called
     */
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

    /**
     * Public method that returns the number of all the Stocks that are being
     * currently tracked by the Server
     * <p/>
     * @return - Integer total number of stocks being tracked
     */
    public int getNumAllStocksTracked()
    {
        return this.stocksTable.size();
    }

    /**
     * Public method that returns a string containing the tickerName of all the
     * stocks that are being tracked
     * <p/>
     * @return - String containing all tickerNames being tracked
     */
    public String getTickerNameAllStocksTracked()
    {
        String mapString = "";
        boolean firstStock = true;

        for (Map.Entry<String, Stock> entry : this.stocksTable.entrySet())
        {

            mapString += entry.getValue().getTickerName() + ",";

        }
        return mapString.substring(0, mapString.length() - 1);
    }
}
