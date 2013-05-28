package stocktracker.api;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Bahman
 */
public class User
{

    private String userName;
    private double balance;
    private Map<String, Integer> stocksOwned;

    public User(String uName, double Balance)
    {
        this.userName = uName;
        this.balance = Balance;
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

    public void setBalance(double balance)
    {
        this.balance = balance;
    }

    public Map<String, Integer> getStocksOwned()
    {
        return this.stocksOwned;
    }

    public String printStocksOwned()
    {
        String mapString = "";
        for (Map.Entry<String, Integer> entry : this.stocksOwned.entrySet())
        {
            mapString += entry.getKey() + "\t\t" + entry.getValue() + "\n";
        }
        return mapString;
    }
}
