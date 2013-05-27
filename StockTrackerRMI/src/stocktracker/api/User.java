package stocktracker.api;

/**
 *
 * @author Bahman
 */

public class User
{

    private String userName;
    private double balance;
    private StockList stocksOwned;

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

    public StockList getStocksOwned()
    {
        return this.stocksOwned;
    }

}
