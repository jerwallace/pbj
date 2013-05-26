/**
 *
 * @author Bahman
 */
import java.util.Map;


public class User {

	private String firstName;
	private String lastName;
	private String userName;
	private double balance;
	private  Map<String, Integer>stocksOwned;
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public Map<String, Integer> getStocksOwned() {
		return stocksOwned;
	}
	public void addStock(String tickerName, Integer numStock)
	{
		if(this.stocksOwned.isEmpty())
		{
			this.stocksOwned.put(tickerName, numStock);
		}
		else {
			if(this.stocksOwned.containsKey(tickerName))
			{
				int currentNumStock = this.stocksOwned.get(tickerName);
				this.stocksOwned.remove(tickerName);
				this.stocksOwned.put(tickerName, (numStock + currentNumStock));
			}
		}
	}
}
