package stocktracker.api;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Class describing a User object
 */
public class User implements Serializable
{

    private String userName;
    private double balance;
    private Map<String, Integer> stocksOwned;

    /**
     * Default public constructor for the User class
     * <p/>
     * @param uName   - String User Name
     * @param balance - Double User Balance
     */
    public User(String uName, double balance)
    {
        setUserName(uName);
        setBalance(balance);
        stocksOwned = new HashMap<>();
    }

    /**
     * Public getter returns userName
     * <p/>
     * @return - String userName
     */
    public String getUserName()
    {
        return this.userName;
    }

    /**
     * Public setter updates userName
     * <p/>
     * @param userName - String userName
     */
    public void setUserName(String userName)
    {

        this.userName = userName;
    }

    /**
     * PPublic getter returns user balance
     * <p/>
     * @return - Double userBalance
     */
    public double getBalance()
    {

        return this.balance;
    }

    /**
     * Public getter returns user balance as String
     * <p/>
     * @return - String userBalance
     */
    public String getBalanceString()
    {
        DecimalFormat dec = new DecimalFormat("#.00 USD");
        return "$" + dec.format(balance);
    }

    /**
     * Public setter updates user Balance
     * <p/>
     * @param balance - Double balance
     */
    public void setBalance(double balance)
    {
        this.balance = balance;
    }

    /**
     * Public method returns the number of stocks this user owns using the
     * tickerName
     * <p/>
     * @param tickerName - String tickerName
     * <p/>
     * @return - int numStockOwned
     */
    public int numStocksOwned(String tickerName)
    {
        try
        {
            return this.stocksOwned.get(tickerName);
        }
        catch (NullPointerException npex)
        {
            return 0;
        }
    }

    /**
     * Public method that removes a stock from list of user stockOwned using the
     * tickerName
     * <p/>
     * @param tickerName - String tickerName
     */
    public void removeUserStock(String tickerName)
    {
        this.stocksOwned.remove(tickerName);
    }

    /**
     * Public method updating number of stocksOwned of a particular stock for
     * this user, using tickerName and new numStocks
     * <p/>
     * @param tickerName - String tickerNmae
     * @param numStocks  - Integer numStocks
     */
    public void updateUserStock(String tickerName, int numStocks)
    {
        this.stocksOwned.put(tickerName, numStocks);
    }

    /**
     * Public method that returns a Map of all stocksOwned by this user. The key
     * is tickerName of each stock and value is numStocksOwned
     * <p/>
     * @return - Map<String tickerName, Integer numStocksOwned>
     */
    public Map<String, Integer> getStocksOwned()
    {
        return this.stocksOwned;
    }

    /**
     * Public method that returns the list of all stocksOwned by this user to be
     * printed to the screen
     * <p/>
     * @return - String printStocksOwned
     */
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
