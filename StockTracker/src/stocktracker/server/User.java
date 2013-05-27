package stocktracker.server;

/**
 *
 * @author Bahman
 */
import java.util.HashMap;
import java.util.Map;

public class User
{

    private String userName;
    private double balance;
    private Map<Stock, Integer> stocksOwned;

    public User(String uName, double Balance)
    {
        this.userName = uName;
        this.balance = Balance;
    }

    public String getUserName()
    {
        this.stocksOwned = new HashMap<Stock, Integer>();
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

    public void setBalance(double balance)
    {
        this.balance = balance;
    }

    public Map<Stock, Integer> getStocksOwned()
    {
        return this.stocksOwned;
    }

    public void addStock(Stock stock, Integer numStock)
    {
        this.stocksOwned.put(stock, numStock);
    }

    public String getStockList()
    {
        String myString = "";
        myString = "Stock Name" + "\t" + "Stock Value\n";
        for (Map.Entry<Stock, Integer> entry : stocksOwned.entrySet())
        {
            myString += entry.getKey().getTickerName() + "\t\t" + entry.getValue();
        }
        return myString;
    }
}
