package stocktracker.api;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import stocktracker.client.protocol.AbstractProtocol.State;

/**
 *
 * @author Bahman
 */
public class User
{
    private String userName;
    private double balance;
    private Map<String, Integer> stocksOwned;

    public User(String uName, double balance)
    {
        setUserName(uName);
        setBalance(balance);
        stocksOwned = new HashMap<>();
    }

    public String getUserName()
    {
        return this.userName;
    }

    public void setUserName(String userName)
    {
        
        this.userName = userName;
    }

    public double getBalance()
    {
        
        return this.balance;
    }
    
    public String getBalanceString() {
        DecimalFormat dec = new DecimalFormat("#.00 USD");
        return "$"+dec.format(balance);
    }
    
    public void setBalance(double balance)
    {
        this.balance = balance;
    }
    
    public int numStocksOwned(String tickerName) {
        try {
            return this.stocksOwned.get(tickerName);
        } catch (NullPointerException npex) {
            return 0;
        }
    }
    
    public void removeUserStock(String tickerName) {
       this.stocksOwned.remove(tickerName);
    }
    
    public void updateUserStock(String tickerName, int numStocks) {
       this.stocksOwned.put(tickerName, numStocks);
    }
    
    public Map<String, Integer> getStocksOwned()
    {
        return this.stocksOwned;
    }

    public String printStocksOwned()
    {
        String mapString = "Stock Name" + "\t" + "Number of Stocks \n"; 
        //+ "\t" + "Current Price " + "\t" + "Total Value";
        for (Map.Entry<String, Integer> entry : this.stocksOwned.entrySet())
        {
            mapString += entry.getKey() + "\t\t" + entry.getValue() + "\n";
        }
        return mapString;
    }

}
