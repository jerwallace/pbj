package stocktracker.server;

/**
 *
 * @author Bahman
 */
import java.util.Map;

public class User
{

    private String userName;
    private double balance;
    private Map<String, Integer> stocksOwned;

    public User(String uName, double Balance)
    {
        this.userName = uName;
        this.balance = Balance;
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

    public void setBalance(double balance)
    {
        this.balance = balance;
    }

    public Map<String, Integer> getStocksOwned()
    {
        return this.stocksOwned;
    }

    public void addStock(String tickerName, Integer numStock)
    {
        if (this.stocksOwned.isEmpty())
        {
            this.stocksOwned.put(tickerName, numStock);
        }
        else
        {
            if (this.stocksOwned.containsKey(tickerName))
            {
                int currentNumStock = this.stocksOwned.get(tickerName);
                this.stocksOwned.remove(tickerName);
                this.stocksOwned.put(tickerName, (numStock + currentNumStock));
            }
        }
    }

    public String getStockList()
    {
        String myString = "";
        myString = "Stock Name" + "\t" + "Stock Value\n";
        for (Map.Entry<String, Integer> entry : stocksOwned.entrySet())
        {
            myString += entry.getKey() + "\t\t" + entry.getValue();
        }
        return myString;
    }
}
